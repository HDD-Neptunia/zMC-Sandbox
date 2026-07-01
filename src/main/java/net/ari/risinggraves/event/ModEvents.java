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
import net.minecraftforge.event.TickEvent;
import net.minecraft.world.food.FoodData;

import net.ari.risinggraves.waves.WaveManager;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.ari.risinggraves.RisingGraves;
import net.ari.risinggraves.block.ModBlocks;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.Component;

import net.ari.risinggraves.barrier.BlockadeData;
import net.ari.risinggraves.networking.SyncBlockadesPacket;
import net.ari.risinggraves.networking.Networking;

import net.minecraftforge.network.PacketDistributor;

import net.ari.risinggraves.block.crate.ChestList;
import static net.ari.risinggraves.waves.WaveManager.wavesActive;


@Mod.EventBusSubscriber(modid = RisingGraves.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEvents {


    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        Player player = event.player;
        FoodData food = player.getFoodData();

        food.setFoodLevel(20);
        food.setSaturation(20f);
        food.setExhaustion(0f);
    }

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
        ServerPlayer player = (ServerPlayer) event.getEntity();

        // Your existing starter pick logic
        if (WaveManager.wavesActive) {
            ItemStack starter = new ItemStack(Items.WOODEN_PICKAXE);
            starter.enchant(Enchantments.UNBREAKING, 3);
            starter.setHoverName(Component.literal("Starter Pick"));

            player.getInventory().add(starter);
        }

        // ⭐ NEW: Sync blockade clusters to client on join
        BlockadeData data = BlockadeData.get(player.getLevel());

        Networking.CHANNEL.send(
            PacketDistributor.PLAYER.with(() -> player),
            new SyncBlockadesPacket(data.getClusters())
        );
    }


    @SubscribeEvent
    public static void onMobSpawn(LivingSpawnEvent.CheckSpawn event) {
        event.setResult(Event.Result.DENY);
    }



}
