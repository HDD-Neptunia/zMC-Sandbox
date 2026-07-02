package net.ari.risinggraves.block.wallbuy;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import net.minecraft.network.chat.Component;

import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.components.Button;

import com.mojang.blaze3d.vertex.PoseStack;


import net.ari.risinggraves.networking.WallbuyConfirmPacket;


public class WallbuyCostScreen extends AbstractContainerScreen<WallbuyCostMenu> {

    private EditBox costField;

    public WallbuyCostScreen(WallbuyCostMenu menu, Inventory inv, Component title) {
        super(menu, inv, Component.empty());
    }

    @Override
    protected void init() {
        super.init();

        int boxWidth = 180;
        int boxHeight = 110;

        int x = this.leftPos + (this.imageWidth - boxWidth) / 2;
        int y = this.topPos + (this.imageHeight - boxHeight) / 2;

        int costBoxWidth = 80;
        int costBoxX = x + (boxWidth - costBoxWidth) / 2;

        this.costField = new EditBox(this.font,
                costBoxX,
                y + 40,
                costBoxWidth,
                20,
                Component.literal("Cost"));

        this.costField.setMaxLength(10);
        this.costField.setValue("0");
        this.addRenderableWidget(this.costField);

        int buttonWidth = 120;
        int buttonX = x + (boxWidth - buttonWidth) / 2;

        this.addRenderableWidget(
            Button.builder(Component.literal("Confirm"), btn -> onConfirmPressed())
                .bounds(buttonX, y + 70, buttonWidth, 20)
                .build()
        );
    }

    private void onConfirmPressed() {
        try {
            int cost = Integer.parseInt(costField.getValue());
            WallbuyConfirmPacket.send(new ItemStack(menu.getItem()), cost);
            this.minecraft.player.closeContainer();
        } catch (NumberFormatException e) {
            this.minecraft.player.displayClientMessage(
                    Component.literal("Invalid number"), true);
        }
    }

    @Override
    protected void renderBg(PoseStack pose, float partialTicks, int mouseX, int mouseY) {
        int boxWidth = 180;
        int boxHeight = 110;

        int x = this.leftPos + (this.imageWidth - boxWidth) / 2;
        int y = this.topPos + (this.imageHeight - boxHeight) / 2;

        int bgColor = 0x08000000;
        int borderColor = 0x33FFFFFF;

        Gui.fill(pose, x, y, x + boxWidth, y + boxHeight, bgColor);

        Gui.fill(pose, x, y, x + boxWidth, y + 1, borderColor);
        Gui.fill(pose, x, y + boxHeight - 1, x + boxWidth, y + boxHeight, borderColor);
        Gui.fill(pose, x, y, x + 1, y + boxHeight, borderColor);
        Gui.fill(pose, x + boxWidth - 1, y, x + boxWidth, y + boxHeight, borderColor);
    }

    @Override
    public void render(PoseStack pose, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(pose);
        super.render(pose, mouseX, mouseY, partialTicks);

        int boxWidth = 180;
        int boxHeight = 110;

        int x = this.leftPos + (this.imageWidth - boxWidth) / 2;
        int y = this.topPos + (this.imageHeight - boxHeight) / 2;

        int titleColor = 0xFFF2C76E;

        // Centered title
        String title = "Set Wallbuy Cost";
        int titleWidth = this.font.width(title);
        int titleX = x + (boxWidth - titleWidth) / 2;

        this.font.drawShadow(pose, title, titleX, y + 15, titleColor);

        this.costField.render(pose, mouseX, mouseY, partialTicks);
    }
}
