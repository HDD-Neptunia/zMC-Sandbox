package net.ari.risinggraves.perks;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.ari.risinggraves.networking.PerkSelectionPacket;
import net.ari.risinggraves.block.PerkMachineBlockEntity;

public class PerkSelectionScreen extends AbstractContainerScreen<PerkSelectionMenu> {

    public PerkSelectionScreen(PerkSelectionMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);
    }

    @Override
    protected void init() {
        super.init();

        int x = this.leftPos + 10;
        int y = this.topPos + 10;

        addButton(x, y, "Speed", PerkType.SPEED);
        addButton(x, y + 25, "Survivability", PerkType.SURVIVABILITY);
        addButton(x, y + 50, "Rapid Fire", PerkType.RAPID_FIRE);
        addButton(x, y + 75, "Might", PerkType.MIGHT);
    }

    private void onClicked(PerkType perk) {
        // send packet to server
        PerkSelectionPacket.send(perk);
    }

    private void addButton(int x, int y, String name, PerkType perk) {
        this.addRenderableWidget(
            Button.builder(Component.literal(name), btn -> onClicked(perk))
                .bounds(x, y, 120, 20)
                .build()
            );

    }

    @Override
    protected void renderBg(PoseStack poseStack, float partialTicks, int mouseX, int mouseY) {}
}
