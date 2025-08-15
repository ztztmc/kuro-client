package website.kuro;

import net.fabricmc.api.ModInitializer;
import net.legacyfabric.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.legacyfabric.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;
import website.kuro.mod.ModManager;
import website.kuro.ui.modmenu.KuroModMenu;

public class KuroClient implements ModInitializer {

	public static KeyBinding openModMenu;
	public static boolean fontsLoaded = false;

	public static ModManager modManager;

	public static void setFontsLoaded(boolean fontsLoaded) {
		KuroClient.fontsLoaded = fontsLoaded;
	}

	@Override
	public void onInitialize() {
		modManager = new ModManager();
		openModMenu = new KeyBinding("Open Menu", Keyboard.KEY_RSHIFT, "Kuro Client");

		KeyBindingHelper.registerKeyBinding(openModMenu);
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (openModMenu.isPressed()) {
				Minecraft.getMinecraft().displayGuiScreen(new KuroModMenu(true, -1));
			}
		});

	}

}
