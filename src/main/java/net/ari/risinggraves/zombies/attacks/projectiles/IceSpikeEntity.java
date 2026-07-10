package net.ari.risinggraves.zombies.attacks.projectiles;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import java.util.Map;
import net.minecraft.util.Mth;
import java.util.HashMap;
import java.util.Iterator;



import net.minecraft.world.level.block.Blocks;

public class IceSpikeEntity {

    private static final Map<BlockPos, Integer> spikes = new HashMap<>();


    public static void spawnSpike(LivingEntity zombie, Vec3 targetPos) {
        Level level = zombie.level;

        Vec3 start = zombie.position();
        Vec3 end = targetPos;

        Vec3 direction = end.subtract(start).normalize().scale(1.5); // spacing between spikes
        Vec3 current = start;

        for (int i = 0; i < 10; i++) {
            int delayTicks = i * 2;
            Vec3 spikePos = new Vec3(current.x, current.y, current.z);


            int height = 1 + i;
            scheduled.put(tickCounter + delayTicks, () -> {
                spawnSingleSpike(level, spikePos, zombie, height);
            });

            current = current.add(direction);
        }

    }

    private static final Map<Integer, Runnable> scheduled = new HashMap<>();
    private static int tickCounter = 0;

    public static void tick(Level level) {
        tickCounter++;
        System.out.println("ICE TICK | spikes=" + spikes.size());
        // Run scheduled spike spawns
        Runnable task = scheduled.remove(tickCounter);
        if (task != null) {
            task.run();
        }

        // Your existing spike cleanup
        Iterator<Map.Entry<BlockPos, Integer>> it = spikes.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<BlockPos, Integer> entry = it.next();
            BlockPos pos = entry.getKey();
            int time = entry.getValue() - 1;

            if (time <= 0) {
                // Remove the tracked spike
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                it.remove();

                // Nuke all ICE in a radius around the spike
                int radius = 20;
                BlockPos center = pos;

                for (int x = -radius; x <= radius; x++) {
                    for (int y = -radius; y <= radius; y++) {
                        for (int z = -radius; z <= radius; z++) {
                            BlockPos check = center.offset(x, y, z);
                            if (level.getBlockState(check).getBlock() == Blocks.ICE) {
                                System.out.println("WIPING ICE AT: " + check);
                                level.setBlock(check, Blocks.AIR.defaultBlockState(), 3);
                            }
                        }
                    }
                }
            } else {
                entry.setValue(time);
            }

        }
    }


    private static void spawnSingleSpike(Level level, Vec3 pos, LivingEntity zombie, int height) {
        BlockPos bp = new BlockPos(
            Mth.floor(pos.x),
            Mth.floor(pos.y),
            Mth.floor(pos.z)
        );


        // Build spike upward based on height
        for (int h = 0; h < height; h++) {
            BlockPos spikePos = bp.above(h);
            level.setBlock(spikePos, Blocks.ICE.defaultBlockState(), 3);
            spikes.put(spikePos, 10);
        }

        // Damage logic stays EXACTLY the same
        for (Player p : level.players()) {
            double dist = p.position().distanceTo(pos);

            if (dist < 1.2) {
                p.hurt(DamageSource.mobAttack(zombie), 14);
                freezePlayer(level, p.blockPosition());
            } else if (dist < 2.5) {
                p.hurt(DamageSource.mobAttack(zombie), 6);
                p.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 2));
            }
        }
    }


    private static void freezePlayer(Level level, BlockPos p) {
        // Ice cage
        level.setBlock(p.north(), Blocks.ICE.defaultBlockState(), 3);
        level.setBlock(p.south(), Blocks.ICE.defaultBlockState(), 3);
        level.setBlock(p.east(), Blocks.ICE.defaultBlockState(), 3);
        level.setBlock(p.west(), Blocks.ICE.defaultBlockState(), 3);

        // Remove after 2 seconds
        level.scheduleTick(p.north(), Blocks.AIR, 40);
        level.scheduleTick(p.south(), Blocks.AIR, 40);
        level.scheduleTick(p.east(), Blocks.AIR, 40);
        level.scheduleTick(p.west(), Blocks.AIR, 40);
    }
}
 