package net.ari.risinggraves.init;

import net.minecraft.resources.ResourceLocation;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


import net.ari.risinggraves.zombies.CZombie;
import net.ari.risinggraves.RisingGraves;
import net.ari.risinggraves.zombies.TankZombie;
import net.ari.risinggraves.zombies.FrostbiteZombie;



@Mod.EventBusSubscriber(modid = RisingGraves.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntities {

        public static final DeferredRegister<EntityType<?>> ENTITIES =
                DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, RisingGraves.MOD_ID);

        public static final RegistryObject<EntityType<CZombie>> CZOMBIE =
                ENTITIES.register("czombie",
                        () -> EntityType.Builder.of(CZombie::new, MobCategory.MONSTER)
                                .sized(0.6F, 1.95F)
                                .build("czombie"));

        public static final RegistryObject<EntityType<TankZombie>> TANK_ZOMBIE =
                ENTITIES.register("tank_zombie",
                        () -> EntityType.Builder.of(TankZombie::new, MobCategory.MONSTER)
                                .sized(0.6F, 1.95F)
                                .build("tank_zombie"));

        public static final RegistryObject<EntityType<FrostbiteZombie>> FROSTBITE_ZOMBIE =
                ENTITIES.register("frostbite_zombie",
                        () -> EntityType.Builder.of(FrostbiteZombie::new, MobCategory.MONSTER)
                        .sized(0.6F, 1.95F)
                        .build("frostbite_zombie"));


    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(CZOMBIE.get(), CZombie.createAttributes().build());
        event.put(TANK_ZOMBIE.get(), TankZombie.createAttributes().build());
        event.put(FROSTBITE_ZOMBIE.get(), FrostbiteZombie.createAttributes().build());

    }
}
