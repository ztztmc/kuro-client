package website.kuro.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.util.MovingObjectPosition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import website.kuro.KuroClient;
import website.kuro.interfaces.IMixinEntityLivingBase;
import website.kuro.util.shader.MotionBlurUtils;

import java.util.ArrayList;
import java.util.List;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin {

    @Shadow
    private ShaderGroup theShaderGroup;

    @Shadow
    private boolean useShader;

    @Inject(method = "updateCameraAndRender", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/shader" + "/Framebuffer;bindFramebuffer(Z)V", shift = At.Shift.BEFORE))
    public void updateCameraAndRender(float partialTicks, long nanoTime, CallbackInfo ci) {

        List<ShaderGroup> shaders = new ArrayList<ShaderGroup>();

        if (this.theShaderGroup != null && this.useShader) {
            shaders.add(this.theShaderGroup);
        }

        ShaderGroup motionBlur = MotionBlurUtils.instance.getShader();

        if(KuroClient.modManager.motionBlurMod.isEnabled()) {
            if (motionBlur != null){
                shaders.add(motionBlur);
            }

            for (ShaderGroup shader : shaders){
                GlStateManager.pushMatrix();
                GlStateManager.loadIdentity();
                shader.loadShaderGroup(partialTicks);
                GlStateManager.popMatrix();
            }
        }
    }

    @Inject(method = "updateShaderGroupSize", at = @At("RETURN"))
    public void updateShaderGroupSize(int width, int height, CallbackInfo ci) {

        if(Minecraft.getMinecraft().theWorld == null) {
            return;
        }

        if (OpenGlHelper.shadersSupported) {
            ShaderGroup motionBlur = MotionBlurUtils.instance.getShader();

            if (motionBlur != null){
                motionBlur.createBindFramebuffers(width, height);
            }
        }
    }

    @Shadow
    private Minecraft mc;

    @Inject(method = "renderHand", at = @At(value = "HEAD"))
    public void renderHand(float partialTicks, int xOffset, CallbackInfo callback) {
        if(mc.thePlayer != null && KuroClient.modManager.animationsMod.isEnabled()
                && mc.objectMouseOver != null
                && mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK
                && mc.gameSettings.keyBindAttack.isKeyDown() && mc.gameSettings.keyBindUseItem.isKeyDown()
                && mc.thePlayer.getItemInUseCount() > 0 && (!mc.thePlayer.isSwingInProgress
                || mc.thePlayer.swingProgressInt >= ((IMixinEntityLivingBase) mc.thePlayer).accessArmSwingAnimationEnd() / 2 || mc.thePlayer.swingProgressInt < 0)) {
            mc.thePlayer.swingProgressInt = -1;
            mc.thePlayer.isSwingInProgress = true;
        }
    }

}
