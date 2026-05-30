package mine.mod.modules;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import meteordevelopment.meteorclient.events.render.Render3DEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.renderer.ShapeMode;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.ColorSetting;
import meteordevelopment.meteorclient.settings.IntSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.Utils;
import meteordevelopment.meteorclient.utils.player.PlayerUtils;
import meteordevelopment.meteorclient.utils.render.MeteorToast;
import meteordevelopment.meteorclient.utils.render.RenderUtils;
import meteordevelopment.meteorclient.utils.render.color.Color;
import meteordevelopment.meteorclient.utils.render.color.SettingColor;
import meteordevelopment.orbit.EventHandler;
import mine.mod.AddonCategories;
import mine.mod.spawner.SpawnerNotifierData;
import mine.mod.spawner.SpawnerNotifierData$TrackedSpawner;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ItemConvertible;
import net.minecraft.world.World;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.text.Text;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.world.chunk.WorldChunk;
import net.minecraft.client.toast.Toast;
import net.minecraft.text.MutableText;
import net.minecraft.client.network.ClientPlayNetworkHandler;

public class SpawnerNotifier extends Module {
    private static final int c = 40;
    private static final float y = 4.6f;
    private static final double dh = 1.75;
    private static final double mb = 0.58;
    private final SettingGroup l;
    private final SettingGroup aj;
    private final SettingGroup d;
    private final SettingGroup rj;
    private final Setting n;
    public final Setting ws;
    private final Setting q;
    private final Setting zp;
    private final Setting jf;
    private final Setting bd;
    private final Setting o;
    private final Setting fl;
    private final Setting ay;
    private final Setting b;
    private final Set<Long> af;
    private int jk;
    private final Color cv;
    private final Color hj;
    private final Color xw;
    private final Color ce;

    public SpawnerNotifier() {
        super(AddonCategories.MAIN, "SpawnerNotifier", "Finds and displays nearby spawners.");
        this.l = this.settings.getDefaultGroup();
        this.aj = this.settings.createGroup("General");
        this.d = this.settings.createGroup("Render");
        this.rj = this.settings.createGroup("Advanced");

        this.n = this.l.add(new IntSetting.Builder().name("Range").description("Scanning range.").defaultValue(64).min(8).sliderMax(128).build());
        this.ws = this.d.add(new ColorSetting.Builder().name("Color").description("ESP color.").defaultValue(new SettingColor(0, 255, 100, 200)).build());
        this.q = this.aj.add(new BoolSetting.Builder().name("Chat").description("Sends a message in chat.").defaultValue(false).build());
        this.zp = this.d.add(new BoolSetting.Builder().name("Tracer").description("Draws a line to the spawner.").defaultValue(true).build());
        this.jf = this.aj.add(new BoolSetting.Builder().name("Console").description("Logs to the console.").defaultValue(false).build());
        this.bd = this.aj.add(new BoolSetting.Builder().name("Toast").description("Shows a toast notification.").defaultValue(true).build());
        this.o = this.d.add(new BoolSetting.Builder().name("Box").description("Draws a box around the spawner.").defaultValue(true).build());
        this.fl = this.d.add(new BoolSetting.Builder().name("Fill").description("Fills the box.").defaultValue(false).visible(() -> (Boolean)this.o.get()).build());
        this.ay = this.rj.add(new IntSetting.Builder().name("Delay").description("Tick delay.").defaultValue(1).range(1, 20).sliderRange(1, 10).build());
        this.b = this.rj.add(new IntSetting.Builder().name("Limit").description("Maximum render limit.").defaultValue(20).range(0, 128).sliderRange(0, 48).build());

        this.af = new HashSet<>();
        this.cv = new Color(0, 0, 0, 0);
        this.hj = new Color(0, 0, 0, 0);
        this.xw = new Color(0, 0, 0, 0);
        this.ce = new Color(255, 255, 255, 255);
    }

    public void onActivate() {
        this.af.clear();
        this.jk = 0;
    }

    public void onDeactivate() {
        SpawnerNotifierData.r(List.of());
    }

    @EventHandler
    private void onTick(TickEvent.Post post) {
        if (!this.isActive() || !Utils.canUpdate() || this.mc.world == null || this.mc.player == null) {
            return;
        }
        if (this.mc.player.age < 40) {
            return;
        }
        if (this.jk++ % (Integer)this.ay.get() != 0) {
            return;
        }
        int n = (Integer)this.n.get();
        int n2 = n * n;
        BlockPos class_23382 = this.mc.player.getBlockPos();
        ArrayList<SpawnerNotifierData$TrackedSpawner> arrayList = new ArrayList<SpawnerNotifierData$TrackedSpawner>();
        this.qh(this.mc.world, class_23382, n, n2, arrayList);
        arrayList.sort(Comparator.comparingDouble(SpawnerNotifierData$TrackedSpawner::iv));
        SpawnerNotifierData.r(arrayList);
    }

