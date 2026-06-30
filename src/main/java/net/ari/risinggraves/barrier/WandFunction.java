package net.ari.risinggraves.barrier;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraftforge.network.NetworkHooks;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.BlockHitResult;



public class WandFunction extends Item {

    public WandFunction(Properties props) {
        super(props);
    }


    @Override
    public InteractionResult useOn(UseOnContext ctx) {

        Player player = ctx.getPlayer();
        Level level = ctx.getLevel();

        if (level.isClientSide) return InteractionResult.SUCCESS;

        // ⭐ Correct block targeting using raycast
        HitResult hit = player.pick(5.0D, 0.0F, false);

        if (hit.getType() != HitResult.Type.BLOCK) {
            return InteractionResult.SUCCESS;
        }

        int active = tag.getInt("activeCluster");
        int clusterId = findClusterAtPos(pos);

        if (clusterId != active) {
            // Not the active cluster → ignore selection
            return InteractionResult.SUCCESS;
        }

        BlockPos pos = ((BlockHitResult) hit).getBlockPos();
        // ⭐ DO NOT SELECT AIR (purchased barrier)
        BlockState clickedState = level.getBlockState(pos);
        if (clickedState.isAir()) {
            player.displayClientMessage(Component.literal("§cCannot select purchased barrier"), true);
            return InteractionResult.SUCCESS;
        }

        // ⭐ Sprint = switch active cluster instead of selecting blocks
        if (player.isSprinting()) {
            int newCluster = findClusterAtPos(pos); // you already have this logic somewhere
            tag.putInt("activeCluster", newCluster);
            player.displayClientMessage(Component.literal("§eSwitched active barrier"), true);
            return InteractionResult.SUCCESS;
        }


        System.out.println("WAND SELECTED POS = " + pos + " | BLOCK = " + level.getBlockState(pos));

        ItemStack stack = ctx.getItemInHand();
        CompoundTag tag = stack.getOrCreateTag();
        ListTag list = tag.getList("selected", Tag.TAG_COMPOUND);

        // Check if block is already selected
        boolean alreadySelected = false;
        int indexToRemove = -1;

        for (int i = 0; i < list.size(); i++) {
            CompoundTag e = list.getCompound(i);

            if (e.getInt("x") == pos.getX() &&
                e.getInt("y") == pos.getY() &&
                e.getInt("z") == pos.getZ()) {
                alreadySelected = true;
                indexToRemove = i;
                break;
            }
        }

        if (alreadySelected) {
            list.remove(indexToRemove);
            tag.put("selected", list);
            player.displayClientMessage(Component.literal("§cRemoved block from selection"), true);
            return InteractionResult.SUCCESS;
        }

        // ⭐ Correct blockstate capture
        BlockState state = level.getBlockState(pos);
        CompoundTag stateTag = NbtUtils.writeBlockState(state);

        CompoundTag entry = new CompoundTag();
        entry.putInt("x", pos.getX());
        entry.putInt("y", pos.getY());
        entry.putInt("z", pos.getZ());
        entry.put("state", stateTag);
        list.add(entry);

        tag.put("selected", list);

        player.displayClientMessage(Component.literal("§aAdded block to blockade selection"), true);

        return InteractionResult.SUCCESS;
    }



    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!player.isShiftKeyDown())
            return InteractionResultHolder.pass(stack);

        // ⭐ Only open menu for active cluster
        int active = tag.getInt("activeCluster");
        CompoundTag clusterTag = getClusterTag(active); // you already have cluster storage

        NetworkHooks.openScreen((ServerPlayer) player, new CostMenuProvider(clusterTag), buf -> {
            buf.writeNbt(clusterTag);
        });


        CompoundTag tag = stack.getOrCreateTag();
        if (!level.isClientSide) {
            NetworkHooks.openScreen((ServerPlayer) player, new CostMenuProvider(tag), buf -> {
                buf.writeNbt(tag);
            });
        }



        return InteractionResultHolder.success(stack);
    }
}
