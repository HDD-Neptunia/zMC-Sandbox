package net.ari.risinggraves.block;


import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

import net.minecraft.resources.ResourceLocation;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import org.joml.Matrix4f;

import net.minecraft.network.chat.Component;

import com.mojang.math.Axis;

import net.minecraft.core.BlockPos;


import net.ari.risinggraves.perks.PerkCosts;


public class PerkMachineRenderer implements BlockEntityRenderer<PerkMachineBlockEntity> {

    private static final ResourceLocation TEX_BACK   =
        new ResourceLocation("risinggraves", "textures/block/perk_machine_back.png");
    private static final ResourceLocation TEX_LEFT   =
        new ResourceLocation("risinggraves", "textures/block/perk_machine_left.png");
    private static final ResourceLocation TEX_RIGHT  =
        new ResourceLocation("risinggraves", "textures/block/perk_machine_right.png");
    private static final ResourceLocation TEX_TOP    =
        new ResourceLocation("risinggraves", "textures/block/perk_machine_topface.png");
    private static final ResourceLocation TEX_BOTTOM =
        new ResourceLocation("risinggraves", "textures/block/perk_machine_back.png");
    private static final ResourceLocation TEX_FRONT  =
        new ResourceLocation("risinggraves", "textures/block/perk_machine_front.png");

    public PerkMachineRenderer(BlockEntityRendererProvider.Context ctx) {}

