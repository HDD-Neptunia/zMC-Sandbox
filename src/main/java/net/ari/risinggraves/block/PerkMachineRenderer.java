package net.ari.risinggraves.block;

import net.ari.risinggraves.perks.PerkCosts;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import org.joml.Matrix4f;
import net.minecraft.world.phys.Vec3;
import net.minecraft.network.chat.Component;




public class PerkMachineRenderer implements BlockEntityRenderer<PerkMachineBlockEntity> {

    private static final ResourceLocation BASE_TEXTURE =
        new ResourceLocation("risinggraves", "textures/block/perk_machine_front.png");

    public PerkMachineRenderer(BlockEntityRendererProvider.Context ctx) {
        System.out.println("[RENDERER] PerkMachineRenderer constructed");
    }

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
        poseStack.translate(0.5, 0.5, 0.5); // center of block

        int color = machine.getColor();
        float r = ((color >> 16) & 0xFF) / 255f;
        float g = ((color >> 8) & 0xFF) / 255f;
        float b = (color & 0xFF) / 255f;

        VertexConsumer vc = buffer.getBuffer(RenderType.entityTranslucentCull(BASE_TEXTURE));
        Matrix4f mat = poseStack.last().pose();

        // FRONT (-Z)
        vc.vertex(mat, -0.5f, -0.5f, -0.5f).color(r, g, b, 1.0f).uv(0, 1).overlayCoords(overlay).uv2(light).normal(0, 0, -1).endVertex();
        vc.vertex(mat,  0.5f, -0.5f, -0.5f).color(r, g, b, 1.0f).uv(1, 1).overlayCoords(overlay).uv2(light).normal(0, 0, -1).endVertex();
        vc.vertex(mat,  0.5f,  0.5f, -0.5f).color(r, g, b, 1.0f).uv(1, 0).overlayCoords(overlay).uv2(light).normal(0, 0, -1).endVertex();
        vc.vertex(mat, -0.5f,  0.5f, -0.5f).color(r, g, b, 1.0f).uv(0, 0).overlayCoords(overlay).uv2(light).normal(0, 0, -1).endVertex();

        // BACK (+Z)
        vc.vertex(mat,  0.5f, -0.5f, 0.5f).color(r, g, b, 1.0f).uv(0, 1).overlayCoords(overlay).uv2(light).normal(0, 0, 1).endVertex();
        vc.vertex(mat, -0.5f, -0.5f, 0.5f).color(r, g, b, 1.0f).uv(1, 1).overlayCoords(overlay).uv2(light).normal(0, 0, 1).endVertex();
        vc.vertex(mat, -0.5f,  0.5f, 0.5f).color(r, g, b, 1.0f).uv(1, 0).overlayCoords(overlay).uv2(light).normal(0, 0, 1).endVertex();
        vc.vertex(mat,  0.5f,  0.5f, 0.5f).color(r, g, b, 1.0f).uv(0, 0).overlayCoords(overlay).uv2(light).normal(0, 0, 1).endVertex();

        // LEFT (-X)
        vc.vertex(mat, -0.5f, -0.5f,  0.5f).color(r, g, b, 1.0f).uv(0, 1).overlayCoords(overlay).uv2(light).normal(-1, 0, 0).endVertex();
        vc.vertex(mat, -0.5f, -0.5f, -0.5f).color(r, g, b, 1.0f).uv(1, 1).overlayCoords(overlay).uv2(light).normal(-1, 0, 0).endVertex();
        vc.vertex(mat, -0.5f,  0.5f, -0.5f).color(r, g, b, 1.0f).uv(1, 0).overlayCoords(overlay).uv2(light).normal(-1, 0, 0).endVertex();
        vc.vertex(mat, -0.5f,  0.5f,  0.5f).color(r, g, b, 1.0f).uv(0, 0).overlayCoords(overlay).uv2(light).normal(-1, 0, 0).endVertex();

        // RIGHT (+X)
        vc.vertex(mat, 0.5f, -0.5f, -0.5f).color(r, g, b, 1.0f).uv(0, 1).overlayCoords(overlay).uv2(light).normal(1, 0, 0).endVertex();
        vc.vertex(mat, 0.5f, -0.5f,  0.5f).color(r, g, b, 1.0f).uv(1, 1).overlayCoords(overlay).uv2(light).normal(1, 0, 0).endVertex();
        vc.vertex(mat, 0.5f,  0.5f,  0.5f).color(r, g, b, 1.0f).uv(1, 0).overlayCoords(overlay).uv2(light).normal(1, 0, 0).endVertex();
        vc.vertex(mat, 0.5f,  0.5f, -0.5f).color(r, g, b, 1.0f).uv(0, 0).overlayCoords(overlay).uv2(light).normal(1, 0, 0).endVertex();

        // TOP (+Y)
        vc.vertex(mat, -0.5f, 0.5f, -0.5f).color(r, g, b, 1.0f).uv(0, 1).overlayCoords(overlay).uv2(light).normal(0, 1, 0).endVertex();
        vc.vertex(mat,  0.5f, 0.5f, -0.5f).color(r, g, b, 1.0f).uv(1, 1).overlayCoords(overlay).uv2(light).normal(0, 1, 0).endVertex();
        vc.vertex(mat,  0.5f, 0.5f,  0.5f).color(r, g, b, 1.0f).uv(1, 0).overlayCoords(overlay).uv2(light).normal(0, 1, 0).endVertex();
        vc.vertex(mat, -0.5f, 0.5f,  0.5f).color(r, g, b, 1.0f).uv(0, 0).overlayCoords(overlay).uv2(light).normal(0, 1, 0).endVertex();

        // BOTTOM (-Y)
        vc.vertex(mat, -0.5f, -0.5f,  0.5f).color(r, g, b, 1.0f).uv(0, 1).overlayCoords(overlay).uv2(light).normal(0, -1, 0).endVertex();
        vc.vertex(mat,  0.5f, -0.5f,  0.5f).color(r, g, b, 1.0f).uv(1, 1).overlayCoords(overlay).uv2(light).normal(0, -1, 0).endVertex();
        vc.vertex(mat,  0.5f, -0.5f, -0.5f).color(r, g, b, 1.0f).uv(1, 0).overlayCoords(overlay).uv2(light).normal(0, -1, 0).endVertex();
        vc.vertex(mat, -0.5f, -0.5f, -0.5f).color(r, g, b, 1.0f).uv(0, 0).overlayCoords(overlay).uv2(light).normal(0, -1, 0).endVertex();

        poseStack.popPose();
    }

}
