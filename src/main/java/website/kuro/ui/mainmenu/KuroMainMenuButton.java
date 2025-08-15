package website.kuro.ui.mainmenu;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import website.kuro.util.MouseUtils;
import website.kuro.util.font.FontUtil;
import website.kuro.util.render.RoundedUtils;

import java.awt.*;

public class KuroMainMenuButton {

    int x, y, w, h;
    String text;
    GuiScreen toGui;

    public KuroMainMenuButton(int x, int y, int w, int h, String text, GuiScreen toGui) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.text = text;
        this.toGui = toGui;
    }

    public void drawButton(int mouseX, int mouseY) {
        RoundedUtils.drawRound(x, y, w, h, 4, new Color(200, 200, 200));
        if(MouseUtils.isInside(mouseX, mouseY, x, y, w, h)) {
            RoundedUtils.drawRound(x, y, w, h, 4, new Color(185, 185, 185));
        }
        FontUtil.normal.drawCenteredString(text, x + 62, (float) (y + 4.5), new Color(9, 9, 9).getRGB());
    }

    public void mouseClicked(int mouseX, int mouseY) {
        if(MouseUtils.isInside(mouseX, mouseY, x, y, w, h)) {
            Minecraft.getMinecraft().displayGuiScreen(toGui);
        }
    }
}
