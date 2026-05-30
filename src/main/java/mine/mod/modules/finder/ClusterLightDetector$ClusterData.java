package mine.mod.modules.finder;

import net.minecraft.util.math.BlockPos;

public record ClusterLightDetector$ClusterData(BlockPos pos, int lightWorld, String clusterType) {
    public BlockPos qj() {
        return this.pos;
    }

    public int sk() {
        return this.lightWorld;
    }

    public String kki(Object[] objectArray) {
        return this.clusterType;
    }
}
