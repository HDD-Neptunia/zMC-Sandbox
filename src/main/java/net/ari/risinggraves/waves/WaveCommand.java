package net.ari.risinggraves.waves;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.ari.risinggraves.init.ModEntities;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.server.level.ServerLevel;




import net.ari.risinggraves.zombies.CZombie;
import net.ari.risinggraves.waves.WaveManager;
import net.ari.risinggraves.scoreboard.ScoreboardHandler;
import net.ari.risinggraves.scoreboard.SidebarScoreboard;



public class WaveCommand {
	
	public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
		
		dispatcher.register(Commands.literal("startwaves")
			.executes(context -> {
			ServerLevel level = context.getSource().getLevel();
			ServerPlayer player = context.getSource().getPlayer();

			WaveManager.startWaves(level);

			// Force scoreboard to appear
			int points = ScoreboardHandler.INSTANCE.getPoints(player.getName().getString());
			SidebarScoreboard.update(player, points);

			return 1;
		}));

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
