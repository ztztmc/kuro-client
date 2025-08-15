package website.kuro.mod.impl;

import net.minecraft.client.Minecraft;
import website.kuro.mod.Mod;
import website.kuro.util.render.RoundedUtils;

import java.awt.*;

public class PingDisplayMod extends Mod {

    public PingDisplayMod() {
        super("Ping Display", "Show connection latency on the HUD", 10, 30, 58, 14, true);
    }

    @Override
    public void draw() {
        RoundedUtils.drawRound(getX(), getY(), getW(), getH(), 4, new Color(14, 14, 14));
        if (Minecraft.getMinecraft().getCurrentServerData() != null && Minecraft.getMinecraft().getNetHandler() != null && Minecraft.getMinecraft().getNetHandler().getPlayerInfo(Minecraft.getMinecraft().thePlayer.getUniqueID()) != null) {
            drawCenteredString(Minecraft.getMinecraft().getCurrentServerData().pingToServer + " ms", getX() + 29, getY() + 3, -1);
            //drawCenteredString(Minecraft.getMinecraft().getNetHandler().getPlayerInfo(Minecraft.getMinecraft().thePlayer.getUniqueID()).getResponseTime() + " ms", getX() + 29, getY() + 13, -1);
        } else {
            drawCenteredString("? ms", getX() + 29, getY() + 3, -1);
        }
    }
}
