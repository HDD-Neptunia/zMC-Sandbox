package net.ari.risinggraves.event;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraft.world.level.Level;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;

import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;


import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.network.chat.Component;
import net.minecraftforge.eventbus.api.Event;


import net.ari.risinggraves.waves.WaveManager;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.ari.risinggraves.RisingGraves;
import net.ari.risinggraves.block.ModBlocks;
import net.ari.risinggraves.block.crate.ChestList;
import static net.ari.risinggraves.waves.WaveManager.wavesActive;


@Mod.EventBusSubscriber(modid = RisingGraves.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEvents {

    public static class OpenChest {

        @SubscribeEvent
        public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {

            Level level = event.getLevel();
            BlockPos pos = event.getPos();

            // Check if the block is YOUR chest block
            if (level.getBlockState(pos).getBlock() == ModBlocks.MYSTERY_CRATE.get()) {

                // Create your list
                ChestList list = new ChestList();

                // Get a random item
                ItemStack reward = list.getRandomItem();

                // Give it to the player
                event.getEntity().addItem(reward);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (WaveManager.wavesActive) {
            ItemStack starter = new ItemStack(Items.WOODEN_PICKAXE);
            starter.enchant(Enchantments.UNBREAKING, 3);
            starter.setHoverName(Component.literal("Starter Pick"));

            event.getEntity().getInventory().add(starter);
        }
    }

    @SubscribeEvent
    public static void onMobSpawn(LivingSpawnEvent.CheckSpawn event) {
        event.setResult(Event.Result.DENY);
    }



}
