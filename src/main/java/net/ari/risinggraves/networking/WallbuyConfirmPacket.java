package net.ari.risinggraves.networking;

import java.util.function.Supplier;
import net.minecraftforge.network.NetworkEvent;
import net.minecraft.world.item.Item;

public class WallbuyConfirmPacket {

    private final Item item;
    private final int cost;

    public WallbuyConfirmPacket(Item item, int cost) {
        this.item = item;
        this.cost = cost;
    }

    public static void send(Item item, int cost) {
        ModNetworking.INSTANCE.sendToServer(new WallbuyConfirmPacket(item, cost));
    }

    public static void handle(WallbuyConfirmPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player == null) return;

            if (player.containerMenu instanceof WallbuyCostMenu menu) {
                WallbuyBlockEntity wallbuy = menu.wallbuy;
                wallbuy.setItem(msg.item);
                wallbuy.setCost(msg.cost);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
