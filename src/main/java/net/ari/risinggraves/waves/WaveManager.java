package net.ari.risinggraves.waves;

import net.minecraft.world.level.Level;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.ari.risinggraves.zombies.CZombieDeathEvent;
import net.ari.risinggraves.scoreboard.ScoreboardHandler;
import net.minecraft.server.level.ServerPlayer;
import net.ari.risinggraves.scoreboard.SidebarScoreboard;





public class WaveManager {

    private static int currentWave = 1;

    private static int zombiesLeftToSpawn = 0;
    private static int zombiesAlive = 0;
    private static int maxActiveZombies = 8;
	public static boolean wavesActive = false;

    private static boolean waveInProgress = false;
    private static Level currentLevel;

	public static int getCurrentWave() { return currentWave; }
	public static int getZombiesAlive() { return zombiesAlive; }
	public static int getZombiesLeftToSpawn() { return zombiesLeftToSpawn; }


    public static void startWaves(Level level) {
        wavesActive = true;
        currentLevel = level;
        currentWave = 1;

        SidebarScoreboard.init(level.getServer());
        // Show scoreboard for all players
        for (ServerPlayer player : level.getServer().getPlayerList().getPlayers()) {
            int points = ScoreboardHandler.INSTANCE.getPoints(player.getName().getString());
            SidebarScoreboard.show(player);
            SidebarScoreboard.update(player, points);
        }

    startWave();
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

        // COD-style total zombies scaling
        int totalZombies = 6 + (int)Math.floor(Math.pow(1.35, currentWave));

        // Use your existing maxActiveZombies increment system
        // (you increase it in startNextWave)
        // So we DO NOT override it here.

        zombiesLeftToSpawn = totalZombies;
        zombiesAlive = 0;

        System.out.println(
            "Starting wave " + currentWave +
            " with " + zombiesLeftToSpawn +
            " zombies (max active " + maxActiveZombies + ")"
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
            startNextWave();
        }
    }

	@SubscribeEvent
	public static void onCZombieDeath(CZombieDeathEvent.Event event) {
		if (!wavesActive) return;
		onZombieDefeated();
	}

    private static void startNextWave() {
    waveInProgress = false;

    System.out.println("Wave " + currentWave + " complete! Next wave in 10 seconds.");

    Executors.newSingleThreadScheduledExecutor()
        .schedule(() -> {
            if (!wavesActive) return;

            currentWave++;          
            maxActiveZombies = Math.min(24, 6 + currentWave);

            // 🔥 ROUND START INDICATOR 🔥
            for (ServerPlayer p : currentLevel.getServer().getPlayerList().getPlayers()) {
                // ominous round stinger
                p.playSound(net.minecraft.sounds.SoundEvents.WITHER_SPAWN, 1.0F, 1.2F);

                // subtle action bar message
                p.displayClientMessage(
                    net.minecraft.network.chat.Component.literal("§6Wave " + currentWave + " begins!"),
                    true
                );
            }

            startWave();            
        }, 10, TimeUnit.SECONDS);
    }


}
