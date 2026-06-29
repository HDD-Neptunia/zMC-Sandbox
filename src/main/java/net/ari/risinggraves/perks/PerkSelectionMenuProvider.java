package net.ari.risinggraves.perks;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import com.mojang.blaze3d.vertex.PoseStack;
import net.ari.risinggraves.block.PerkMachineBlockEntity;
import net.minecraft.world.MenuProvider;


public class PerkSelectionMenuProvider implements MenuProvider {

    private final PerkMachineBlockEntity machine;

    public PerkSelectionMenuProvider(PerkMachineBlockEntity machine) {
        this.machine = machine;
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Select Perk");
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
        return new PerkSelectionMenu(id, inv, machine);
    }
}
