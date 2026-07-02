package net.ari.risinggraves.registry;

import net.minecraft.resources.ResourceLocation;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;

import net.minecraft.world.level.block.Block;


import net.ari.risinggraves.RisingGraves;


public class ModTags {

    public static class Blocks {
        public static final TagKey<Block> GEM_BLOCKS =
                BlockTags.create(new ResourceLocation(RisingGraves.MOD_ID, "gem_blocks"));

        public static final TagKey<Block> ORE_BLOCKS =
                BlockTags.create(new ResourceLocation(RisingGraves.MOD_ID, "ore_blocks"));
    }
}
