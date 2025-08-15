package website.kuro.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.util.MovingObjectPosition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import website.kuro.KuroClient;

@Mixin(Minecraft.class)
public class MinecraftMixin {

    @Inject(method = "getLimitFramerate", at = @At("HEAD"), cancellable = true)
    private void modifyMenuFramerate(CallbackInfoReturnable<Integer> cir) {
        Minecraft mc = (Minecraft)(Object)this;
        if (mc.theWorld == null && mc.currentScreen != null) {
            // In menus, override the default 30 fps cap with 60
            cir.setReturnValue(60);
        }
    }

}