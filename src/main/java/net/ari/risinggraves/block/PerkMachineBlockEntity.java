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


public class PerkMachineBlockEntity extends BlockEntity implements MenuProvider {

    private PerkType perk = PerkType.NONE;
    private int colorCache;

    public static final ModelProperty<Integer> COLOR_PROPERTY = new ModelProperty<>();

    public PerkMachineBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PERK_MACHINE.get(), pos, state);
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
        load(tag);
    }

    // -------------------------
    //   SAVE + LOAD
    // -------------------------

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("perk")) {
            this.perk = PerkType.valueOf(tag.getString("perk"));
            System.out.println("[BE] load: perk=" + this.perk + " at " + worldPosition + " (client=" + level.isClientSide + ")");
        } else {
            System.out.println("[BE] load: NO 'perk' key at " + worldPosition);
        }
    }

    @Override
    public void onLoad() {
        System.out.println("[BE] onLoad client=" + level.isClientSide + " pos=" + worldPosition);
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
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
            requestModelDataUpdate();
        }
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
