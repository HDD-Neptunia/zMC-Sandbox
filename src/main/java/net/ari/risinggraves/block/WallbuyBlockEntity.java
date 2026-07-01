package net.ari.risinggraves.block;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;



public class WallbuyBlockEntity extends BlockEntity {

    private Item item = Items.AIR;
    private int cost = 0;

    public WallbuyBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.WALLBUY.get(), pos, state);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag);
        return tag;
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public Item getItem() { return item; }
    public int getCost() { return cost; }

    public void setItem(Item item) {
        this.item = item;
        setChanged();

        if (level != null && !level.isClientSide) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    
    public void setCost(int cost) { this.cost = cost; setChanged(); }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putString("Item", ForgeRegistries.ITEMS.getKey(item).toString());
        tag.putInt("Cost", cost);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(tag.getString("Item")));
        cost = tag.getInt("Cost");
    }
}
