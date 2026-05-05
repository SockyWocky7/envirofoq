package com.envirofoq.client;

import com.envirofoq.EnviroFoq;
import com.envirofoq.client.config.EnviroFoqConfig;
import com.envirofoq.client.gui.EnviroFoqConfigScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class EnviroFoqClient implements ClientModInitializer {

	public static KeyMapping openConfigKey;

	@Override
	public void onInitializeClient() {
		EnviroFoqConfig.load();

		openConfigKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
				"key.envirofoq.open_config",
				GLFW.GLFW_KEY_H,
				"key.categories.envirofoq"
		));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (openConfigKey.consumeClick() && client.screen == null) {
				client.setScreen(new EnviroFoqConfigScreen(null));
			}
		});

		EnviroFoq.LOGGER.info("EnviroFoq loaded — press H to open settings");
	}
}
