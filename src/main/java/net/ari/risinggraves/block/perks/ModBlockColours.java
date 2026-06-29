package net.ari.risinggraves.block.perks;

import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.block.BlockColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.ari.risinggraves.block.ModBlocks;
import net.ari.risinggraves.block.PerkMachineBlockEntity;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBlockColours {

    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {

        BlockColors colors = event.getBlockColors();

        BlockColor perkMachineColor = (state, level, pos, tintIndex) -> {
            if (level != null && pos != null) {
                var be = level.getBlockEntity(pos);
                if (be instanceof PerkMachineBlockEntity machine) {
                    return machine.getColor(); // we’ll add this next
                }
            }
            return 0xFFFFFF; // default white
        };

        event.register(perkMachineColor, ModBlocks.PERK_MACHINE.get());
    }
}
