package net.ari.risinggraves.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.entity.BlockEntity;

import net.minecraft.world.level.Level;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.BlockPlaceContext;

import net.minecraft.network.chat.Component;

import net.ari.risinggraves.block.WallbuyBlockEntity;
import net.ari.risinggraves.block.wallbuy.WallbuyCostMenuProvider;

import net.ari.risinggraves.scoreboard.ScoreboardHandler;

public class WallbuyBlock extends Block implements EntityBlock {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public WallbuyBlock(Properties props) {
        super(props);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.defaultBlockState().setValue(FACING, ctx.getHorizontalDirection().getOpposite());
    }

    

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new WallbuyBlockEntity(pos, state);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos,
                                Player player, InteractionHand hand, BlockHitResult hit) {

        if (level.isClientSide) return InteractionResult.SUCCESS;

        BlockEntity be = level.getBlockEntity(pos);
        if (!(be instanceof WallbuyBlockEntity wallbuy)) return InteractionResult.FAIL;

        ItemStack held = player.getItemInHand(hand);

        // CREATIVE SETUP MODE
        if (player.isCreative()) {
            if (!held.isEmpty()) {
                // Open cost menu, passing the item
                player.openMenu(new WallbuyCostMenuProvider(wallbuy, held.getItem()));
                return InteractionResult.SUCCESS;
            }
        }

        // SURVIVAL PURCHASE MODE
        int cost = wallbuy.getCost();
        Item item = wallbuy.getItem();

        if (item == Items.AIR) {
            player.displayClientMessage(Component.literal("§cNo item set!"), true);
            return InteractionResult.SUCCESS;
        }

        String name = player.getName().getString();
        int points = ScoreboardHandler.INSTANCE.getPoints(name);

        if (points < cost) {
            player.displayClientMessage(Component.literal("§cNot enough points!"), true);
            return InteractionResult.SUCCESS;
        }

        ScoreboardHandler.INSTANCE.addPoints(name, -cost);
        player.addItem(new ItemStack(item));

        player.displayClientMessage(Component.literal("§aPurchased " + item.getDescription().getString()), true);

        return InteractionResult.SUCCESS;
}

}
