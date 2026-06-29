package net.ari.risinggraves.barrier;


import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.Tag;
import net.ari.risinggraves.barrier.WandFunction;


import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

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

        CompoundTag tag = held.getOrCreateTag();
        ListTag list = tag.getList("selected", Tag.TAG_COMPOUND);

        PoseStack poseStack = event.getPoseStack();
        MultiBufferSource buffer = mc.renderBuffers().bufferSource();

        for (int i = 0; i < list.size(); i++) {
            CompoundTag b = list.getCompound(i);
            BlockPos pos = new BlockPos(b.getInt("x"), b.getInt("y"), b.getInt("z"));

            renderOutline(poseStack, buffer, pos);
        }
    }

    private static void renderOutline(PoseStack poseStack, MultiBufferSource buffer, BlockPos pos) {
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
            1f, 1f, 0f, 1f // yellow outline
        );

        poseStack.popPose();
    }
}
