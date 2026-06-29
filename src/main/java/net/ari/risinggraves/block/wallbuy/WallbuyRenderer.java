package net.ari.risinggraves.block.wallbuy;

import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.ari.risinggraves.block.WallbuyBlockEntity;

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

        // -----------------------------
        // HOLOGRAM TEXT (name + cost)
        // -----------------------------
        poseStack.pushPose();

        poseStack.translate(0.5, 1.3, 0.5);
        poseStack.mulPose(Minecraft.getInstance().gameRenderer.getMainCamera().rotation());
        poseStack.scale(0.02f, -0.02f, 0.02f);

        Item item = wallbuy.getItem();
        int cost = wallbuy.getCost();

        String text = item.getDescription().getString() + " (" + cost + ")";

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

        // -----------------------------
        // ITEM RENDERED ON THE WALL
        // -----------------------------
        poseStack.pushPose();

        // Move item slightly forward from the wall
        poseStack.translate(0.5, 0.5, 0.5);

        // Rotate based on block facing
        Direction facing = wallbuy.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING);
        poseStack.mulPose(facing.getRotation());

        // Scale item
        poseStack.scale(0.5f, 0.5f, 0.5f);

        ItemStack stack = new ItemStack(wallbuy.getItem());
        Minecraft.getInstance().getItemRenderer().renderStatic(
                stack,
                ItemDisplayContext.FIXED,
                light,
                overlay,
                poseStack,
                buffer,
                wallbuy.getLevel(),
                0
        );

        poseStack.popPose();
    }
}
