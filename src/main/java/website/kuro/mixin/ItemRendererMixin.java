package website.kuro.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import website.kuro.KuroClient;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {

    @Shadow private ItemStack itemToRender;
    @Shadow private float equippedProgress;
    @Shadow private float prevEquippedProgress;
    @Shadow private int equippedItemSlot;

    @ModifyConstant(method = "renderItemInFirstPerson", constant = @Constant(floatValue = 0.0f))
    private float modifyBlockAnimationBob(float original, float partialTicks) {
        AbstractClientPlayer player = Minecraft.getMinecraft().thePlayer;

        if (KuroClient.modManager.animationsMod.isEnabled()) {
            return player.getSwingProgress(partialTicks);
        }

        return original;
    }

}