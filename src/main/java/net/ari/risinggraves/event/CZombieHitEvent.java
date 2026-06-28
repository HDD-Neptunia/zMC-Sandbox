package net.ari.risinggraves.event;

import net.minecraft.world.entity.player.Player;
import net.ari.risinggraves.zombies.CZombie;
import net.minecraftforge.eventbus.api.Event;

public class CZombieHitEvent extends net.minecraftforge.eventbus.api.Event {
    private final Player player;
    private final CZombie zombie;

    public CZombieHitEvent(Player player, CZombie zombie) {
        this.player = player;
        this.zombie = zombie;
    }

    public Player getPlayer() { return player; }
    public CZombie getZombie() { return zombie; }
}
