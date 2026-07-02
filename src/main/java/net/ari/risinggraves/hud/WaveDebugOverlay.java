package net.ari.risinggraves.hud;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import net.minecraft.server.level.ServerPlayer;


import net.ari.risinggraves.waves.WaveManager;
import net.ari.risinggraves.scoreboard.SidebarScoreboard;


@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class WaveDebugOverlay {

    @SubscribeEvent
    public static void onRenderOverlay(RenderGuiOverlayEvent.Post event) {
        if (!WaveManager.wavesActive) {
            return;
        }
        
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        PoseStack pose = event.getPoseStack();
        Gui gui = mc.gui;

        int x = 10;
        int y = 10;

        int bgColor = 0x08000000;
        int borderColor = 0x33FFFFFF;
        int titleColor = 0xFFF2C76E;
        int textColor = 0xFFEEEEEE;


        Gui.fill(pose, x - 6, y - 6, x + 70, y + 46, bgColor);

        Gui.fill(pose, x - 6, y - 6, x + 70, y - 5, borderColor);
        Gui.fill(pose, x - 6, y + 45, x + 70, y + 46, borderColor);
        Gui.fill(pose, x - 6, y - 6, x - 5, y + 46, borderColor);
        Gui.fill(pose, x + 69, y - 6, x + 70, y + 46, borderColor);

        // ⭐ Title
        gui.drawString(pose, mc.font, "§6Zombies", x, y, titleColor);
        y += 14;

        // ⭐ Wave
        gui.drawString(pose, mc.font, "Wave: " + WaveManager.getCurrentWave(), x, y, textColor);
        y += 12;

        // ⭐ Alive
        gui.drawString(pose, mc.font, "Alive: " + WaveManager.getZombiesAlive(), x, y, textColor);
    }
}
