package net.ari.risinggraves.barrier;

import net.minecraft.core.BlockPos;
import java.util.List;

public class BlockadeCluster {
    public final List<BlockPos> blocks;
    public final int cost;

    public BlockadeCluster(List<BlockPos> blocks, int cost) {
        this.blocks = blocks;
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }

    public List<BlockPos> getBlocks() {
        return blocks;
    }

}
