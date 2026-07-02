package net.ari.risinggraves.block.wallbuy;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import net.minecraft.network.chat.Component;
import net.minecraft.network.FriendlyByteBuf;

import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;

import com.mojang.blaze3d.vertex.PoseStack;


import net.ari.risinggraves.block.WallbuyBlockEntity;
import net.ari.risinggraves.barrier.ModMenus;


public class WallbuyCostMenu extends AbstractContainerMenu {

    private final WallbuyBlockEntity wallbuy;
    private final Item item;

    public WallbuyCostMenu(int id, Inventory inv, WallbuyBlockEntity wallbuy, Item item) {
        super(ModMenus.WALLBUY_MENU.get(), id);
        this.wallbuy = wallbuy;
        this.item = item;
        System.out.println("[WALLBUY MENU] Constructed with item: " + item);
    }

    @Override
    public boolean stillValid(Player player) { return true; }

    @Override
    public ItemStack quickMoveStack(Player player, int index) { return ItemStack.EMPTY; }

    public WallbuyBlockEntity getWallbuy() { return wallbuy; }
    public Item getItem() { return item; }
}
