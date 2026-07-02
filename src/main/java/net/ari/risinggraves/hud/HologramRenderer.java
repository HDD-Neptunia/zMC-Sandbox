package net.ari.risinggraves.hud;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import net.minecraft.client.Minecraft;
import net.minecraft.client.Camera;
import net.minecraft.client.gui.Font;

import net.minecraft.core.BlockPos;

import com.mojang.blaze3d.vertex.PoseStack;

import java.util.List;
import java.util.ArrayList;


import net.ari.risinggraves.barrier.BlockadeData;
import net.ari.risinggraves.barrier.BlockadeCluster;


@Mod.EventBusSubscriber(modid = "risinggraves", value = Dist.CLIENT)
public class HologramRenderer {

    @SubscribeEvent
    public static void onRenderLevel(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_TRANSLUCENT_BLOCKS)
            return;

        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        Level level = mc.level;

        if (player == null || level == null) return;

        BlockadeData data = BlockadeData.CLIENT;

        List<BlockadeCluster> safeClusters = new ArrayList<>(data.getClusters());

        for (BlockadeCluster cluster : safeClusters) {

            if (cluster.blocks.isEmpty())
                continue;

            BlockPos center = getClusterCenter(cluster);
            double[] pos = offsetTowardPlayer(center, player, 0.8);

            double dist = player.distanceToSqr(pos[0], pos[1], pos[2]);
            if (dist > 16) continue;

            String text = "§ePress §lCrouch§r§e & §lPlace§r§e to open (§a" + cluster.cost + "§e)";
            renderFloatingText(event.getPoseStack(), text, pos[0], pos[1], pos[2]);

        }
    }

    private static BlockPos getClusterCenter(BlockadeCluster cluster) {
        int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE, minZ = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE, maxZ = Integer.MIN_VALUE;

        for (BlockPos pos : cluster.blocks) {
            minX = Math.min(minX, pos.getX());
            minY = Math.min(minY, pos.getY());
            minZ = Math.min(minZ, pos.getZ());

            maxX = Math.max(maxX, pos.getX());
            maxY = Math.max(maxY, pos.getY());
            maxZ = Math.max(maxZ, pos.getZ());
        }

        return new BlockPos(
            (minX + maxX) / 2.0,
            (minY + maxY) / 2.0,
            (minZ + maxZ) / 2.0
        );
    }

    private static double[] offsetTowardPlayer(BlockPos center, Player player, double distance) {
        double dx = player.getX() - (center.getX() + 0.5);
        double dy = player.getY() - (center.getY() + 0.5);
        double dz = player.getZ() - (center.getZ() + 0.5);

        double len = Math.sqrt(dx*dx + dy*dy + dz*dz);
        if (len == 0) len = 1;

        dx /= len;
        dy /= len;
        dz /= len;

        return new double[] {
            center.getX() + 0.5 + dx * distance,
            center.getY() + 1.5 + dy * (distance * 0.3),
            center.getZ() + 0.5 + dz * distance
        };
    }

    private static void renderFloatingText(PoseStack poseStack, String text, double x, double y, double z) {
        Minecraft mc = Minecraft.getInstance();
        Camera camera = mc.gameRenderer.getMainCamera();

        double camX = camera.getPosition().x;
        double camY = camera.getPosition().y;
        double camZ = camera.getPosition().z;

        poseStack.pushPose();
        poseStack.translate(x - camX, y - camY, z - camZ);
        poseStack.mulPose(mc.getEntityRenderDispatcher().cameraOrientation());
        poseStack.scale(-0.025f, -0.025f, 0.025f);

        Font font = mc.font;
        float width = font.width(text) / 2f;

        font.drawInBatch(
            text,
            -width,
            0,
            0xFFFFFF,
            false,
            poseStack.last().pose(),
            mc.renderBuffers().bufferSource(),
            false,
            0,
            15728880
        );

        poseStack.popPose();
    }
}
