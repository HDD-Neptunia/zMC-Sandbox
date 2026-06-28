package net.ari.risinggraves.item;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.ari.risinggraves.RisingGraves;



public class ModItems {
    public static final DeferredRegister<Item>  ITEMS =
           DeferredRegister.create(ForgeRegistries.ITEMS, RisingGraves.MOD_ID);


public static final RegistryObject<Item> LIGHTER = ITEMS.register ( "lighter",
        () -> new Item(new Item.Properties()));




public static final RegistryObject<Item> SAPPHIRE = ITEMS.register ("sapphire",
        () -> new Item(new Item.Properties()));

public static final RegistryObject<SwordItem> SAPPHIRE_SWORD = ITEMS.register ("sapphire_sword",
            () -> new SwordItem(Tiers.NETHERITE, 6, 2.7f, new Item.Properties()));

public static final RegistryObject<ShovelItem> SAPPHIRE_SHOVEL = ITEMS.register ("sapphire_shovel",
            () -> new ShovelItem(Tiers.NETHERITE, 6, 2.7f, new Item.Properties()));
        
public static final RegistryObject<AxeItem> SAPPHIRE_AXE = ITEMS.register ("sapphire_axe",
            () -> new AxeItem(Tiers.NETHERITE, 6, 2.7f, new Item.Properties()));

public static final RegistryObject<PickaxeItem> SAPPHIRE_PICKAXE = ITEMS.register ("sapphire_pickaxe",
            () -> new PickaxeItem(Tiers.NETHERITE, 6, 2.7f, new Item.Properties()));




public static final RegistryObject<SwordItem> EMERALD_SWORD = ITEMS.register ("emerald_sword",
            () -> new SwordItem(Tiers.NETHERITE, 6, 2.7f, new Item.Properties()));

public static final RegistryObject<ShovelItem> EMERALD_SHOVEL = ITEMS.register ("emerald_shovel",
            () -> new ShovelItem(Tiers.NETHERITE, 6, 2.7f, new Item.Properties()));
        
public static final RegistryObject<AxeItem> EMERALD_AXE = ITEMS.register ("emerald_axe",
            () -> new AxeItem(Tiers.NETHERITE, 6, 2.7f, new Item.Properties()));

public static final RegistryObject<PickaxeItem> EMERALD_PICKAXE = ITEMS.register ("emerald_pickaxe",
            () -> new PickaxeItem(Tiers.NETHERITE, 6, 2.7f, new Item.Properties()));





public static final RegistryObject<Item> AMETHYST = ITEMS.register ("amethyst",
        () -> new Item(new Item.Properties()));

public static final RegistryObject<SwordItem> AMETHYST_SWORD = ITEMS.register ("amethyst_sword",
            () -> new SwordItem(Tiers.NETHERITE, 6, 2.7f, new Item.Properties()));

public static final RegistryObject<ShovelItem> AMETHYST_SHOVEL = ITEMS.register ("amethyst_shovel",
            () -> new ShovelItem(Tiers.NETHERITE, 6, 2.7f, new Item.Properties()));
        
public static final RegistryObject<AxeItem> AMETHYST_AXE = ITEMS.register ("amethyst_axe",
            () -> new AxeItem(Tiers.NETHERITE, 6, 2.7f, new Item.Properties()));

public static final RegistryObject<PickaxeItem> AMETHYST_PICKAXE = ITEMS.register ("amethyst_pickaxe",
            () -> new PickaxeItem(Tiers.NETHERITE, 6, 2.7f, new Item.Properties()));





public static final RegistryObject<Item> RUBY = ITEMS.register ("ruby",
        () -> new Item(new Item.Properties()));

public static final RegistryObject<SwordItem> RUBY_SWORD = ITEMS.register ("ruby_sword",
            () -> new SwordItem(Tiers.NETHERITE, 6, 2.7f, new Item.Properties()));

public static final RegistryObject<ShovelItem> RUBY_SHOVEL = ITEMS.register ("ruby_shovel",
            () -> new ShovelItem(Tiers.NETHERITE, 6, 2.7f, new Item.Properties()));
        
public static final RegistryObject<AxeItem> RUBY_AXE = ITEMS.register ("ruby_axe",
            () -> new AxeItem(Tiers.NETHERITE, 6, 2.7f, new Item.Properties()));

public static final RegistryObject<PickaxeItem> RUBY_PICKAXE = ITEMS.register ("ruby_pickaxe",
            () -> new PickaxeItem(Tiers.NETHERITE, 6, 2.7f, new Item.Properties()));





public static final RegistryObject<Item> CITRINE = ITEMS.register ("citrine",
        () -> new Item(new Item.Properties()));

public static final RegistryObject<SwordItem> CITRINE_SWORD = ITEMS.register ("citrine_sword",
            () -> new SwordItem(Tiers.NETHERITE, 6, 2.7f, new Item.Properties()));

public static final RegistryObject<ShovelItem> CITRINE_SHOVEL = ITEMS.register ("citrine_shovel",
            () -> new ShovelItem(Tiers.NETHERITE, 6, 2.7f, new Item.Properties()));
        
public static final RegistryObject<AxeItem> CITRINE_AXE = ITEMS.register ("citrine_axe",
            () -> new AxeItem(Tiers.NETHERITE, 6, 2.7f, new Item.Properties()));

public static final RegistryObject<PickaxeItem> CITRINE_PICKAXE = ITEMS.register ("citrine_pickaxe",
            () -> new PickaxeItem(Tiers.NETHERITE, 6, 2.7f, new Item.Properties()));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}

