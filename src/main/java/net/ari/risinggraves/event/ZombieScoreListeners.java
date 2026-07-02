package net.ari.risinggraves.event;

import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;

import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


import net.ari.risinggraves.scoreboard.ScoreboardHandler;


@Mod.EventBusSubscriber(modid = "risinggraves", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ZombieScoreListeners {

    @SubscribeEvent
    public static void onHit(CZombieHitEvent event) {
        Player player = event.getPlayer();

        if (player.getAttackStrengthScale(0.5F) >= 1.0F) {
            ScoreboardHandler.INSTANCE.addPoints(player.getName().getString(), 10);
        }
    }

    @SubscribeEvent
    public static void onKill(CZombieKillEvent event) {
        ScoreboardHandler.INSTANCE.addPoints(event.getPlayer().getName().getString(), 100);
    }
}


