package net.ari.risinggraves.perks;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;

import net.minecraft.network.chat.Component;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.Gui;

import com.mojang.blaze3d.vertex.PoseStack;


import net.ari.risinggraves.networking.PerkSelectionPacket;
import net.ari.risinggraves.block.PerkMachineBlockEntity;


public class PerkSelectionScreen extends AbstractContainerScreen<PerkSelectionMenu> {

    public PerkSelectionScreen(PerkSelectionMenu menu, Inventory inv, Component title) {
        super(menu, inv, Component.empty()); // remove faint default title
    }

    @Override
    protected void init() {
        super.init();

        int boxWidth = 180;
        int boxHeight = 160;

        int x = this.leftPos + (this.imageWidth - boxWidth) / 2;
        int y = this.topPos + (this.imageHeight - boxHeight) / 2;

        // Center buttons
        int buttonWidth = 120;
        int buttonX = x + (boxWidth - buttonWidth) / 2;

        addButton(buttonX, y + 40, "Speed", PerkType.SPEED);
        addButton(buttonX, y + 65, "Survivability", PerkType.SURVIVABILITY);
        addButton(buttonX, y + 90, "Rapid Fire", PerkType.RAPID_FIRE);
        addButton(buttonX, y + 115, "Might", PerkType.MIGHT);
    }

    private void onClicked(PerkType perk) {
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
    protected void renderBg(PoseStack pose, float partialTicks, int mouseX, int mouseY) {
        int boxWidth = 180;
        int boxHeight = 160;

        int x = this.leftPos + (this.imageWidth - boxWidth) / 2;
        int y = this.topPos + (this.imageHeight - boxHeight) / 2;

        int bgColor = 0x08000000;
        int borderColor = 0x33FFFFFF;

        Gui.fill(pose, x, y, x + boxWidth, y + boxHeight, bgColor);

        // Border
        Gui.fill(pose, x, y, x + boxWidth, y + 1, borderColor);
        Gui.fill(pose, x, y + boxHeight - 1, x + boxWidth, y + boxHeight, borderColor);
        Gui.fill(pose, x, y, x + 1, y + boxHeight, borderColor);
        Gui.fill(pose, x + boxWidth - 1, y, x + boxWidth, y + boxHeight, borderColor);
    }

    @Override
    protected void renderLabels(PoseStack pose, int mouseX, int mouseY) {
    }

    @Override
    public void render(PoseStack pose, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(pose);
        super.render(pose, mouseX, mouseY, partialTicks);

        int boxWidth = 180;
        int boxHeight = 160;

        int x = this.leftPos + (this.imageWidth - boxWidth) / 2;
        int y = this.topPos + (this.imageHeight - boxHeight) / 2;

        int titleColor = 0xFFF2C76E;

        String title = "Perk Machine";
        int titleWidth = this.font.width(title);
        int titleX = x + (boxWidth - titleWidth) / 2;

        this.font.drawShadow(pose, title, titleX, y + 15, titleColor);
    }
}

