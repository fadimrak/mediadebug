package mine.mod.modules;

import meteordevelopment.meteorclient.renderer.Renderer2D;
import meteordevelopment.meteorclient.utils.render.color.Color;
import meteordevelopment.meteorclient.utils.render.color.SettingColor;
import mine.mod.modules.RegionMap$MapRenderContext;

class RegionMap$RegionRenderer {
    final /* synthetic */ RegionMap t;

    RegionMap$RegionRenderer(RegionMap regionMap) {
        this.t = regionMap;
    }

    void z(RegionMap$MapRenderContext regionMap$MapRenderContext) {
        if (regionMap$MapRenderContext == null || this.t.bog.get() == null) {
            return;
        }
        try {
            Color color = new Color((Color) this.t.bog.get());
            color.a = (int) (regionMap$MapRenderContext.py * 255.0);
            Renderer2D.COLOR.begin();
            Renderer2D.COLOR.quad((double) regionMap$MapRenderContext.g, (double) regionMap$MapRenderContext.v, (double) regionMap$MapRenderContext.xzl(new Object[0]), (double) regionMap$MapRenderContext.rth(new Object[0]), color);
            Renderer2D.COLOR.render();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    void fb(RegionMap$MapRenderContext ctx, RegionMap$MapDataManager data) {
        if (ctx == null || data == null) return;
        try {
            int cell = ctx.k;
            Renderer2D.COLOR.begin();
            for (int i = 0; i < 81; i++) {
                RegionMap$RegionInfo info = data.fiq(new Object[]{i});
                if (info == null) continue;
                Color c = data.ydg(new Object[]{info.pl});
                c.a = (int) (ctx.py * 255.0);
                int col = i % 9;
                int row = i / 9;
                int x = ctx.g + col * cell;
                int y = ctx.v + row * cell;
                Renderer2D.COLOR.quad(x, y, x + cell, y + cell, c);
            }
            Renderer2D.COLOR.render();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    void l(RegionMap$MapRenderContext ctx, SettingColor settingColor) {
        if (ctx == null || settingColor == null) return;
        try {
            Color color = new Color(settingColor);
            color.a = (int) (ctx.py * 255.0);
            Renderer2D.COLOR.begin();
            int w = ctx.xzl(new Object[0]) - ctx.g;
            int h = ctx.rth(new Object[0]) - ctx.v;
            Renderer2D.COLOR.quad(ctx.g, ctx.v, ctx.g + w, ctx.v, color);
            Renderer2D.COLOR.quad(ctx.g, ctx.v + h, ctx.g + w, ctx.v + h, color);
            Renderer2D.COLOR.quad(ctx.g, ctx.v, ctx.g, ctx.v + h, color);
            Renderer2D.COLOR.quad(ctx.g + w, ctx.v, ctx.g + w, ctx.v + h, color);
            Renderer2D.COLOR.render();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    void b(RegionMap$MapRenderContext ctx, RegionMap$MapDataManager data, Double scale) {
        if (ctx == null || data == null) return;
        this.fb(ctx, data);
    }
}
