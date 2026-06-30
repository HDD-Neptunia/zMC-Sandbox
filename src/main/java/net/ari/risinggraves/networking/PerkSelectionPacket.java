package net.ari.risinggraves.networking;

import net.ari.risinggraves.perks.PerkCosts;
import net.ari.risinggraves.perks.PerkType;
import java.util.function.Supplier;
import net.minecraftforge.network.NetworkEvent;
import net.minecraft.world.item.Item;
import net.ari.risinggraves.networking.Networking;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.ari.risinggraves.perks.PerkSelectionMenu;




public class PerkSelectionPacket {

    private final PerkType perk;

    public PerkSelectionPacket(PerkType perk) {
        this.perk = perk;
    }

    public static void send(PerkType perk) {
        Networking.CHANNEL.sendToServer(new PerkSelectionPacket(perk));
    }

    public static void encode(PerkSelectionPacket msg, FriendlyByteBuf buf) {
        buf.writeEnum(msg.perk);
    }

    public static PerkSelectionPacket decode(FriendlyByteBuf buf) {
        return new PerkSelectionPacket(buf.readEnum(PerkType.class));
    }

    public static void handle(PerkSelectionPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();

            System.out.println("[PACKET] Received perk=" + msg.perk);

            if (player != null && player.containerMenu instanceof PerkSelectionMenu menu) {
                System.out.println("[PACKET] Updating machine at " + menu.getMachine().getBlockPos());
                menu.getMachine().setPerk(msg.perk);

                player.closeContainer();
            } else {
                System.out.println("[PACKET] FAILED: containerMenu=" + player.containerMenu.getClass().getName());
            }
        });
        ctx.get().setPacketHandled(true);
    }


}
