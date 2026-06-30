package net.ari.risinggraves.hud;

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
        if (!WaveManager.wavesActive) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        PoseStack pose = event.getPoseStack();
        Gui gui = mc.gui;

        int x = 10;
        int y = 10;

        int bgColor = 0x44000000;     // translucent black
        int borderColor = 0x55FFFFFF; // faint white border
        int titleColor = 0xE6C46A;    // soft gold
        int textColor = 0xDDDDDD;     // soft grey

        // ⭐ Background box (Forge 1.19.3 way)
        Gui.fill(pose, x - 6, y - 6, x + 130, y + 46, bgColor);

        // ⭐ Border (Forge 1.19.3 way)
        Gui.fill(pose, x - 6, y - 6, x + 130, y - 5, borderColor); // top
        Gui.fill(pose, x - 6, y + 45, x + 130, y + 46, borderColor); // bottom
        Gui.fill(pose, x - 6, y - 6, x - 5, y + 46, borderColor); // left
        Gui.fill(pose, x + 129, y - 6, x + 130, y + 46, borderColor); // right

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
