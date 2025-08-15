package website.kuro.mod;

import net.minecraft.client.Minecraft;

public class Mod {

    private final String name, description;
    private boolean enabled, movable;

    //position
    public final int defaultX, defaultY;
    public int x, y, w, h;

    public Mod(String name, String description, int x, int y, int w, int h, boolean movable) {
        this.name = name;
        this.description = description;
        this.defaultX = x;
        this.defaultY = y;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.movable = movable;

        this.enabled = true;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void resetPosition() {
        this.x = defaultX;
        this.y = defaultY;
    }

    public void setEnabled(boolean enabled) {

        this.enabled = enabled;

        if (enabled) {
            onEnable();
        } else {
            onDisable();
        }
    }

    public void toggle() {
        this.setEnabled(!enabled);
    }

    public void onEnable() {
    }

    public void onDisable() {
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isMovable() {
        return movable;
    }

    public void setMovable(boolean movable) {
        this.movable = movable;
    }

    public void draw() {}

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getW() {
        return this.w;
    }

    public int getH() {
        return this.h;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void drawCenteredString(String text, int x, int y, int color)
    {
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(text, (float)(x - Minecraft.getMinecraft().fontRendererObj.getStringWidth(text) / 2), (float)y, color);
    }

}