package website.kuro.mixin;

import net.minecraft.client.gui.GuiIngame;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import website.kuro.KuroClient;

@Mixin(GuiIngame.class)
public class GuiInGameMixin {

    @Inject(method = "renderGameOverlay", at = @At("TAIL"))
    private void renderGameOverlay(float partialTicks, CallbackInfo ci) {
        KuroClient.modManager.renderMods();
    }
}
