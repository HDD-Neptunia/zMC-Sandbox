package net.ari.risinggraves.barrier;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.ItemStack;
import net.ari.risinggraves.barrier.WandFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;


import java.util.List;

@Mod.EventBusSubscriber(modid = "risinggraves", value = Dist.CLIENT)
public class SelectionRenderer {

@SubscribeEvent
public static void onRenderLevel(RenderLevelStageEvent event) {
    if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_TRANSLUCENT_BLOCKS)
        return;

    Minecraft mc = Minecraft.getInstance();
    Player player = mc.player;
    Level level = mc.level;

    if (player == null || level == null) return;

    // Only render when holding the wand
    ItemStack held = player.getMainHandItem();
    if (!(held.getItem() instanceof WandFunction)) return;

    // Get wand tag once
    CompoundTag tag = held.getOrCreateTag();
    ListTag list = tag.getList("selected", ListTag.TAG_COMPOUND);

    // Prepare renderer BEFORE sprint logic
    PoseStack poseStack = event.getPoseStack();
    MultiBufferSource buffer = mc.renderBuffers().bufferSource();

    // SPRINT = highlight all clusters
    if (mc.options.keySprint.isDown()) {
        int activeCluster = tag.getInt("activeCluster");

        BlockadeData data = BlockadeData.CLIENT;
        List<BlockadeCluster> clusters = data.getClusters();
        player.displayClientMessage(Component.literal("Clusters: " + clusters.size()), true);

        for (int i = 0; i < clusters.size(); i++) {
            BlockadeCluster cluster = clusters.get(i);

            float r, g, b;

            if (i == activeCluster) {
                // active cluster = yellow
                r = 1f; g = 1f; b = 0f;
            } else {
                // inactive clusters = blue
                r = 0f; g = 0.5f; b = 1f;
            }

            for (BlockPos pos : cluster.blocks) {
                renderOutline(poseStack, buffer, pos, r, g, b);
            }
        }

        return; // stop normal selection rendering
    }

        // NORMAL RENDERING: show ACTIVE CLUSTER
    int activeCluster = tag.getInt("activeCluster");

    BlockadeData data = BlockadeData.CLIENT;
    List<BlockadeCluster> clusters = data.getClusters();

    if (activeCluster >= 0 && activeCluster < clusters.size()) {
        BlockadeCluster cluster = clusters.get(activeCluster);

        // active cluster = yellow
        for (BlockPos pos : cluster.blocks) {
            renderOutline(poseStack, buffer, pos, 1f, 1f, 0f);
        }
    }

    // ALSO render selected blocks (your manual selection)
    for (int i = 0; i < list.size(); i++) {
        CompoundTag b = list.getCompound(i);
        BlockPos pos = new BlockPos(b.getInt("x"), b.getInt("y"), b.getInt("z"));

        renderOutline(poseStack, buffer, pos, 1f, 1f, 0f);
    }

}



    private static void renderOutline(PoseStack poseStack, MultiBufferSource buffer, BlockPos pos,
                                      float r, float g, float b) {
        Minecraft mc = Minecraft.getInstance();
        Camera camera = mc.gameRenderer.getMainCamera();

        double camX = camera.getPosition().x;
        double camY = camera.getPosition().y;
        double camZ = camera.getPosition().z;

        poseStack.pushPose();
        poseStack.translate(pos.getX() - camX, pos.getY() - camY, pos.getZ() - camZ);

        LevelRenderer.renderLineBox(
            poseStack,
            buffer.getBuffer(RenderType.lines()),
            0, 0, 0,
            1, 1, 1,
            r, g, b, 1f
        );

        poseStack.popPose();
    }
}

