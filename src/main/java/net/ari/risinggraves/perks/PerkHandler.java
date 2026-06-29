package net.ari.risinggraves.perks;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.nbt.CompoundTag;

public class PerkHandler {

    private static final String PERK_TAG = "risinggraves_perks";

    public static boolean hasPerk(Player player, PerkType perk) {
        CompoundTag tag = player.getPersistentData().getCompound(PERK_TAG);
        return tag.getBoolean(perk.name());
    }

    public static void givePerk(Player player, PerkType perk) {
        CompoundTag tag = player.getPersistentData().getCompound(PERK_TAG);
        tag.putBoolean(perk.name(), true);
        player.getPersistentData().put(PERK_TAG, tag);

        applyEffect(player, perk);
    }

    private static void applyEffect(Player player, PerkType perk) {
        switch (perk) {

            case SPEED -> {
                player.addEffect(new MobEffectInstance(
                        MobEffects.MOVEMENT_SPEED,
                        Integer.MAX_VALUE,
                        1,
                        false, false
                ));
            }

            case SURVIVABILITY -> {
                AttributeInstance health = player.getAttribute(Attributes.MAX_HEALTH);
                if (health != null) {
                    health.addPermanentModifier(new AttributeModifier(
                            "survivability_bonus",
                            8.0,
                            AttributeModifier.Operation.ADDITION
                    ));
                }
                player.setHealth(player.getMaxHealth());
            }

            case RAPID_FIRE -> {
                AttributeInstance attackSpeed = player.getAttribute(Attributes.ATTACK_SPEED);
                if (attackSpeed != null) {
                    attackSpeed.addPermanentModifier(new AttributeModifier(
                            "rapid_fire_bonus",
                            0.2,
                            AttributeModifier.Operation.MULTIPLY_TOTAL
                    ));
                }
            }

            case MIGHT -> {
                AttributeInstance damage = player.getAttribute(Attributes.ATTACK_DAMAGE);
                if (damage != null) {
                    damage.addPermanentModifier(new AttributeModifier(
                            "might_bonus",
                            3.0,
                            AttributeModifier.Operation.ADDITION
                    ));
                }
            }
        }
    }
}
