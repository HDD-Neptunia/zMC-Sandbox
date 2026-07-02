package net.ari.risinggraves.perks;

import net.minecraft.client.Minecraft;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import net.minecraft.core.BlockPos;


import net.ari.risinggraves.block.PerkMachineBlockEntity;


@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class PerkMachineHUD {

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) return;

        HitResult hit = mc.hitResult;
        if (!(hit instanceof BlockHitResult bhr)) return;

        BlockPos pos = bhr.getBlockPos();
        BlockEntity be = mc.level.getBlockEntity(pos);

        if (!(be instanceof PerkMachineBlockEntity machine)) return;

        String raw = machine.getPerk().name().replace("_", " ").toLowerCase();
        String text = Character.toUpperCase(raw.charAt(0)) + raw.substring(1);

        int price = PerkCosts.getCost(machine.getPerk());

        HUDText.currentText = text + " " + "-" + " " + price;
        HUDText.currentColor = machine.getColor();
    }
}

