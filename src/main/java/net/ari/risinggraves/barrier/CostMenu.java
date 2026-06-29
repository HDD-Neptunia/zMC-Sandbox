package net.ari.risinggraves.barrier;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.Level;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.ari.risinggraves.barrier.ModMenus;
import net.ari.risinggraves.networking.Networking;
import net.ari.risinggraves.networking.SyncBlockadesPacket;
import net.minecraftforge.network.PacketDistributor;


import java.util.List;
import java.util.ArrayList;

import net.ari.risinggraves.RisingGraves;
import net.ari.risinggraves.barrier.BlockadeData;
import net.ari.risinggraves.barrier.BlockadeCluster;



public class CostMenu extends AbstractContainerMenu {

    private final Player player;
    private final Level level;
    private final List<BlockPos> collectedBlocks;

    public CostMenu(int id, Inventory inv, Player player, CompoundTag wandTag) {
        super(ModMenus.COST_MENU.get(), id);

        this.player = player;
        this.level = player.level;
        this.collectedBlocks = readBlocksFromTag(wandTag);
    }

    public CostMenu(int id, Inventory inv) {
        super(ModMenus.COST_MENU.get(), id);

        this.player = inv.player;
        this.level = player.level;

        CompoundTag tag = player.getMainHandItem().getOrCreateTag();
        this.collectedBlocks = readBlocksFromTag(tag);
    }


    private List<BlockPos> readBlocksFromTag(CompoundTag tag) {
        List<BlockPos> list = new ArrayList<>();
        ListTag blocks = tag.getList("selected", Tag.TAG_COMPOUND);

        for (int i = 0; i < blocks.size(); i++) {
            CompoundTag b = blocks.getCompound(i);
            list.add(new BlockPos(b.getInt("x"), b.getInt("y"), b.getInt("z")));
        }

        return list;
    }

    public List<BlockPos> getCollectedBlocks() {
        return collectedBlocks;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }

    public void onConfirm(int cost) {
        //noop
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}
