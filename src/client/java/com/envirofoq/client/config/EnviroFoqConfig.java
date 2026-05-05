package com.envirofoq.client.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class EnviroFoqConfig {

	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private static final Path CONFIG_PATH = FabricLoader.getInstance()
			.getConfigDir().resolve("envirofoq.json");

	public static EnviroFoqConfig INSTANCE = new EnviroFoqConfig();

	// Atmospheric fog
	public boolean atmosphericFogEnabled        = true;
	public int atmosphericFogEnvironmentStart   = 55;
	public int atmosphericFogEnvironmentEnd     = 95;

	// Colour tinting
	public boolean biomeTintEnabled     = true;
	public boolean timeOfDayTintEnabled = true;
	public int sunsetTintStrength       = 60;
	public int nightTintStrength        = 75;

	// Voxy atmospheric multiplier
	public int voxyFogMultiplier = 100;

	// Border fog (Voxy cutoff hider)
	public boolean borderFogEnabled   = true;
	public int borderFogStart         = 85; // % of Voxy render distance
	public int borderFogEnd           = 100; // % of Voxy render distance

	// Debug overlay
	public boolean showDebugOverlay = true;

	public static void load() {
		if (CONFIG_PATH.toFile().exists()) {
			try (FileReader reader = new FileReader(CONFIG_PATH.toFile())) {
				EnviroFoqConfig loaded = GSON.fromJson(reader, EnviroFoqConfig.class);
				if (loaded != null) INSTANCE = loaded;
			} catch (IOException ignored) {}
		}
	}

	public static void save() {
		try (FileWriter writer = new FileWriter(CONFIG_PATH.toFile())) {
			GSON.toJson(INSTANCE, writer);
		} catch (IOException ignored) {}
	}
}