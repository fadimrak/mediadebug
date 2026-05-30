package mine.mod.spawner;

import java.util.List;

public final class SpawnerNotifierData {
    private static volatile List<SpawnerNotifierData$TrackedSpawner> en = List.of();

    private SpawnerNotifierData() {
    }

    public static List<SpawnerNotifierData$TrackedSpawner> kw() {
        return en;
    }

    public static void r(List<SpawnerNotifierData$TrackedSpawner> list) {
        en = list.isEmpty() ? List.of() : List.copyOf(list);
    }
}
