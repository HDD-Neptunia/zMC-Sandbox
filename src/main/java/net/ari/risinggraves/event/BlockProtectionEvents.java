package net.ari.risinggraves.event;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;


import net.ari.risinggraves.RisingGraves;
import net.ari.risinggraves.registry.ModTags;


@Mod.EventBusSubscriber(modid = RisingGraves.MOD_ID)
public class BlockProtectionEvents {

    @SubscribeEvent
    public static void onBreakSpeed(PlayerEvent.BreakSpeed event) {
        Player player = event.getEntity();

        if (player.isCreative()) return;

        BlockState state = event.getState();
        ItemStack tool = player.getMainHandItem();

        if (tool.getItem() instanceof PickaxeItem &&
            (state.is(ModTags.Blocks.GEM_BLOCKS) || state.is(ModTags.Blocks.ORE_BLOCKS))) {
            return;
        }

        event.setNewSpeed(0.0F);
    }

    @SubscribeEvent
    public static void onBreak(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();

        if (player.isCreative()) return;

        BlockState state = event.getState();
        ItemStack tool = player.getMainHandItem();

        if (tool.getItem() instanceof PickaxeItem &&
            (state.is(ModTags.Blocks.GEM_BLOCKS) || state.is(ModTags.Blocks.ORE_BLOCKS))) {
            return;
        }

        event.setCanceled(true);
    }
}
