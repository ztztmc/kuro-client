package website.kuro.util.SimpleAnimation;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

public class GlUtil {
    private static Minecraft mc = Minecraft.getMinecraft();

    public static void scissor(float x, float y, float x1, float y1) {
        int scaleFactor = GlUtil.getScaleFactor();
        GL11.glScissor((int)((int)(x * (float)scaleFactor)), (int)((int)((float)GlUtil.mc.displayHeight - y1 * (float)scaleFactor)), (int)((int)((x1 - x) * (float)scaleFactor)), (int)((int)((y1 - y) * (float)scaleFactor)));
    }

    public void glScissor(double x, double y, double width, double height) {
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        Minecraft mc = Minecraft.getMinecraft();
        GL11.glScissor((int)((int)(x * (double)mc.displayWidth / (double)scaledResolution.getScaledWidth())), (int)((int)(((double)scaledResolution.getScaledHeight() - (y += height)) * (double)mc.displayHeight / (double)scaledResolution.getScaledHeight())), (int)((int)(width * (double)mc.displayWidth / (double)scaledResolution.getScaledWidth())), (int)((int)(height * (double)mc.displayHeight / (double)scaledResolution.getScaledHeight())));
    }

    public static int getScaleFactor() {
        int scaleFactor = 1;
        boolean isUnicode = mc.isUnicode();
        int guiScale = GlUtil.mc.gameSettings.guiScale;
        if (guiScale == 0) {
            guiScale = 1000;
        }
        while (scaleFactor < guiScale && GlUtil.mc.displayWidth / (scaleFactor + 1) >= 320 && GlUtil.mc.displayHeight / (scaleFactor + 1) >= 240) {
            ++scaleFactor;
        }
        if (isUnicode && scaleFactor % 2 != 0 && scaleFactor != 1) {
            --scaleFactor;
        }
        return scaleFactor;
    }

    public static void startScale(float x, float y, float scale) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, 0.0f);
        GlStateManager.scale(scale, scale, 1.0f);
        GlStateManager.translate(-x, -y, 0.0f);
    }

    public static void startScale(float x, float y, float width, float height, float scale) {
        GlStateManager.pushMatrix();
        GlStateManager.translate((x + (x + width)) / 2.0f, (y + (y + height)) / 2.0f, 0.0f);
        GlStateManager.scale(scale, scale, 1.0f);
        GlStateManager.translate(-(x + (x + width)) / 2.0f, -(y + (y + height)) / 2.0f, 0.0f);
    }

    public static void stopScale() {
        GlStateManager.popMatrix();
    }

    public static void startTranslate(float x, float y) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, 0.0f);
    }

    public static void stopTranslate() {
        GlStateManager.popMatrix();
    }
}
