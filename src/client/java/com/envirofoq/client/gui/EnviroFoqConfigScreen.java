package com.envirofoq.client.gui;

import com.envirofoq.client.config.EnviroFoqConfig;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class EnviroFoqConfigScreen extends Screen {

	private final Screen parent;
	private final EnviroFoqConfig cfg = EnviroFoqConfig.INSTANCE;

	public EnviroFoqConfigScreen(Screen parent) {
		super(Component.literal("EnviroFoq Settings"));
		this.parent = parent;
	}

	@Override
	protected void init() {
		int cx   = this.width / 2;
		int y    = 40;
		int btnW = 300;
		int btnH = 20;
		int gap  = 24;

		// Atmospheric fog
		addRenderableWidget(Button.builder(
						Component.literal("Atmospheric Fog: " + on(cfg.atmosphericFogEnabled)),
						btn -> { cfg.atmosphericFogEnabled = !cfg.atmosphericFogEnabled;
							btn.setMessage(Component.literal("Atmospheric Fog: " + on(cfg.atmosphericFogEnabled)));
							EnviroFoqConfig.save(); })
				.bounds(cx - btnW / 2, y, btnW, btnH).build()); y += gap;

		addRenderableWidget(Button.builder(
						Component.literal("Fog Start: " + cfg.atmosphericFogEnvironmentStart + "%"),
						btn -> { cfg.atmosphericFogEnvironmentStart = (cfg.atmosphericFogEnvironmentStart + 5) % 105;
							btn.setMessage(Component.literal("Fog Start: " + cfg.atmosphericFogEnvironmentStart + "%"));
							EnviroFoqConfig.save(); })
				.bounds(cx - btnW / 2, y, btnW, btnH).build()); y += gap;

		addRenderableWidget(Button.builder(
						Component.literal("Fog End: " + cfg.atmosphericFogEnvironmentEnd + "%"),
						btn -> { cfg.atmosphericFogEnvironmentEnd = (cfg.atmosphericFogEnvironmentEnd + 5) % 205;
							btn.setMessage(Component.literal("Fog End: " + cfg.atmosphericFogEnvironmentEnd + "%"));
							EnviroFoqConfig.save(); })
				.bounds(cx - btnW / 2, y, btnW, btnH).build()); y += gap;

		// Colour tinting
		addRenderableWidget(Button.builder(
						Component.literal("Biome Tinting: " + on(cfg.biomeTintEnabled)),
						btn -> { cfg.biomeTintEnabled = !cfg.biomeTintEnabled;
							btn.setMessage(Component.literal("Biome Tinting: " + on(cfg.biomeTintEnabled)));
							EnviroFoqConfig.save(); })
				.bounds(cx - btnW / 2, y, btnW, btnH).build()); y += gap;

		addRenderableWidget(Button.builder(
						Component.literal("Time of Day Tinting: " + on(cfg.timeOfDayTintEnabled)),
						btn -> { cfg.timeOfDayTintEnabled = !cfg.timeOfDayTintEnabled;
							btn.setMessage(Component.literal("Time of Day Tinting: " + on(cfg.timeOfDayTintEnabled)));
							EnviroFoqConfig.save(); })
				.bounds(cx - btnW / 2, y, btnW, btnH).build()); y += gap;

		addRenderableWidget(Button.builder(
						Component.literal("Sunset Strength: " + cfg.sunsetTintStrength + "%"),
						btn -> { cfg.sunsetTintStrength = (cfg.sunsetTintStrength + 10) % 110;
							btn.setMessage(Component.literal("Sunset Strength: " + cfg.sunsetTintStrength + "%"));
							EnviroFoqConfig.save(); })
				.bounds(cx - btnW / 2, y, btnW, btnH).build()); y += gap;

		addRenderableWidget(Button.builder(
						Component.literal("Night Strength: " + cfg.nightTintStrength + "%"),
						btn -> { cfg.nightTintStrength = (cfg.nightTintStrength + 10) % 110;
							btn.setMessage(Component.literal("Night Strength: " + cfg.nightTintStrength + "%"));
							EnviroFoqConfig.save(); })
				.bounds(cx - btnW / 2, y, btnW, btnH).build()); y += gap;

		// Voxy atmospheric multiplier
		addRenderableWidget(Button.builder(
						Component.literal("Voxy Fog Multiplier: " + cfg.voxyFogMultiplier + "%"),
						btn -> { cfg.voxyFogMultiplier = cfg.voxyFogMultiplier >= 500 ? 10 : cfg.voxyFogMultiplier + 10;
							btn.setMessage(Component.literal("Voxy Fog Multiplier: " + cfg.voxyFogMultiplier + "%"));
							EnviroFoqConfig.save(); })
				.bounds(cx - btnW / 2, y, btnW, btnH).build()); y += gap;

		// Border fog
		addRenderableWidget(Button.builder(
						Component.literal("Border Fog: " + on(cfg.borderFogEnabled)),
						btn -> { cfg.borderFogEnabled = !cfg.borderFogEnabled;
							btn.setMessage(Component.literal("Border Fog: " + on(cfg.borderFogEnabled)));
							EnviroFoqConfig.save(); })
				.bounds(cx - btnW / 2, y, btnW, btnH).build()); y += gap;

		addRenderableWidget(Button.builder(
						Component.literal("Border Fog Start: " + cfg.borderFogStart + "%"),
						btn -> { cfg.borderFogStart = cfg.borderFogStart >= 100 ? 0 : cfg.borderFogStart + 5;
							btn.setMessage(Component.literal("Border Fog Start: " + cfg.borderFogStart + "%"));
							EnviroFoqConfig.save(); })
				.bounds(cx - btnW / 2, y, btnW, btnH).build()); y += gap;

		addRenderableWidget(Button.builder(
						Component.literal("Border Fog End: " + cfg.borderFogEnd + "%"),
						btn -> { cfg.borderFogEnd = cfg.borderFogEnd >= 120 ? 50 : cfg.borderFogEnd + 5;
							btn.setMessage(Component.literal("Border Fog End: " + cfg.borderFogEnd + "%"));
							EnviroFoqConfig.save(); })
				.bounds(cx - btnW / 2, y, btnW, btnH).build()); y += gap;

		// Debug
		addRenderableWidget(Button.builder(
						Component.literal("Debug Overlay: " + on(cfg.showDebugOverlay)),
						btn -> { cfg.showDebugOverlay = !cfg.showDebugOverlay;
							btn.setMessage(Component.literal("Debug Overlay: " + on(cfg.showDebugOverlay)));
							EnviroFoqConfig.save(); })
				.bounds(cx - btnW / 2, y, btnW, btnH).build()); y += gap + 4;

		addRenderableWidget(Button.builder(CommonComponents.GUI_DONE,
						btn -> this.onClose())
				.bounds(cx - 100, y, 200, btnH).build());
	}

	private static String on(boolean val) { return val ? "ON" : "OFF"; }

	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
		super.render(graphics, mouseX, mouseY, partialTick);
		graphics.drawCenteredString(this.font, this.title, this.width / 2, 15, 0xFFFFFF);
		graphics.drawCenteredString(this.font,
				Component.literal("Click to cycle. Edit envirofoq.json for precise values."),
				this.width / 2, 26, 0xAAAAAA);
	}

	@Override
	public void onClose() { this.minecraft.setScreen(parent); }
}