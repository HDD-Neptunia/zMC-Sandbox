package net.ari.risinggraves.zombies.attacks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.damagesource.DamageSource;


import net.minecraft.world.level.block.Blocks;

public class IceSpikeEntity {

    public static void spawnSpike(LivingEntity zombie, Player player) {
        Level level = zombie.level;
        BlockPos p = player.blockPosition();

        double dist = zombie.distanceTo(player);

        // Distance-based damage
        if (dist > 6) {
            player.hurt(DamageSource.mobAttack(zombie), 4);



        } else if (dist > 3) {
            player.hurt(DamageSource.mobAttack(zombie), 6);



        } else {
            player.hurt(DamageSource.mobAttack(zombie), 14);



            freezePlayer(level, p);
        }
    }

    private static void freezePlayer(Level level, BlockPos p) {
        // Ice cage
        level.setBlock(p.north(), Blocks.ICE.defaultBlockState(), 3);
        level.setBlock(p.south(), Blocks.ICE.defaultBlockState(), 3);
        level.setBlock(p.east(), Blocks.ICE.defaultBlockState(), 3);
        level.setBlock(p.west(), Blocks.ICE.defaultBlockState(), 3);

        // Remove after 2 seconds
        level.scheduleTick(p.north(), Blocks.ICE, 40);
        level.scheduleTick(p.south(), Blocks.ICE, 40);
        level.scheduleTick(p.east(), Blocks.ICE, 40);
        level.scheduleTick(p.west(), Blocks.ICE, 40);
    }
}
 