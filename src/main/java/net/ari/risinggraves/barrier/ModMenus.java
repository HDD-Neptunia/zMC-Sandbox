package net.ari.risinggraves.barrier;

import net.ari.risinggraves.RisingGraves;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.ari.risinggraves.barrier.CostMenu;


public class ModMenus {

    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, RisingGraves.MOD_ID);

    public static final RegistryObject<MenuType<CostMenu>> COST_MENU =
            MENUS.register("cost_menu", () -> new MenuType<>(CostMenu::new));
}
