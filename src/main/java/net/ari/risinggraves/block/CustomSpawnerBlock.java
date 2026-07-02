package net.ari.risinggraves.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;

import net.minecraft.core.BlockPos;


public class CustomSpawnerBlock extends Block implements EntityBlock {

    public CustomSpawnerBlock(BlockBehaviour.Properties props) {
        super(props);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CustomSpawnerBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return level.isClientSide ? null : (lvl, p, s, be) -> ((CustomSpawnerBlockEntity) be).tickServer();
    }
}
