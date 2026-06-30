package net.ari.risinggraves.networking;

import net.ari.risinggraves.RisingGraves;
import net.ari.risinggraves.networking.SyncBlockadesPacket;
import net.ari.risinggraves.networking.PerkSelectionPacket;
import net.ari.risinggraves.networking.WallbuyConfirmPacket;
import net.ari.risinggraves.networking.ConfirmCostPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class Networking {

    private static final String PROTOCOL_VERSION = "1";

    public static final Networking INSTANCE = new Networking();

    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(RisingGraves.MOD_ID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    private static int id = 0;

    public static void register() {
        CHANNEL.registerMessage(
            id++,
            SyncBlockadesPacket.class,
            SyncBlockadesPacket::encode,
            SyncBlockadesPacket::decode,
            SyncBlockadesPacket::handle
        );

        CHANNEL.registerMessage(
            id++,
            ConfirmCostPacket.class,
            ConfirmCostPacket::encode,
            ConfirmCostPacket::decode,
            ConfirmCostPacket::handle
        );

        CHANNEL.registerMessage(
            id++,
            PerkSelectionPacket.class,
            PerkSelectionPacket::encode,
            PerkSelectionPacket::decode,
            PerkSelectionPacket::handle
        );

        CHANNEL.registerMessage(
            id++,
            WallbuyConfirmPacket.class,
            WallbuyConfirmPacket::encode,
            WallbuyConfirmPacket::decode,
            WallbuyConfirmPacket::handle
        );
    }
}

