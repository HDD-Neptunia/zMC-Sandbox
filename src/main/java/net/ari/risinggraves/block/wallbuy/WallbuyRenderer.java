package net.ari.risinggraves.block.wallbuy;

import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.ari.risinggraves.block.WallbuyBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.Font;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import com.mojang.math.Axis;


public class WallbuyRenderer implements BlockEntityRenderer<WallbuyBlockEntity> {

        public WallbuyRenderer(BlockEntityRendererProvider.Context ctx) {}

        @Override
        public void render(WallbuyBlockEntity wallbuy, float partialTicks,
                        PoseStack poseStack, MultiBufferSource buffer,
                        int light, int overlay) {

        Player player = Minecraft.getInstance().player;
        if (player == null) return;

        Vec3 cam = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
        double dist = cam.distanceTo(Vec3.atCenterOf(wallbuy.getBlockPos()));
        if (dist > 8) return;

        // ⭐ Declare facing ONCE
        Direction facing = wallbuy.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING);


        // -----------------------------
        // HOLOGRAM TEXT (name + cost)
        // -----------------------------
        poseStack.pushPose();

        // 1. Move to block center
        poseStack.translate(0.5, 0.5, 1);

        switch (facing) {
        case NORTH -> poseStack.translate(0, 0.35, -0.501);
        case SOUTH -> poseStack.translate(0, 0.35, 0.501);
        case WEST  -> poseStack.translate(-0.501, 0.35, 0);
        case EAST  -> poseStack.translate(0.501, 0.35, 0);
        }

        // 3. Billboard to face the player
        poseStack.mulPose(Minecraft.getInstance().gameRenderer.getMainCamera().rotation());

        // 4. Keep text upright (fix vertical tilt)
        poseStack.mulPose(Axis.YP.rotationDegrees(180)); // optional if mirrored
        poseStack.mulPose(Axis.ZP.rotationDegrees(0));   // ensures no sideways tilt

        // 5. Scale text
        poseStack.scale(0.02f, -0.02f, 0.02f);

        String text = wallbuy.getItem().getDescription().getString() + " (" + wallbuy.getCost() + ")";
        Font font = Minecraft.getInstance().font;
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



        // -----------------------------
        // ITEM RENDER (3D)
        // -----------------------------
        // ⭐ YOUR ITEM BLOCK — UNCHANGED
        poseStack.pushPose();

        poseStack.translate(0.5, 0.5, 0.5);
        poseStack.mulPose(facing.getRotation());
        poseStack.translate(0, -0.48, 0);
        poseStack.mulPose(Axis.XP.rotationDegrees(90));
        poseStack.scale(0.5f, 0.5f, 0.5f);

        ItemStack stack = new ItemStack(wallbuy.getItem());
        Minecraft.getInstance().getItemRenderer().renderStatic(
                stack,
                ItemTransforms.TransformType.FIXED,
                light,
                overlay,
                poseStack,
                buffer,
                0
        );

        poseStack.popPose();
        }

}
