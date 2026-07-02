package net.ari.risinggraves.waves;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.Map;
import java.util.UUID;
import java.util.HashMap;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

import net.minecraft.core.BlockPos;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;

import net.minecraft.network.protocol.game.ClientboundStopSoundPacket;
import net.minecraft.network.chat.Component;


import net.ari.risinggraves.item.ModItems;
import net.ari.risinggraves.zombies.CZombieDeathEvent;
import net.ari.risinggraves.scoreboard.ScoreboardHandler;
import net.ari.risinggraves.scoreboard.SidebarScoreboard;
import net.ari.risinggraves.zombies.TankZombie;


public class WaveManager {

    private static final ScheduledExecutorService executor =
        Executors.newSingleThreadScheduledExecutor();

    private static int currentWave = 1;

    public static final Map<UUID, Boolean> isOutThisRound = new HashMap<>();

    private static int zombiesLeftToSpawn = 0;
    private static int zombiesAlive = 0;
    private static int maxActiveZombies = 8;
    public static int bossCountThisWave = 0;

    private static int roundSoundDelay = -1;

	public static boolean wavesActive = false;
    public static boolean spawnTankThisWave = false;


    private static boolean waveInProgress = false;
    private static Level currentLevel;

	public static int getCurrentWave() { return currentWave; }
	public static int getZombiesAlive() { return zombiesAlive; }
	public static int getZombiesLeftToSpawn() { return zombiesLeftToSpawn; }


    public static void startWaves(Level level) {
        wavesActive = true;
        currentLevel = level;
        currentWave = 1;
        
        for (ServerPlayer p : level.getServer().getPlayerList().getPlayers()) {

        // Stop ALL music tracks
        p.connection.send(new ClientboundStopSoundPacket(SoundEvents.MUSIC_MENU.get().getLocation(), SoundSource.MUSIC));
        p.connection.send(new ClientboundStopSoundPacket(SoundEvents.MUSIC_CREATIVE.get().getLocation(), SoundSource.MUSIC));
        p.connection.send(new ClientboundStopSoundPacket(SoundEvents.MUSIC_GAME.get().getLocation(), SoundSource.MUSIC));
        p.connection.send(new ClientboundStopSoundPacket(SoundEvents.MUSIC_UNDER_WATER.get().getLocation(), SoundSource.MUSIC));
        p.connection.send(new ClientboundStopSoundPacket(SoundEvents.MUSIC_END.get().getLocation(), SoundSource.MUSIC));
        p.connection.send(new ClientboundStopSoundPacket(SoundEvents.MUSIC_DRAGON.get().getLocation(), SoundSource.MUSIC));
        p.connection.send(new ClientboundStopSoundPacket(SoundEvents.MUSIC_CREDITS.get().getLocation(), SoundSource.MUSIC));

        // Ambient + weather
        p.connection.send(new ClientboundStopSoundPacket(null, SoundSource.AMBIENT));
        p.connection.send(new ClientboundStopSoundPacket(null, SoundSource.WEATHER));
        }

         zombiesAlive = 0;
        zombiesLeftToSpawn = 0;
        bossCountThisWave = 0;
        spawnTankThisWave = false;
        waveInProgress = false;


        SidebarScoreboard.init(level.getServer());
        // Show scoreboard for all players
        for (ServerPlayer player : level.getServer().getPlayerList().getPlayers()) {
            int points = ScoreboardHandler.INSTANCE.getPoints(player.getName().getString());
            SidebarScoreboard.show(player);
            SidebarScoreboard.update(player, points);
        }

    startWave();
    }

    public static boolean shouldSpawnBoss(int round) {
        return round % 6 == 0;
    }

    @SubscribeEvent
    public static void onPlayerDeath(LivingDeathEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        if (!WaveManager.wavesActive) return;

        WaveManager.isOutThisRound.put(player.getUUID(), true);

        player.setRespawnPosition(player.level.dimension(), player.blockPosition(), 0, true, false);

        player.setGameMode(GameType.SPECTATOR);
    }

