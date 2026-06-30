package net.ari.risinggraves.barrier;

import net.minecraft.core.BlockPos;
import java.util.List;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import java.util.List;



public class BlockadeCluster {
    public final List<BlockPos> blocks;
    public final List<BlockState> states;
    public final int cost;

    public boolean purchased = false;


    public BlockadeCluster(List<BlockPos> blocks, List<BlockState> states, int cost) {
        this.blocks = blocks;
        this.states = states;
        this.cost = cost;
    }

    public List<BlockState> getStates() {
        return states;
    }


    public int getCost() {
        return cost;
    }

    public List<BlockPos> getBlocks() {
        return blocks;
    }

}
