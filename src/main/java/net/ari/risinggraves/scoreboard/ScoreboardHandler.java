package net.ari.risinggraves.scoreboard;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;

import net.minecraft.client.Minecraft;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;

import net.minecraft.world.scores.*;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.server.ServerLifecycleHooks;


import net.ari.risinggraves.waves.WaveManager;


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

        if (server != null && WaveManager.wavesActive) {
            ServerPlayer player = server.getPlayerList().getPlayerByName(playerName);
            if (player != null) {
                SidebarScoreboard.update(player, updated);
            }
        }

    }

    public void resetPoints(String playerName) {
        playerScores.put(playerName, 0);

        if (server != null) {
            ServerPlayer player = server.getPlayerList().getPlayerByName(playerName);
            if (player != null) {
                SidebarScoreboard.update(player, 0);
            }
        }
    }


    public int getPoints(String playerName) {
        return playerScores.getOrDefault(playerName, 0);
    }
}
