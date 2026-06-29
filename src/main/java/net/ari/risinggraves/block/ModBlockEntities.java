package net.ari.risinggraves.block;

import net.ari.risinggraves.RisingGraves;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, RisingGraves.MOD_ID);

        public static final RegistryObject<BlockEntityType<CustomSpawnerBlockEntity>> CUSTOM_SPAWNER =
                BLOCK_ENTITIES.register("custom_spawner",
                        () -> BlockEntityType.Builder.of(
                                CustomSpawnerBlockEntity::new,
                                ModBlocks.CUSTOM_SPAWNER.get()
                        ).build(null));

        public static final RegistryObject<BlockEntityType<PerkMachineBlockEntity>> PERK_MACHINE =
                BLOCK_ENTITIES.register("perk_machine",
                        () -> BlockEntityType.Builder.of(
                                PerkMachineBlockEntity::new,
                                ModBlocks.PERK_MACHINE.get()
                        ).build(null));

        public static final RegistryObject<BlockEntityType<WallbuyBlockEntity>> WALLBUY =
                BLOCK_ENTITIES.register("wallbuy",
                        () -> BlockEntityType.Builder.of(
                                WallbuyBlockEntity::new,
                                ModBlocks.WALLBUY.get()
                        ).build(null));

}

