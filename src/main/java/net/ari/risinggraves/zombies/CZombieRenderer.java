package net.ari.risinggraves.zombies;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Zombie;

import net.ari.risinggraves.RisingGraves;

public class CZombieRenderer extends ZombieRenderer {

    private static final ResourceLocation TEXTURE =
            new ResourceLocation("minecraft", "textures/entity/zombie/zombie.png");

    public CZombieRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(Zombie entity) {
        return TEXTURE;
    }
}




