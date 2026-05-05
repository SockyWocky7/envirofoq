package com.envirofoq.client.mixin;

import com.envirofoq.client.VoxyDetector;
import com.envirofoq.client.config.EnviroFoqConfig;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class GameRendererMixin {

	@Inject(
			method = "render(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/client/DeltaTracker;)V",
			at = @At("TAIL")
	)
	private void envirofoq$renderOverlay(GuiGraphics graphics, DeltaTracker deltaTracker, CallbackInfo ci) {
		Minecraft client = Minecraft.getInstance();
		if (client.getDebugOverlay().showDebugScreen()) return;
		if (!EnviroFoqConfig.INSTANCE.showDebugOverlay) return;

		boolean voxyEnabled = VoxyDetector.isVoxyEnabled();
		float voxyDist      = VoxyDetector.getVoxyViewDistanceChunks();

		String line1 = "EnviroFoq | Voxy: " + (voxyEnabled ? "ENABLED" : "DISABLED");
		String line2 = voxyEnabled ? "Voxy render dist: " + (int) voxyDist + " chunks" : null;

		int color = voxyEnabled ? 0x55FF55 : 0xFF5555;
		graphics.drawString(client.font, Component.literal(line1), 4, 4, color, true);
		if (line2 != null) {
			graphics.drawString(client.font, Component.literal(line2), 4, 14, 0xFFFFFF, true);
		}
	}
}
