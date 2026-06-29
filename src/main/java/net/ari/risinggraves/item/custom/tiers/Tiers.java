package net.ari.risinggraves.item.custom.tiers;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.ari.risinggraves.item.ModItems;

public class Tiers {
    public static final Tier SAPPHIRE = new ForgeTier(
            5,
            2224,
            10.5f,
            6,
            13,
            null,
            () -> Ingredient.of(ModItems.SAPPHIRE.get()));

    public static final Tier AMETHYST = new ForgeTier(
            5,
            3063,
            9.5f,
            5,
            19,
            null,
            () -> Ingredient.of(ModItems.AMETHYST.get()));

    public static final Tier RUBY = new ForgeTier(
            5,
            1824,
            9.0f,
            7,
            12,
            null,
            () -> Ingredient.of(ModItems.RUBY.get()));

    public static final Tier CITRINE = new ForgeTier(
            5,
            1167,
            12.0f,
            8,
            10,
            null,
            () -> Ingredient.of(ModItems.CITRINE.get()));
}



