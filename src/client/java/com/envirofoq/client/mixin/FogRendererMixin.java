package com.envirofoq.client.mixin;

import com.envirofoq.client.VoxyDetector;
import com.envirofoq.client.config.EnviroFoqConfig;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.Camera;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.material.FogType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = FogRenderer.class, priority = 9999)
public class FogRendererMixin {

	@Inject(method = "setupFog", at = @At("TAIL"))
	private static void envirofoq$applyAtmosphericFog(
			Camera camera,
			FogRenderer.FogMode fogMode,
			float viewDistance,
			boolean thickenFog,
			float partialTick,
			CallbackInfo ci
	) {
		EnviroFoqConfig cfg = EnviroFoqConfig.INSTANCE;
		if (fogMode != FogRenderer.FogMode.FOG_TERRAIN) return;
		if (camera.getFluidInCamera() != FogType.NONE) return;

		Minecraft client = Minecraft.getInstance();
		ClientLevel world = client.level;
		if (world == null) return;

		boolean voxyActive = VoxyDetector.isVoxyEnabled();
		float voxyChunks   = voxyActive ? VoxyDetector.getVoxyViewDistanceChunks() : 0;

		// --- Atmospheric fog ---
		if (cfg.atmosphericFogEnabled) {
			float effectiveDistance = viewDistance;
			if (voxyActive && voxyChunks > 0) {
				float vanillaChunks  = viewDistance / 16.0f;
				float ratio          = voxyChunks / vanillaChunks;
				float userMultiplier = cfg.voxyFogMultiplier / 100.0f;
				effectiveDistance    = viewDistance * ratio * userMultiplier;
			}

			float fogStart = Math.max(0.0f, effectiveDistance * (cfg.atmosphericFogEnvironmentStart / 100.0f));
			float fogEnd   = Math.max(fogStart + 1.0f, effectiveDistance * (cfg.atmosphericFogEnvironmentEnd / 100.0f));

			RenderSystem.setShaderFogStart(fogStart);
			RenderSystem.setShaderFogEnd(fogEnd);

			// Biome fog colour
			BlockPos pos = camera.getEntity().blockPosition();
			int packed   = world.getBiome(pos).value().getFogColor();
			float fogR   = ((packed >> 16) & 0xFF) / 255.0f;
			float fogG   = ((packed >>  8) & 0xFF) / 255.0f;
			float fogB   = ( packed        & 0xFF) / 255.0f;

			// Time-of-day tinting
			if (cfg.timeOfDayTintEnabled) {
				float sunAngle = Mth.cos(world.getTimeOfDay(partialTick) * Mth.TWO_PI);
				float golden   = Mth.clamp(1.0f - Math.abs(sunAngle), 0.0f, 1.0f);
				golden         = golden * golden;
				float night    = Mth.clamp(-sunAngle * 0.5f + 0.5f, 0.0f, 1.0f);
				float ss       = cfg.sunsetTintStrength / 100.0f;
				float ns       = cfg.nightTintStrength  / 100.0f;

				fogR = Mth.lerp(golden * ss, fogR, 1.0f);
				fogG = Mth.lerp(golden * ss, fogG, 0.55f);
				fogB = Mth.lerp(golden * ss, fogB, 0.2f);
				fogR = Mth.lerp(night  * ns, fogR, 0.05f);
				fogG = Mth.lerp(night  * ns, fogG, 0.07f);
				fogB = Mth.lerp(night  * ns, fogB, 0.15f);
			}

			if (cfg.biomeTintEnabled) {
				RenderSystem.setShaderFogColor(
						Mth.clamp(fogR, 0.0f, 1.0f),
						Mth.clamp(fogG, 0.0f, 1.0f),
						Mth.clamp(fogB, 0.0f, 1.0f),
						1.0f
				);
			}
		}

		// --- Border fog (Voxy cutoff hider) ---
		if (cfg.borderFogEnabled && voxyActive && voxyChunks > 0) {
			float voxyBlocks  = voxyChunks * 16.0f;
			float borderStart = voxyBlocks * (cfg.borderFogStart / 100.0f);
			float borderEnd   = voxyBlocks * (cfg.borderFogEnd   / 100.0f);

			// Only override if border fog is closer than whatever is already set
			if (borderStart < RenderSystem.getShaderFogEnd()) {
				RenderSystem.setShaderFogStart(Math.min(borderStart, RenderSystem.getShaderFogStart()));
				RenderSystem.setShaderFogEnd(borderEnd);
			}
		}
	}
}