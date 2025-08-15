package website.kuro.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import website.kuro.KuroClient;
import website.kuro.ui.mainmenu.KuroMainMenu;
import website.kuro.util.font.FontUtil;

@Mixin(GuiMainMenu.class)
public class StartMixin {
	@Inject(at = @At("HEAD"), method = "initGui()V")
	private void init(CallbackInfo info) {
		if(!KuroClient.fontsLoaded) {
			FontUtil.bootstrap();
			KuroClient.setFontsLoaded(true);
		}
		KuroClient.modManager.fullbrightMod.onEnable();
		Minecraft.getMinecraft().displayGuiScreen(new KuroMainMenu(-1));
	}

}
