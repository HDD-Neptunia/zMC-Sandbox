package net.ari.risinggraves.networking;

import net.minecraft.network.FriendlyByteBuf;

import net.minecraft.server.level.ServerPlayer;

import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;


public class SprintKeyPacket {
    private final boolean down;

    public SprintKeyPacket(boolean down) {
        this.down = down;
    }

    public static void encode(SprintKeyPacket msg, FriendlyByteBuf buf) {
        buf.writeBoolean(msg.down);
    }

    public static SprintKeyPacket decode(FriendlyByteBuf buf) {
        return new SprintKeyPacket(buf.readBoolean());
    }

    public static void handle(SprintKeyPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player == null) return;

            // store in persistent data
            player.getPersistentData().putBoolean("sprintKeyDown", msg.down);
        });
        ctx.get().setPacketHandled(true);
    }
}
