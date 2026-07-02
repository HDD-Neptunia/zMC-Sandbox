package net.ari.risinggraves.block.wallbuy;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.item.ItemStack;

import net.minecraft.network.chat.Component;
import net.minecraft.network.FriendlyByteBuf;

import net.minecraft.server.level.ServerPlayer;

import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;

import com.mojang.blaze3d.vertex.PoseStack;


import net.ari.risinggraves.block.WallbuyBlockEntity;


public class WallbuyCostMenuProvider implements MenuProvider {

    private final WallbuyBlockEntity wallbuy;
    private final Item item;

    public WallbuyCostMenuProvider(WallbuyBlockEntity wallbuy, Item item) {
        this.wallbuy = wallbuy;
        this.item = item;
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Wallbuy Cost");
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
        return new WallbuyCostMenu(id, inv, wallbuy, item);
    }
}

