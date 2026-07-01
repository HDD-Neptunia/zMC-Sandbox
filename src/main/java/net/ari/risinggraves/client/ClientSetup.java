package net.ari.risinggraves.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.ari.risinggraves.block.wallbuy.WallbuyRenderer;
import net.ari.risinggraves.block.PerkMachineRenderer;
import net.ari.risinggraves.block.ModBlockEntities;
import net.ari.risinggraves.block.ModBlocks;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;



import net.ari.risinggraves.RisingGraves;

@Mod.EventBusSubscriber(
        modid = RisingGraves.MOD_ID,
        value = Dist.CLIENT,
        bus = Mod.EventBusSubscriber.Bus.MOD
)
public class ClientSetup {

    static {
        System.out.println("[CLIENTSETUP] CLASS LOADED");
    }

    @SubscribeEvent
    public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
        System.out.println("[CLIENTSETUP] Registering PERK_MACHINE renderer");
        event.registerBlockEntityRenderer(
                ModBlockEntities.PERK_MACHINE.get(),
                PerkMachineRenderer::new
        );

        event.registerBlockEntityRenderer(
                ModBlockEntities.WALLBUY.get(),
                WallbuyRenderer::new
        );
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.WALLBUY.get(), RenderType.cutout());
    }
}

