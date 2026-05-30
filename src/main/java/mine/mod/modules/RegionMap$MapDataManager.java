package mine.mod.modules;

import java.util.HashMap;
import java.util.Map;
import meteordevelopment.meteorclient.utils.render.color.Color;
import mine.mod.modules.RegionMap$RegionInfo;

class RegionMap$MapDataManager {
    private static final int f = 9;
    private static final double j = 50000.0;
    private static final double w = 225000.0;
    private final Map h = new HashMap();
    private final String[] u;
    private final Color[] x;

    public RegionMap$MapDataManager() {
        String[] stringArray = new String[6];
        int n = (-194888785 >> 1525136548 | 0) & 0x188045;
        StringBuilder stringBuilder = new StringBuilder("\u036a\u032a\u01fe\u0372\u02ea\u04c6\u04ae\u04b6\u02fa\u04ce");
        while (n < ((1422987292 >>> 1422987292 | 0xA) & 0x23CBF85A)) {
            int n2 = stringBuilder.charAt(n) + 169 + 121;
            stringBuilder.setCharAt(n, (char)((((n2 & 0xFFFC) >> 2 | n2 << 14) ^ 0x3F) - 215));
            stringBuilder = stringBuilder;
            n = n + (-1592181380 - 2027481754 ^ 0x284052E3);
        }
        stringArray[0] = stringBuilder.toString();
        int n3 = (0x660BAEEA & 0xADCEC90F | 0) & 0x4A800635;
        StringBuilder stringBuilder2 = new StringBuilder("\u7f30\u3f34\u3f27\ubf34\u3f38\ubf3b\uff3c");
        while (n3 < ((-216873503 >> -1263647999 | 7) & 0x2248107)) {
            int n4 = stringBuilder2.charAt(n3) + 206;
            int n5 = (n4 & 0xFFF8) >> 3 | n4 << 13;
            stringBuilder2.setCharAt(n3, (char)(((n5 & 0xF800) >> 11 | n5 << 5) - 111 + 187));
            stringBuilder2 = stringBuilder2;
            n3 = n3 + ((-38389223 - (0x4C1C3D0 | 0x59F6A227) | 1) & 0x6000898D);
        }
        stringArray[1] = stringBuilder2.toString();
        int n6 = (763777786 - -1339172226 | 0) & 0x870680;
        StringBuilder stringBuilder3 = new StringBuilder("\u33dd\u29dd\ue7dd\u21dd\u69dd\u4ddd\u3fdd");
        while (n6 < (0x6F202B1A & 0xB37E43A5 ^ 0x23200307)) {
            int n7 = stringBuilder3.charAt(n6) + 38;
            int n8 = ((n7 & 0xFC00) >> 10 | n7 << 6) ^ 0x10 ^ 0xFA;
            stringBuilder3.setCharAt(n6, (char)((n8 & 0x8000) >> 15 | n8 << 1));
            stringBuilder3 = stringBuilder3;
            n6 = n6 + (-1581153168 + -1459019429 ^ 0x4ACAA5CA);
        }
        stringArray[2] = stringBuilder3.toString();
        int n9 = (0x843D8868 & 1204546034 >> 5820805 | 0) & 0x6CC03431;
        StringBuilder stringBuilder4 = new StringBuilder("\u0e60\u0df8\u0cf0\u0ea8\u0f18\u0f88\u0f90");
        while (n9 < (-1820937511 + -1137569971 ^ 0x4FA8C221)) {
            char c = stringBuilder4.charAt(n9);
            stringBuilder4.setCharAt(n9, (char)(((c & 0xFFF8) >> 3 | c << 13) - 97 - 125 - 216 + 56));
            stringBuilder4 = stringBuilder4;
            n9 = n9 + (0xC0335563 & 0xC0335563 ^ 0xC0335562);
        }
        stringArray[3] = stringBuilder4.toString();
        int n10 = (-225739183 * -225739183 | 0) & 0x10902248;
        StringBuilder stringBuilder5 = new StringBuilder("\u0971\u08c1\u0831\u0871");
        while (n10 < ((1022004135 >> (-1170623386 >>> (0x3CEA8BA7 | 0xBA39B466)) | 4) & 0xACC10414)) {
            int n11 = stringBuilder5.charAt(n10) ^ 0xF9;
            stringBuilder5.setCharAt(n10, (char)((((n11 & 0xFFF8) >> 3 | n11 << 13) ^ 0x33) - 126 - 67));
            stringBuilder5 = stringBuilder5;
            n10 = n10 + (0xB6520842 ^ 0x432D3E21 ^ 0xF57F3662);
        }
        stringArray[4] = stringBuilder5.toString();
        int n12 = 1579091931 - 1325230983 ^ 0xF219C54;
        StringBuilder stringBuilder6 = new StringBuilder("\ue8ff\ufaff\ufdff\ufbff\uf87f\uf7ff\ufbff");
        while (n12 < ((0xE654B918 & 0x500AE5EF | 7) & 0x848D44E7)) {
            char c = stringBuilder6.charAt(n12);
            stringBuilder6.setCharAt(n12, (char)(((c & 0xFF80) >> 7 | c << 9) + 201 - 133 ^ 0xBF ^ 0xE5));
            stringBuilder6 = stringBuilder6;
            n12 = n12 + (-983116095 - 1993840966 ^ 0x4E8F3D7A);
        }
        stringArray[5] = stringBuilder6.toString();
        this.u = stringArray;
        this.x = new Color[]{new Color(159, 206, 99, 255), new Color(0, 166, 99, 255), new Color(79, 173, 234, 255), new Color(47, 110, 186, 255), new Color(245, 194, 66, 255), new Color(252, 136, 3, 255)};
        this.yyl(new Object[0]);
    }

