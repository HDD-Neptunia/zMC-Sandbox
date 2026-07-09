package net.ari.risinggraves.zombies;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;

import net.minecraft.core.particles.ParticleTypes;


public class TankZombie extends CZombie {

    public TankZombie(EntityType<? extends CZombie> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return CZombie.createAttributes()
                .add(Attributes.MAX_HEALTH, 200.0D)
                .add(Attributes.ATTACK_DAMAGE, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.22D);
    }



    @Override
    public void tick() {
        super.tick();

        if (this.level.isClientSide) {
            this.level.addParticle(ParticleTypes.SMOKE,
                    this.getX(),
                    this.getY() + 1.0,
                    this.getZ(),
                    0, 0.02, 0);
        }
    }
}
