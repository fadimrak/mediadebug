package mine.mod.modules;

import meteordevelopment.meteorclient.events.render.Render2DEvent;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.ColorSetting;
import meteordevelopment.meteorclient.settings.DoubleSetting;
import meteordevelopment.meteorclient.settings.IntSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.render.color.SettingColor;
import meteordevelopment.orbit.EventHandler;
import mine.mod.AddonCategories;
import net.minecraft.util.math.Vec3d;

public class RegionMap extends Module {
    private final SettingGroup bh;
    private final SettingGroup it;
    private final SettingGroup rr;
    private final SettingGroup mo;
    
    private final Setting<Integer> m;
    private final Setting<Integer> le;
    private final Setting<Integer> bv;
    private final Setting<Boolean> nc;
    private final Setting<Boolean> nm;
    private final Setting<Boolean> gw;
    private final Setting<Boolean> jl;
    private final Setting<Double> qw;
    private final Setting<Double> va;
    
    final Setting<SettingColor> bog;
    private final Setting<SettingColor> rg;
    private final Setting<SettingColor> cy;
    
    final RegionMap$MapDataManager a;
    private final RegionMap$RegionRenderer xu;
    private final RegionMap$PlayerTracker jy;

    public RegionMap() {
        super(AddonCategories.MAIN, "RegionMap", "Displays a region map on screen.");
        
        this.bh = this.settings.createGroup("General");
        this.it = this.settings.createGroup("Position");
        this.rr = this.settings.createGroup("Scale");
        this.mo = this.settings.createGroup("Colors");

        this.m = this.it.add(new IntSetting.Builder().name("X").description("X position of the map.").defaultValue(15).min(0).sliderMax(1920).build());
        this.le = this.it.add(new IntSetting.Builder().name("Y").description("Y position of the map.").defaultValue(15).min(0).sliderMax(1080).build());
        this.bv = this.it.add(new IntSetting.Builder().name("Cell Size").description("Size of each cell.").defaultValue(22).range(12, 50).sliderRange(12, 50).build());
        
        this.nc = this.bh.add(new BoolSetting.Builder().name("Setting 1").description("Toggle setting 1.").defaultValue(true).build());
        this.nm = this.bh.add(new BoolSetting.Builder().name("Setting 2").description("Toggle setting 2.").defaultValue(true).build());
        this.gw = this.bh.add(new BoolSetting.Builder().name("Draw Outline").description("Draws an outline around the map.").defaultValue(true).build());
        this.jl = this.bh.add(new BoolSetting.Builder().name("Show Player").description("Shows the player on the map.").defaultValue(true).build());
        
        this.qw = this.rr.add(new DoubleSetting.Builder().name("Opacity").description("Opacity of the map.").defaultValue(0.75).range(0.1, 1.0).sliderRange(0.1, 1.0).build());
        this.va = this.rr.add(new DoubleSetting.Builder().name("Scale").description("Scale of the map.").defaultValue(0.9).range(0.4, 2.5).sliderRange(0.4, 2.5).build());
        
        this.bog = this.mo.add(new ColorSetting.Builder().name("Background").description("Background color.").defaultValue(new SettingColor(25, 25, 25, 180)).build());
        this.rg = this.mo.add(new ColorSetting.Builder().name("Player Color").description("Color of the player indicator.").defaultValue(new SettingColor(255, 50, 50, 255)).build());
        this.cy = this.mo.add(new ColorSetting.Builder().name("Outline Color").description("Color of the outline.").defaultValue(new SettingColor(15, 15, 15, 255)).build());
        
        this.a = new RegionMap$MapDataManager();
        this.xu = new RegionMap$RegionRenderer(this);
        this.jy = new RegionMap$PlayerTracker(this);
    }

    @EventHandler
    private void onRender2D(Render2DEvent render2DEvent) {
        if (!this.isActive()) {
            return;
        }
        try {
            Vec3d playerPos = new Vec3d(this.mc.player.getX(), this.mc.player.getY(), this.mc.player.getZ());
            RegionMap$MapRenderContext regionMap$MapRenderContext = new RegionMap$MapRenderContext(this, this.m.get(), this.le.get(), this.bv.get(), this.qw.get());
            this.xu.z(regionMap$MapRenderContext);
            this.xu.fb(regionMap$MapRenderContext, this.a);
            if (this.gw.get().booleanValue()) {
                this.xu.l(regionMap$MapRenderContext, this.cy.get());
            }
            this.xu.b(regionMap$MapRenderContext, this.a, this.va.get());
            if (this.jl.get().booleanValue()) {
                this.jy.zc(regionMap$MapRenderContext, playerPos, this.mc.player.getYaw(), this.rg.get());
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}