    private void qh(net.minecraft.world.World class_19372, BlockPos class_23382, int n, int n2, List<SpawnerNotifierData$TrackedSpawner> list) {
        int n3 = class_23382.getX();
        int n4 = class_23382.getZ();
        int n5 = n3 - n >> 4;
        int n6 = n3 + n >> 4;
        int n7 = n4 - n >> 4;
        int n8 = n4 + n >> 4;
        for (int i = n5; i <= n6; ++i) {
            for (int k = n7; k <= n8; ++k) {
                if (!class_19372.isChunkLoaded(i, k)) continue;
                WorldChunk class_28182 = class_19372.getChunk(i, k);
                for (BlockEntity class_25862 : class_28182.getBlockEntities().values()) {
                    String string;
                    EntityType class_12992;
                    if (!(class_25862 instanceof MobSpawnerBlockEntity)) continue;
                    MobSpawnerBlockEntity class_26362 = (MobSpawnerBlockEntity)class_25862;
                    BlockPos class_23383 = class_26362.getPos();
                    double d2 = PlayerUtils.squaredDistanceTo((double) class_23383.getX() + 0.5, (double) class_23383.getY() + 0.5, (double) class_23383.getZ() + 0.5);
                    if (d2 > (double) n2) continue;
                    class_12992 = null;
                    string = "Spawner";
                    String string2 = string;
                    list.add(new SpawnerNotifierData$TrackedSpawner(class_23383, string2, Math.sqrt(d2), class_12992));
                    long l = class_23383.asLong();
                    if (this.af.contains(l)) continue;
                    this.af.add(l);
                    Object[] objectArray = new Object[3];
                    objectArray[2] = class_12992;
                    objectArray[1] = string2;
                    objectArray[0] = class_23383;
                    this.pbz(objectArray);
                }
            }
        }
    }

    private void pbz(Object[] objectArray) {
        EntityType class_12992 = (EntityType)objectArray[2];
        String string = (String)objectArray[1];
        BlockPos class_23382 = (BlockPos)objectArray[0];
        String string2 = Blocks.SPAWNER.getName().getString();
        
        String string5 = "Found Spawner: X: " + class_23382.getX() + " Y: " + class_23382.getY() + " Z: " + class_23382.getZ();
        if (((Boolean)this.jf.get()).booleanValue()) {
            this.info(Text.literal(string5));
        }
        if (((Boolean)this.bd.get()).booleanValue()) {
            Object[] objectArray2 = new Object[4];
            objectArray2[3] = class_12992;
            objectArray2[2] = string2;
            objectArray2[1] = string;
            objectArray2[0] = class_23382;
            this.lee(objectArray2);
        }
        if (((Boolean)this.q.get()).booleanValue()) {
            ClientPlayNetworkHandler handler = this.mc.getNetworkHandler();
            if (handler != null) {
                handler.sendChatMessage(string5);
            }
        }
    }

    private static ItemStack wtt(Object[] objectArray) {
        EntityType class_12992 = (EntityType)objectArray[0];
        if (class_12992 == null) {
            return new ItemStack(Items.SPAWNER);
        }
        if (class_12992 == EntityType.ZOMBIE) {
            return new ItemStack(Items.ZOMBIE_HEAD);
        }
        if (class_12992 == EntityType.SKELETON || class_12992 == EntityType.STRAY || class_12992 == EntityType.WITHER_SKELETON || class_12992 == EntityType.BOGGED) {
            return new ItemStack(Items.SKELETON_SKULL);
        }
        if (class_12992 == EntityType.CREEPER) {
            return new ItemStack(Items.CREEPER_HEAD);
        }
        if (class_12992 == EntityType.PIGLIN || class_12992 == EntityType.ZOMBIFIED_PIGLIN || class_12992 == EntityType.PIGLIN_BRUTE) {
            return new ItemStack(Items.SPAWNER);
        }
        if (class_12992 == EntityType.ENDERMAN) {
            return new ItemStack(Items.ENDER_PEARL);
        }
        if (class_12992 == EntityType.SPIDER) {
            return new ItemStack(Items.SPIDER_EYE);
        }
        if (class_12992 == EntityType.BLAZE || class_12992 == EntityType.MAGMA_CUBE || class_12992 == EntityType.GHAST) {
            return new ItemStack(Items.BLAZE_ROD);
        }
        return new ItemStack(Items.SPAWNER);
    }

    private void lee(Object[] objectArray) {
        EntityType class_12992 = (EntityType)objectArray[3];
        String string = (String)objectArray[2];
        String string2 = (String)objectArray[1];
        BlockPos class_23382 = (BlockPos)objectArray[0];
        if (this.mc.getToastManager() == null) {
            return;
        }
        String string4 = "Spawner";
        String string5 = class_23382.getX() + " " + class_23382.getY() + " " + class_23382.getZ();
        Object[] objectArray2 = new Object[1];
        objectArray2[0] = class_12992;
        this.mc.getToastManager().add(new MeteorToast.Builder(string4).icon(SpawnerNotifier.wtt(objectArray2).getItem()).text(string5).build());
    }

    @EventHandler
    private void onRender3D(Render3DEvent render3DEvent) {
        if (!this.isActive() || !Utils.canUpdate() || this.mc.world == null) {
            return;
        }
        if (!mine.mod.q.iq()) {
            return;
        }
        this.hj.set((Color)this.ws.get());
        this.cv.set((Color)this.ws.get());
        this.cv.a = Math.min(((SettingColor)this.ws.get()).a, 120);
        ShapeMode shapeMode = (Boolean)this.fl.get() != false ? ShapeMode.Both : ShapeMode.Lines;
        int n = (Integer)this.b.get();
        int n2 = 0;
        for (SpawnerNotifierData$TrackedSpawner spawnerNotifierData$TrackedSpawner : SpawnerNotifierData.kw()) {
            BlockPos class_23382 = spawnerNotifierData$TrackedSpawner.jn();
            if (((Boolean)this.o.get()).booleanValue()) {
                render3DEvent.renderer.box(class_23382, this.cv, this.hj, shapeMode, 0);
            }
            if (!((Boolean)this.zp.get()).booleanValue() || n != 0 && n2 >= n) continue;
            render3DEvent.renderer.line(RenderUtils.center.x, RenderUtils.center.y, RenderUtils.center.z, (double)class_23382.getX() + 0.5, (double)class_23382.getY() + 0.5, (double)class_23382.getZ() + 0.5, this.hj);
            ++n2;
        }
    }

    public String getInfoString() {
        return Integer.toString(SpawnerNotifierData.kw().size());
    }
}