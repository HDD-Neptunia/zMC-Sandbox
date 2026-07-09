package net.ari.risinggraves.zombies.attacks;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import net.ari.risinggraves.zombies.FrostbiteZombie;

public class FrostbiteAttackGoal extends Goal {

    private final FrostbiteZombie zombie;
    private Player target;

    public FrostbiteAttackGoal(FrostbiteZombie zombie) {
        this.zombie = zombie;
    }

    @Override
    public boolean canUse() {
        target = zombie.level.getNearestPlayer(zombie, 10);
        return target != null;
    }

    @Override
    public void start() {
        zombie.isChargingAttack = true;
        zombie.attackTimer = 0;
    }

    @Override
    public void stop() {
        zombie.isChargingAttack = false;
        zombie.attackTimer = 0;
    }

    @Override
    public void tick() {
        if (target == null) return;

        zombie.attackTimer++;

        // Telegraph for 20 ticks (1 second)
        if (zombie.attackTimer == 20) {
            IceSpikeEntity.spawnSpike(zombie, target);
            zombie.isChargingAttack = false;
        }
    }
}
