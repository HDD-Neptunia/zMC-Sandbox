package net.ari.risinggraves.networking;

import net.ari.risinggraves.perks.PerkCosts;
import net.ari.risinggraves.perks.PerkType;
import java.util.function.Supplier;
import net.minecraftforge.network.NetworkEvent;
import net.minecraft.world.item.Item;


public class PerkSelectionPacket {

    private final PerkType perk;

    public PerkSelectionPacket(PerkType perk) {
        this.perk = perk;
    }

    public static void send(PerkType perk) {
        ModNetworking.INSTANCE.sendToServer(new PerkSelectionPacket(perk));
    }

    public static void handle(PerkSelectionPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player == null) return;

            if (player.containerMenu instanceof PerkSelectionMenu menu) {
                menu.getMachine().setPerk(msg.perk);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
