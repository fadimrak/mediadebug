package mine.mod.modules.finder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import meteordevelopment.meteorclient.events.render.Render3DEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.renderer.ShapeMode;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.ColorSetting;
import meteordevelopment.meteorclient.settings.DoubleSetting;
import meteordevelopment.meteorclient.settings.EnumSetting;
import meteordevelopment.meteorclient.settings.IntSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.render.MeteorToast;
import meteordevelopment.meteorclient.utils.render.RenderUtils;
import meteordevelopment.meteorclient.utils.render.color.Color;
import meteordevelopment.meteorclient.utils.render.color.SettingColor;
import meteordevelopment.orbit.EventHandler;
import mine.mod.modules.finder.ClusterLightDetector$ClusterData;
import net.minecraft.item.Items;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.LightType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.chunk.WorldChunk;
import net.minecraft.world.chunk.ChunkSection;
import mine.mod.AddonCategories;
import net.minecraft.client.toast.Toast;
import net.minecraft.client.toast.ToastManager;

public class ClusterLightDetector extends Module {
    private final SettingGroup l;
    private final SettingGroup d;
    private final SettingGroup mi;
    private final SettingGroup r;
    private final Setting kc;
    private final Setting qq;
    private final Setting mt;
    private final Setting vi;
    private final Setting sl;
    private final Setting ex;
    private final Setting ya;
    private final Setting pa;
    private final Setting zt;
    private final Setting dg;
    private final Setting ll;
    private final Setting yd;
    private final Setting io;
    private final Setting mjb;
    private final Setting fx;
    private final Setting fc;
    private final Queue<ChunkPos> yl;
    private final List<ClusterLightDetector$ClusterData> eb;
    private final List<ClusterLightDetector$ClusterData> qd;
    private final Set<ChunkPos> oc;
    private final ConcurrentHashMap<ChunkPos, Long> st;

    public ClusterLightDetector() {
        super(AddonCategories.MAIN, "ClusterLightDetector", "Detects clusters of block light.");
        this.l = this.settings.getDefaultGroup();
        this.d = this.settings.createGroup("Render");
        this.mi = this.settings.createGroup("Area");
        this.r = this.settings.createGroup("Notifications");

        this.kc = this.l.add(new IntSetting.Builder().name("Radius").description("Scanning radius.").defaultValue(64).min(16).sliderMax(128).build());
        this.qq = this.l.add(new IntSetting.Builder().name("Chunks/Tick").description("Chunks to scan per tick.").defaultValue(5).min(1).sliderMax(20).build());
        this.mt = this.l.add(new IntSetting.Builder().name("Min Size").description("Minimum light cluster size.").defaultValue(5).min(1).sliderMax(20).build());
        
        this.vi = this.d.add(new BoolSetting.Builder().name("Draw Box").description("Draws bounding box.").defaultValue(true).build());
        this.sl = this.d.add(new ColorSetting.Builder().name("Box Color").description("Box color.").defaultValue(new SettingColor(204, 102, 255, 230)).visible(() -> (Boolean)this.vi.get()).build());
        this.ex = this.d.add(new BoolSetting.Builder().name("Outline").description("Outline render.").defaultValue(true).build());
        this.ya = this.d.add(new ColorSetting.Builder().name("Outline Color").description("Outline color.").defaultValue(new SettingColor(255, 242, 153, 38)).visible(() -> (Boolean)this.ex.get()).build());
        this.pa = this.d.add(new BoolSetting.Builder().name("Tracer").description("Tracer line.").defaultValue(true).build());
        this.zt = this.d.add(new ColorSetting.Builder().name("Tracer Color").description("Tracer color.").defaultValue(new SettingColor(242, 153, 255, 242)).visible(() -> (Boolean)this.pa.get()).build());
        
        this.dg = this.mi.add(new BoolSetting.Builder().name("Show Area").description("Shows scanning area.").defaultValue(true).build());
        this.ll = this.mi.add(new DoubleSetting.Builder().name("Y Level").description("Area render height level.").defaultValue(64.0).range(-64.0, 320.0).sliderRange(-64.0, 320.0).build());
        this.yd = this.mi.add(new EnumSetting.Builder<ShapeMode>().name("Shape Mode").description("Shape of the area.").defaultValue(ShapeMode.Both).visible(() -> (Boolean)this.dg.get()).build());
        this.io = this.mi.add(new ColorSetting.Builder().name("Area Color").description("Color of the area.").defaultValue(new SettingColor(204, 102, 255, 80)).visible(() -> (Boolean)this.dg.get()).build());
        this.mjb = this.mi.add(new DoubleSetting.Builder().name("Area Height").description("Render height of the area.").defaultValue(0.3).range(0.1, 2.0).sliderRange(0.1, 2.0).visible(() -> (Boolean)this.dg.get()).build());
        
        this.fx = this.r.add(new BoolSetting.Builder().name("Toast").description("Shows a toast notification.").defaultValue(true).build());
        this.fc = this.r.add(new IntSetting.Builder().name("Cooldown").description("Notification cooldown (seconds).").defaultValue(45).min(5).sliderMax(120).build());

        this.yl = new ConcurrentLinkedQueue<>();
        this.eb = new ArrayList<>();
        this.qd = new CopyOnWriteArrayList<>();
        this.oc = ConcurrentHashMap.newKeySet();
        this.st = new ConcurrentHashMap<>();
    }

