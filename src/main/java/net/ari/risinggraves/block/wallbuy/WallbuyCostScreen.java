package net.ari.risinggraves.block.wallbuy;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.world.item.ItemStack;
import net.ari.risinggraves.networking.WallbuyConfirmPacket;


public class WallbuyCostScreen extends AbstractContainerScreen<WallbuyCostMenu> {

    private EditBox costField;

    public WallbuyCostScreen(WallbuyCostMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);
    }

    @Override
    protected void init() {
        super.init();

        // Cost input box
        this.costField = new EditBox(this.font,
                this.leftPos + 10,
                this.topPos + 10,
                80,
                20,
                Component.literal("Cost"));

        this.costField.setMaxLength(10);
        this.costField.setValue("0");
        this.addRenderableWidget(this.costField);

        // Confirm button
        this.addRenderableWidget(
            Button.builder(Component.literal("Confirm"), btn -> onConfirmPressed())
                .bounds(this.leftPos + 10, this.topPos + 40, 120, 20)
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
    protected void renderBg(PoseStack poseStack, float partialTicks, int mouseX, int mouseY) {
        // No background for now
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, partialTicks);
        this.costField.render(poseStack, mouseX, mouseY, partialTicks);
    }
}
