package net.ari.risinggraves.armour;

import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;

import java.util.function.Supplier;

import net.ari.risinggraves.RisingGraves;
import net.ari.risinggraves.item.ModItems;

public class ModArmourMaterials {

    public enum SapphireArmorMaterial implements ArmorMaterial {
        SAPPHIRE("sapphire", 28, new int[]{3, 7, 8, 3}, 13,
                SoundEvents.ARMOR_EQUIP_DIAMOND, 2.0F, 0.1F, () -> Ingredient.of(ModItems.SAPPHIRE.get()));

        private static final int[] HEALTH_PER_SLOT = new int[]{13, 15, 16, 11};
        private final String name;
        private final int durabilityMultiplier;
        private final int[] slotProtections;
        private final int enchantmentValue;
        private final SoundEvent equipSound;
        private final float toughness;
        private final float knockbackResistance;
        private final Supplier<Ingredient> repairIngredient;

        SapphireArmorMaterial(String name, int durabilityMultiplier, int[] slotProtections,
                            int enchantmentValue, SoundEvent equipSound,
                            float toughness, float knockbackResistance,
                            Supplier<Ingredient> repairIngredient) {
            this.name = name;
            this.durabilityMultiplier = durabilityMultiplier;
            this.slotProtections = slotProtections;
            this.enchantmentValue = enchantmentValue;
            this.equipSound = equipSound;
            this.toughness = toughness;
            this.knockbackResistance = knockbackResistance;
            this.repairIngredient = repairIngredient;
        }

        @Override
        public int getDurabilityForSlot(EquipmentSlot slot) {
            return HEALTH_PER_SLOT[slot.getIndex()] * durabilityMultiplier;
        }

        @Override
        public int getDefenseForSlot(EquipmentSlot slot) {
            return slotProtections[slot.getIndex()];
        }

        @Override
        public int getEnchantmentValue() {
            return enchantmentValue;
        }

        @Override
        public SoundEvent getEquipSound() {
            return equipSound;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return repairIngredient.get();
        }

        @Override
        public String getName() {
            return RisingGraves.MOD_ID + ":" + name;
        }

        @Override
        public float getToughness() {
            return toughness;
        }

        @Override
        public float getKnockbackResistance() {
            return knockbackResistance;
        }
    }


    public enum RubyArmorMaterial implements ArmorMaterial {
        RUBY("ruby", 22, new int[]{4, 7, 9, 4}, 19,
                SoundEvents.ARMOR_EQUIP_DIAMOND, 2.5F, 0.05F, () -> Ingredient.of(ModItems.RUBY.get()));

        private static final int[] HEALTH_PER_SLOT = new int[]{13, 15, 16, 11};
        private final String name;
        private final int durabilityMultiplier;
        private final int[] slotProtections;
        private final int enchantmentValue;
        private final SoundEvent equipSound;
        private final float toughness;
        private final float knockbackResistance;
        private final Supplier<Ingredient> repairIngredient;

        RubyArmorMaterial(String name, int durabilityMultiplier, int[] slotProtections,
                            int enchantmentValue, SoundEvent equipSound,
                            float toughness, float knockbackResistance,
                            Supplier<Ingredient> repairIngredient) {
            this.name = name;
            this.durabilityMultiplier = durabilityMultiplier;
            this.slotProtections = slotProtections;
            this.enchantmentValue = enchantmentValue;
            this.equipSound = equipSound;
            this.toughness = toughness;
            this.knockbackResistance = knockbackResistance;
            this.repairIngredient = repairIngredient;
        }

        @Override
        public int getDurabilityForSlot(EquipmentSlot slot) {
            return HEALTH_PER_SLOT[slot.getIndex()] * durabilityMultiplier;
        }

        @Override
        public int getDefenseForSlot(EquipmentSlot slot) {
            return slotProtections[slot.getIndex()];
        }

        @Override
        public int getEnchantmentValue() {
            return enchantmentValue;
        }

        @Override
        public SoundEvent getEquipSound() {
            return equipSound;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return repairIngredient.get();
        }

        @Override
        public String getName() {
            return RisingGraves.MOD_ID + ":" + name;
        }

        @Override
        public float getToughness() {
            return toughness;
        }

