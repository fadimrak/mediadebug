package mine.mod.modules;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.IntSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.Utils;
import meteordevelopment.meteorclient.utils.render.MeteorToast;
import meteordevelopment.orbit.EventHandler;
import mine.mod.AddonCategories;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.client.toast.Toast;

public class PlayerDetector extends Module {
    private static final int c = 120;
    private final SettingGroup l;
    private final Setting n;
    private final Setting ay;
    private final Setting bd;
    private final Set<UUID> af;
    private int i;
    private int ql;

    public PlayerDetector() {
        super(AddonCategories.MAIN, "PlayerDetector", "Detects nearby players.");
        this.l = this.settings.getDefaultGroup();
        
        this.n = this.l.add(new IntSetting.Builder()
            .name("Range")
            .description("Player detection range.")
            .defaultValue(64)
            .min(8)
            .sliderMax(128)
            .build()
        );
        
        this.ay = this.l.add(new IntSetting.Builder()
            .name("Delay")
            .description("Scan delay in ticks.")
            .defaultValue(20)
            .range(1, 80)
            .sliderRange(1, 40)
            .build()
        );
        
        this.bd = this.l.add(new BoolSetting.Builder()
            .name("Toast")
            .description("Shows a toast notification.")
            .defaultValue(true)
            .build()
        );
        
        this.af = new HashSet<>();
        this.ql = -1000000;
    }

    public void onActivate() {
        this.af.clear();
        this.i = 0;
    }

    public void onDeactivate() {
        this.af.clear();
    }

    @EventHandler
    private void onTick(TickEvent.Post post) {
        if (!this.isActive() || !((Boolean)this.bd.get()).booleanValue()) {
            return;
        }
        if (!mine.mod.q.iq()) {
            return;
        }
        if (!Utils.canUpdate() || this.mc.world == null || this.mc.player == null) {
            return;
        }
        if (this.mc.player.age < 120) {
            return;
        }
        if (this.i++ % (Integer)this.ay.get() != 0) {
            return;
        }
        int n = (Integer)this.n.get();
        int n2 = n * n;
        BlockPos class_23382 = this.mc.player.getBlockPos();
        for (PlayerEntity class_16572 : this.mc.world.getPlayers()) {
            String string;
            UUID uUID;
            double d;
            double d2;
            if (class_16572 == this.mc.player || (d2 = class_16572.getX() - ((double)class_23382.getX() + 0.5)) * d2 + (d = class_16572.getZ() - ((double)class_23382.getZ() + 0.5)) * d > (double)n2 || this.af.contains(uUID = class_16572.getUuid()) || this.mc.player.age - this.ql < 8) continue;
            BlockPos class_23383 = class_16572.getBlockPos();
            String string2 = class_16572.getName().getString();
            if (string2.isEmpty()) {
                string2 = "Unknown";
            }
            String string3 = string = mine.mod.pr.ri(class_16572, 220);
            String string5 = string2 + " [" + string3 + "]";
            this.af.add(uUID);
            this.ql = this.mc.player.age;
            this.w(string2, string5);
        }
    }

    private void w(String string, String string2) {
        if (this.mc.getToastManager() == null) {
            return;
        }
        this.mc.getToastManager().add(new MeteorToast.Builder(string).icon(Items.PLAYER_HEAD).text(string2).build());
    }
}