package net.ari.risinggraves.barrier;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.PacketDistributor;


import java.util.List;

import net.ari.risinggraves.networking.Networking;
import net.ari.risinggraves.networking.SyncBlockadesPacket;

public class WandFunction extends Item {

    public WandFunction(Properties props) {
        super(props);
    }

    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        Player player = ctx.getPlayer();
        Level level = ctx.getLevel();

        if (player == null || level.isClientSide) return InteractionResult.SUCCESS;

        // raycast
        HitResult hit = player.pick(5.0D, 0.0F, false);
        if (hit.getType() != HitResult.Type.BLOCK) {
            return InteractionResult.SUCCESS;
        }

        BlockPos pos = ((BlockHitResult) hit).getBlockPos();

        ItemStack stack = ctx.getItemInHand();
        CompoundTag tag = stack.getOrCreateTag();

        // get active cluster
        int active = tag.getInt("activeCluster");
        int clusterId = findClusterAtPos(level, pos);

        // SPRINT = switch active cluster
        if (player.getPersistentData().getBoolean("sprintKeyDown")) {
            int newCluster = findClusterAtPos(level, pos);
            if (newCluster >= 0) {
                tag.putInt("activeCluster", newCluster);
                player.displayClientMessage(Component.literal("§eSwitched active barrier"), true);
            }
            return InteractionResult.SUCCESS;
        }

        // block selecting AIR (purchased barrier)
        BlockState clickedState = level.getBlockState(pos);
        if (clickedState.isAir()) {
            player.displayClientMessage(Component.literal("§cCannot select purchased barrier"), true);
            return InteractionResult.SUCCESS;
        }
        
        // If clicking inside active cluster → DESELECT from cluster
        if (active >= 0 && clusterId == active) {
            BlockadeData data = BlockadeData.get(level);
            BlockadeCluster cluster = data.getClusters().get(active);

            int idx = cluster.blocks.indexOf(pos);
            if (idx >= 0) {
                cluster.blocks.remove(idx);
                cluster.states.remove(idx);
                data.setDirty();

                // NEW: If cluster is now empty, delete it entirely
                if (cluster.blocks.isEmpty()) {
                    data.getClusters().remove(active);

                    // Reset active cluster on the wand
                    tag.putInt("activeCluster", -1);

                    // Sync updated cluster list
                    Networking.CHANNEL.send(PacketDistributor.ALL.noArg(), new SyncBlockadesPacket(data.getClusters()));

                    player.displayClientMessage(Component.literal("§cCluster removed"), true);
                    return InteractionResult.SUCCESS;
                }

                // Normal sync if cluster still has blocks
                Networking.CHANNEL.send(PacketDistributor.ALL.noArg(), new SyncBlockadesPacket(data.getClusters()));
                player.displayClientMessage(Component.literal("§cRemoved block from active cluster"), true);
            }

            return InteractionResult.SUCCESS;
        }

        // If clicking outside active cluster → DO NOT add to active cluster
        if (active >= 0 && clusterId != active) {
            player.displayClientMessage(Component.literal("§cThis block is not part of the active cluster"), true);
            return InteractionResult.SUCCESS;
        }

        // Otherwise: normal NEW cluster selection
        ListTag list = tag.getList("selected", ListTag.TAG_COMPOUND);

        // check if already selected
        for (int i = 0; i < list.size(); i++) {
            CompoundTag e = list.getCompound(i);

            if (e.getInt("x") == pos.getX() &&
                e.getInt("y") == pos.getY() &&
                e.getInt("z") == pos.getZ()) {

                list.remove(i);
                tag.put("selected", list);
                player.displayClientMessage(Component.literal("§cRemoved block from selection"), true);
                return InteractionResult.SUCCESS;
            }
        }

        // save block + state
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

    private int findClusterAtPos(Level level, BlockPos pos) {
        BlockadeData data = BlockadeData.get(level);
        List<BlockadeCluster> clusters = data.getClusters();

        for (int i = 0; i < clusters.size(); i++) {
            if (clusters.get(i).blocks.contains(pos)) {
                return i;
            }
        }
        return -1;
    }

    private CompoundTag getClusterTag(Level level, int clusterId) {
        BlockadeData data = BlockadeData.get(level);
        List<BlockadeCluster> clusters = data.getClusters();

        BlockadeCluster cluster = clusters.get(clusterId);

        CompoundTag tag = new CompoundTag();
        ListTag list = new ListTag();

        for (int i = 0; i < cluster.blocks.size(); i++) {
            BlockPos pos = cluster.blocks.get(i);
            BlockState state = cluster.states.get(i);

            CompoundTag entry = new CompoundTag();
            entry.putInt("x", pos.getX());
            entry.putInt("y", pos.getY());
            entry.putInt("z", pos.getZ());
            entry.put("state", NbtUtils.writeBlockState(state));

            list.add(entry);
        }

        tag.put("selected", list);
        tag.putInt("cost", cluster.cost);

        return tag;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        CompoundTag tag = stack.getOrCreateTag();

        // only crouch opens menu
        if (!player.isShiftKeyDown())
            return InteractionResultHolder.pass(stack);

        if (!level.isClientSide) {
            NetworkHooks.openScreen((ServerPlayer) player, new CostMenuProvider(tag), buf -> {
                buf.writeNbt(tag);
            });
        }

        return InteractionResultHolder.success(stack);
    }
}
