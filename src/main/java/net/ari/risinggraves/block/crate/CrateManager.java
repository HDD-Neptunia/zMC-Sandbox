package net.ari.risinggraves.block.crate;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public class CrateManager {

    public static final CrateManager INSTANCE = new CrateManager();

    private final List<BlockPos> crateLocations = new ArrayList<>();
    private BlockPos activeCrate = null;

    // Chance the box breaks (e.g., 10%)
    private static final double BREAK_CHANCE = 0.10;

    private CrateManager() {}

    public void registerCrate(BlockPos pos) {
        if (!crateLocations.contains(pos)) {
            crateLocations.add(pos);

            // If this is the first crate, make it active
            if (activeCrate == null) {
                activeCrate = pos;
            }
        }
    }

    public boolean isActive(BlockPos pos) {
        return activeCrate != null && activeCrate.equals(pos);
    }

    public void trySwitch(Level level, Player player) {
        if (Math.random() < BREAK_CHANCE) {
            switchCrate(level, player);
        }
    }

    private void switchCrate(Level level, Player culprit) {
        if (crateLocations.isEmpty()) return;

        BlockPos newPos;
        do {
            newPos = crateLocations.get(level.random.nextInt(crateLocations.size()));
        } while (newPos.equals(activeCrate));

        activeCrate = newPos;

        // Message to ALL players
        for (Player p : level.players()) {
            p.displayClientMessage(Component.literal("§eThe Mystery Box has moved!"), false);
        }

        // Special roast message for the player who broke it
        if (culprit != null) {
            culprit.displayClientMessage(
                Component.literal("§6You should stop gambling."),
                false
            );
        }
    }


    public BlockPos getActiveCrate() {
        return activeCrate;
    }
}
