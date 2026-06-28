package net.ari.risinggraves.scoreboard;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.network.chat.Component;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;

public class SidebarScoreboard {

    private static Objective objective;

    public static void init(MinecraftServer server) {
        Scoreboard board = server.getScoreboard();

        // Check if objective already exists
        objective = board.getObjective("rg_points");
        if (objective == null) {
            objective = board.addObjective(
                "rg_points",
                ObjectiveCriteria.DUMMY,
                Component.literal("Points"),
                ObjectiveCriteria.RenderType.INTEGER
            );
        }

        board.setDisplayObjective(Scoreboard.DISPLAY_SLOT_SIDEBAR, objective);
    }

    public static void update(ServerPlayer player, int points) {
        Scoreboard board = player.getScoreboard();
        board.getOrCreatePlayerScore(player.getScoreboardName(), objective).setScore(points);
    }
}

