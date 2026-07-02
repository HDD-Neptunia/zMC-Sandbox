package net.ari.risinggraves.perks;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import net.minecraft.client.Minecraft;


@Mod.EventBusSubscriber
public class HUDText {

    public static String currentText = null;
    public static int currentColor = 0xFFFFFF;

    @SubscribeEvent
    public static void onRenderHUD(RenderGuiOverlayEvent.Pre event) {
        if (currentText == null) return;

        Minecraft mc = Minecraft.getInstance();
        PoseStack poseStack = event.getPoseStack();

        int screenWidth = mc.getWindow().getGuiScaledWidth();
        int screenHeight = mc.getWindow().getGuiScaledHeight();

        int x = screenWidth / 2;
        int y = screenHeight / 2 - 40;

        int w = mc.font.width(currentText);

        poseStack.pushPose();
        mc.font.drawShadow(poseStack, currentText, x - w / 2, y, currentColor);
        poseStack.popPose();

        currentText = null;
    }

}