    private void yyl(Object[] ignored) {
        int[][] regions = new int[][]{{82, 5}, {100, 3}, {101, 3}, {102, 3}, {103, 2}, {104, 2}, {105, 2}, {106, 2}, {91, 2}, {83, 5}, {44, 3}, {75, 3}, {42, 3}, {41, 2}, {40, 2}, {39, 2}, {38, 2}, {92, 2}, {84, 5}, {45, 3}, {14, 3}, {13, 3}, {12, 2}, {11, 2}, {10, 2}, {37, 2}, {93, 2}, {85, 5}, {46, 5}, {74, 5}, {3, 3}, {2, 2}, {1, 2}, {25, 2}, {36, 2}, {94, 2}, {86, 4}, {47, 4}, {72, 4}, {71, 4}, {5, 2}, {4, 2}, {24, 2}, {35, 2}, {95, 2}, {87, 4}, {51, 1}, {17, 1}, {9, 0}, {8, 0}, {7, 0}, {23, 0}, {34, 0}, {96, 2}, {88, 4}, {54, 1}, {18, 1}, {61, 0}, {62, 0}, {21, 0}, {22, 0}, {33, 0}, {97, 0}, {89, 0}, {26, 1}, {27, 0}, {28, 0}, {29, 0}, {30, 0}, {59, 0}, {32, 0}, {98, 0}, {90, 0}, {107, 1}, {108, 1}, {109, 1}, {110, 1}, {111, 1}, {112, 1}, {113, 1}, {99, 0}};
        for (int i = 0; i < regions.length; ++i) {
            int n = i / 9;
            int n2 = i % 9;
            if (regions[i].length < 2) continue;
            int regionId = regions[i][0];
            int n3 = Math.min(regions[i][1], this.u.length - 1);
            this.h.put(i, new RegionMap$RegionInfo(regionId, n3, n, n2));
        }
    }

    public RegionMap$RegionInfo fiq(Object[] objectArray) {
        int n = (Integer)objectArray[0];
        return (RegionMap$RegionInfo)this.h.get(n);
    }

