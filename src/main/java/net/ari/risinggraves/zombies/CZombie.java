package net.ari.risinggraves.zombies;

import net.ari.risinggraves.waves.WaveManager;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.ari.risinggraves.event.CZombieHitEvent;
import net.ari.risinggraves.event.CZombieKillEvent;
import net.minecraft.world.damagesource.DamageSource;


public class CZombie extends Zombie {

    public CZombie(EntityType<? extends CZombie> type, Level level) {
        super(type, level);
    }

    @Override
    public void die(DamageSource source) {
        super.die(source);

        if (!this.level.isClientSide) {
            CZombieDeathEvent.fire(this);
        }
		if (!this.level.isClientSide && source.getEntity() instanceof Player player) {
			MinecraftForge.EVENT_BUS.post(new CZombieKillEvent(player, this));
		}
    }

	@Override
	public boolean hurt(DamageSource source, float amount) {
		boolean result = super.hurt(source, amount);

		if (!this.level.isClientSide && source.getEntity() instanceof Player player) {
			MinecraftForge.EVENT_BUS.post(new CZombieHitEvent(player, this));
		}

		return result;
	}
}



