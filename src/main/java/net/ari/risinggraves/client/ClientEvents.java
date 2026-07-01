package net.ari.risinggraves.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.event.TickEvent;

import net.minecraft.client.Minecraft;

import net.ari.risinggraves.RisingGraves;
import net.ari.risinggraves.networking.Networking;
import net.ari.risinggraves.networking.SprintKeyPacket;

@Mod.EventBusSubscriber(
        modid = RisingGraves.MOD_ID,
        value = Dist.CLIENT,
        bus = Mod.EventBusSubscriber.Bus.FORGE
)
public class ClientEvents {

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        boolean sprintKey = mc.options.keySprint.isDown();

        Networking.CHANNEL.sendToServer(new SprintKeyPacket(sprintKey));
    }
}
