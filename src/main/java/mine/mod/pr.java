package mine.mod;

import net.minecraft.entity.player.PlayerEntity;

public final class pr {
    private pr() {}

    public static String ri(PlayerEntity player, int maxLength) {
        if (player == null) return "";
        String name = player.getGameProfile().name();
        if (name.length() <= maxLength) return name;
        return name.substring(0, maxLength);
    }
}
