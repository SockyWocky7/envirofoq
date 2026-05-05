package com.envirofoq.client.mixin;

import com.envirofoq.client.gui.EnviroFoqSodiumPage;
import net.caffeinemc.mods.sodium.client.gui.SodiumOptionsGUI;
import net.caffeinemc.mods.sodium.client.gui.options.OptionPage;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(value = SodiumOptionsGUI.class, remap = false)
public class SodiumOptionsGUIMixin {

	@Shadow(remap = false) @Final private List<OptionPage> pages;

	@Inject(method = "<init>", at = @At("TAIL"), remap = false)
	private void envirofoq$addPage(Screen prevScreen, CallbackInfo ci) {
		this.pages.add(EnviroFoqSodiumPage.create());
	}
}
