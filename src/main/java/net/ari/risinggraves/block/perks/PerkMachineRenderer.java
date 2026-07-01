package net.ari.risinggraves.block.perks;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.ari.risinggraves.block.PerkMachineBlockEntity;
import net.ari.risinggraves.perks.PerkCosts;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.Font;
import com.mojang.blaze3d.vertex.VertexConsumer;
import org.joml.Matrix4f;
import net.minecraft.client.renderer.RenderType;







public class PerkMachineRenderer implements BlockEntityRenderer<PerkMachineBlockEntity> {

    public PerkMachineRenderer(BlockEntityRendererProvider.Context ctx) {
        System.out.println("[RENDERER] PerkMachineRenderer constructed");
    }

    

    @Override
    public void render(PerkMachineBlockEntity machine, float partialTicks,
                    PoseStack poseStack, MultiBufferSource buffer,
                    int light, int overlay) {

        System.out.println("[RENDER] CLIENT perk=" + machine.getPerk() +
                   " color=" + machine.getColor() +
                   " pos=" + machine.getBlockPos());

        Player player = Minecraft.getInstance().player;
        if (player == null) return;

        Vec3 cam = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
        double dist = cam.distanceTo(Vec3.atCenterOf(machine.getBlockPos()));

        System.out.println("[RENDER] dist=" + dist);

        if (dist > 8) return;

        // 1. RENDER COLOURED PANEL ON THE FRONT (NORTH) FACE

        int color = machine.getColor();


        System.out.println("[RENDER] PANEL START");

        poseStack.pushPose();

        // Move to the NORTH face (front)
        poseStack.translate(0.5, 0.5, 1.001); // just barely in front of the face

        VertexConsumer vc = buffer.getBuffer(RenderType.translucent());
        System.out.println("[RENDER] vc=" + vc);

        Matrix4f mat = poseStack.last().pose();
        System.out.println("[RENDER] mat=" + mat);


        float r = ((machine.getColor() >> 16) & 0xFF) / 255f;
        float g = ((machine.getColor() >> 8) & 0xFF) / 255f;
        float b = (machine.getColor() & 0xFF) / 255f;

        System.out.println("[RENDER] DRAWING PANEL r=" + r + " g=" + g + " b=" + b);

        // Quad facing NORTH → normal(0,0,-1)
        vc.vertex(mat, -0.5f, -0.5f, 0).color(r, g, b, 0.8f).uv(0, 0).uv2(light).normal(0, 0, -1).endVertex();
        vc.vertex(mat,  0.5f, -0.5f, 0).color(r, g, b, 0.8f).uv(1, 0).uv2(light).normal(0, 0, -1).endVertex();
        vc.vertex(mat,  0.5f,  0.5f, 0).color(r, g, b, 0.8f).uv(1, 1).uv2(light).normal(0, 0, -1).endVertex();
        vc.vertex(mat, -0.5f,  0.5f, 0).color(r, g, b, 0.8f).uv(0, 1).uv2(light).normal(0, 0, -1).endVertex();

        poseStack.popPose();

        System.out.println("[RENDER] PANEL END");


        // ------------------------------------------------------
        // 2. RENDER FLOATING HOLOGRAM TEXT (AFTER PANEL)
        // ------------------------------------------------------

        poseStack.pushPose();

        poseStack.translate(0.5, 1.3, 0.5);
        poseStack.mulPose(Minecraft.getInstance().gameRenderer.getMainCamera().rotation());
        poseStack.scale(0.02f, -0.02f, 0.02f);

        String name = machine.getPerk().name();
        int cost = PerkCosts.getCost(machine.getPerk());
        String text = name + " (" + cost + ")";

        var font = Minecraft.getInstance().font;
        float width = font.width(text) / 2f;

        font.drawInBatch(
                Component.literal(text),
                -width, 0,
                0xFFFFFF,
                false,
                poseStack.last().pose(),
                buffer,
                false,
                0,
                15728880
        );

        poseStack.popPose();
    }

}
