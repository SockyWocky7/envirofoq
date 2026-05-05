package com.envirofoq.client;

public class VoxyDetector {

	private static Object voxyConfig = null;
	private static boolean checked = false;

	private static Object getConfig() {
		if (checked) return voxyConfig;
		checked = true;
		try {
			Class<?> cls = Class.forName("me.cortex.voxy.client.config.VoxyConfig");
			voxyConfig = cls.getField("CONFIG").get(null);
		} catch (Exception e) {
			voxyConfig = null;
		}
		return voxyConfig;
	}

	public static boolean isVoxyPresent() {
		return getConfig() != null;
	}

	/** Voxy render distance in chunks, or 0 if unavailable. */
	public static float getVoxyViewDistanceChunks() {
		Object cfg = getConfig();
		if (cfg == null) return 0;
		try {
			float srd = (float) cfg.getClass().getField("sectionRenderDistance").get(cfg);
			return srd * 16.0f;
		} catch (Exception e) {
			return 0;
		}
	}

	/** Voxy render distance in blocks, or 0 if unavailable. */
	public static float getVoxyViewDistanceBlocks() {
		return getVoxyViewDistanceChunks() * 16.0f;
	}

	public static boolean isVoxyEnabled() {
		Object cfg = getConfig();
		if (cfg == null) return false;
		try {
			return (boolean) cfg.getClass().getMethod("isRenderingEnabled").invoke(cfg);
		} catch (Exception e) {
			return false;
		}
	}
}