package website.kuro.mixin;

import net.minecraft.client.entity.EntityPlayerSP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import website.kuro.KuroClient;

@Mixin(EntityPlayerSP.class)
public class EntityPlayerSPMixin {

    @Inject(method = "onUpdate", at = @At("HEAD"))
    private void eventUpdateCallEvent(CallbackInfo ci) {
        KuroClient.modManager.toggleSprintMod.startSprint();
    }
}
