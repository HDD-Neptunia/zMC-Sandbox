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
        storage.add(new ItemStack(ModItems.CITRINE_SWORD.get()));
        storage.add(new ItemStack(ModItems.EMERALD_SWORD.get()));
        storage.add(new ItemStack(ModItems.SAPPHIRE_SWORD.get()));
        storage.add(new ItemStack(ModItems.RUBY_SWORD.get()));
        storage.add(new ItemStack(ModItems.AMETHYST_SWORD.get()));

        storage.add(new ItemStack(Items.WOODEN_AXE));
        storage.add(new ItemStack(Items.STONE_AXE));
        storage.add(new ItemStack(Items.IRON_AXE));
        storage.add(new ItemStack(Items.GOLDEN_AXE));
        storage.add(new ItemStack(Items.DIAMOND_AXE));
        storage.add(new ItemStack(ModItems.CITRINE_AXE.get()));
        storage.add(new ItemStack(ModItems.EMERALD_AXE.get()));
        storage.add(new ItemStack(ModItems.SAPPHIRE_AXE.get()));
        storage.add(new ItemStack(ModItems.RUBY_AXE.get()));
        storage.add(new ItemStack(ModItems.AMETHYST_AXE.get()));

        storage.add(new ItemStack(Items.DIAMOND_SHOVEL));
        storage.add(new ItemStack(Items.IRON_SHOVEL));
        storage.add(new ItemStack(Items.STONE_SHOVEL));
        storage.add(new ItemStack(Items.WOODEN_SHOVEL));
        storage.add(new ItemStack(Items.GOLDEN_SHOVEL));
        storage.add(new ItemStack(ModItems.CITRINE_SHOVEL.get()));
        storage.add(new ItemStack(ModItems.EMERALD_SHOVEL.get()));
        storage.add(new ItemStack(ModItems.SAPPHIRE_SHOVEL.get()));
        storage.add(new ItemStack(ModItems.RUBY_SHOVEL.get()));
        storage.add(new ItemStack(ModItems.AMETHYST_SHOVEL.get()));

        storage.add(new ItemStack(Items.WOODEN_PICKAXE));
        storage.add(new ItemStack(Items.STONE_PICKAXE));
        storage.add(new ItemStack(Items.IRON_PICKAXE));
        storage.add(new ItemStack(Items.GOLDEN_PICKAXE));
        storage.add(new ItemStack(Items.DIAMOND_PICKAXE));
        storage.add(new ItemStack(ModItems.CITRINE_PICKAXE.get()));
        storage.add(new ItemStack(ModItems.EMERALD_PICKAXE.get()));
        storage.add(new ItemStack(ModItems.SAPPHIRE_PICKAXE.get()));
        storage.add(new ItemStack(ModItems.RUBY_PICKAXE.get()));
        storage.add(new ItemStack(ModItems.AMETHYST_PICKAXE.get()));
    }


    public ItemStack getRandomItem() {
        return storage.get((int)(Math.random() * storage.size())).copy();
    }


}
