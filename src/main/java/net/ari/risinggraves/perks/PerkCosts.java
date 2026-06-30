package net.ari.risinggraves.perks;

import java.util.Map;

public class PerkCosts {

    private static final Map<PerkType, Integer> COSTS = Map.of(
            PerkType.SPEED, 10,
            PerkType.SURVIVABILITY, 4000,
            PerkType.RAPID_FIRE, 2300,
            PerkType.MIGHT, 5100
    );

    public static int getCost(PerkType perk) {
        return COSTS.getOrDefault(perk, 9999);
    }
}
