package net.ari.risinggraves.barrier;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;


import java.util.ArrayList;
import java.util.List;


public class BlockadeData extends SavedData {

    public static BlockadeData CLIENT = new BlockadeData();
    private List<BlockadeCluster> clusters = new ArrayList<>();
   

    // REQUIRED default constructor
    public BlockadeData() {}

    public void addCluster(BlockadeCluster cluster) {
        clusters.add(cluster);
        setDirty();
    }

    public List<BlockadeCluster> getClusters() {
        return clusters;
    }

    private static final String ID = "risinggraves_blockades";

    public static BlockadeData get(Level level) {

        if (level.isClientSide()) {
            return CLIENT;
        }

        // SERVER SIDE — load real persistent data
        return level.getServer().overworld().getDataStorage().computeIfAbsent(
            BlockadeData::load,
            BlockadeData::new,
            ID
        );
    }

    public void setClusters(List<BlockadeCluster> clusters) {
        this.clusters = clusters;
    }

    public static BlockadeData load(CompoundTag tag) {
        BlockadeData data = new BlockadeData();

        ListTag list = tag.getList("clusters", Tag.TAG_COMPOUND);

        for (Tag t : list) {
            CompoundTag clusterTag = (CompoundTag) t;

            int cost = clusterTag.getInt("cost");

            ListTag blocksList = clusterTag.getList("blocks", Tag.TAG_COMPOUND);
            List<BlockPos> blocks = new ArrayList<>();

            for (Tag bt : blocksList) {
                CompoundTag b = (CompoundTag) bt;
                blocks.add(new BlockPos(b.getInt("x"), b.getInt("y"), b.getInt("z")));
            }

            data.clusters.add(new BlockadeCluster(blocks, cost));
        }

        return data;
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        ListTag list = new ListTag();

        for (BlockadeCluster cluster : clusters) {
            CompoundTag clusterTag = new CompoundTag();
            clusterTag.putInt("cost", cluster.cost);

            ListTag blocksList = new ListTag();
            for (BlockPos pos : cluster.blocks) {
                CompoundTag b = new CompoundTag();
                b.putInt("x", pos.getX());
                b.putInt("y", pos.getY());
                b.putInt("z", pos.getZ());
                blocksList.add(b);
            }

            clusterTag.put("blocks", blocksList);
            list.add(clusterTag);
        }

        tag.put("clusters", list);
        return tag;
    }

}

