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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStack;
import net.ari.risinggraves.barrier.WandFunction;

import net.minecraft.core.BlockPos;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ConfirmCostPacket {

    private final int cost;
    private final List<BlockPos> blocks;
    public final List<BlockState> states;


    public ConfirmCostPacket(int cost, List<BlockPos> blocks, List<BlockState> states) {
        this.cost = cost;
        this.blocks = blocks;
        this.states = states;
    }

    public static void encode(ConfirmCostPacket msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.cost);

        // BLOCK POSITIONS
        buf.writeInt(msg.blocks.size());
        for (BlockPos pos : msg.blocks) {
            buf.writeBlockPos(pos);
        }

        // BLOCK STATES
        buf.writeInt(msg.states.size());
        for (BlockState state : msg.states) {
            buf.writeNbt(NbtUtils.writeBlockState(state));
        }
    }


    public static ConfirmCostPacket decode(FriendlyByteBuf buf) {
        int cost = buf.readInt();

        // BLOCK POSITIONS
        int blockCount = buf.readInt();
        List<BlockPos> blocks = new ArrayList<>();
        for (int i = 0; i < blockCount; i++) {
            blocks.add(buf.readBlockPos());
        }

        // BLOCK STATES
        int stateCount = buf.readInt();
        List<BlockState> states = new ArrayList<>();
        for (int i = 0; i < stateCount; i++) {
            CompoundTag tag = buf.readNbt();
            states.add(NbtUtils.readBlockState(BuiltInRegistries.BLOCK.asLookup(), tag));
        }

        return new ConfirmCostPacket(cost, blocks, states);
    }


    public static void handle(ConfirmCostPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player == null) return;

            BlockadeData data = BlockadeData.get(player.getCommandSenderWorld());

            // ⭐ Create cluster
            BlockadeCluster cluster = new BlockadeCluster(msg.blocks, msg.states, msg.cost);

            // ⭐ Add to SavedData FIRST
            data.addCluster(cluster);

            // ⭐ Clear wand selection + set active cluster
            ItemStack stack = player.getMainHandItem();
            if (stack.getItem() instanceof WandFunction) {
                CompoundTag tag = stack.getOrCreateTag();
                tag.remove("selected");
                tag.putInt("activeCluster", data.getClusters().size() - 1);
            }

            // ⭐ Sync back to client
            Networking.CHANNEL.send(
                PacketDistributor.PLAYER.with(() -> player),
                new SyncBlockadesPacket(data.getClusters())
            );
        });
        ctx.get().setPacketHandled(true);
    }


}
