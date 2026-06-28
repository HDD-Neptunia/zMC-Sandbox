package net.ari.risinggraves.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.ari.risinggraves.waves.WaveManager;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class WaveDebugOverlay {

    @SubscribeEvent
    public static void onRenderOverlay(RenderGuiOverlayEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        PoseStack pose = event.getPoseStack();

        int x = 10;
        int y = 10;

        Gui gui = mc.gui;

        gui.drawString(pose, mc.font, "WAVE DEBUG", x, y, 0xFFFFFF);
        y += 12;

        gui.drawString(pose, mc.font, "Wave: " + WaveManager.getCurrentWave(), x, y, 0xFFFFFF);
        y += 12;

        gui.drawString(pose, mc.font, "Alive: " + WaveManager.getZombiesAlive(), x, y, 0xFFFFFF);
        y += 12;

        gui.drawString(pose, mc.font, "Left to spawn: " + WaveManager.getZombiesLeftToSpawn(), x, y, 0xFFFFFF);
        y += 12;

        gui.drawString(pose, mc.font, "Can spawn more: " + WaveManager.canSpawnMore(), x, y, 0xFFFFFF);
        y += 12;

        gui.drawString(pose, mc.font, "Wave in progress: " + WaveManager.isWaveInProgress(), x, y, 0xFFFFFF);
    }
}
