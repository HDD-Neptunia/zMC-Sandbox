package net.ari.risinggraves.networking;

import net.ari.risinggraves.barrier.BlockadeCluster;
import net.ari.risinggraves.barrier.BlockadeData;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;

import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.core.registries.BuiltInRegistries;


public class SyncBlockadesPacket {
    private final List<BlockadeCluster> clusters;


    public SyncBlockadesPacket(List<BlockadeCluster> clusters) {
        this.clusters = clusters;
    }

    public static void encode(SyncBlockadesPacket msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.clusters.size());

        for (BlockadeCluster c : msg.clusters) {
            buf.writeInt(c.getCost());

            // BLOCK POSITIONS
            buf.writeInt(c.getBlocks().size());
            for (BlockPos pos : c.getBlocks()) {
                buf.writeBlockPos(pos);
            }

            // BLOCK STATES
            buf.writeInt(c.getStates().size());
            for (BlockState state : c.getStates()) {
                buf.writeNbt(NbtUtils.writeBlockState(state));
            }
        }
    }


    public static SyncBlockadesPacket decode(FriendlyByteBuf buf) {
        int size = buf.readInt();
        List<BlockadeCluster> clusters = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            int cost = buf.readInt();

            // BLOCK POSITIONS
            int blockCount = buf.readInt();
            List<BlockPos> blocks = new ArrayList<>();
            for (int j = 0; j < blockCount; j++) {
                blocks.add(buf.readBlockPos());
            }

            // BLOCK STATES
            int stateCount = buf.readInt();
            List<BlockState> states = new ArrayList<>();
            for (int j = 0; j < stateCount; j++) {
                CompoundTag tag = buf.readNbt();
                states.add(NbtUtils.readBlockState(BuiltInRegistries.BLOCK.asLookup(), tag));
            }

            clusters.add(new BlockadeCluster(blocks, states, cost));
        }

        return new SyncBlockadesPacket(clusters);
    }


    public static void handle(SyncBlockadesPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            BlockadeData.CLIENT.getClusters().clear();
            BlockadeData.CLIENT.setClusters(msg.clusters);
        });
        ctx.get().setPacketHandled(true);
    }
}
