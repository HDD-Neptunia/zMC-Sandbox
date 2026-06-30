package net.ari.risinggraves;


import com.mojang.logging.LogUtils;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.ari.risinggraves.block.ModBlocks;
import net.ari.risinggraves.item.ModItems;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.ari.risinggraves.waves.WaveCommand;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.ari.risinggraves.zombies.CZombieRenderer;
import net.ari.risinggraves.scoreboard.SidebarScoreboard;
import net.ari.risinggraves.scoreboard.ScoreboardHandler;
import net.ari.risinggraves.barrier.ModMenus;
import net.minecraft.client.gui.screens.MenuScreens;
import net.ari.risinggraves.networking.Networking;
import net.ari.risinggraves.perks.PerkSelectionScreen;
import net.ari.risinggraves.barrier.ModMenus;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.ari.risinggraves.block.wallbuy.WallbuyCostScreen;
import net.minecraft.client.renderer.RenderType;
import net.ari.risinggraves.barrier.ResetBlockadesCommand;


import net.ari.risinggraves.init.ModEntities;

import org.slf4j.Logger;

import net.ari.risinggraves.barrier.CostScreen;
import net.ari.risinggraves.block.ModBlockEntities;
import net.ari.risinggraves.waves.WaveManager;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(RisingGraves.MOD_ID)
public class RisingGraves
{
    public static final String MOD_ID = "risinggraves";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static boolean zombiesMode = false;
    public RisingGraves()
    {
        
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModMenus.MENUS.register(modEventBus);

        ModEntities.ENTITIES.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(WaveManager.class);

        ModBlockEntities.BLOCK_ENTITIES.register(modEventBus);

        // Register the commonSetup method for mod loading
        modEventBus.addListener(this::commonSetup);

        //Register the main class event for the Forge event bus
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        MinecraftForge.EVENT_BUS.addListener(this::registerCommands);

    }

    @SubscribeEvent
    public void onServerStarted(ServerStartedEvent event) {
        ScoreboardHandler.INSTANCE.setServer(event.getServer());

    }

    private void registerCommands(RegisterCommandsEvent event) {
        WaveCommand.register(event.getDispatcher());
        ResetBlockadesCommand.register(event.getDispatcher());
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        System.out.println("COMMON SETUP FIRED");
        event.enqueueWork(() -> {
            System.out.println("REGISTERING NETWORKING");
            Networking.register();
        });
    }


    private void addCreative(CreativeModeTabEvent.BuildContents event) {
        if (event.getTab() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(ModBlocks.SAPPHIRE_ORE);
            event.accept(ModBlocks.BLOCK_OF_SAPPHIRE);
            event.accept(ModBlocks.RUBY_ORE);
            event.accept(ModBlocks.BLOCK_OF_RUBY);
            event.accept(ModBlocks.CITRINE_ORE);
            event.accept(ModBlocks.BLOCK_OF_CITRINE);
            event.accept(ModBlocks.AMETHYST_ORE);
            event.accept(ModBlocks.BLOCK_OF_AMETHYST);
            event.accept(ModBlocks.MYSTERY_CRATE);
            event.accept(ModBlocks.PERK_MACHINE);
            event.accept(ModBlocks.WALLBUY);
        }


        {
            if (event.getTab() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
                event.accept(ModItems.BLOCKADE_WAND);
                event.accept(ModItems.SAPPHIRE_AXE);
                event.accept(ModItems.SAPPHIRE_PICKAXE);
                event.accept(ModItems.SAPPHIRE_SHOVEL);
                event.accept(ModItems.RUBY_AXE);
                event.accept(ModItems.RUBY_PICKAXE);
                event.accept(ModItems.RUBY_SHOVEL);
                event.accept(ModItems.CITRINE_AXE);
                event.accept(ModItems.CITRINE_PICKAXE);
                event.accept(ModItems.CITRINE_SHOVEL);
                event.accept(ModItems.AMETHYST_AXE);
                event.accept(ModItems.AMETHYST_PICKAXE);
                event.accept(ModItems.AMETHYST_SHOVEL);
                event.accept(ModItems.EMERALD_AXE);
                event.accept(ModItems.EMERALD_PICKAXE);
                event.accept(ModItems.EMERALD_SHOVEL);
            }
            if (event.getTab() == CreativeModeTabs.COMBAT) {
                event.accept(ModItems.SAPPHIRE_SWORD);
                event.accept(ModItems.AMETHYST_SWORD);
                event.accept(ModItems.CITRINE_SWORD);
                event.accept(ModItems.RUBY_SWORD);
                event.accept(ModItems.EMERALD_SWORD);
            }
            if (event.getTab() == CreativeModeTabs.INGREDIENTS) {
                event.accept(ModItems.SAPPHIRE);
                event.accept(ModItems.CITRINE);
                event.accept(ModItems.RUBY);
                event.accept(ModItems.AMETHYST);


                /*Create own tabs for mod items*/
            }

        }
    }
    // You can use SubscribeEvent and let the Event Bus discover methods to call

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)


    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            MenuScreens.register(ModMenus.COST_MENU.get(), CostScreen::new);
            MenuScreens.register(ModMenus.PERK_MENU.get(), PerkSelectionScreen::new);
            MenuScreens.register(ModMenus.WALLBUY_MENU.get(), WallbuyCostScreen::new);

        }

    @SubscribeEvent
        public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(ModEntities.CZOMBIE.get(), CZombieRenderer::new);
        }
    }
}
