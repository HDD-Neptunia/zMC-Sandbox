package net.ari.risinggraves.block;

import net.ari.risinggraves.RisingGraves;
import net.ari.risinggraves.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, RisingGraves.MOD_ID);

    // -------------------------
    //   BLOCK REGISTRATIONS
    // -------------------------

    public static final RegistryObject<Block> MYSTERY_CRATE = registerBlock("mystery_crate",
            () -> new MysteryCrateBlock(BlockBehaviour.Properties.of(Material.WOOD)
                    .strength(8.0f)));

    public static final RegistryObject<Block> SAPPHIRE_ORE = registerBlock("sapphire_ore",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(4.0f, 3.0f)
                    .requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> RUBY_ORE = registerBlock("ruby_ore",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(4.0f, 3.0f)
                    .requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> CITRINE_ORE = registerBlock("citrine_ore",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(4.0f, 3.0f)
                    .requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> AMETHYST_ORE = registerBlock("amethyst_ore",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(4.0f, 3.0f)
                    .requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> BLOCK_OF_SAPPHIRE = registerBlock("block_of_sapphire",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(5.0f, 6.0f)
                    .requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> BLOCK_OF_AMETHYST = registerBlock("block_of_amethyst",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(5.0f, 6.0f)
                    .requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> BLOCK_OF_CITRINE = registerBlock("block_of_citrine",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(5.0f, 6.0f)
                    .requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> BLOCK_OF_RUBY = registerBlock("block_of_ruby",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(5.0f, 6.0f)
                    .requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> CUSTOM_SPAWNER = registerBlock("custom_spawner",
            () -> new CustomSpawnerBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(6.0f)
                    .noOcclusion()));

    public static final RegistryObject<Block> PERK_MACHINE = registerBlock("perk_machine",
            () -> new PerkMachineBlock(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(6.0f)
                    .noOcclusion()
        ));

    public static final RegistryObject<Block> WALLBUY = registerBlock("wallbuy",
            () -> new WallbuyBlock(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(6.0f)
                    .noOcclusion()));

    // -------------------------
    //   BLOCK ITEM REGISTRATION
    // -------------------------

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name,
                () -> new BlockItem(block.get(), new Item.Properties()));
    }

    // -------------------------
    //   REGISTER WITH EVENT BUS
    // -------------------------

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
