package website.kuro.ui.modmenu.components;

import net.minecraft.client.renderer.GlStateManager;
import website.kuro.mod.Mod;
import website.kuro.util.MouseUtils;
import website.kuro.util.SimpleAnimation.SimpleAnimation;
import website.kuro.util.font.FontUtil;
import website.kuro.util.render.GuiImage;
import website.kuro.util.render.RoundedUtils;

import java.awt.*;

public class KuroModButton {

    int x, y, w, h;
    Mod m;

    private SimpleAnimation toggleScaleAnim = new SimpleAnimation(0.8f);
    private SimpleAnimation backgroundAnim = new SimpleAnimation(0.0f);

    public KuroModButton(int x, int y, Mod m) {
        this.x = x;
        this.y = y;
        this.m = m;

        this.w = 98;
        this.h = 65;
    }

    public void drawButton(int mouseX, int mouseY) {
        RoundedUtils.drawRound(x, y, w, h, 4, new Color(16, 16, 16));

        GlStateManager.enableBlend();
        GuiImage.drawImage(x + 44, y + 12, 12, 12, "kuro/icons/mods/" + m.getName().toLowerCase() + ".png");
        FontUtil.normal.drawCenteredString(m.getName(), x + 50, y + 29, new Color(200, 200, 200).getRGB());

        backgroundAnim.setAnimation(m.isEnabled() ? 1.0f : 0.0f, 15.0);

        float t = backgroundAnim.getValue();
        Color active = new Color(200, 200, 200);
        Color inactive = new Color(28, 28, 28);

        int r = (int) (inactive.getRed() + (active.getRed() - inactive.getRed()) * t);
        int g = (int) (inactive.getGreen() + (active.getGreen() - inactive.getGreen()) * t);
        int b = (int) (inactive.getBlue() + (active.getBlue() - inactive.getBlue()) * t);
        Color animatedColor = new Color(r, g, b);

        toggleScaleAnim.setAnimation(m.isEnabled() ? 1.0f : 0.0f, 10.0);
        float toggleProgress = toggleScaleAnim.getValue();

        RoundedUtils.drawRound(x + w - 22, y + h - 14, 16, 8, 4, animatedColor);

        float dotX = x + w - 22 + 0.5f + (toggleProgress * 8);
        float dotY = y + h - 14 + 0.5f;

        RoundedUtils.drawRound(dotX, dotY, 7, 7, 3.5f, new Color(4, 5, 4));
    }

    public void onClick(int mouseX, int mouseY) {
        if(MouseUtils.isInside(mouseX, mouseY, x, y, w, h)) {
            m.toggle();
        }
    }

}
