package net.ari.risinggraves.perks;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.nbt.CompoundTag;
import java.util.UUID;


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

                    // Remove old modifier if it exists
                    UUID SURVIVABILITY_UUID = UUID.fromString("11111111-1111-1111-1111-111111111111");
                    health.removeModifier(SURVIVABILITY_UUID);

                    // Add new modifier
                    health.addPermanentModifier(new AttributeModifier(
                            SURVIVABILITY_UUID,
                            "survivability_bonus",
                            8.0,
                            AttributeModifier.Operation.ADDITION
                    ));

                    // Force health to update
                    player.setHealth(player.getMaxHealth());
                }
            }

            case RAPID_FIRE -> {
                AttributeInstance attackSpeed = player.getAttribute(Attributes.ATTACK_SPEED);
                if (attackSpeed != null) {

                    UUID RAPID_FIRE_UUID = UUID.fromString("22222222-2222-2222-2222-222222222222");
                    attackSpeed.removeModifier(RAPID_FIRE_UUID);

                    attackSpeed.addPermanentModifier(new AttributeModifier(
                            RAPID_FIRE_UUID,
                            "rapid_fire_bonus",
                            0.2,
                            AttributeModifier.Operation.MULTIPLY_TOTAL
                    ));
                }
            }

            case MIGHT -> {
                AttributeInstance damage = player.getAttribute(Attributes.ATTACK_DAMAGE);
                if (damage != null) {

                    UUID MIGHT_UUID = UUID.fromString("33333333-3333-3333-3333-333333333333");
                    damage.removeModifier(MIGHT_UUID);

                    damage.addPermanentModifier(new AttributeModifier(
                            MIGHT_UUID,
                            "might_bonus",
                            3.0,
                            AttributeModifier.Operation.ADDITION
                    ));
                }
            }
        }
    }
}