    public void onActivate() {
        this.yl.clear();
        this.eb.clear();
        this.qd.clear();
        this.oc.clear();
        this.st.clear();
    }

    public void onDeactivate() {
        this.yl.clear();
        this.eb.clear();
        this.qd.clear();
        this.oc.clear();
        this.st.clear();
    }

    @EventHandler
    private void onTick(TickEvent.Pre pre) {
        ChunkPos class_19232;
        int n;
        if (this.mc.world == null || this.mc.player == null) {
            this.yl.clear();
            this.eb.clear();
            this.qd.clear();
            return;
        }
        if (this.yl.isEmpty()) {
            if (!this.eb.isEmpty()) {
                this.qd.clear();
                this.qd.addAll(this.eb);
                this.eb.clear();
                this.qi();
            }
            BlockPos class_23382 = this.mc.player.getBlockPos();
            n = class_23382.getX();
            int n2 = class_23382.getZ();
            int n3 = (Integer)this.kc.get();
            int n4 = n - n3 >> 4;
            int n5 = n + n3 >> 4;
            int n6 = n2 - n3 >> 4;
            int n7 = n2 + n3 >> 4;
            for (int i = n4; i <= n5; ++i) {
                for (int k = n6; k <= n7; ++k) {
                    this.yl.add(new ChunkPos(i, k));
                }
            }
        }
        int n8 = (Integer)this.qq.get();
        for (n = 0; n < n8 && (class_19232 = (ChunkPos)this.yl.poll()) != null; ++n) {
            if (!this.mc.world.isChunkLoaded(class_19232.x, class_19232.z)) continue;
            WorldChunk class_28182 = this.mc.world.getChunk(class_19232.x, class_19232.z);
            this.se(class_28182);
        }
    }

    private void se(WorldChunk class_28182) {
        int n = class_28182.getPos().getStartX();
        int n2 = class_28182.getPos().getStartZ();
        ChunkSection[] class_2826Array = class_28182.getSectionArray();
        int n3 = class_28182.getBottomY();
        BlockPos.Mutable class_23392 = new BlockPos.Mutable();
        for (int i = 0; i < class_2826Array.length; ++i) {
            int n4;
            ChunkSection class_28262 = class_2826Array[i];
            if (class_28262 == null || class_28262.isEmpty() || (n4 = n3 + i * 16) > 32 || n4 + 15 < -64) continue;
            for (int k = 0; k < 16; ++k) {
                int n5 = n4 + k;
                if (n5 < -64 || n5 > 32) continue;
                for (int i2 = 0; i2 < 16; ++i2) {
                    for (int i3 = 0; i3 < 16; ++i3) {
                        int n6 = n + i2;
                        int n7 = n2 + i3;
                        class_23392.set(n6, n5, n7);
                        int n8 = this.mc.world.getLightLevel(LightType.BLOCK, (BlockPos)class_23392);
                        if (n8 != 5) continue;
                        Object[] objectArray = new Object[3];
                        objectArray[2] = n7;
                        objectArray[1] = n5;
                        objectArray[0] = n6;
                        if (!this.cxh(objectArray)) continue;
                        BlockPos class_23382 = class_23392.toImmutable();
                        this.eb.add(new ClusterLightDetector$ClusterData(class_23382, 5, "Light"));
                    }
                }
            }
        }
    }

    private boolean cxh(Object[] objectArray) {
        int n = (Integer)objectArray[2];
        int n2 = (Integer)objectArray[1];
        int n3 = (Integer)objectArray[0];
        if (this.mc.world == null) {
            return false;
        }
        int[][] nArrayArray = new int[][]{{n3, n2 + 1, n}, {n3, n2 - 1, n}, {n3 + 1, n2, n}, {n3 - 1, n2, n}, {n3, n2, n + 1}, {n3, n2, n - 1}};
        BlockPos.Mutable class_23392 = new BlockPos.Mutable();
        for (int[] nArray : nArrayArray) {
            if (!this.mc.world.isChunkLoaded(nArray[0] >> 4, nArray[2] >> 4)) continue;
            class_23392.set(nArray[0], nArray[1], nArray[2]);
            int n4 = this.mc.world.getLightLevel(LightType.BLOCK, (BlockPos)class_23392);
            if (n4 <= 5) continue;
            return false;
        }
        return true;
    }