    @Override
    public void render(PerkMachineBlockEntity machine,
                       float partialTicks,
                       PoseStack poseStack,
                       MultiBufferSource buffer,
                       int light,
                       int overlay) {

        Player player = Minecraft.getInstance().player;
        if (player == null) return;

        Vec3 cam = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
        double dist = cam.distanceTo(Vec3.atCenterOf(machine.getBlockPos()));
        if (dist > 32) return;

        poseStack.pushPose();
        poseStack.translate(0.0f, 1.1f, 0.0f);

        poseStack.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
        poseStack.mulPose(Axis.YP.rotationDegrees(180));

        poseStack.scale(0.02f, 0.02f, 0.02f);

        String raw = machine.getPerk().name().replace("_", " ").toLowerCase();
        String text = Character.toUpperCase(raw.charAt(0)) + raw.substring(1);
        float w = Minecraft.getInstance().font.width(text);

        poseStack.translate(-w / 2f, 0, 0);

        int argb = (255 << 24) | (machine.getColor() & 0xFFFFFF);

        MultiBufferSource.BufferSource textBuffer = Minecraft.getInstance().renderBuffers().bufferSource();

        Minecraft.getInstance().font.drawInBatch(
                text,
                0,
                0,
                argb,
                false,
                poseStack.last().pose(),
                textBuffer,
                false,
                0,
                light
        );

        textBuffer.endBatch();
        poseStack.popPose();

        poseStack.pushPose();
        poseStack.translate(0.5, 0.5, 0.5);

        int color = machine.getColor();
        float r = ((color >> 16) & 0xFF) / 255f;
        float g = ((color >> 8) & 0xFF) / 255f;
        float b = (color & 0xFF) / 255f;

        Matrix4f mat = poseStack.last().pose();

        VertexConsumer front  = buffer.getBuffer(RenderType.entityCutout(TEX_FRONT));
        VertexConsumer back   = buffer.getBuffer(RenderType.entityCutout(TEX_BACK));
        VertexConsumer left   = buffer.getBuffer(RenderType.entityCutout(TEX_LEFT));
        VertexConsumer right  = buffer.getBuffer(RenderType.entityCutout(TEX_RIGHT));
        VertexConsumer top    = buffer.getBuffer(RenderType.entityCutout(TEX_TOP));
        VertexConsumer bottom = buffer.getBuffer(RenderType.entityCutout(TEX_BOTTOM));

        front.vertex(mat, -0.5f, -0.5f, -0.5f).color(r,g,b,1).uv(0,1).overlayCoords(overlay).uv2(light).normal(0,0,-1).endVertex();
        front.vertex(mat,  0.5f, -0.5f, -0.5f).color(r,g,b,1).uv(1,1).overlayCoords(overlay).uv2(light).normal(0,0,-1).endVertex();
        front.vertex(mat,  0.5f,  0.5f, -0.5f).color(r,g,b,1).uv(1,0).overlayCoords(overlay).uv2(light).normal(0,0,-1).endVertex();
        front.vertex(mat, -0.5f,  0.5f, -0.5f).color(r,g,b,1).uv(0,0).overlayCoords(overlay).uv2(light).normal(0,0,-1).endVertex();

        back.vertex(mat,  0.5f, -0.5f, 0.5f).color(r,g,b,1).uv(0,1).overlayCoords(overlay).uv2(light).normal(0,0,1).endVertex();
        back.vertex(mat, -0.5f, -0.5f, 0.5f).color(r,g,b,1).uv(1,1).overlayCoords(overlay).uv2(light).normal(0,0,1).endVertex();
        back.vertex(mat, -0.5f,  0.5f, 0.5f).color(r,g,b,1).uv(1,0).overlayCoords(overlay).uv2(light).normal(0,0,1).endVertex();
        back.vertex(mat,  0.5f,  0.5f, 0.5f).color(r,g,b,1).uv(0,0).overlayCoords(overlay).uv2(light).normal(0,0,1).endVertex();

        left.vertex(mat, -0.5f,-0.5f, 0.5f).color(r,g,b,1).uv(0,1).overlayCoords(overlay).uv2(light).normal(-1,0,0).endVertex();
        left.vertex(mat, -0.5f,-0.5f,-0.5f).color(r,g,b,1).uv(1,1).overlayCoords(overlay).uv2(light).normal(-1,0,0).endVertex();
        left.vertex(mat, -0.5f, 0.5f,-0.5f).color(r,g,b,1).uv(1,0).overlayCoords(overlay).uv2(light).normal(-1,0,0).endVertex();
        left.vertex(mat, -0.5f, 0.5f, 0.5f).color(r,g,b,1).uv(0,0).overlayCoords(overlay).uv2(light).normal(-1,0,0).endVertex();

        right.vertex(mat, 0.5f,-0.5f,-0.5f).color(r,g,b,1).uv(0,1).overlayCoords(overlay).uv2(light).normal(1,0,0).endVertex();
        right.vertex(mat, 0.5f,-0.5f, 0.5f).color(r,g,b,1).uv(1,1).overlayCoords(overlay).uv2(light).normal(1,0,0).endVertex();
        right.vertex(mat, 0.5f, 0.5f, 0.5f).color(r,g,b,1).uv(1,0).overlayCoords(overlay).uv2(light).normal(1,0,0).endVertex();
        right.vertex(mat, 0.5f, 0.5f,-0.5f).color(r,g,b,1).uv(0,0).overlayCoords(overlay).uv2(light).normal(1,0,0).endVertex();

        top.vertex(mat,-0.5f,0.5f,-0.5f).color(r,g,b,1).uv(0,1).overlayCoords(overlay).uv2(light).normal(0,1,0).endVertex();
        top.vertex(mat, 0.5f,0.5f,-0.5f).color(r,g,b,1).uv(1,1).overlayCoords(overlay).uv2(light).normal(0,1,0).endVertex();
        top.vertex(mat, 0.5f,0.5f, 0.5f).color(r,g,b,1).uv(1,0).overlayCoords(overlay).uv2(light).normal(0,1,0).endVertex();
        top.vertex(mat,-0.5f,0.5f, 0.5f).color(r,g,b,1).uv(0,0).overlayCoords(overlay).uv2(light).normal(0,1,0).endVertex();

        bottom.vertex(mat,-0.5f,-0.5f, 0.5f).color(r,g,b,1).uv(0,1).overlayCoords(overlay).uv2(light).normal(0,-1,0).endVertex();
        bottom.vertex(mat, 0.5f,-0.5f, 0.5f).color(r,g,b,1).uv(1,1).overlayCoords(overlay).uv2(light).normal(0,-1,0).endVertex();
        bottom.vertex(mat, 0.5f,-0.5f,-0.5f).color(r,g,b,1).uv(1,0).overlayCoords(overlay).uv2(light).normal(0,-1,0).endVertex();
        bottom.vertex(mat,-0.5f,-0.5f,-0.5f).color(r,g,b,1).uv(0,0).overlayCoords(overlay).uv2(light).normal(0,-1,0).endVertex();

        poseStack.popPose();
    }
}
