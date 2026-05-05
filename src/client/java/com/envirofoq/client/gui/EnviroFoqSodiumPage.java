package com.envirofoq.client.gui;

import com.envirofoq.client.config.EnviroFoqConfig;
import com.google.common.collect.ImmutableList;
import net.caffeinemc.mods.sodium.client.gui.options.*;
import net.caffeinemc.mods.sodium.client.gui.options.control.SliderControl;
import net.caffeinemc.mods.sodium.client.gui.options.control.TickBoxControl;
import net.caffeinemc.mods.sodium.client.gui.options.storage.OptionStorage;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public class EnviroFoqSodiumPage {

	public static OptionPage create() {
		List<OptionGroup> groups = new ArrayList<>();
		EnviroFoqStorage storage = new EnviroFoqStorage();

		// Atmospheric fog
		groups.add(OptionGroup.createBuilder()
				.add(OptionImpl.createBuilder(boolean.class, storage)
						.setName(Component.literal("Atmospheric Fog"))
						.setTooltip(Component.literal("Backported atmospheric fog. Best used with Voxy."))
						.setControl(TickBoxControl::new)
						.setBinding((s, v) -> s.atmosphericFogEnabled = v, s -> s.atmosphericFogEnabled)
						.build())
				.add(OptionImpl.createBuilder(int.class, storage)
						.setName(Component.literal("Fog Start"))
						.setTooltip(Component.literal("Haze start distance, as % of render distance."))
						.setControl(opt -> new SliderControl(opt, 0, 100, 5, v -> Component.literal(v + "%")))
						.setBinding((s, v) -> s.atmosphericFogEnvironmentStart = v, s -> s.atmosphericFogEnvironmentStart)
						.build())
				.add(OptionImpl.createBuilder(int.class, storage)
						.setName(Component.literal("Fog End"))
						.setTooltip(Component.literal("Full density distance, as % of render distance."))
						.setControl(opt -> new SliderControl(opt, 0, 200, 5, v -> Component.literal(v + "%")))
						.setBinding((s, v) -> s.atmosphericFogEnvironmentEnd = v, s -> s.atmosphericFogEnvironmentEnd)
						.build())
				.build());

		// Colour tinting
		groups.add(OptionGroup.createBuilder()
				.add(OptionImpl.createBuilder(boolean.class, storage)
						.setName(Component.literal("Biome Colour Tinting"))
						.setTooltip(Component.literal("Tints fog using the current biome's colour."))
						.setControl(TickBoxControl::new)
						.setBinding((s, v) -> s.biomeTintEnabled = v, s -> s.biomeTintEnabled)
						.build())
				.add(OptionImpl.createBuilder(boolean.class, storage)
						.setName(Component.literal("Time of Day Tinting"))
						.setTooltip(Component.literal("Warm tones at sunrise/sunset, cool tones at night."))
						.setControl(TickBoxControl::new)
						.setBinding((s, v) -> s.timeOfDayTintEnabled = v, s -> s.timeOfDayTintEnabled)
						.build())
				.add(OptionImpl.createBuilder(int.class, storage)
						.setName(Component.literal("Sunset Tint Strength"))
						.setTooltip(Component.literal("Intensity of warm toning at sunrise/sunset."))
						.setControl(opt -> new SliderControl(opt, 0, 100, 5, v -> Component.literal(v + "%")))
						.setBinding((s, v) -> s.sunsetTintStrength = v, s -> s.sunsetTintStrength)
						.build())
				.add(OptionImpl.createBuilder(int.class, storage)
						.setName(Component.literal("Night Tint Strength"))
						.setTooltip(Component.literal("Intensity of cool toning at night."))
						.setControl(opt -> new SliderControl(opt, 0, 100, 5, v -> Component.literal(v + "%")))
						.setBinding((s, v) -> s.nightTintStrength = v, s -> s.nightTintStrength)
						.build())
				.build());

		// Voxy atmospheric
		groups.add(OptionGroup.createBuilder()
				.add(OptionImpl.createBuilder(int.class, storage)
						.setName(Component.literal("Voxy Fog Multiplier"))
						.setTooltip(Component.literal("Scales atmospheric fog when Voxy is active. 100% matches Voxy render distance."))
						.setControl(opt -> new SliderControl(opt, 10, 500, 10, v -> Component.literal(v + "%")))
						.setBinding((s, v) -> s.voxyFogMultiplier = v, s -> s.voxyFogMultiplier)
						.build())
				.build());

		// Border fog
		groups.add(OptionGroup.createBuilder()
				.add(OptionImpl.createBuilder(boolean.class, storage)
						.setName(Component.literal("Border Fog"))
						.setTooltip(Component.literal("Thick fog at Voxy's render distance edge to hide the LOD cutoff. Requires Voxy."))
						.setControl(TickBoxControl::new)
						.setBinding((s, v) -> s.borderFogEnabled = v, s -> s.borderFogEnabled)
						.build())
				.add(OptionImpl.createBuilder(int.class, storage)
						.setName(Component.literal("Border Fog Start"))
						.setTooltip(Component.literal("Where border fog begins, as % of Voxy render distance."))
						.setControl(opt -> new SliderControl(opt, 0, 100, 5, v -> Component.literal(v + "%")))
						.setBinding((s, v) -> s.borderFogStart = v, s -> s.borderFogStart)
						.build())
				.add(OptionImpl.createBuilder(int.class, storage)
						.setName(Component.literal("Border Fog End"))
						.setTooltip(Component.literal("Where border fog reaches full density, as % of Voxy render distance."))
						.setControl(opt -> new SliderControl(opt, 50, 120, 5, v -> Component.literal(v + "%")))
						.setBinding((s, v) -> s.borderFogEnd = v, s -> s.borderFogEnd)
						.build())
				.build());

		// Debug
		groups.add(OptionGroup.createBuilder()
				.add(OptionImpl.createBuilder(boolean.class, storage)
						.setName(Component.literal("Debug Overlay"))
						.setTooltip(Component.literal("Shows Voxy status and render distance on screen."))
						.setControl(TickBoxControl::new)
						.setBinding((s, v) -> s.showDebugOverlay = v, s -> s.showDebugOverlay)
						.build())
				.build());

		return new OptionPage(Component.literal("EnviroFoq"), ImmutableList.copyOf(groups));
	}

	public static class EnviroFoqStorage implements OptionStorage<EnviroFoqConfig> {
		@Override
		public EnviroFoqConfig getData() { return EnviroFoqConfig.INSTANCE; }

		@Override
		public void save() { EnviroFoqConfig.save(); }
	}
}