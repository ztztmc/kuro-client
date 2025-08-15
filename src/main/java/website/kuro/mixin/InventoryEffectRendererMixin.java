package website.kuro.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import website.kuro.mixin.accessor.GuiContainerAccessor;
import website.kuro.mixin.accessor.GuiScreenAccessor;

@Mixin(InventoryEffectRenderer.class)
public abstract class InventoryEffectRendererMixin {

    @Shadow protected boolean hasActivePotionEffects;

    @Inject(method = "updateActivePotionEffects", at = @At("HEAD"), cancellable = true)
    private void onUpdateActivePotionEffects(CallbackInfo ci) {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;

        GuiContainerAccessor containerAccessor = (GuiContainerAccessor)(Object)this;
        GuiScreenAccessor screenAccessor = (GuiScreenAccessor)(Object)this;

        int width = screenAccessor.getWidth();
        int xSize = containerAccessor.getXSize();
        int newGuiLeft = (width - xSize) / 2;

        //make this only happen when enabled from mod menu qol settings
        containerAccessor.setGuiLeft(newGuiLeft);

        this.hasActivePotionEffects = !player.getActivePotionEffects().isEmpty();

        ci.cancel();
    }
}
