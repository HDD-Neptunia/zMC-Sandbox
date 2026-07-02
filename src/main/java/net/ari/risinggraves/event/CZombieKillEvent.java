package net.ari.risinggraves.event;

import net.minecraft.world.entity.player.Player;

import net.minecraftforge.eventbus.api.Event;


import net.ari.risinggraves.zombies.CZombie;

public class CZombieKillEvent extends net.minecraftforge.eventbus.api.Event {
    private final Player player;
    private final CZombie zombie;

    public CZombieKillEvent(Player player, CZombie zombie) {
        this.player = player;
        this.zombie = zombie;
    }

    public Player getPlayer() { return player; }
    public CZombie getZombie() { return zombie; }
}
