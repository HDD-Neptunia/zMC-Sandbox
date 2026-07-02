package net.ari.risinggraves.zombies;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.Items;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.common.MinecraftForge;

import net.minecraft.nbt.CompoundTag;


import net.ari.risinggraves.item.ModItems;
import net.ari.risinggraves.block.ModBlocks;
import net.ari.risinggraves.waves.WaveManager;
import net.ari.risinggraves.event.CZombieHitEvent;
import net.ari.risinggraves.event.CZombieKillEvent;


public class CZombie extends Zombie {

    public CZombie(EntityType<? extends CZombie> type, Level level) {
        super(type, level);
    }

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, SpawnGroupData spawnData, CompoundTag dataTag) {
		SpawnGroupData data = super.finalizeSpawn(level, difficulty, reason, spawnData, dataTag);

		int wave = WaveManager.getCurrentWave();

		// 10% chance to spawn holding a stick
		if (this.level.random.nextFloat() < 0.10f) {
			this.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.STICK));
		}

		double health = 40 + (5 * wave) + (0.5 * wave * wave);
		this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(health);
		this.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(100.0D);
		this.setHealth((float)health);

		double damage = 4 + (0.1 * wave);
		this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(damage);

		double speed = 0.23 + (wave * 0.003);
		this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(speed);

		return data;
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
	protected boolean isSunSensitive() {
		return false;
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		boolean result = super.hurt(source, amount);

		if (!this.level.isClientSide && source.getEntity() instanceof Player player) {
			MinecraftForge.EVENT_BUS.post(new CZombieHitEvent(player, this));
		}

		return result;
	}

	private static void onZombieDefeated() {}

	@SubscribeEvent
	public static void onCZombieDeath(CZombieDeathEvent.Event event) {
		if (!WaveManager.wavesActive) return;

		CZombie zombie = (CZombie) event.zombie;

		Level level = zombie.level;


		if (level.random.nextFloat() < 0.15f) {

			ItemStack drop;
			int pick = level.random.nextInt(4);

			switch (pick) {
				case 0 -> drop = new ItemStack(ModItems.SAPPHIRE_SHARD.get());
				case 1 -> drop = new ItemStack(ModItems.AMETHYST_SHARD.get());
				case 2 -> drop = new ItemStack(ModItems.RUBY_SHARD.get());
				default -> drop = new ItemStack(ModItems.CITRINE_SHARD.get());
			}

			zombie.spawnAtLocation(drop, 1.0f);
		}

		if (level.random.nextFloat() < 0.05f) {

			ItemStack drop;
			int pick = level.random.nextInt(4);

			switch (pick) {
				case 0 -> drop = new ItemStack(ModItems.SAPPHIRE.get());
				case 1 -> drop = new ItemStack(ModItems.AMETHYST.get());
				case 2 -> drop = new ItemStack(ModItems.RUBY.get());
				default -> drop = new ItemStack(ModItems.CITRINE.get());
			}

			zombie.spawnAtLocation(drop, 1.0f);
		}

		if (level.random.nextFloat() < 0.03f) {

			ItemStack blockDrop;
			int pick = level.random.nextInt(4);

			switch (pick) {
				case 0 -> blockDrop = new ItemStack(ModBlocks.SAPPHIRE_ORE.get());
				case 1 -> blockDrop = new ItemStack(ModBlocks.AMETHYST_ORE.get());
				case 2 -> blockDrop = new ItemStack(ModBlocks.RUBY_ORE.get());
				default -> blockDrop = new ItemStack(ModBlocks.CITRINE_ORE.get());
			}

			zombie.spawnAtLocation(blockDrop, 1.0f);
		}

		if (level.random.nextFloat() < 0.01f) {

			ItemStack blockDrop;
			int pick = level.random.nextInt(4);

			switch (pick) {
				case 0 -> blockDrop = new ItemStack(ModBlocks.BLOCK_OF_SAPPHIRE.get());
				case 1 -> blockDrop = new ItemStack(ModBlocks.BLOCK_OF_AMETHYST.get());
				case 2 -> blockDrop = new ItemStack(ModBlocks.BLOCK_OF_RUBY.get());
				default -> blockDrop = new ItemStack(ModBlocks.BLOCK_OF_CITRINE.get());
			}

			zombie.spawnAtLocation(blockDrop, 1.0f);
		}

		ItemStack held = zombie.getMainHandItem();
		if (held.is(Items.STICK)) {
			if (level.random.nextFloat() < 0.25f) {
				zombie.spawnAtLocation(new ItemStack(Items.STICK), 1.0f);
			}
		}

		onZombieDefeated();
	}


}



