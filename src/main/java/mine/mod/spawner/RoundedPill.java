package mine.mod.spawner;

import meteordevelopment.meteorclient.renderer.Renderer2D;
import meteordevelopment.meteorclient.utils.render.color.Color;

public final class RoundedPill {
    private RoundedPill() {
    }

    public static void ewr(Object[] objectArray) {
        double d;
        double d2;
        double d3;
        double d4;
        double d5;
        double d6;
        int n;
        Color color = (Color)objectArray[5];
        double d7 = (Double)objectArray[4];
        double d8 = (Double)objectArray[3];
        double d9 = (Double)objectArray[2];
        double d10 = (Double)objectArray[1];
        Renderer2D renderer2D = (Renderer2D)objectArray[0];
        double d11 = d7 / 2.0;
        if (d8 <= d11 * 2.0) {
            renderer2D.quad(d10, d9, d8, d7, color);
            return;
        }
        double d12 = d10 + d11;
        double d13 = d10 + d8 - d11;
        double d14 = d9 + d11;
        int n2 = 8;
        double d15 = 1.5707963267948966;
        renderer2D.quad(d12, d9, d13 - d12, d7, color);
        for (n = 0; n < n2; ++n) {
            d6 = d15 + (double)n * (Math.PI / (double)n2);
            d5 = d15 + (double)(n + 1) * (Math.PI / (double)n2);
            d4 = d12 + Math.cos(d6) * d11;
            d3 = d14 - Math.sin(d6) * d11;
            d2 = d12 + Math.cos(d5) * d11;
            d = d14 - Math.sin(d5) * d11;
            renderer2D.triangle(d12, d14, d4, d3, d2, d, color);
        }
        for (n = 0; n < n2; ++n) {
            d6 = -d15 + (double)n * (Math.PI / (double)n2);
            d5 = -d15 + (double)(n + 1) * (Math.PI / (double)n2);
            d4 = d13 + Math.cos(d6) * d11;
            d3 = d14 - Math.sin(d6) * d11;
            d2 = d13 + Math.cos(d5) * d11;
            d = d14 - Math.sin(d5) * d11;
            renderer2D.triangle(d13, d14, d4, d3, d2, d, color);
        }
    }
}
