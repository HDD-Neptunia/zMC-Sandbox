package net.ari.risinggraves.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.item.ItemStack;

import net.minecraft.core.BlockPos;

import net.minecraft.network.chat.Component;

import net.minecraft.server.level.ServerLevel;

import net.minecraft.util.RandomSource;


import net.ari.risinggraves.block.crate.CrateManager;
import net.ari.risinggraves.scoreboard.ScoreboardHandler;
import net.ari.risinggraves.block.crate.ChestList;


public class MysteryCrateBlock extends Block {

    public static final int COST = 950;

    public MysteryCrateBlock(Properties props) {
        super(props);
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (!level.isClientSide) {
            CrateManager.INSTANCE.registerCrate(pos);
        }
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!CrateManager.INSTANCE.isActive(pos)) {
            return;
        }

        if (random.nextInt(80) == 0) {
            FireworkRocketEntity fw = new FireworkRocketEntity(
                level,
                pos.getX() + 0.5,
                pos.getY() + 1.0,
                pos.getZ() + 0.5,
                ItemStack.EMPTY
            );
            level.addFreshEntity(fw);
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos,
                                Player player, InteractionHand hand, BlockHitResult hit) {

        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }

        if (!CrateManager.INSTANCE.isActive(pos)) {
            player.displayClientMessage(Component.literal("§cThe Mystery Box is not here."), true);
            return InteractionResult.SUCCESS;
        }

        String name = player.getName().getString();
        int points = ScoreboardHandler.INSTANCE.getPoints(name);

        if (points < COST) {
            player.displayClientMessage(Component.literal("§cNot enough points! (" + points + "/" + COST + ")"), true);
            return InteractionResult.SUCCESS;
        }

        ScoreboardHandler.INSTANCE.addPoints(name, -COST);

        ChestList list = new ChestList();
        ItemStack reward = list.getRandomItem();
        player.addItem(reward);

        CrateManager.INSTANCE.trySwitch(level, player);

        return InteractionResult.SUCCESS;
    }

}

