package website.kuro.ui.modmenu.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import website.kuro.util.MouseUtils;
import website.kuro.util.font.FontUtil;
import website.kuro.util.render.GuiImage;
import website.kuro.util.render.RoundedUtils;

import java.awt.*;

public class KuroCategoryButton {

    int x, y, w, h;
    String imgName, text;
    GuiScreen toGui = null;

    public KuroCategoryButton(int x, int y, int w, int h, String imgName, String text) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.imgName = imgName;
        this.text = text;
    }

    public KuroCategoryButton(int x, int y, int w, int h, String imgName, String text, GuiScreen toGui) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.imgName = imgName;
        this.text = text;
        this.toGui = toGui;
    }

    public void drawButton(int mouseX, int mouseY) {
        if(toGui == null) {
            RoundedUtils.drawRound(x, y, w, h, 4, new Color(19, 19, 19));
        } else {
            if(MouseUtils.isInside(mouseX, mouseY, x, y, w, h)) {
                RoundedUtils.drawRound(x, y, w, h, 4, new Color(19, 19, 19));
            }
        }
        GlStateManager.enableBlend();
        Color color = toGui == null ? new Color(200, 200, 200) : new Color(160, 160, 160);
        GlStateManager.color(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, 1f);
        GuiImage.drawImage(x + 2, y + 2, 12, 12, "kuro/icons/" + imgName + ".png");
        GlStateManager.color(1f, 1f, 1f, 1f);
        FontUtil.normal.drawString(text, x + 16, y + 5, color.getRGB());
    }

    public void mouseClicked(int mouseX, int mouseY) {
        if(MouseUtils.isInside(mouseX, mouseY, x, y, w, h) && toGui != null) {
            Minecraft.getMinecraft().displayGuiScreen(toGui);
        }
    }

}
