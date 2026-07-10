package net.ari.risinggraves.systems;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

import java.util.*;

public class SetBonusRegistry {

    private static final Map<String, List<MobEffectInstance>> BONUSES = new HashMap<>();

    public static void registerBonus(String setId, List<MobEffectInstance> effects) {
        BONUSES.put(setId, effects);
    }

    public static List<MobEffectInstance> getBonus(String setId) {
        return BONUSES.getOrDefault(setId, List.of());
    }

    public static void registerDefaults() {

        SetBonusRegistry.registerBonus("emerald",
                List.of(
                    new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 10, 2),
                    new MobEffectInstance(MobEffects.DAMAGE_BOOST, 10, 4),
                    new MobEffectInstance(MobEffects.REGENERATION, 10, 1),
                    new MobEffectInstance(MobEffects.LUCK, 10, 5)
                )
            );

            SetBonusRegistry.registerBonus("ruby",
                List.of(
                    new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 10, 0),
                    new MobEffectInstance(MobEffects.DAMAGE_BOOST, 10, 2),
                    new MobEffectInstance(MobEffects.HEALTH_BOOST, 10, 1)
                )
            );

            SetBonusRegistry.registerBonus("sappphire",
                List.of(
                    new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 10, 2),
                    new MobEffectInstance(MobEffects.DIG_SPEED, 10, 1),
                    new MobEffectInstance(MobEffects.NIGHT_VISION, 10, 1)

                )
            );

            SetBonusRegistry.registerBonus("amethyst",
                List.of(
                    new MobEffectInstance(MobEffects.LUCK, 10, 1),
                    new MobEffectInstance(MobEffects.REGENERATION, 10, 2),
                    new MobEffectInstance(MobEffects.ABSORPTION, 10, 1)
                )
            );

            SetBonusRegistry.registerBonus("citrine",
                List.of(
                    new MobEffectInstance(MobEffects.LUCK, 10, 3),
                    new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 10, 1),
                    new MobEffectInstance(MobEffects.DAMAGE_BOOST, 10, 2)
                )
            );
    }
}