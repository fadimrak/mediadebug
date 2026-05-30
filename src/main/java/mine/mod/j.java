package mine.mod;

import com.mojang.logging.LogUtils;
import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.systems.modules.Category;
import meteordevelopment.meteorclient.systems.modules.Modules;
import mine.mod.AddonCategories;
import mine.mod.modules.PlayerDetector;
import mine.mod.modules.RegionMap;
import mine.mod.modules.SpawnerNotifier;
import mine.mod.modules.finder.ClusterLightDetector;
import org.slf4j.Logger;

public class j extends MeteorAddon {
    public static final Logger rv = LogUtils.getLogger();
    public static final Category qn;

    @Override
    public void onInitialize() {
        this.uft();
    }

    private void uft() {
        Modules modules = Modules.get();
        modules.add(new SpawnerNotifier());
        modules.add(new PlayerDetector());
        modules.add(new ClusterLightDetector());
        modules.add(new RegionMap());
    }

    @Override
    public void onRegisterCategories() {
        Modules.registerCategory(qn);
    }

    @Override
    public String getPackage() {
        return "mine.mod";
    }

    static {
        qn = AddonCategories.MAIN;
    }
}
