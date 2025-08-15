package website.kuro.util;

import org.lwjgl.input.Mouse;

public class MouseUtils {
    public static Scroll scroll() {
        int mouse = Mouse.getDWheel();
        if (mouse > 0) {
            return Scroll.UP;
        }
        if (mouse < 0) {
            return Scroll.DOWN;
        }
        return null;
    }

    public static boolean isInside(int mouseX, int mouseY, double x, double y, double width, double height) {
        return (double)mouseX > x && (double)mouseX < x + width && (double)mouseY > y && (double)mouseY < y + height;
    }

    public static enum Scroll {
        UP,
        DOWN;

    }
}
