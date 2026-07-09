package net.ari.risinggraves.zombies;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

import net.minecraft.world.entity.LivingEntity;

public class BossRegistry {

    public static final BossRegistry INSTANCE = new BossRegistry();

    private final Map<Integer, List<LivingEntity>> bossesByRound = new HashMap<>();

    public void registerBoss(LivingEntity boss, int round) {
        bossesByRound.computeIfAbsent(round, r -> new ArrayList<>()).add(boss);
    }

    public List<LivingEntity> getBossesForRound(int round) {
        return bossesByRound.getOrDefault(round, List.of());
    }

    public int getBossCount(int round) {
        return bossesByRound.getOrDefault(round, List.of()).size();
    }
}
