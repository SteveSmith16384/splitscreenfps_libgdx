package com.scs.splitscreenfps;

import java.util.Random;

public class Settings {
	
	public static final int MODE_TAG = 1;
	public static final int MODE_MM = 2;
	public static final int MODE_DUNGEON = 3;
	public static final int MODE_FARM = 4;
	public static final int MODE_FTL = 5;
	
	public static final int CURRENT_MODE = MODE_MM;
	public static final String VERSION = "1.01";
	public static final boolean RELEASE_MODE = false;
	
	// Hacks
	public static final boolean TEST_ALT_TREX = !RELEASE_MODE && false;
	public static final boolean SMALL_MAP = !RELEASE_MODE && true;
	public static final boolean DEFAULT_4_PLAYERS = !RELEASE_MODE && true;
	public static final boolean TEST_START_IN_WALL = !RELEASE_MODE && false;
	public static final boolean TEST_SCREEN_COORDS = !RELEASE_MODE && false;
	public static final boolean SHOW_FPS = !RELEASE_MODE && false;
	
	public static final float PLAYER_HEIGHT = 0.52f;
	
	public static final String TITLE ="Split-Screen Multiplayer";

	public static final int WINDOW_WIDTH_PIXELS = RELEASE_MODE ? 1024 : 512;
	public static final int WINDOW_HEIGHT_PIXELS = (int)(WINDOW_WIDTH_PIXELS * .68);

	// Logical size of viewport
	//public static final int LOGICAL_WIDTH_PIXELS = 320;//640;
	//public static final int LOGICAL_HEIGHT_PIXELS = (int)(LOGICAL_WIDTH_PIXELS * .68);
	
	public static Random random = new Random();

	private Settings() {

	}

	
	public static final void p(String s) {
		System.out.println(s);
	}


	public static final void pe(String s) {
		System.err.println(s);
	}

}
