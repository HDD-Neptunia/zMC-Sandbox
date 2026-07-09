package net.ari.risinggraves.block.crate;

import java.util.List;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;

import net.minecraft.world.level.Level;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;


public class EnchantmentLogic {


    private static final List<Enchantment> POOL_1 = List.of(
            Enchantments.UNBREAKING,
            Enchantments.BLOCK_EFFICIENCY,
            Enchantments.FIRE_PROTECTION
    );

    private static final List<Enchantment> POOL_2 = List.of(
            Enchantments.PROJECTILE_PROTECTION,
            Enchantments.FIRE_ASPECT,
            Enchantments.ALL_DAMAGE_PROTECTION,
            Enchantments.MOB_LOOTING,
            Enchantments.KNOCKBACK,
            Enchantments.SHARPNESS
    );


    private static final List<Enchantment> POOL_3 = List.of(
            Enchantments.BLOCK_FORTUNE,
            Enchantments.SWEEPING_EDGE,
            Enchantments.MENDING
    );

    private static Enchantment weightedRandom(Level level, List<Enchantment> pool) {
        return pool.get(level.random.nextInt(pool.size()));
    }

    private static Enchantment rollFromPools(Level level, float p1, float p2, float p3) {
        float roll = level.random.nextFloat();

        if (roll < p1) {
            return POOL_1.get(level.random.nextInt(POOL_1.size()));
        }
        if (roll < p1 + p2) {
            return POOL_2.get(level.random.nextInt(POOL_2.size()));
        }
        return POOL_3.get(level.random.nextInt(POOL_3.size()));
    }

    public static void rollCrateEnchants(Level level, ItemStack reward) {

        float roll = level.random.nextFloat();

        if (roll < 0.10f) {
            applyWildTier(level, reward);
        }
        else if (roll < 0.30f) {
            applyRareTierCrate(level, reward);
        }
        else if (roll < 0.50f) {
            applyUncommonTierCrate(level, reward);
        }
    }



    private static List<ItemStack> applyUncommonTierCrate(Level level, ItemStack reward) {

        int enchantCount = 1 + level.random.nextInt(3);

        for (int i = 0; i < enchantCount; i++) {
            int enchantLevel = rollUncommonLevel(level);
            Enchantment ench = rollFromPools(level, 0.50f, 0.35f, 0.10f);
            reward.enchant(ench, enchantLevel);
        }
        return List.of(reward);
    }

    private static int rollUncommonLevel(Level level) {
        float roll = level.random.nextFloat();

        if (roll < 0.5f) {
            return 1 + level.random.nextInt(2);
        }
        if (roll < 0.75f) {
            return 1 + level.random.nextInt(3);
        }
        if (roll < 0.95f) {
            return 2 + level.random.nextInt(2);
        }

        return 1;
    }



    private static List<ItemStack> applyRareTierCrate(Level level, ItemStack reward) {

        int enchantCount = 2 + level.random.nextInt(3);

        for (int i = 0; i < enchantCount; i++) {
            int enchantLevel = rollRareLevel(level);
            Enchantment ench = rollFromPools(level, 0.35f, 0.40f, 0.25f);

            reward.enchant(ench, enchantLevel);
        }
        return List.of(reward);
    }

    private static int rollRareLevel(Level level) {
        float roll = level.random.nextFloat();

        if (roll < 0.05f) {
            return 3 + level.random.nextInt(4);
        }
        if (roll < 0.30f) {
            return 3 + level.random.nextInt(3);
        }
        if (roll < 0.75f) {
            return 2 + level.random.nextInt(4);
        }

        return 1;
    }



    private static List<ItemStack> applyWildTier(Level level, ItemStack reward) {

        int enchantCount = 4 + level.random.nextInt(5); // 4-5 enchants

        for (int i = 0; i < enchantCount; i++) {
            int enchantLevel = rollWildLevel(level);
            Enchantment ench = rollFromPools(level, 0.20f, 0.40f, 0.40f);
            reward.enchant(ench, enchantLevel);
        }
        return List.of(reward);
    }

    private static int rollWildLevel(Level level) {
        float roll = level.random.nextFloat();

        if (roll < 0.25f) {
            return 4 + level.random.nextInt(5);
        }
        if (roll < 0.30f) {
            return 3 + level.random.nextInt(5);
        }
        if (roll < 0.45f) {
            return 2 + level.random.nextInt(4);
        }

        return 1;
    }

    private static Enchantment randomEnchantment(Level level) {
        return BuiltInRegistries.ENCHANTMENT.getRandom(level.random)
                .map(Holder::value)
                .orElse(Enchantments.UNBREAKING);
    }
}