    private void qi() {
        if (this.mc.player == null) {
            return;
        }
        HashMap<ChunkPos, Integer> hashMap = new HashMap<>();
        for (Object object : this.qd) {
            BlockPos dataPos = ((ClusterLightDetector$ClusterData) object).qj();
            ChunkPos object2 = new ChunkPos(dataPos);
            hashMap.merge(object2, 1, Integer::sum);
        }
        HashSet<ChunkPos> hashSet = new HashSet<>();
        for (Map.Entry<ChunkPos, Integer> entry : hashMap.entrySet()) {
            if (entry.getValue() < (Integer)this.mt.get()) continue;
            ChunkPos class_19232 = entry.getKey();
            hashSet.add(class_19232);
            if (!this.oc.add(class_19232)) continue;
            Object[] objectArray = new Object[2];
            objectArray[1] = entry.getValue();
            objectArray[0] = class_19232;
            this.vjy(objectArray);
        }
        this.oc.retainAll(hashSet);
    }

    private void vjy(Object[] objectArray) {
        int n = (Integer)objectArray[1];
        ChunkPos class_19232 = (ChunkPos)objectArray[0];
        if (this.mc.player == null) {
            return;
        }
        long l = System.currentTimeMillis();
        Long l2 = this.st.get(class_19232);
        if (l2 != null && l - l2 < (long)((Integer)this.fc.get()).intValue() * 1000L) {
            return;
        }
        this.st.put(class_19232, l);
        String string = String.format("X: %d Z: %d Amount: %d", class_19232.x, class_19232.z, n);
        this.mc.execute(() -> {
            if (((Boolean) this.fx.get()).booleanValue()) {
                ToastManager toastManager = this.mc.getToastManager();
                toastManager.add(new MeteorToast.Builder("Light Cluster").icon(Items.AMETHYST_SHARD).text(string).build());
            }
        });
    }

    @EventHandler
    private void onRender3D(Render3DEvent render3DEvent) {
        if (this.mc.player == null || this.mc.world == null || this.qd.isEmpty()) {
            return;
        }
        for (Object object : this.qd) {
            BlockPos class_23382 = ((ClusterLightDetector$ClusterData)object).qj();
            ChunkPos class_19232 = new ChunkPos(class_23382);
            boolean bl = this.oc.contains(class_19232);
            if (((Boolean)this.vi.get()).booleanValue()) {
                render3DEvent.renderer.box(class_23382, (Color)this.sl.get(), (Color)this.sl.get(), ShapeMode.Lines, 0);
            }
            if (((Boolean)this.ex.get()).booleanValue()) {
                int n = ((ClusterLightDetector$ClusterData)object).sk();
                Box bounds = new Box((double)(class_23382.getX() - n), (double)(class_23382.getY() - n), (double)(class_23382.getZ() - n), (double)(class_23382.getX() + 1 + n), (double)(class_23382.getY() + 1 + n), (double)(class_23382.getZ() + 1 + n));
                render3DEvent.renderer.box(bounds, (Color)this.ya.get(), (Color)this.ya.get(), ShapeMode.Sides, 0);
            }
            if (!((Boolean)this.pa.get()).booleanValue() || !bl) continue;
            render3DEvent.renderer.line(RenderUtils.center.x, RenderUtils.center.y, RenderUtils.center.z, (double)class_23382.getX() + 0.5, (double)class_23382.getY() + 0.5, (double)class_23382.getZ() + 0.5, (Color)this.zt.get());
        }
        if (((Boolean)this.dg.get()).booleanValue() && !this.oc.isEmpty()) {
            Color color = new Color((Color)this.io.get());
            for (ChunkPos chunkPos : this.oc) {
                this.kh(render3DEvent, chunkPos, color);
            }
        }
    }

    private void kh(Render3DEvent render3DEvent, ChunkPos class_19232, Color color) {
        int n = class_19232.getStartX();
        int n2 = class_19232.getStartZ();
        int n3 = class_19232.getEndX();
        int n4 = class_19232.getEndZ();
        double d = (Double)this.ll.get();
        double d2 = (Double)this.mjb.get();
        Box bounds = new Box((double)n, d, (double)n2, (double)(n3 + 1), d + d2, (double)(n4 + 1));
        render3DEvent.renderer.box(bounds, color, color, (ShapeMode)this.yd.get(), 0);
    }

    public String getInfoString() {
        return String.format("%d Areas, %d Clusters", this.oc.size(), this.qd.size());
    }
}