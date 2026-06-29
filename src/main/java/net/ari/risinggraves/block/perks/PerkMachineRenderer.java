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

public class PerkMachineRenderer implements BlockEntityRenderer<PerkMachineBlockEntity> {

    public PerkMachineRenderer(BlockEntityRendererProvider.Context ctx) {}

    @Override
    public void render(PerkMachineBlockEntity machine, float partialTicks,
                       PoseStack poseStack, MultiBufferSource buffer,
                       int light, int overlay) {

        Player player = Minecraft.getInstance().player;
        if (player == null) return;

        Vec3 cam = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
        double dist = cam.distanceTo(Vec3.atCenterOf(machine.getBlockPos()));

        if (dist > 8) return; // only show when close

        poseStack.pushPose();

        // Position above block
        poseStack.translate(0.5, 1.3, 0.5);

        // Face the player
        poseStack.mulPose(Minecraft.getInstance().gameRenderer.getMainCamera().rotation());

        // Scale text
        poseStack.scale(0.02f, -0.02f, 0.02f);

        // Text
        String name = machine.getPerk().name();
        int cost = PerkCosts.getCost(machine.getPerk());
        String text = name + " (" + cost + ")";

        var font = Minecraft.getInstance().font;
        float width = font.width(text) / 2f;

        font.drawInBatch(
                text,
                -width,
                0,
                0xFFFFFF,
                false,
                poseStack.last().pose(),
                buffer,
                net.minecraft.client.gui.Font.DisplayMode.NORMAL,
                0,
                light
        );

        poseStack.popPose();
    }
}
