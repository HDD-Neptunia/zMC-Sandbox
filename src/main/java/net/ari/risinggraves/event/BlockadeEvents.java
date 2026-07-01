package net.ari.risinggraves.event;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.ari.risinggraves.scoreboard.ScoreboardHandler;

import net.ari.risinggraves.barrier.BlockadeData;
import net.ari.risinggraves.barrier.BlockadeCluster;



@Mod.EventBusSubscriber(modid = "risinggraves")
public class BlockadeEvents {

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        Level level = event.getLevel();
        BlockPos pos = event.getPos();

        if (level.isClientSide) return;

        BlockadeData data = BlockadeData.get(level);

        for (BlockadeCluster cluster : data.getClusters()) {
            if (cluster.blocks.contains(pos)) {

                // Check points
                String name = player.getName().getString();
                int points = ScoreboardHandler.INSTANCE.getPoints(name);

                if (points < cluster.cost) {
                    player.displayClientMessage(Component.literal("§cNot enough points"), true);
                    return;
                }

                // Deduct points
                ScoreboardHandler.INSTANCE.addPoints(name, -cluster.cost);

                // Remove all blocks in the cluster
                for (BlockPos bp : cluster.blocks) {
                    level.removeBlock(bp, false);
                }

                player.displayClientMessage(Component.literal("§aBlockade opened!"), true);

                // Remove the cluster from the list
                cluster.purchased = true;
                data.setDirty();

                return;
            }
        }
    }
}
