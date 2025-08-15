package website.kuro.util.font;

import java.awt.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import java.io.*;
import java.util.*;

public class FontUtil
{
    public static volatile int completed;
    public static MinecraftFontRenderer normal;
    public static MinecraftFontRenderer small;
    private static Font normal_;
    private static Font small_;

    private static Font getFont(final Map<String, Font> locationMap, final String location, final int size) {
        Font font = null;
        try {
            if (locationMap.containsKey(location)) {
                font = locationMap.get(location).deriveFont(0, (float)size);
            }
            else {
                final InputStream is = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("kuro/font/" + location)).getInputStream();
                font = Font.createFont(0, is);
                locationMap.put(location, font);
                font = font.deriveFont(0, (float)size);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading font");
            font = new Font("default", 0, 20);
        }
        return font;
    }

    public static boolean hasLoaded() {
        return FontUtil.completed >= 3;
    }

    public static void bootstrap() {
        new Thread(() -> {
            Map<String, Font> locationMap = new HashMap<>();
            FontUtil.normal_ = getFont(locationMap, "Geist-Medium.ttf", 17);
            ++FontUtil.completed;
            return;
        }).start();
        new Thread(() -> {
            Map<String, Font> locationMap = new HashMap<>();
            FontUtil.small_ = getFont(locationMap, "Geist-Regular.ttf", 15);
            ++FontUtil.completed;
            return; // Added return for clarity
        }).start();
        new Thread(() -> {
            Map<String, Font> locationMap = new HashMap<>();
            ++FontUtil.completed;
        }).start();
        new Thread(() -> {
            Map<String, Font> locationMap = new HashMap<>();
            ++FontUtil.completed;
        }).start();
        while (!hasLoaded()) {
            try {
                Thread.sleep(5L);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        FontUtil.normal = new MinecraftFontRenderer(FontUtil.normal_, true, true);
        FontUtil.small = new MinecraftFontRenderer(FontUtil.small_, true, true);
    }
}