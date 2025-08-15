package website.kuro.mod.impl;

import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import website.kuro.mod.Mod;

public class ToggleSprintMod extends Mod {

    public ToggleSprintMod() {
        super("Toggle Sprint", "Makes you always sprint.", -1, -1, -1, -1, false);
    }

    public void startSprint() {
        if (this.isEnabled() && Minecraft.getMinecraft().thePlayer !=null && !Minecraft.getMinecraft().thePlayer.movementInput.sneak && ((float)Minecraft.getMinecraft().thePlayer.getFoodStats().getFoodLevel() > 6.0f || Minecraft.getMinecraft().thePlayer.capabilities.allowFlying) && !Minecraft.getMinecraft().thePlayer.isPotionActive(Potion.blindness) && Minecraft.getMinecraft().thePlayer.movementInput.moveForward >= 0.8f && !Minecraft.getMinecraft().thePlayer.isSprinting() && !Minecraft.getMinecraft().thePlayer.isUsingItem() && !Minecraft.getMinecraft().thePlayer.isCollidedHorizontally) {
            Minecraft.getMinecraft().thePlayer.setSprinting(true);
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        if(Minecraft.getMinecraft().thePlayer != null)
            Minecraft.getMinecraft().thePlayer.setSprinting(false);
    }

}