    public int mhb(Object[] objectArray) {
        double d = (Double)objectArray[1];
        double d2 = (Double)objectArray[0];
        try {
            Object[] objectArray2 = new Object[2];
            objectArray2[1] = d;
            objectArray2[0] = d2;
            int[] nArray = this.nlv(objectArray2);
            Object[] objectArray3 = new Object[2];
            objectArray3[1] = nArray[1];
            objectArray3[0] = nArray[0];
            if (this.ncp(objectArray3)) {
                int n = nArray[1] * 9 + nArray[0];
                RegionMap$RegionInfo regionMap$RegionInfo = (RegionMap$RegionInfo)this.h.get(n);
                return regionMap$RegionInfo != null ? regionMap$RegionInfo.wu : -1;
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return -1;
    }

    public String vne(Object[] objectArray) {
        double d = (Double)objectArray[1];
        double d2 = (Double)objectArray[0];
        try {
            int n;
            RegionMap$RegionInfo regionMap$RegionInfo;
            Object[] objectArray2 = new Object[2];
            objectArray2[1] = d;
            objectArray2[0] = d2;
            int[] nArray = this.nlv(objectArray2);
            Object[] objectArray3 = new Object[2];
            objectArray3[1] = nArray[1];
            objectArray3[0] = nArray[0];
            if (this.ncp(objectArray3) && (regionMap$RegionInfo = (RegionMap$RegionInfo)this.h.get(n = nArray[1] * 9 + nArray[0])) != null && regionMap$RegionInfo.pl >= 0 && regionMap$RegionInfo.pl < this.u.length) {
                return this.u[regionMap$RegionInfo.pl];
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        int n = (-2005220647 >>> (-2005220647 << -38578238) | 0) & 0xF1384200;
        StringBuilder stringBuilder = new StringBuilder("\u9010\ue010\ub010\ue010\uf010\u7012\ue010");
        while (n < ((1223677235 * -236793124 | 7) & 0x5AF2420F)) {
            char c = stringBuilder.charAt(n);
            int n2 = ((c & 0xF000) >> 12 | c << 4) - 37 - 5 ^ 0x8A;
            stringBuilder.setCharAt(n, (char)((n2 & 0xFFFF) >> 0 | n2 << 16));
            stringBuilder = stringBuilder;
            n = n + (0xB00CAC26 ^ 0x7916F43E ^ 0xC91A5819);
        }
        return stringBuilder.toString();
    }

    public Color ydg(Object[] objectArray) {
        int n = (Integer)objectArray[0];
        if (n >= 0 && n < this.x.length) {
            return this.x[n];
        }
        return Color.WHITE;
    }

    public String[] klg(Object[] objectArray) {
        return (String[])this.u.clone();
    }

    public Color[] bxf(Object[] objectArray) {
        return (Color[])this.x.clone();
    }

    public int[] nlv(Object[] objectArray) {
        double d = (Double)objectArray[1];
        double d2 = (Double)objectArray[0];
        int n = (int)((d2 + 225000.0) / 50000.0);
        int n2 = (int)((d + 225000.0) / 50000.0);
        return new int[]{n, n2};
    }

    public double[] ahz(Object[] objectArray) {
        double d = (Double)objectArray[1];
        double d2 = (Double)objectArray[0];
        double d3 = (d2 + 225000.0) % 50000.0 / 50000.0;
        double d4 = (d + 225000.0) % 50000.0 / 50000.0;
        d3 = Math.max(0.0, Math.min(1.0, d3));
        d4 = Math.max(0.0, Math.min(1.0, d4));
        return new double[]{d3, d4};
    }

    private boolean ncp(Object[] objectArray) {
        int n = (Integer)objectArray[1];
        int n2 = (Integer)objectArray[0];
        return n2 >= 0 && n2 < 9 && n >= 0 && n < 9;
    }

    public int roh(Object[] objectArray) {
        return 9;
    }
}
