package net.ari.risinggraves.barrier;

import com.mojang.brigadier.CommandDispatcher;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

import net.minecraft.core.BlockPos;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.ItemStack;

import net.minecraft.network.chat.Component;


import net.ari.risinggraves.barrier.BlockadeData;
import net.ari.risinggraves.barrier.BlockadeCluster;


public class ResetBlockadesCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
            Commands.literal("resetblockades")
                .requires(source -> source.hasPermission(2))
                .executes(ctx -> reset(ctx.getSource()))
        );
    }

    private static int reset(CommandSourceStack source) {
        ServerLevel level = source.getLevel();
        BlockadeData data = BlockadeData.get(level);
        DoorData doorData = DoorData.get(level);

        doorData.clear();

        doorData.setDirty();
        int restored = 0;

        for (ServerPlayer p : level.getServer().getPlayerList().getPlayers()) {
            ItemStack wand = p.getMainHandItem();
            if (wand.getItem() instanceof WandFunction) {
                wand.getOrCreateTag().remove("selected");
                wand.getOrCreateTag().putInt("activeCluster", -1);
            }
        }

        for (BlockadeCluster cluster : data.getClusters()) {
            System.out.println("Cluster states size = " + cluster.states.size());

            for (int i = 0; i < cluster.blocks.size(); i++) {
                BlockPos pos = cluster.blocks.get(i);
                BlockState state = cluster.states.get(i);

                System.out.println("Placing state: " + state);

                level.setBlock(pos, state, 3);
                restored++;
            }
            cluster.purchased = false;
        }

        data.setDirty();

        source.sendSuccess(
            Component.literal("§aRegenerated " + restored + " blockade blocks."),
            true
        );

        return restored;
    }
}
