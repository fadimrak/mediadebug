package mine.mod.modules;

import mine.mod.modules.RegionMap;

class RegionMap$MapRenderContext {
    final int g;
    final int v;
    final int k;
    final double py;
    final /* synthetic */ RegionMap t;

    RegionMap$MapRenderContext(RegionMap regionMap, int n, int n2, int n3, double d) {
        this.t = regionMap;
        this.g = n;
        this.v = n2;
        this.k = Math.max(1, n3);
        this.py = Math.max(0.1, Math.min(1.0, d));
    }

    int xzl(Object[] objectArray) {
        return this.t.a.roh(new Object[0]) * this.k;
    }

    int rth(Object[] objectArray) {
        return this.t.a.roh(new Object[0]) * this.k;
    }
}