    public static void revivePlayers() {
        for (ServerPlayer player : currentLevel.getServer().getPlayerList().getPlayers()) {
            if (isOutThisRound.getOrDefault(player.getUUID(), false)) {
 
                ServerLevel serverLevel = (ServerLevel) currentLevel;

                BlockPos spawn = serverLevel.getSharedSpawnPos();
                player.teleportTo(serverLevel, spawn.getX(), spawn.getY(), spawn.getZ(), 0, 0);

                
				ItemStack drop;
				int pick = player.level.random.nextInt(3);

				switch (pick) {
					case 0 -> drop = new ItemStack(ModItems.SAPPHIRE_SHARD.get());
					case 1 -> drop = new ItemStack(ModItems.AMETHYST_SHARD.get());
					case 2 -> drop = new ItemStack(ModItems.RUBY_SHARD.get());
					default -> drop = new ItemStack(ModItems.CITRINE_SHARD.get());
				}
				
				ItemStack starter = new ItemStack(Items.WOODEN_PICKAXE);
				starter.enchant(Enchantments.UNBREAKING, 3);
				starter.setHoverName(Component.literal("The beginning of your end."));

				drop.enchant(Enchantments.SHARPNESS, 1);
				drop.setCount(1);
				drop.setDamageValue(1);

				player.getInventory().add(drop);
				player.getInventory().add(starter);

                player.setGameMode(GameType.SURVIVAL);

                isOutThisRound.put(player.getUUID(), false);
            }
        }
    }


    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (WaveManager.wavesActive) {
            ServerPlayer sp = (ServerPlayer) event.getEntity();
            sp.setGameMode(GameType.SPECTATOR);
        }
    }



	public static void stopWaves() {
        wavesActive = false;
        waveInProgress = false;
        zombiesAlive = 0;
        zombiesLeftToSpawn = 0;

        for (ServerPlayer player : currentLevel.getServer().getPlayerList().getPlayers()) {
            SidebarScoreboard.clear(player);
        }
    }


    private static void startWave() {
        waveInProgress = true;

        bossCountThisWave = getBossCountForRound(currentWave);

        if (bossCountThisWave > 0) {
            playBossWarningSound(bossCountThisWave);
        }

        int totalZombies = (int)(Math.pow(currentWave, 2) * 0.5 + 5);

        zombiesLeftToSpawn = totalZombies;
        zombiesAlive = 0;

        System.out.println(
            "Starting wave " + currentWave +
            " with " + zombiesLeftToSpawn +
            " zombies (max active " + maxActiveZombies + ")" +
            " total zombies " + totalZombies +
            " zombies alive " + zombiesAlive +
            " and " + bossCountThisWave + " boss zombies."
        );
    }



    public static boolean isWaveInProgress() {
        return waveInProgress;
    }

    public static boolean canSpawnMore() {
        return zombiesLeftToSpawn > 0 && zombiesAlive < maxActiveZombies;
    }

    public static void onZombieSpawned() {
        zombiesLeftToSpawn--;
        zombiesAlive++;

        System.out.println(
            "[WaveManager] Zombie spawned | leftToSpawn=" + zombiesLeftToSpawn +
            " | alive=" + zombiesAlive +
            " | wave=" + currentWave
        );
    }

    public static void onZombieDefeated() {
        zombiesAlive--;

        if (zombiesAlive <= 0 && zombiesLeftToSpawn <= 0) {
			if (!wavesActive) return;
            revivePlayers();
            startNextWave();
        }
    }

    public static boolean shouldSpawnTank() {
        return spawnTankThisWave;
    }

    public static int getBossCountForRound(int round) {
        return (round % 6 == 0) ? (round / 6) : 0;
    }

	@SubscribeEvent
	public static void onCZombieDeath(CZombieDeathEvent.Event event) {
		if (!wavesActive) return;
		onZombieDefeated();
	}

    @SubscribeEvent
    public static void onServerTick(net.minecraftforge.event.TickEvent.ServerTickEvent event) {
        if (roundSoundDelay > 0) {
            roundSoundDelay--;

            if (roundSoundDelay == 0) {
                for (ServerPlayer p : currentLevel.getServer().getPlayerList().getPlayers()) {
                    currentLevel.playSound(
                        null,
                        p.blockPosition(),
                        net.minecraft.sounds.SoundEvents.WITHER_SPAWN,
                        net.minecraft.sounds.SoundSource.PLAYERS,
                        1.0F,
                        1.2F
                    );

                }
            }
        }
    }

    private static void playBossWarningSound(int count) {
        for (int i = 0; i < count; i++) {
            for (ServerPlayer player : currentLevel.getServer().getPlayerList().getPlayers()) {
                currentLevel.playSound(
                    null,
                    player.getX(),
                    player.getY(),
                    player.getZ(),
                    net.minecraft.sounds.SoundEvents.WITHER_AMBIENT,
                    net.minecraft.sounds.SoundSource.HOSTILE,
                    1.0F,
                    0.85F
                );
            }

            try { Thread.sleep(1500); } catch (InterruptedException ignored) {}
        }
    }



    private static void startNextWave() {
    waveInProgress = false;

    System.out.println("Wave " + currentWave + " complete! Next wave in 10 seconds.");


    roundSoundDelay = 200;

    executor.schedule(() -> {
        if (!wavesActive) return;

        currentWave++;

        spawnTankThisWave = shouldSpawnBoss(currentWave);
        maxActiveZombies = Math.min(24, 6 + currentWave);

        for (ServerPlayer p : currentLevel.getServer().getPlayerList().getPlayers()) {
            p.displayClientMessage(
                Component.literal("§6Wave " + currentWave + " begins!"),
                true
            );
        }

        startWave();
    }, 10, TimeUnit.SECONDS);

    }
}
