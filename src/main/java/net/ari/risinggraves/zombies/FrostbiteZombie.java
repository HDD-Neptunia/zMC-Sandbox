package net.ari.risinggraves.zombies;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.core.particles.ParticleTypes;

import net.ari.risinggraves.zombies.attacks.FrostbiteAttackGoal;

public class FrostbiteZombie extends CZombie {

    public boolean isChargingAttack = false;
    public int attackTimer = 0;
    private int spawnRound = 11;
    public int frostbiteCooldown = 0;


    public void setSpawnRound(int round) {
        this.spawnRound = round;
    }

    public int getSpawnRound() {
        return spawnRound;
    }


    public FrostbiteZombie(EntityType<? extends CZombie> type, Level level) {
        super(type, level);

        // REGISTER ATTACK GOAL HERE
        this.goalSelector.addGoal(1, new FrostbiteAttackGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return CZombie.createAttributes()
                .add(Attributes.MAX_HEALTH, 40.0D)
                .add(Attributes.ATTACK_DAMAGE, 4.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.30D);
    }

    public void setFrostbiteSpawnRound(int round) {
        this.spawnRound = round;
    }


    @Override
    public void tick() {
        super.tick();

        if (frostbiteCooldown > 0) frostbiteCooldown--;
        
        if (this.level.isClientSide) {
            this.level.addParticle(ParticleTypes.SNOWFLAKE,
                    this.getX(),
                    this.getY() + 1.0,
                    this.getZ(),
                    0, 0.02, 0);
        }
        // Telegraph particles
        if (this.level.isClientSide && this.isChargingAttack) {
            this.level.addParticle(ParticleTypes.SNOWFLAKE,
                    this.getX(),
                    this.getY() + 1.0,
                    this.getZ(),
                    0, 0.02, 0);
        }
    }
}
