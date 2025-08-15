package website.kuro.ui.modmenu;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import website.kuro.KuroClient;
import website.kuro.mod.Mod;
import website.kuro.util.MouseUtils;
import website.kuro.util.render.RoundedUtils;

import java.awt.*;
import java.util.List;

public class HUDPositioningScreen extends GuiScreen {
    private final Minecraft mc = Minecraft.getMinecraft();
    private final List<Mod> mods = KuroClient.modManager.getMods();
    private Mod draggingMod = null;
    private int dragOffsetX, dragOffsetY;

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        for (Mod mod : mods) {
            if (!mod.isEnabled() || !mod.isMovable()) continue;

            if(MouseUtils.isInside(mouseX, mouseY, mod.getX() - 3, mod.getY() - 3, mod.getW() + 6, mod.getH() + 6)) {
                RoundedUtils.drawRoundOutline(mod.getX() - 3, mod.getY() - 3, mod.getW() + 6, mod.getH() + 6, 6, 0.1f, new Color(1, 1, 1, 0), new Color(220, 220, 220));
            }
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) { // Left click
            for (Mod mod : mods) {
                if (!mod.isMovable() || !mod.isEnabled()) continue;

                if (mouseX >= mod.getX() && mouseX <= mod.getX() + mod.getW() &&
                        mouseY >= mod.getY() && mouseY <= mod.getY() + mod.getH()) {

                    draggingMod = mod;
                    dragOffsetX = mouseX - mod.getX();
                    dragOffsetY = mouseY - mod.getY();
                    break;
                }
            }
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        draggingMod = null;
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        if (draggingMod != null) {
            draggingMod.setPosition(mouseX - dragOffsetX, mouseY - dragOffsetY);
        }
        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
    }
}