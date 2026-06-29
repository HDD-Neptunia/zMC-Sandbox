package net.ari.risinggraves.barrier;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.network.chat.Component;
import com.mojang.blaze3d.vertex.PoseStack;
import net.ari.risinggraves.networking.ConfirmCostPacket;
import net.ari.risinggraves.networking.Networking;

import net.minecraft.client.gui.components.Button;


public class CostScreen extends AbstractContainerScreen<CostMenu> {

    private final CostMenu menu;
    private EditBox costField;

    public CostScreen(CostMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);
        this.menu = menu;
    }

    @Override
    protected void init() {
        super.init();

        // Text field: x, y, width, height
        this.costField = new EditBox(this.font, this.leftPos + 10, this.topPos + 10, 80, 20, Component.literal("Cost"));
        this.costField.setMaxLength(10);
        this.costField.setValue("0");
        this.addRenderableWidget(this.costField);

        // Confirm button
        Button confirm = Button.builder(
                Component.literal("Confirm"),
                btn -> onConfirmPressed()
            )
            .bounds(this.leftPos + 10, this.topPos + 40, 80, 20)
            .build();

        this.addRenderableWidget(confirm);

    }


    @Override
    protected void renderBg(PoseStack poseStack, float partialTicks, int mouseX, int mouseY) {
        // You can leave this empty for now
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        super.render(poseStack, mouseX, mouseY, partialTicks);
    }

    private void onConfirmPressed() {
        try {
            int cost = Integer.parseInt(costField.getValue());
            Networking.CHANNEL.sendToServer(
                new ConfirmCostPacket(cost, menu.getCollectedBlocks())
            );
            this.minecraft.player.closeContainer();
        } catch (NumberFormatException e) {
            this.minecraft.player.displayClientMessage(
                    Component.literal("Invalid number"),
                    true
            );
        }
    }
}
