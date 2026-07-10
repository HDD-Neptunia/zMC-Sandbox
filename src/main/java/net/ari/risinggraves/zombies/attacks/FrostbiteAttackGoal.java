package net.ari.risinggraves.zombies.attacks;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import net.ari.risinggraves.zombies.attacks.projectiles.IceSpikeEntity;

import net.ari.risinggraves.zombies.FrostbiteZombie;

public class FrostbiteAttackGoal extends Goal {

    private final FrostbiteZombie zombie;
    private Player target;
    private Vec3 lockedTargetPos;


    public FrostbiteAttackGoal(FrostbiteZombie zombie) {
        this.zombie = zombie;
    }

    @Override
    public boolean canUse() {
        target = zombie.level.getNearestPlayer(zombie, 10);
        return target != null && zombie.frostbiteCooldown == 0;
    }


    @Override
    public boolean canContinueToUse() {
        // Only continue while charging
        return zombie.isChargingAttack;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void start() {
        zombie.isChargingAttack = true;
        zombie.attackTimer = 0;

        lockedTargetPos = target.position();
        // Stop movement while charging
        zombie.getNavigation().stop();
    }

    @Override
    public void stop() {
        zombie.isChargingAttack = false;
        zombie.attackTimer = 0;

        zombie.frostbiteCooldown = 60;
        // Resume movement
        zombie.getNavigation().moveTo(target, 1.0);
    }

    @Override
    public void tick() {
        if (target == null) {
            stop();
            return;
        }

        zombie.attackTimer++;

        // Telegraph for 20 ticks (1 second)
        if (zombie.attackTimer >= 20) {
            IceSpikeEntity.spawnSpike(zombie, lockedTargetPos);

            // End the attack
            stop();
        }
    }

}
