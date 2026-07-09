package net.ari.risinggraves.block;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.core.BlockPos;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraft.server.level.ServerLevel;

import net.minecraft.nbt.CompoundTag;


import net.ari.risinggraves.barrier.DoorData;
import net.ari.risinggraves.init.ModEntities;
import net.ari.risinggraves.waves.WaveManager;


public class CustomSpawnerBlockEntity extends BlockEntity {

    private int cooldown = 0;
    private BlockPos linkedDoor = null;
    private int spawnRound = 0;



    public CustomSpawnerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CUSTOM_SPAWNER.get(), pos, state);
    }

    public void setLinkedDoor(BlockPos pos) {
        this.linkedDoor = pos;
    }

    public int getSpawnRound() {
        return spawnRound;
    }

    public BlockPos getLinkedDoor() {
        return linkedDoor;
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);

        if (linkedDoor != null) {
            tag.putInt("doorX", linkedDoor.getX());
            tag.putInt("doorY", linkedDoor.getY());
            tag.putInt("doorZ", linkedDoor.getZ());
        }
    }

    private void trySpawn() {

        if (linkedDoor != null && !DoorData.get(level).isDoorPurchased(linkedDoor)) return;
        if (!WaveManager.isWaveInProgress()) return;
        if (!WaveManager.canSpawnMore()) return;

        if (WaveManager.spawnTankThisWave && WaveManager.bossCountThisWave > 0) {
            spawnTankZombie(level, worldPosition);
            WaveManager.onZombieSpawned();
            WaveManager.bossCountThisWave--;
            cooldown = 40;
            return;
        }

        if (WaveManager.spawnFrostbiteThisWave && WaveManager.bossCountThisWave > 0) {
            spawnFrostbiteZombie(level, worldPosition);
            WaveManager.onZombieSpawned();
            WaveManager.bossCountThisWave--;
            cooldown = 40;
            return;
        }


        spawnCZombie(level, worldPosition);
        cooldown = 20;
    }


    public void tickServer() {
        if (cooldown > 0) {
            cooldown--;
        } else {
            trySpawn();
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);

        if (tag.contains("doorX")) {
            int x = tag.getInt("doorX");
            int y = tag.getInt("doorY");
            int z = tag.getInt("doorZ");
            this.linkedDoor = new BlockPos(x, y, z);
        }
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

    private void spawnFrostbiteZombie(Level level, BlockPos pos) {
        if (!(level instanceof ServerLevel server)) return;

        var frostbite = ModEntities.FROSTBITE_ZOMBIE.get().create(server);
        if (frostbite == null) return;

        frostbite.moveTo(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5,
                    level.random.nextFloat() * 360F, 0);

        server.addFreshEntity(frostbite);
    }

}
