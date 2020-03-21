package com.scs.splitscreenfps;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.Random;

public class Settings {
	
	public static final int MODE_TAG = 1; // Finished
	public static final int MODE_MM = 2; // Finished
	public static final int MODE_DUNGEON = 3; // Barely started
	public static final int MODE_FUNNY_FARM = 4; // Barely started
	public static final int MODE_FTL = 5; // Barely started
	public static final int MODE_CAR_PARK = 6; // Barely started
	public static final int MODE_DEATHCHASE = 7; // Basic
	public static final int MODE_TOWER_DEFENCE = 8; // Started
	public static final int MODE_BLADE_RUNNER = 9; // Barely started
	public static final int MODE_STOCK_CAR = 10; // Started
	
	public static final boolean RELEASE_MODE = new File("debug_mode.tmp").exists() == false;

	public static int CURRENT_MODE = RELEASE_MODE ? MODE_TAG : MODE_TAG;
	public static boolean FIXED_GAME = RELEASE_MODE || true;
	public static final String VERSION = "1.01";
	
	// Hacks
	public static final boolean DEBUG_TAG = !RELEASE_MODE && true;
	public static final boolean STRICT = !RELEASE_MODE && true;
	public static final boolean DARKMODE = !RELEASE_MODE && false;
	public static final boolean AUTO_START = !RELEASE_MODE && false;
	public static final boolean START_4_PLAYERS = !RELEASE_MODE && false;
	public static final boolean TEST_MODEL = !RELEASE_MODE && false;
	public static final boolean SMALL_MAP = !RELEASE_MODE && true;
	public static final boolean TEST_SCREEN_COORDS = !RELEASE_MODE && false;
	public static final boolean SHOW_FPS = !RELEASE_MODE && false;
	
	public static final float MIN_AXIS = 0.2f; // Movement less than this is ignored
	public static final float PLAYER_HEIGHT = 0.52f;
	public static final float CAM_OFFSET = 0.14f;
	
	public static String TITLE;

	public static final int WINDOW_WIDTH_PIXELS = RELEASE_MODE ? 1024 : 512;
	public static final int WINDOW_HEIGHT_PIXELS = (int)(WINDOW_WIDTH_PIXELS * .68);

	public static Properties prop;
	
	// Logical size of viewport
	//public static final int LOGICAL_WIDTH_PIXELS = 320;//640;
	//public static final int LOGICAL_HEIGHT_PIXELS = (int)(LOGICAL_WIDTH_PIXELS * .68);
	
	public static Random random = new Random();

	private Settings() {

	}
	
	
	public static void init() {
		// load any settings file
		prop = new Properties();
		try {
			prop.load(new FileInputStream("config.txt"));
		} catch (Exception e) {
			//e.printStackTrace();
		}
		
		switch (CURRENT_MODE) {
		case MODE_TAG:
			TITLE = "Tag";
			break;
		case MODE_MM:
			TITLE = "3D Monster Maze";
			break;
		case MODE_FUNNY_FARM:
			TITLE = "The Funny Farm";
			break;
		case MODE_FTL:
			TITLE = "FTL";
			break;
		case MODE_CAR_PARK:
			TITLE = "Crazy car Park";
			break;
		case MODE_DEATHCHASE:
			TITLE = "3D Death Chase";
			break;
		case MODE_TOWER_DEFENCE:
			TITLE = "Tower Defence";
			break;
		case MODE_BLADE_RUNNER:
			TITLE = "Bladerunner";
			break;
		case MODE_STOCK_CAR:
			TITLE = "Stock Car Racer";
			break;
		default:
			TITLE = "[Unknown]";
			//throw new RuntimeException("Unknown Mode: " + CURRENT_MODE);
		}
		
	}

	
	public static final void p(String s) {
		System.out.println(s);
	}


	public static final void pe(String s) {
		System.err.println(s);
	}

}
