package net.ari.risinggraves.barrier;

import net.minecraft.world.MenuProvider;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;

import net.minecraft.network.chat.Component;

import net.minecraft.nbt.CompoundTag;

public class CostMenuProvider implements MenuProvider {

    private final CompoundTag wandTag;

    public CostMenuProvider(CompoundTag wandTag) {
        this.wandTag = wandTag;
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
        return new CostMenu(id, inv, player, wandTag);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Set Blockade Cost");
    }
}
