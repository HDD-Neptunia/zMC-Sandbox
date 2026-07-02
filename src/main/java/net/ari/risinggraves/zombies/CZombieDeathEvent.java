package net.ari.risinggraves.zombies;

import net.minecraft.world.entity.Entity;

import net.minecraftforge.common.MinecraftForge;


public class CZombieDeathEvent {

    public static void fire(Entity zombie) {
        MinecraftForge.EVENT_BUS.post(new Event(zombie));
    }

    public static class Event extends net.minecraftforge.eventbus.api.Event {
        public final Entity zombie;

        public Event(Entity zombie) {
            this.zombie = zombie;
        }
    }
}
