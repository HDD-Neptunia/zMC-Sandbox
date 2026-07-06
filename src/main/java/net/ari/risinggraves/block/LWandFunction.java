package net.ari.risinggraves.block;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraft.core.BlockPos;

import net.minecraft.nbt.CompoundTag;

import net.minecraft.network.chat.Component;


import net.ari.risinggraves.barrier.BlockadeData;
import net.ari.risinggraves.barrier.BlockadeCluster;
import net.ari.risinggraves.block.CustomSpawnerBlockEntity;


public class LWandFunction extends Item {

    public LWandFunction(Properties props) {
        super(props);
    }

    @Override
        public InteractionResult useOn(UseOnContext ctx) {
            BlockPos pos = ctx.getClickedPos();
            Level level = ctx.getLevel();
            Player player = ctx.getPlayer();
            ItemStack stack = ctx.getItemInHand();
            CompoundTag tag = stack.getOrCreateTag();

            BlockEntity be = level.getBlockEntity(pos);

            if (be instanceof CustomSpawnerBlockEntity spawner) {
                tag.putInt("linkingSpawnerX", pos.getX());
                tag.putInt("linkingSpawnerY", pos.getY());
                tag.putInt("linkingSpawnerZ", pos.getZ());

                player.displayClientMessage(Component.literal("§eSpawner selected. Now click a door."), true);
                return InteractionResult.SUCCESS;
            }

            if (tag.contains("linkingSpawnerX")) {

                BlockadeData data = BlockadeData.get(level);

                // Find which cluster this block belongs to
                BlockadeCluster targetCluster = null;
                for (BlockadeCluster cluster : data.getClusters()) {
                    if (cluster.blocks.contains(pos)) {
                        targetCluster = cluster;
                        break;
                    }
                }

                if (targetCluster == null) {
                    player.displayClientMessage(Component.literal("§cThis block is not part of a blockade."), true);
                    return InteractionResult.SUCCESS;
                }

                // Link spawner → blockade
                BlockPos spawnerPos = new BlockPos(
                    tag.getInt("linkingSpawnerX"),
                    tag.getInt("linkingSpawnerY"),
                    tag.getInt("linkingSpawnerZ")
                );

                BlockEntity be2 = level.getBlockEntity(spawnerPos);
                if (be2 instanceof CustomSpawnerBlockEntity spawner2) {

                    // ALWAYS link to the cluster root, not the clicked block
                    BlockPos doorRoot = pos.immutable();

                    spawner2.setLinkedDoor(doorRoot);
                    spawner2.setChanged();
                    level.sendBlockUpdated(spawnerPos, level.getBlockState(spawnerPos), level.getBlockState(spawnerPos), 3);

                    System.out.println("Linked spawner at " + spawnerPos + " to door " + doorRoot);

                    player.displayClientMessage(Component.literal("§aSpawner linked to blockade!"), true);
                }

                // Clear linking mode
                tag.remove("linkingSpawnerX");
                tag.remove("linkingSpawnerY");
                tag.remove("linkingSpawnerZ");

                return InteractionResult.SUCCESS;
            }


            return InteractionResult.PASS;
        }
    }
