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



@Mod.EventBusSubscriber(modid = RisingGraves.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntities {
	
	public static final 
		DeferredRegister<EntityType<?>> ENTITIES =
			DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, RisingGraves.MOD_ID);
		
	public static final RegistryObject<EntityType<CZombie>>
		CZOMBIE = ENTITIES.register("czombie",
			() -> EntityType.Builder.of(CZombie::new, MobCategory.MONSTER)
				.sized(0.6F, 1.95F)
				.build(RisingGraves.MOD_ID + ":czombie")
			);
	
	@SubscribeEvent
		public static void registerAttributes(EntityAttributeCreationEvent event) {
			event.put(CZOMBIE.get(),
			CZombie.createAttributes().build());
			System.out.println("Attributes registered");
	}
}
