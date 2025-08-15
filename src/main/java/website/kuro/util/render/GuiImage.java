package website.kuro.util.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;

public class GuiImage
{
    public static void drawImage(final int x, final int y, final int w, final int h, final String texture) {
        final ResourceLocation resource = new ResourceLocation(texture);
        Minecraft.getMinecraft().getTextureManager().bindTexture(resource);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0f, 0.0f, w, h, (float)w, (float)h);
    }
}