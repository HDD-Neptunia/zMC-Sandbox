package net.ari.risinggraves.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.ari.risinggraves.block.PerkMachineBlockEntity;

public class PerkMachineBlock extends Block implements EntityBlock {

    public PerkMachineBlock(Properties props) {
        super(props);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new PerkMachineBlockEntity(pos, state);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos,
                                Player player, InteractionHand hand, BlockHitResult hit) {

        if (level.isClientSide) return InteractionResult.SUCCESS;

        BlockEntity be = level.getBlockEntity(pos);
        if (!(be instanceof PerkMachineBlockEntity machine)) return InteractionResult.FAIL;

        // CREATIVE SETUP MODE — open perk selection UI
        if (player.isCreative()) {
            player.openMenu(new PerkSelectionMenuProvider(machine));
            return InteractionResult.SUCCESS;
        }

        // SURVIVAL PURCHASE MODE
        PerkType perk = machine.getPerk();
        int cost = PerkCosts.getCost(perk);

        String name = player.getName().getString();
        int points = ScoreboardHandler.INSTANCE.getPoints(name);

        if (points < cost) {
            player.displayClientMessage(Component.literal("§cNot enough points!"), true);
            return InteractionResult.SUCCESS;
        }

        if (PerkHandler.hasPerk(player, perk)) {
            player.displayClientMessage(Component.literal("§eYou already have this perk!"), true);
            return InteractionResult.SUCCESS;
        }

        ScoreboardHandler.INSTANCE.addPoints(name, -cost);
        PerkHandler.givePerk(player, perk);

        player.displayClientMessage(Component.literal("§a" + perk.name() + " acquired!"), true);

        return InteractionResult.SUCCESS;
    }


}
