package init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import zombies.CZombie;
import cogs.debug;

@Mod.EventBusSubscriber(modid = "undead", bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntities {
	
	
	
		public static final 
		  DeferredRegister<EntityType<?>> ENTITIES =
		  DeferredRegister.create(ForgeRegistries.ENTITIES, "undead");
		
		public static final RegistryObject<EntityType<CZombie>>
		CZOMBIE = ENTITIES.register("czombie",
				() -> EntityType.Builder.of(CZombie::new, MobCategory.MONSTER)
					.sized(0.6F, 1.95F)
					.build(new ResourceLocation("undead", "czombie").toString()));
	
		@SubscribeEvent
		public static void
		registerAttributes(EntityAttributeCreationEvent event) {
			debug.instance();
			event.put(CZOMBIE.get(),
		CZombie.createAttributes().build());
			System.out.println("Attributes registered");
		}
	
}
