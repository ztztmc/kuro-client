package website.kuro.ui.mainmenu;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.renderer.GlStateManager;
import website.kuro.ui.modmenu.HUDPositioningScreen;
import website.kuro.ui.modmenu.KuroModMenu;
import website.kuro.util.MouseUtils;
import website.kuro.util.SimpleAnimation.SimpleAnimation;
import website.kuro.util.render.GuiImage;
import website.kuro.util.render.RoundedUtils;

import java.awt.*;
import java.util.Objects;

public class KuroImageButton {

    int x, y;
    String imgName;

    private SimpleAnimation hovercolor = new SimpleAnimation(0.0f);

    public KuroImageButton(int x, int y, String imgName) {
        this.x = x;
        this.y = y;
        this.imgName = imgName;
    }

    public void drawButton(int mouseX, int mouseY) {
        if(MouseUtils.isInside(mouseX, mouseY, x - 2, y - 2, 19, 19)) {
            RoundedUtils.drawRound(x - 1, y - 1, 18, 18, 4, new Color(22, 22, 22));
        }
        GlStateManager.enableBlend();
        GuiImage.drawImage(x + 2, y + 2, 12, 12, "kuro/icons/" + imgName + ".png");
    }

    public void mouseClicked(int mouseX, int mouseY, int panoramaTimer) {
        if(MouseUtils.isInside(mouseX, mouseY, x - 2, y - 2, 19, 19)) {
            if(Objects.equals(imgName, "x")) {
                Minecraft.getMinecraft().shutdown();
            } else if(Objects.equals(imgName, "settings")) {
                Minecraft.getMinecraft().displayGuiScreen(new GuiOptions(new KuroMainMenu(-1), Minecraft.getMinecraft().gameSettings));
            } else if(Objects.equals(imgName, "menu")) {
                Minecraft.getMinecraft().displayGuiScreen(new KuroModMenu(false, panoramaTimer));
            } else if(Objects.equals(imgName, "mods")) {
                Minecraft.getMinecraft().displayGuiScreen(new KuroModMenu(false, panoramaTimer));
            } else if(Objects.equals(imgName, "kuro-settings")) {
                Minecraft.getMinecraft().displayGuiScreen(new KuroModMenu(false, panoramaTimer));
            } else if(Objects.equals(imgName, "edit")) {
                Minecraft.getMinecraft().displayGuiScreen(new HUDPositioningScreen());
            }
        }
    }
}
