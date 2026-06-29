package net.ari.risinggraves.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.ari.risinggraves.block.wallbuy.WallbuyRenderer;
import net.ari.risinggraves.block.perks.PerkMachineRenderer;
import net.ari.risinggraves.block.ModBlockEntities;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {

    @SubscribeEvent
    public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {

        event.registerBlockEntityRenderer(
                ModBlockEntities.PERK_MACHINE.get(),
                PerkMachineRenderer::new
        );

        event.registerBlockEntityRenderer(
                ModBlockEntities.WALLBUY.get(),
                WallbuyRenderer::new
        );
    }
}
