package net.ari.risinggraves.scoreboard;

import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.scores.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.server.ServerLifecycleHooks;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;

public class ScoreboardHandler {

    public static final ScoreboardHandler INSTANCE = new ScoreboardHandler();

    private final HashMap<String, Integer> playerScores = new HashMap<>();
    private MinecraftServer server;

    public void setServer(MinecraftServer server) {
        this.server = server;
    }

    public void addPoints(String playerName, int points) {
        int current = playerScores.getOrDefault(playerName, 0);
        int updated = current + points;
        playerScores.put(playerName, updated);

        // Update sidebar if player is online
        if (server != null) {
            ServerPlayer player = server.getPlayerList().getPlayerByName(playerName);
            if (player != null) {
                SidebarScoreboard.update(player, updated);
            }
        }
    }

    public int getPoints(String playerName) {
        return playerScores.getOrDefault(playerName, 0);
    }
}



    /*    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent.Text event) {
        Minecraft mc = Minecraft.getInstance();
        FontRenderer fontRenderer = mc.font;
        String text = "Player: " + mc.player.getName().getString() + ", Points: " + getPoints(mc.player);
        mc.fontRenderer.drawString(event.getMatrixStack(), text, 10, 10, 0xFFFFFF);
    }


    public void displayScoreboard(ServerPlayer player, String objectiveName) {
        MinecraftServer minecraftServer = ServerLifecycleHooks.getCurrentServer();
        Scoreboard scoreboard = minecraftServer.getScoreboard();


        if (scoreboard != null) {

            ScoreCriteria objective = scoreboard.addObjective(objectiveName, ScoreCriteria.DUMMY, new TextComponent("Your Display Name Here"), Scoreboard.HealthDisplay.INTEGER);
            objective.getCriteria().forEach(c -> c.registerScoreboard(scoreboard));
            scoreboard.setObjectiveInDisplaySlot(1, objective);
            Score score = scoreboard.getOrCreatePlayerScore(player.getScoreboardName(), objective);

            Collection<ScoreboardPlayerScore> scores = //score method
            List<ScoreboardPlayerScore> sortedScores = new ArrayList<>(scores);

            Collection.sort(sortedScores, new Comparator<ScoreboardPlayerScore>() {
                public int compare(ScoreboardPlayerScore s1, ScoreboardPlayerScore s2) {
                    return Integer.compare(s2.getScore(), s1.getScore());
                }
            });


            score.setScore(points);

            for (ScoreboardPlayerScore playerScore : scores) {
                String playerName = score.getOwner();
                int scoreValue = playerScore.getScore();
                TextComponent textComponent = new TextComponent(playerName + ": " + scoreValue);

            }
        }
    
    } */