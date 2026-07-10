package net.ari.risinggraves.armour;

import net.ari.risinggraves.armor.ModArmorItem;
import net.ari.risinggraves.systems.SetBonusRegistry;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.effect.MobEffectInstance;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.TickEvent;


public class SetBonusHandler {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;

        ItemStack head  = player.getInventory().getArmor(3);
        ItemStack chest = player.getInventory().getArmor(2);
        ItemStack legs  = player.getInventory().getArmor(1);
        ItemStack boots = player.getInventory().getArmor(0);

        if (!(head.getItem() instanceof ModArmorItem h)) return;
        if (!(chest.getItem() instanceof ModArmorItem c)) return;
        if (!(legs.getItem() instanceof ModArmorItem l)) return;
        if (!(boots.getItem() instanceof ModArmorItem b)) return;

        String setId = h.getSetId();

        if (!setId.equals(c.getSetId()) ||
            !setId.equals(l.getSetId()) ||
            !setId.equals(b.getSetId())) return;

        for (MobEffectInstance effect : SetBonusRegistry.getBonus(setId)) {
            player.addEffect(new MobEffectInstance(
                effect.getEffect(),
                effect.getDuration(),
                effect.getAmplifier(),
                false,
                false
            ));
        }
    }
    }