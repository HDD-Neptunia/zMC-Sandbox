package net.ari.risinggraves.block.crate;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;


import net.ari.risinggraves.item.ModItems;


public class ChestList {

    public List<ItemStack> storage = new ArrayList<>();

    public ChestList() {
        storage.add(new ItemStack(Items.WOODEN_SWORD));
        storage.add(new ItemStack(Items.STONE_SWORD));
        storage.add(new ItemStack(Items.IRON_SWORD));
        storage.add(new ItemStack(Items.GOLDEN_SWORD));
        storage.add(new ItemStack(Items.DIAMOND_SWORD));
        storage.add(new ItemStack(Items.NETHERITE_SWORD));
        storage.add(new ItemStack(ModItems.CITRINE_SWORD.get()));
        storage.add(new ItemStack(ModItems.SAPPHIRE_SWORD.get()));
        storage.add(new ItemStack(ModItems.RUBY_SWORD.get()));
        storage.add(new ItemStack(ModItems.AMETHYST_SWORD.get()));

        storage.add(new ItemStack(Items.WOODEN_AXE));
        storage.add(new ItemStack(Items.STONE_AXE));
        storage.add(new ItemStack(Items.IRON_AXE));
        storage.add(new ItemStack(Items.GOLDEN_AXE));
        storage.add(new ItemStack(Items.DIAMOND_AXE));
        storage.add(new ItemStack(Items.NETHERITE_AXE));
        storage.add(new ItemStack(ModItems.CITRINE_AXE.get()));
        storage.add(new ItemStack(ModItems.SAPPHIRE_AXE.get()));
        storage.add(new ItemStack(ModItems.RUBY_AXE.get()));
        storage.add(new ItemStack(ModItems.AMETHYST_AXE.get()));

        storage.add(new ItemStack(Items.DIAMOND_SHOVEL));
        storage.add(new ItemStack(Items.IRON_SHOVEL));
        storage.add(new ItemStack(Items.STONE_SHOVEL));
        storage.add(new ItemStack(Items.WOODEN_SHOVEL));
        storage.add(new ItemStack(Items.GOLDEN_SHOVEL));
        storage.add(new ItemStack(Items.NETHERITE_SHOVEL));
        storage.add(new ItemStack(ModItems.CITRINE_SHOVEL.get()));
        storage.add(new ItemStack(ModItems.SAPPHIRE_SHOVEL.get()));
        storage.add(new ItemStack(ModItems.RUBY_SHOVEL.get()));
        storage.add(new ItemStack(ModItems.AMETHYST_SHOVEL.get()));

        storage.add(new ItemStack(Items.WOODEN_PICKAXE));
        storage.add(new ItemStack(Items.STONE_PICKAXE));
        storage.add(new ItemStack(Items.IRON_PICKAXE));
        storage.add(new ItemStack(Items.GOLDEN_PICKAXE));
        storage.add(new ItemStack(Items.DIAMOND_PICKAXE));
        storage.add(new ItemStack(Items.NETHERITE_PICKAXE));
        storage.add(new ItemStack(ModItems.CITRINE_PICKAXE.get()));
        storage.add(new ItemStack(ModItems.SAPPHIRE_PICKAXE.get()));
        storage.add(new ItemStack(ModItems.RUBY_PICKAXE.get()));
        storage.add(new ItemStack(ModItems.AMETHYST_PICKAXE.get()));

        storage.add(new ItemStack(Items.IRON_HELMET));
        storage.add(new ItemStack(Items.GOLDEN_HELMET));
        storage.add(new ItemStack(Items.DIAMOND_HELMET));
        storage.add(new ItemStack(Items.NETHERITE_HELMET));
        storage.add(new ItemStack(ModItems.CITRINE_HELMET.get()));
        storage.add(new ItemStack(ModItems.SAPPHIRE_HELMET.get()));
        storage.add(new ItemStack(ModItems.RUBY_HELMET.get()));
        storage.add(new ItemStack(ModItems.AMETHYST_HELMET.get()));

        storage.add(new ItemStack(Items.IRON_CHESTPLATE));
        storage.add(new ItemStack(Items.GOLDEN_CHESTPLATE));
        storage.add(new ItemStack(Items.DIAMOND_CHESTPLATE));
        storage.add(new ItemStack(Items.NETHERITE_CHESTPLATE));
        storage.add(new ItemStack(ModItems.CITRINE_CHESTPLATE.get()));
        storage.add(new ItemStack(ModItems.SAPPHIRE_CHESTPLATE.get()));
        storage.add(new ItemStack(ModItems.RUBY_CHESTPLATE.get()));
        storage.add(new ItemStack(ModItems.AMETHYST_CHESTPLATE.get()));

        storage.add(new ItemStack(Items.IRON_LEGGINGS));
        storage.add(new ItemStack(Items.GOLDEN_LEGGINGS));
        storage.add(new ItemStack(Items.DIAMOND_LEGGINGS));
        storage.add(new ItemStack(Items.NETHERITE_LEGGINGS));
        storage.add(new ItemStack(ModItems.CITRINE_LEGGINGS.get()));
        storage.add(new ItemStack(ModItems.SAPPHIRE_LEGGINGS.get()));
        storage.add(new ItemStack(ModItems.RUBY_LEGGINGS.get()));
        storage.add(new ItemStack(ModItems.AMETHYST_LEGGINGS.get()));

        storage.add(new ItemStack(Items.IRON_BOOTS));
        storage.add(new ItemStack(Items.GOLDEN_BOOTS));
        storage.add(new ItemStack(Items.DIAMOND_BOOTS));
        storage.add(new ItemStack(Items.NETHERITE_BOOTS));
        storage.add(new ItemStack(ModItems.CITRINE_BOOTS.get()));
        storage.add(new ItemStack(ModItems.SAPPHIRE_BOOTS.get()));
        storage.add(new ItemStack(ModItems.RUBY_BOOTS.get()));
        storage.add(new ItemStack(ModItems.AMETHYST_BOOTS.get()));
    }

    public ItemStack getRandomItem() {
        return storage.get((int)(Math.random() * storage.size())).copy();
    }
}

