package net.ari.risinggraves.networking;

import net.ari.risinggraves.barrier.BlockadeCluster;
import net.ari.risinggraves.barrier.BlockadeData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;
import java.util.function.Supplier;
import net.minecraftforge.network.NetworkEvent;
import net.minecraft.world.item.Item;


import net.minecraft.core.BlockPos;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ConfirmCostPacket {

    private final int cost;
    private final List<BlockPos> blocks;

    public ConfirmCostPacket(int cost, List<BlockPos> blocks) {
        this.cost = cost;
        this.blocks = blocks;
    }

    public static void encode(ConfirmCostPacket msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.cost);
        buf.writeInt(msg.blocks.size());
        for (BlockPos pos : msg.blocks) {
            buf.writeBlockPos(pos);
        }
    }

    public static ConfirmCostPacket decode(FriendlyByteBuf buf) {
        int cost = buf.readInt();
        int size = buf.readInt();
        List<BlockPos> blocks = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            blocks.add(buf.readBlockPos());
        }
        return new ConfirmCostPacket(cost, blocks);
    }

    public static void handle(ConfirmCostPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player == null) return;

            BlockadeData data = BlockadeData.get(player.getCommandSenderWorld());


            data.addCluster(new BlockadeCluster(msg.blocks, msg.cost));

            // Sync back to client
            Networking.CHANNEL.send(
                PacketDistributor.PLAYER.with(() -> player),
                new SyncBlockadesPacket(data.getClusters())
            );
        });
        ctx.get().setPacketHandled(true);
    }
}