        @Override
        public float getKnockbackResistance() {
            return knockbackResistance;
        }
    }

    public enum CitrineArmorMaterial implements ArmorMaterial {
        CITRINE("citrine", 18, new int[]{2, 5, 6, 2}, 10,
                SoundEvents.ARMOR_EQUIP_DIAMOND, 1.0F, 0.0F, () -> Ingredient.of(ModItems.CITRINE.get()));

        private static final int[] HEALTH_PER_SLOT = new int[]{13, 15, 16, 11};
        private final String name;
        private final int durabilityMultiplier;
        private final int[] slotProtections;
        private final int enchantmentValue;
        private final SoundEvent equipSound;
        private final float toughness;
        private final float knockbackResistance;
        private final Supplier<Ingredient> repairIngredient;

        CitrineArmorMaterial(String name, int durabilityMultiplier, int[] slotProtections,
                            int enchantmentValue, SoundEvent equipSound,
                            float toughness, float knockbackResistance,
                            Supplier<Ingredient> repairIngredient) {
            this.name = name;
            this.durabilityMultiplier = durabilityMultiplier;
            this.slotProtections = slotProtections;
            this.enchantmentValue = enchantmentValue;
            this.equipSound = equipSound;
            this.toughness = toughness;
            this.knockbackResistance = knockbackResistance;
            this.repairIngredient = repairIngredient;
        }

        @Override
        public int getDurabilityForSlot(EquipmentSlot slot) {
            return HEALTH_PER_SLOT[slot.getIndex()] * durabilityMultiplier;
        }

        @Override
        public int getDefenseForSlot(EquipmentSlot slot) {
            return slotProtections[slot.getIndex()];
        }

        @Override
        public int getEnchantmentValue() {
            return enchantmentValue;
        }

        @Override
        public SoundEvent getEquipSound() {
            return equipSound;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return repairIngredient.get();
        }

        @Override
        public String getName() {
            return RisingGraves.MOD_ID + ":" + name;
        }

        @Override
        public float getToughness() {
            return toughness;
        }

        @Override
        public float getKnockbackResistance() {
            return knockbackResistance;
        }
    }

    public enum AmethystArmorMaterial implements ArmorMaterial {
        AMETHYST("amethyst", 30, new int[]{3, 6, 7, 3}, 19,
                SoundEvents.ARMOR_EQUIP_DIAMOND, 2.5F, 0.05F, () -> Ingredient.of(ModItems.AMETHYST.get()));

        private static final int[] HEALTH_PER_SLOT = new int[]{13, 15, 16, 11};
        private final String name;
        private final int durabilityMultiplier;
        private final int[] slotProtections;
        private final int enchantmentValue;
        private final SoundEvent equipSound;
        private final float toughness;
        private final float knockbackResistance;
        private final Supplier<Ingredient> repairIngredient;

        AmethystArmorMaterial(String name, int durabilityMultiplier, int[] slotProtections,
                            int enchantmentValue, SoundEvent equipSound,
                            float toughness, float knockbackResistance,
                            Supplier<Ingredient> repairIngredient) {
            this.name = name;
            this.durabilityMultiplier = durabilityMultiplier;
            this.slotProtections = slotProtections;
            this.enchantmentValue = enchantmentValue;
            this.equipSound = equipSound;
            this.toughness = toughness;
            this.knockbackResistance = knockbackResistance;
            this.repairIngredient = repairIngredient;
        }

        @Override
        public int getDurabilityForSlot(EquipmentSlot slot) {
            return HEALTH_PER_SLOT[slot.getIndex()] * durabilityMultiplier;
        }

        @Override
        public int getDefenseForSlot(EquipmentSlot slot) {
            return slotProtections[slot.getIndex()];
        }

        @Override
        public int getEnchantmentValue() {
            return enchantmentValue;
        }

        @Override
        public SoundEvent getEquipSound() {
            return equipSound;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return repairIngredient.get();
        }

        @Override
        public String getName() {
            return RisingGraves.MOD_ID + ":" + name;
        }

        @Override
        public float getToughness() {
            return toughness;
        }

        @Override
        public float getKnockbackResistance() {
            return knockbackResistance;
        }
    }



}