package net.ari.risinggraves.barrier;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.BlockPos;

import java.util.HashSet;
import java.util.Set;

public class DoorData extends SavedData {

    private final Set<BlockPos> purchasedDoors = new HashSet<>();

    public static DoorData get(Level level) {
        return level.getServer().overworld().getDataStorage().computeIfAbsent(
            DoorData::load,
            DoorData::new,
            "risinggraves_doors"
        );
    }

    public boolean isDoorPurchased(BlockPos pos) {
        return purchasedDoors.contains(pos);
    }

    public void markPurchased(BlockPos pos) {
        purchasedDoors.add(pos);
        setDirty();
    }

    public static DoorData load(CompoundTag tag) {
        DoorData data = new DoorData();

        var list = tag.getList("doors", 10); // 10 = compound
        for (int i = 0; i < list.size(); i++) {
            CompoundTag entry = list.getCompound(i);
            BlockPos pos = new BlockPos(
                entry.getInt("x"),
                entry.getInt("y"),
                entry.getInt("z")
            );
            data.purchasedDoors.add(pos);
        }

        return data;
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        var list = new net.minecraft.nbt.ListTag();

        for (BlockPos pos : purchasedDoors) {
            CompoundTag entry = new CompoundTag();
            entry.putInt("x", pos.getX());
            entry.putInt("y", pos.getY());
            entry.putInt("z", pos.getZ());
            list.add(entry);
        }

        tag.put("doors", list);
        return tag;
    }
}
