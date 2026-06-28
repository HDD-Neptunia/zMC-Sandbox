package net.ari.risinggraves.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.ari.risinggraves.RisingGraves;
import net.ari.risinggraves.item.ModItems;

import net.ari.risinggraves.block.CustomSpawnerBlock;

import java.util.function.Supplier;


public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, RisingGraves.MOD_ID);

    //Blocks Here

    public static final RegistryObject<Block> MYSTERY_CRATE = registerBlock("mystery_crate",
        () -> new MysteryCrateBlock(BlockBehaviour.Properties.of(Material.WOOD)
                .strength(8f)));


    public static final  RegistryObject<Block> SAPPHIRE_ORE = registerBlock( "sapphire_ore",
         () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                .strength(6f).requiresCorrectToolForDrops()));

        

    public static final RegistryObject<Block> BLOCK_OF_SAPPHIRE = registerBlock("block_of_sapphire",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                .strength(6f).requiresCorrectToolForDrops()));


    public static final RegistryObject<Block> CUSTOM_SPAWNER =
        registerBlock("custom_spawner",
                () -> new CustomSpawnerBlock(BlockBehaviour.Properties.of(Material.STONE)
                        .strength(4f)
                        .noOcclusion()));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
    RegistryObject<T> toReturn = BLOCKS.register(name, block);
    registerBlockItem(name, toReturn);
    return toReturn;
    }
    //T can be anything that is a block


private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
    return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
            new Item.Properties()));
}


    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
