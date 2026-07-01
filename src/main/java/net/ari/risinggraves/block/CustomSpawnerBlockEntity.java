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
import net.minecraft.nbt.CompoundTag;
import net.ari.risinggraves.barrier.DoorData;


import java.util.ArrayList;
import java.util.List;

public class CustomSpawnerBlockEntity extends BlockEntity {

    private int cooldown = 0;
    private BlockPos linkedDoor = null;


    // GLOBAL LIST OF ALL SPAWNERS
    public static final List<BlockPos> ALL_SPAWNERS = new ArrayList<>();

    public CustomSpawnerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CUSTOM_SPAWNER.get(), pos, state);
    }

    public void setLinkedDoor(BlockPos pos) {
        this.linkedDoor = pos;
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

    public void tickServer() {
        if (cooldown > 0) {
            cooldown--;
            return;
        }

        if (linkedDoor != null) {
            if (!DoorData.get(level).isDoorPurchased(linkedDoor)) {
                return; // spawner stays inactive
            }
        }

        if (cooldown > 0) {
            cooldown--;
            return;
        }

        if (!WaveManager.isWaveInProgress()) return;
        if (!WaveManager.canSpawnMore()) return;
        // Spawn boss zombies first, before normal zombies
        if (WaveManager.bossCountThisWave > 0 && WaveManager.getZombiesAlive() == 0) {

            spawnTankZombie(level, worldPosition);

            WaveManager.onZombieSpawned();
            WaveManager.bossCountThisWave--;

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
