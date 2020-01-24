package com.scs.splitscreenfps;

import java.util.Random;

public class Settings {
	
	public static final String VERSION = "1.01";
	public static final boolean RELEASE_MODE = false;
	
	// Hacks
	public static final boolean PLAYER_INVINCIBLE = !RELEASE_MODE && false;
	public static final boolean TEST_FLOOR_SQ = !RELEASE_MODE && false;
	public static final boolean SHOW_FPS = !RELEASE_MODE && true;
	
	public static final int ENEMY_HEALTH = 3;
	
	public static final String TITLE ="Split-Screen FPS";

	public static final int WINDOW_WIDTH_PIXELS = RELEASE_MODE ? 1024 : 512;
	public static final int WINDOW_HEIGHT_PIXELS = (int)(WINDOW_WIDTH_PIXELS * .68);

	public static final int LOGICAL_WIDTH_PIXELS = 640;
	public static final int LOGICAL_HEIGHT_PIXELS = (int)(LOGICAL_WIDTH_PIXELS * .68);
	
	public static Random random = new Random();

	private Settings() {

	}

	
	public static final void p(String s) {
		System.out.println(s);
	}
}
