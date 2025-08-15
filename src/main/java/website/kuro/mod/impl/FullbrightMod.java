package website.kuro.mod.impl;

import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import website.kuro.mod.Mod;

public class FullbrightMod extends Mod {

    public FullbrightMod() {
        super("Fullbright", "Sets maximum brightness", -1, -1, -1, -1, false);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        Minecraft.getMinecraft().gameSettings.gammaSetting = 100.0F;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        Minecraft.getMinecraft().gameSettings.gammaSetting = 1.0F;
    }
}
