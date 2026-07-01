package net.ari.risinggraves.waves;

import java.util.HashMap;

import net.minecraft.world.level.Level;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.ari.risinggraves.zombies.CZombieDeathEvent;
import net.ari.risinggraves.scoreboard.ScoreboardHandler;
import net.minecraft.server.level.ServerPlayer;
import net.ari.risinggraves.scoreboard.SidebarScoreboard;
import net.ari.risinggraves.zombies.TankZombie;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.network.chat.Component;
import java.util.Map;
import java.util.UUID;
import java.util.HashMap;
import net.minecraft.world.level.Level;
import net.minecraft.network.protocol.game.ClientboundStopSoundPacket;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.GameType;
import net.minecraft.server.level.ServerLevel;

import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;





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
            p.connection.send(new ClientboundStopSoundPacket(null, SoundSource.MUSIC));
            p.connection.send(new ClientboundStopSoundPacket(null, SoundSource.AMBIENT));
            p.connection.send(new ClientboundStopSoundPacket(null, SoundSource.WEATHER));

        }


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
        return round % 6 == 0; // every 6 rounds
    }

    @SubscribeEvent
    public static void onPlayerDeath(LivingDeathEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        if (!WaveManager.wavesActive) return;

        WaveManager.isOutThisRound.put(player.getUUID(), true);

        // Prevent normal respawn
        player.setRespawnPosition(player.level.dimension(), player.blockPosition(), 0, true, false);

        // Put them in spectator mode
        player.setGameMode(GameType.SPECTATOR);
    }

    public static void revivePlayers() {
        for (ServerPlayer player : currentLevel.getServer().getPlayerList().getPlayers()) {
            if (isOutThisRound.getOrDefault(player.getUUID(), false)) {

                // Convert Level -> ServerLevel
                ServerLevel serverLevel = (ServerLevel) currentLevel;

                // Respawn them at spawn or a custom location
                BlockPos spawn = serverLevel.getSharedSpawnPos();
                player.teleportTo(serverLevel, spawn.getX(), spawn.getY(), spawn.getZ(), 0, 0);

                // Put them back in survival
                player.setGameMode(GameType.SURVIVAL);

                // Clear the flag
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

        // Hide scoreboard for all players
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
        return round / 6;
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
                        null, // null = send to all players
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

            // small delay between each warning noise
            try { Thread.sleep(300); } catch (InterruptedException ignored) {}
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
