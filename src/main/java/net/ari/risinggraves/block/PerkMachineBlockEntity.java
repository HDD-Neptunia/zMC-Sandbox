package net.ari.risinggraves.block;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.network.chat.Component;
import net.ari.risinggraves.perks.PerkSelectionMenu;
import net.ari.risinggraves.perks.PerkType;
import net.minecraft.world.MenuProvider;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.client.model.data.ModelProperty;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;



public class PerkMachineBlockEntity extends BlockEntity implements MenuProvider {

    private PerkType perk = PerkType.NONE;
    private int colorCache;

    public static final ModelProperty<Integer> COLOR_PROPERTY = new ModelProperty<>();

    public PerkMachineBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PERK_MACHINE.get(), pos, state);
        System.out.println("[BE] Constructed");
    }


    @Override
    public ModelData getModelData() {
        return ModelData.builder()
                .with(COLOR_PROPERTY, this.colorCache)
                .build();
    }


    public PerkType getPerk() {
        return perk;
    }

    // -------------------------
    //   SYNC TO CLIENT
    // -------------------------

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag);
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        System.out.println("[BE] handleUpdateTag CLIENT=" + level.isClientSide + " pos=" + worldPosition);
        System.out.println("[BE] handleUpdateTag TAG=" + tag);

        load(tag);
    }

    // -------------------------
    //   SAVE + LOAD
    // -------------------------

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);

        // DO NOT USE level HERE, IT'S NULL DURING CHUNK LOAD

        if (tag.contains("perk")) {
            String s = tag.getString("perk");
            try {
                this.perk = PerkType.valueOf(s);
            } catch (IllegalArgumentException e) {
                this.perk = PerkType.NONE;
            }
        }

        // If you want logging, don't rely on level:
        System.out.println("[BE] load: perk=" + this.perk + " (client=" + (this.level != null && this.level.isClientSide) + ")");
    }




    @Override
    public void onLoad() {
        System.out.println("[BE] onLoad CLIENT=" + level.isClientSide +
                       " pos=" + worldPosition +
                       " perk=" + perk);

        if (level != null && level.isClientSide) {
            requestModelDataUpdate();
        }
    }


    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putString("perk", perk.name());
    }

    // -------------------------
    //   SET PERK + UPDATE CLIENT
    // -------------------------

    public void setPerk(PerkType perk) {
        this.perk = perk;
        this.getColor(); // refresh cache

        System.out.println("[BE] setPerk=" + perk + " client=" + level.isClientSide);

        setChanged();

        if (level != null) {
            BlockState state = getBlockState();
            level.sendBlockUpdated(worldPosition, state, state, 3);
            requestModelDataUpdate();
        }
    }




    @Override
    public net.minecraft.network.protocol.Packet<net.minecraft.network.protocol.game.ClientGamePacketListener> getUpdatePacket() {
        return net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(net.minecraft.network.Connection net,
                            net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket pkt) {
        handleUpdateTag(pkt.getTag());
    }




    // -------------------------
    //   MENU
    // -------------------------

    @Override
    public Component getDisplayName() {
        return Component.literal("Perk Machine");
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
        return new PerkSelectionMenu(id, inv, this);
    }

    // -------------------------
    //   COLOR
    // -------------------------

    public int getColor() {
        this.colorCache = switch (perk) {
            case NONE -> 0xAAAAAA;
            case SPEED -> 0x00FFAA;
            case SURVIVABILITY -> 0xFF4444;
            case RAPID_FIRE -> 0xFFAA00;
            case MIGHT -> 0x8888FF;
        };
        return this.colorCache;
    }

}
