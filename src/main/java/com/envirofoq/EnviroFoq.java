package com.envirofoq;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnviroFoq implements ModInitializer {

	public static final String MOD_ID = "envirofoq";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// Client-only mod, nothing to do server-side
	}
}
