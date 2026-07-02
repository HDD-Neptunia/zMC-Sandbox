package net.ari.risinggraves.perks;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.network.chat.Component;
import net.minecraft.network.FriendlyByteBuf;

import net.minecraft.core.BlockPos;

import net.ari.risinggraves.barrier.ModMenus;
import net.ari.risinggraves.block.PerkMachineBlockEntity;

import net.ari.risinggraves.block.PerkMachineBlockEntity;


public class PerkSelectionMenu extends AbstractContainerMenu {

    private final PerkMachineBlockEntity machine;

    public PerkSelectionMenu(int id, Inventory inv, PerkMachineBlockEntity machine) {
        super(ModMenus.PERK_MENU.get(), id);
        this.machine = machine;
    }

    public PerkSelectionMenu(int id, Inventory inv, FriendlyByteBuf buf) {
        super(ModMenus.PERK_MENU.get(), id);

        BlockPos pos = buf.readBlockPos();
        this.machine = (PerkMachineBlockEntity) inv.player.level.getBlockEntity(pos);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    public PerkMachineBlockEntity getMachine() {
        return machine;
    }
}
