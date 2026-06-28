package net.ari.risinggraves.waves;

import net.minecraft.world.level.Level;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.ari.risinggraves.zombies.CZombieDeathEvent;


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
        startWave();
    }

	public static void stopWaves() {
		wavesActive = false;
		waveInProgress = false;
		zombiesAlive = 0;
		zombiesLeftToSpawn = 0;
	}

    private static void startWave() {
        waveInProgress = true;

        zombiesLeftToSpawn = 7 + (currentWave - 1) * 2;
        zombiesAlive = 0;

        System.out.println("Starting wave " + currentWave + " with " + zombiesLeftToSpawn + " zombies.");
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

		System.out.println("Wave " + currentWave + " complete! Next wave in 5 seconds.");

		Executors.newSingleThreadScheduledExecutor()
			.schedule(() -> {
				if (!wavesActive) return;
				currentWave++;          // increment HERE, not before
				maxActiveZombies++;     // pacing increase
				startWave();            // now start the wave
			}, 5, TimeUnit.SECONDS);
		}

}
