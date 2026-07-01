package net.ari.risinggraves.hud;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;

import net.minecraft.client.Minecraft;
import net.minecraft.client.Camera;
import net.minecraft.client.gui.Font;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.core.BlockPos;

import com.mojang.blaze3d.vertex.PoseStack;

// ADD THESE
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

        // FIX: copy list to avoid mutation during render
        List<BlockadeCluster> safeClusters = new ArrayList<>(data.getClusters());

        for (BlockadeCluster cluster : safeClusters) {

            // FIX: avoid crash when deselecting
            if (cluster.blocks.isEmpty())
                continue;

            BlockPos pos = cluster.blocks.get(0);

            double dist = player.distanceToSqr(pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5);
            if (dist > 16) continue;

            String text = "§ePress §lCrouch§r§e & §lPlace§r§e to open (§a" + cluster.cost + "§e)";

            renderFloatingText(event.getPoseStack(), text, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5);
        }
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
