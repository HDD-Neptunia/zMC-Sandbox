package net.ari.risinggraves.item.custom.tiers;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.ari.risinggraves.item.ModItems;

public class Tiers {
    public static final Tier Sapphire = new ForgeTier(
            5,
            3863,
            1.3f,
            5,
            350,
            null,
            () -> Ingredient.of(ModItems.SAPPHIRE.get()));

    public static final Tier Amethyst = new ForgeTier(
            5,
            3863,
            1.3f,
            5,
            350,
            null,
            () -> Ingredient.of(ModItems.AMETHYST.get()));

    public static final Tier Ruby = new ForgeTier(
            5,
            3863,
            1.3f,
            5,
            350,
            null,
            () -> Ingredient.of(ModItems.RUBY.get()));

    public static final Tier Citrine = new ForgeTier(
            5,
            3863,
            1.3f,
            5,
            350,
            null,
            () -> Ingredient.of(ModItems.CITRINE.get()));
}



