package net.ari.risinggraves.barrier;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraftforge.network.NetworkHooks;
import net.minecraft.server.level.ServerPlayer;



public class WandFunction extends Item {

    public WandFunction(Properties props) {
        super(props);
    }


    @Override
    public InteractionResult useOn(UseOnContext ctx) {

        System.out.println("WAND USEON FIRED");

        Player player = ctx.getPlayer();
        Level level = ctx.getLevel();
        BlockPos pos = ctx.getClickedPos();

        if (level.isClientSide) return InteractionResult.SUCCESS;

        ItemStack stack = ctx.getItemInHand();
        CompoundTag tag = stack.getOrCreateTag();

        ListTag list = tag.getList("selected", Tag.TAG_COMPOUND);

        CompoundTag entry = new CompoundTag();
        entry.putInt("x", pos.getX());
        entry.putInt("y", pos.getY());
        entry.putInt("z", pos.getZ());
        list.add(entry);

        tag.put("selected", list);

        player.displayClientMessage(Component.literal("§aAdded block to blockade selection"), true);

        return InteractionResult.SUCCESS;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!player.isShiftKeyDown())
            return InteractionResultHolder.pass(stack);

        CompoundTag tag = stack.getOrCreateTag();
        if (!level.isClientSide) {
            NetworkHooks.openScreen((ServerPlayer) player, new CostMenuProvider(tag), buf -> {
                buf.writeNbt(tag);
            });
        }



        return InteractionResultHolder.success(stack);
    }
}
