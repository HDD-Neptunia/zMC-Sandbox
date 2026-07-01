package net.ari.risinggraves.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;
import net.ari.risinggraves.scoreboard.ScoreboardHandler;
import net.minecraft.network.chat.Component;
import net.ari.risinggraves.block.crate.ChestList;
import net.minecraft.world.item.ItemStack;
import net.ari.risinggraves.block.crate.CrateManager;




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
    public InteractionResult use(BlockState state, Level level, BlockPos pos,
                                Player player, InteractionHand hand, BlockHitResult hit) {

        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }

        // Check if this crate is the active one
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

        // Deduct points
        ScoreboardHandler.INSTANCE.addPoints(name, -COST);

        // Give reward
        ChestList list = new ChestList();
        ItemStack reward = list.getRandomItem();
        player.addItem(reward);

        player.displayClientMessage(Component.literal("§aMystery Box Reward: " + reward.getHoverName().getString()), true);

        // Chance to break and move
        CrateManager.INSTANCE.trySwitch(level, player);

        return InteractionResult.SUCCESS;
    }

}

