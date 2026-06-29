package net.ari.risinggraves.perks;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import com.mojang.blaze3d.vertex.PoseStack;
import net.ari.risinggraves.block.PerkMachineBlockEntity;

public class PerkSelectionMenu extends AbstractContainerMenu {

    private final PerkMachineBlockEntity machine;

    public PerkSelectionMenu(int id, Inventory inv, PerkMachineBlockEntity machine) {
        super(null, id);
        this.machine = machine;
    }

    public PerkMachineBlockEntity getMachine() {
        return machine;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}
