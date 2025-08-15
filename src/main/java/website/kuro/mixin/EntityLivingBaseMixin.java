package website.kuro.mixin;

import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import website.kuro.interfaces.IMixinEntityLivingBase;

@Mixin(EntityLivingBase.class)
public abstract class EntityLivingBaseMixin implements IMixinEntityLivingBase {

    @Shadow
    public abstract int getArmSwingAnimationEnd();

    @Override
    public int accessArmSwingAnimationEnd() {
        return getArmSwingAnimationEnd();
    }
}