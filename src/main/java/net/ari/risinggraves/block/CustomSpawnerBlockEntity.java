package net.ari.risinggraves.block;

import net.ari.risinggraves.init.ModEntities;
import net.ari.risinggraves.waves.WaveManager;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.server.level.ServerLevel;

import java.util.ArrayList;
import java.util.List;

public class CustomSpawnerBlockEntity extends BlockEntity {

    private int cooldown = 0;

    // GLOBAL LIST OF ALL SPAWNERS
    public static final List<BlockPos> ALL_SPAWNERS = new ArrayList<>();

    public CustomSpawnerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CUSTOM_SPAWNER.get(), pos, state);
    }

    public void tickServer() {
        if (cooldown > 0) {
            cooldown--;
            return;
        }

        if (!WaveManager.isWaveInProgress()) return;
        if (!WaveManager.canSpawnMore()) return;
        if (WaveManager.shouldSpawnTank() && WaveManager.getZombiesAlive() == 0) {
            spawnTankZombie(level, worldPosition);
            WaveManager.onZombieSpawned();
            WaveManager.spawnTankThisWave = false;
            cooldown = 40;
            return;
        }


        spawnCZombie(level, worldPosition);
        cooldown = 20; // 1 second
    }


    @Override
    public void onLoad() {
        if (!level.isClientSide) {
            ALL_SPAWNERS.add(worldPosition);
        }
    }

    @Override
    public void setRemoved() {
        if (!level.isClientSide) {
            ALL_SPAWNERS.remove(worldPosition);
        }
        super.setRemoved();
    }

    private void spawnCZombie(Level level, BlockPos pos) {
        if (!(level instanceof ServerLevel server)) return;

        var zombie = ModEntities.CZOMBIE.get().create(server);
        if (zombie == null) return;

        zombie.moveTo(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, level.random.nextFloat() * 360F, 0);
        server.addFreshEntity(zombie);

        WaveManager.onZombieSpawned();
    }

    private void spawnTankZombie(Level level, BlockPos pos) {
        if (!(level instanceof ServerLevel server)) return;

        var tank = ModEntities.TANK_ZOMBIE.get().create(server);
        if (tank == null) return;

        tank.moveTo(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5,
                    level.random.nextFloat() * 360F, 0);

        server.addFreshEntity(tank);
    }


}
