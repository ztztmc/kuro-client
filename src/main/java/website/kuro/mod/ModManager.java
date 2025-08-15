package website.kuro.mod;

import website.kuro.mod.impl.*;

import java.util.ArrayList;

public class ModManager {

    public ArrayList<Mod> mods = new ArrayList<>();

    public AnimationsMod animationsMod;
    public FPSDisplayMod fpsDisplayMod;
    public FullbrightMod fullbrightMod;
    public MotionBlurMod motionBlurMod;
    public PingDisplayMod pingDisplayMod;
    public QOLMod qolMod;
    public ToggleSprintMod toggleSprintMod;

    public ModManager() {
        mods.add(animationsMod = new AnimationsMod());
        mods.add(fpsDisplayMod = new FPSDisplayMod());
        mods.add(fullbrightMod = new FullbrightMod());
        mods.add(motionBlurMod = new MotionBlurMod());
        mods.add(pingDisplayMod = new PingDisplayMod());
        mods.add(qolMod = new QOLMod());
        mods.add(toggleSprintMod = new ToggleSprintMod());
    }

    public void renderMods() {
        for(Mod m : mods) {
            if(m.isEnabled() && m.isMovable()) {
                m.draw();
            }
        }
    }

    public ArrayList<Mod> getMods() {
        return mods;
    }
}
