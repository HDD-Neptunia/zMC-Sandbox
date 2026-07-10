package net.ari.risinggraves.event;

import net.minecraft.core.BlockPos;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;

import net.minecraftforge.event.level.ChunkEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;

import net.minecraft.network.chat.Component;

import net.minecraft.server.level.ServerPlayer;

import java.util.ArrayList;


import net.ari.risinggraves.zombies.attacks.projectiles.IceSpikeEntity;
import net.ari.risinggraves.RisingGraves;
import net.ari.risinggraves.block.ModBlocks;
import net.ari.risinggraves.barrier.BlockadeCluster;
import net.ari.risinggraves.barrier.BlockadeData;
import net.ari.risinggraves.block.crate.CrateManager;
import net.ari.risinggraves.block.MysteryCrateBlock;
import net.ari.risinggraves.networking.SyncBlockadesPacket;
import net.ari.risinggraves.networking.Networking;
import net.ari.risinggraves.waves.WaveManager;
import net.ari.risinggraves.block.crate.ChestList;
import net.ari.risinggraves.item.ModItems;
import net.ari.risinggraves.block.crate.EnchantmentLogic;
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


    @SubscribeEvent
    public static void onChunkLoad(ChunkEvent.Load event) {
        Level level = (Level) event.getLevel();
        if (level.isClientSide()) return;

        var chunk = event.getChunk();

        int xStart = chunk.getPos().getMinBlockX();
        int zStart = chunk.getPos().getMinBlockZ();

        for (int x = xStart; x < xStart + 16; x++) {
            for (int z = zStart; z < zStart + 16; z++) {
                for (int y = level.getMinBuildHeight(); y < level.getMaxBuildHeight(); y++) {

                    BlockPos pos = new BlockPos(x, y, z);
                    BlockState state = chunk.getBlockState(pos);

                    if (state.getBlock() instanceof MysteryCrateBlock) {
                        CrateManager.INSTANCE.registerCrate(pos);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        ServerPlayer player = (ServerPlayer) event.getEntity();

        BlockadeData data = BlockadeData.get(player.getLevel());

        if (data.getClusters().isEmpty()) {
            BlockadeCluster first = new BlockadeCluster(
                new ArrayList<>(),
                new ArrayList<>(),
                0
            );

            data.addCluster(first);
            data.setActiveCluster(0);

            System.out.println("[Blockades] Created initial cluster 0");
        }

        if (WaveManager.wavesActive) {

            ItemStack drop;
            int pick = player.level.random.nextInt(3);

            switch (pick) {
                case 0 -> drop = new ItemStack(ModItems.SAPPHIRE_SHARD.get());
                case 1 -> drop = new ItemStack(ModItems.AMETHYST_SHARD.get());
                case 2 -> drop = new ItemStack(ModItems.RUBY_SHARD.get());
                default -> drop = new ItemStack(ModItems.CITRINE_SHARD.get());
            }
            
		    System.out.println("Picked shard: " + pick + " -> " + drop);

            drop.enchant(Enchantments.SHARPNESS, 1);

            ItemStack starter = new ItemStack(Items.WOODEN_PICKAXE);
            starter.enchant(Enchantments.UNBREAKING, 3);
            starter.setHoverName(Component.literal("The beginning of your end."));

            drop.setCount(1);
            drop.setDamageValue(1);

            player.getInventory().add(drop);
            player.getInventory().add(starter);
        }

        Networking.CHANNEL.send(
            PacketDistributor.PLAYER.with(() -> player),
            new SyncBlockadesPacket(data.getClusters(), data.getActiveCluster())
        );
    }

    @SubscribeEvent
    public static void onMobSpawn(LivingSpawnEvent.CheckSpawn event) {
        event.setResult(Event.Result.DENY);
    }

    @SubscribeEvent
    public static void onWorldTick(TickEvent.LevelTickEvent event) {
        if (!event.level.isClientSide() && event.phase == TickEvent.Phase.END) {
            IceSpikeEntity.tick(event.level);
        }
    }

}
