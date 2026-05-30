package mine.mod.spawner;

import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.EntityType;

public record SpawnerNotifierData$TrackedSpawner(BlockPos pos, String mobLabel, double distanceBlocks, EntityType spawnedType) {
    public BlockPos jn() {
        return this.pos;
    }

    public String pqx(Object[] objectArray) {
        return this.mobLabel;
    }

    public double iv() {
        return this.distanceBlocks;
    }

    public EntityType sbm(Object[] objectArray) {
        return this.spawnedType;
    }
}
