package website.kuro.mixin;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import website.kuro.KuroClient;

@Mixin(EntityPlayer.class)
public abstract class EntityPlayerMixin extends EntityLivingBase {

    public EntityPlayerMixin(World worldIn) { super(worldIn); }

    private long kuro_sneakTimer = 0L;
    private boolean kuro_prevSneaking = false;

    @Inject(method = "getEyeHeight", at = @At("HEAD"), cancellable = true)
    private void smoothSneak(CallbackInfoReturnable<Float> cir) {
        if (!KuroClient.modManager.animationsMod.isEnabled()) return;

        boolean sneaking = this.isSneaking();
        if (sneaking != kuro_prevSneaking || kuro_sneakTimer <= 0L) {
            kuro_sneakTimer = System.currentTimeMillis();
        }
        kuro_prevSneaking = sneaking;

        float height = 1.62F;

        if (sneaking) {
            int i = (int) (kuro_sneakTimer + 8L - System.currentTimeMillis());
            if (i > -50) {
                height += i * 0.0017D;
                if (height < 0.0F || height > 10.0F) height = 1.54F;
            } else {
                height -= 0.08D;
            }
        } else {
            int j = (int) (kuro_sneakTimer + 8L - System.currentTimeMillis());
            if (j > -50) {
                height -= j * 0.0017D;
                height -= 0.08D;
                if (height < 0.0F) height = 1.62F;
            }
        }
        cir.setReturnValue(height);
    }
}
