package net.ari.risinggraves.networking;

import java.util.function.Supplier;
import net.minecraftforge.network.NetworkEvent;
import net.minecraft.world.item.Item;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.ari.risinggraves.networking.Networking;
import net.minecraft.world.item.ItemStack;
import net.ari.risinggraves.block.wallbuy.WallbuyCostMenu;
import net.ari.risinggraves.block.WallbuyBlockEntity;

public class WallbuyConfirmPacket {

    private final ItemStack stack;
    private final int cost;

    public WallbuyConfirmPacket(ItemStack stack, int cost) {
        this.stack = stack;
        this.cost = cost;
    }

    public static void send(ItemStack stack, int cost) {
        System.out.println("[WALLBUY PACKET SEND] Sending item: " + stack.getItem() + " cost: " + cost);
        Networking.CHANNEL.sendToServer(new WallbuyConfirmPacket(stack, cost));
    }

    public static void encode(WallbuyConfirmPacket msg, FriendlyByteBuf buf) {
        buf.writeItem(msg.stack);
        buf.writeInt(msg.cost);
    }

    public static WallbuyConfirmPacket decode(FriendlyByteBuf buf) {
        return new WallbuyConfirmPacket(buf.readItem(), buf.readInt());
    }

    public static void handle(WallbuyConfirmPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            System.out.println("[WALLBUY PACKET HANDLE] Received item: " + msg.stack.getItem() + " cost: " + msg.cost);
            
            ServerPlayer player = ctx.get().getSender();
            if (player != null && player.containerMenu instanceof WallbuyCostMenu menu) {
                WallbuyBlockEntity wallbuy = menu.getWallbuy();
                System.out.println("[WALLBUY PACKET HANDLE] BlockEntity before set: " + wallbuy.getItem());

                wallbuy.setItem(msg.stack.getItem());
                wallbuy.setCost(msg.cost);

                System.out.println("[WALLBUY PACKET HANDLE] BE after set: " + wallbuy.getItem());
            }
        });
        ctx.get().setPacketHandled(true);
    }

}
