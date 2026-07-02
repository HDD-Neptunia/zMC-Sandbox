package net.ari.risinggraves.waves;

import com.mojang.brigadier.CommandDispatcher;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.MinecraftServer;

import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.network.chat.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitlesAnimationPacket;


import net.ari.risinggraves.block.ModBlocks;
import net.ari.risinggraves.item.ModItems;
import net.ari.risinggraves.perks.PerkHandler;
import net.ari.risinggraves.zombies.CZombie;
import net.ari.risinggraves.waves.WaveManager;
import net.ari.risinggraves.scoreboard.ScoreboardHandler;
import net.ari.risinggraves.scoreboard.SidebarScoreboard;
import net.ari.risinggraves.init.ModEntities;
import net.ari.risinggraves.perks.PerkHandler;


public class WaveCommand {
	
	public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {

		dispatcher.register(Commands.literal("startwaves")
			.executes(context -> {
				ServerLevel level = context.getSource().getLevel();
				ServerPlayer player = context.getSource().getPlayer();

				WaveManager.wavesActive = true;

				MinecraftServer server = context.getSource().getServer();

				for (ServerPlayer p: server.getPlayerList().getPlayers()) {
					PerkHandler.resetPerks(player);
					ScoreboardHandler.INSTANCE.resetPoints(player.getName().getString());
				}

				ItemStack drop;
				int pick = player.level.random.nextInt(3);

				switch (pick) {
					case 0 -> drop = new ItemStack(ModItems.SAPPHIRE_SHARD.get());
					case 1 -> drop = new ItemStack(ModItems.AMETHYST_SHARD.get());
					case 2 -> drop = new ItemStack(ModItems.RUBY_SHARD.get());
					default -> drop = new ItemStack(ModItems.CITRINE_SHARD.get());
				}
				
				ItemStack starter = new ItemStack(Items.WOODEN_PICKAXE);
				starter.enchant(Enchantments.UNBREAKING, 3);
				starter.setHoverName(Component.literal("The beginning of your end."));

				drop.enchant(Enchantments.SHARPNESS, 1);
				drop.setCount(1);
				drop.setDamageValue(1);

				player.getInventory().add(drop);
				player.getInventory().add(starter);

				for (ServerPlayer p : level.players()) {
					p.getInventory().add(starter.copy());

					int points = ScoreboardHandler.INSTANCE.getPoints(p.getName().getString());
					SidebarScoreboard.show(p);
					SidebarScoreboard.update(p, points);
				}

				for (ServerPlayer p : level.getServer().getPlayerList().getPlayers()) {

					p.connection.send(new ClientboundSetTitlesAnimationPacket(10, 60, 20));

					p.connection.send(new ClientboundSetTitleTextPacket(
						Component.literal("§c§lOh... Oh no...")
					));

					p.connection.send(new ClientboundSetSubtitleTextPacket(
						Component.literal("§6You fool.. What have you done?!")
					));
				}

				Executors.newSingleThreadScheduledExecutor().schedule(() -> {
					if (WaveManager.wavesActive) {
						WaveManager.startWaves(level);
					}
				}, 5, TimeUnit.SECONDS);

				return 1;
			}));


		dispatcher.register(Commands.literal("rgivetools")
			.executes(context -> {
				ServerPlayer player = context.getSource().getPlayer();


				player.getInventory().add(new ItemStack(ModItems.LINKING_WAND.get()));

				player.getInventory().add(new ItemStack(ModItems.BLOCKADE_WAND.get()));

				player.getInventory().add(new ItemStack(ModBlocks.WALLBUY.get()));

				player.getInventory().add(new ItemStack(ModBlocks.CUSTOM_SPAWNER.get()));

				player.getInventory().add(new ItemStack(ModBlocks.MYSTERY_CRATE.get()));

				player.getInventory().add(new ItemStack(ModBlocks.PERK_MACHINE.get()));

				player.displayClientMessage(
					Component.literal("§aGo wild, babes!~"),
					false
				);

				return 1;
			})
		);


		dispatcher.register(Commands.literal("stopwaves")
			.executes(context -> {
			ServerLevel level = context.getSource().getLevel();

			System.out.println("[RisingGraves] /stopwaves executed");

			Iterable<Entity> all = level.getAllEntities();

			int total = 0;
			int killed = 0;

			for (Entity e : all) {
				total++;

				String namespace = e.getType().builtInRegistryHolder().key().location().getNamespace();
				if (namespace.equals("risinggraves")) {
					e.discard();
					killed++;
				}
			}

			System.out.println("[RisingGraves] Total entities: " + total);
			System.out.println("[RisingGraves] Total killed: " + killed);

			WaveManager.stopWaves();

			return 1;
		}));
	}
}
