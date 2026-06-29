package net.ari.risinggraves.block.wallbuy;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import com.mojang.blaze3d.vertex.PoseStack;
import net.ari.risinggraves.block.WallbuyBlockEntity;

public class WallbuyCostMenu extends AbstractContainerMenu {

    private final WallbuyBlockEntity wallbuy;
    private final Item item;

    public WallbuyCostMenu(int id, Inventory inv, WallbuyBlockEntity wallbuy, Item item) {
        super(null, id);
        this.wallbuy = wallbuy;
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}
