package mine.mod.modules;

import meteordevelopment.meteorclient.renderer.Renderer2D;
import meteordevelopment.meteorclient.utils.render.color.Color;
import meteordevelopment.meteorclient.utils.render.color.SettingColor;
import mine.mod.modules.RegionMap;
import mine.mod.modules.RegionMap$MapRenderContext;
import net.minecraft.util.math.Vec3d;

class RegionMap$PlayerTracker {
    final /* synthetic */ RegionMap t;

    RegionMap$PlayerTracker(RegionMap regionMap) {
        this.t = regionMap;
    }

    void zc(RegionMap$MapRenderContext regionMap$MapRenderContext, Vec3d VanillaChestLootTableGenerator, float f, SettingColor settingColor) {
        if (regionMap$MapRenderContext == null || VanillaChestLootTableGenerator == null || settingColor == null) {
            return;
        }
        try {
            Object[] objectArray = new Object[2];
            objectArray[1] = VanillaChestLootTableGenerator.z;
            objectArray[0] = VanillaChestLootTableGenerator.x;
            int[] nArray = this.t.a.nlv(objectArray);
            if (nArray[0] >= 0 && nArray[0] < this.t.a.roh(new Object[0]) && nArray[1] >= 0 && nArray[1] < this.t.a.roh(new Object[0])) {
                Object[] objectArray2 = new Object[2];
                objectArray2[1] = VanillaChestLootTableGenerator.z;
                objectArray2[0] = VanillaChestLootTableGenerator.x;
                double[] dArray = this.t.a.ahz(objectArray2);
                int n = regionMap$MapRenderContext.g + nArray[0] * regionMap$MapRenderContext.k + (int)(dArray[0] * (double)regionMap$MapRenderContext.k);
                int n2 = regionMap$MapRenderContext.v + nArray[1] * regionMap$MapRenderContext.k + (int)(dArray[1] * (double)regionMap$MapRenderContext.k);
                double d = Math.toRadians((double)(-f) - 90.0);
                Object[] objectArray3 = new Object[4];
                objectArray3[3] = settingColor;
                objectArray3[2] = d;
                objectArray3[1] = n2;
                objectArray3[0] = n;
                this.bwu(objectArray3);
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void bwu(Object[] objectArray) {
        SettingColor settingColor = (SettingColor)objectArray[3];
        double d = (Double)objectArray[2];
        int n = (Integer)objectArray[1];
        int n2 = (Integer)objectArray[0];
        try {
            Renderer2D.COLOR.begin();
            Color color = new Color((Color)settingColor);
            int n3 = 9;
            int n4 = n2 + (int)(Math.cos(d) * (double)n3);
            int n5 = n - (int)(Math.sin(d) * (double)n3);
            double d2 = d + Math.toRadians(135.0);
            double d3 = d - Math.toRadians(135.0);
            int n6 = n2 + (int)(Math.cos(d2) * (double)n3);
            int n7 = n - (int)(Math.sin(d2) * (double)n3);
            int n8 = n2 + (int)(Math.cos(d3) * (double)n3);
            int n9 = n - (int)(Math.sin(d3) * (double)n3);
            Object[] objectArray2 = new Object[7];
            objectArray2[6] = color;
            objectArray2[5] = n9;
            objectArray2[4] = n8;
            objectArray2[3] = n7;
            objectArray2[2] = n6;
            objectArray2[1] = n5;
            objectArray2[0] = n4;
            Renderer2D.COLOR.triangle(n4, n5, n6, n7, n8, n9, color);
            Renderer2D.COLOR.render();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
