package net.ari.risinggraves.block;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.ari.risinggraves.perks.PerkType;

public class PerkMachineBlockEntity extends BlockEntity {

    private PerkType perk = PerkType.SPEED;

    public PerkMachineBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PERK_MACHINE.get(), pos, state);
    }

    public PerkType getPerk() {
        return perk;
    }

    public void setPerk(PerkType perk) {
        this.perk = perk;
        setChanged();
    }

    public int getColor() {
        return switch (perk) {
            case SPEED -> 0x00FFAA;
            case SURVIVABILITY -> 0xFF4444;
            case RAPID_FIRE -> 0xFFAA00;
            case MIGHT -> 0x8888FF;
        };
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        perk = PerkType.valueOf(tag.getString("Perk"));
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putString("Perk", perk.name());
    }
}
