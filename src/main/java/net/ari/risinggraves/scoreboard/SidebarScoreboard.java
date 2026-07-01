package net.ari.risinggraves.scoreboard;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.network.chat.Component;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;
import net.ari.risinggraves.scoreboard.ScoreboardHandler;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SidebarScoreboard {


    private static Objective objective;

    public static void init(MinecraftServer server) {

        Scoreboard board = server.getScoreboard();

        objective = board.getObjective("rg_points");
        if (objective == null) {
            objective = board.addObjective(
                "rg_points",
                ObjectiveCriteria.DUMMY,
                Component.literal("Points"),
                ObjectiveCriteria.RenderType.INTEGER
            );
        }
    }

    @SubscribeEvent
    public static void onPlayerJoinHide(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            SidebarScoreboard.hide(player);
        }
    }


    public static void update(ServerPlayer player, int points) {
        Scoreboard board = player.getScoreboard();
        board.getOrCreatePlayerScore(player.getScoreboardName(), objective).setScore(points);
    }

    public static void show(ServerPlayer player) {
        player.getScoreboard().setDisplayObjective(Scoreboard.DISPLAY_SLOT_SIDEBAR, objective);
    }

    public static void hide(ServerPlayer player) {
        player.getScoreboard().setDisplayObjective(Scoreboard.DISPLAY_SLOT_SIDEBAR, null);
    }

    public static void clear(ServerPlayer player) {
        hide(player);
    }
}
