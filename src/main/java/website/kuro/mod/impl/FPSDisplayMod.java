package website.kuro.mod.impl;

import net.minecraft.client.Minecraft;
import website.kuro.mod.Mod;
import website.kuro.util.render.RoundedUtils;

import java.awt.*;

public class FPSDisplayMod extends Mod {

    public FPSDisplayMod() {
        super("FPS Display", "Show frames per second on the HUD", 10, 10, 58, 14, true);
    }

    @Override
    public void draw() {
        RoundedUtils.drawRound(getX(), getY(), getW(), getH(), 4, new Color(14, 14, 14));
        drawCenteredString(Minecraft.getDebugFPS() + " FPS", getX() + 29, getY() + 3, -1);
    }
}
