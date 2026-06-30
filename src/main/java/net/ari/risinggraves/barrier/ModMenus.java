package net.ari.risinggraves.barrier;

import net.ari.risinggraves.RisingGraves;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.ari.risinggraves.barrier.CostMenu;
import net.ari.risinggraves.perks.PerkSelectionMenu;
import net.minecraftforge.network.IContainerFactory;
import net.ari.risinggraves.block.wallbuy.WallbuyCostMenu;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.ari.risinggraves.block.WallbuyBlockEntity;
import net.minecraft.nbt.CompoundTag;





public class ModMenus {

        public static final DeferredRegister<MenuType<?>> MENUS =
                DeferredRegister.create(ForgeRegistries.MENU_TYPES, RisingGraves.MOD_ID);

        public static final RegistryObject<MenuType<CostMenu>> COST_MENU =
                MENUS.register("cost_menu", () ->
                        IForgeMenuType.create((windowId, inv, data) -> {
                        CompoundTag tag = data.readNbt();
                        return new CostMenu(windowId, inv, inv.player, tag);
                        })
                );


        public static final RegistryObject<MenuType<PerkSelectionMenu>> PERK_MENU =
                MENUS.register("perk_menu",
                () -> new MenuType<>((IContainerFactory<PerkSelectionMenu>)
                        (id, inv, buf) -> new PerkSelectionMenu(id, inv, buf)
                ));

        public static final RegistryObject<MenuType<WallbuyCostMenu>> WALLBUY_MENU =
                MENUS.register("wallbuy_menu", () -> new MenuType<>(WallbuyCostMenu::new));


}
