package com.scs.splitscreenfps;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.Random;

public class Settings {
	
	public static final int MODE_TAG = 1;
	public static final int MODE_MM = 2;
	public static final int MODE_DUNGEON = 3;
	public static final int MODE_FARM = 4;
	public static final int MODE_FTL = 5;
	public static final int MODE_CAR_PARK = 6;
	
	public static final int CURRENT_MODE = MODE_CAR_PARK;
	public static final String VERSION = "1.01";
	public static final boolean RELEASE_MODE = false;
	
	// Hacks
	public static final boolean AUTO_START = !RELEASE_MODE && true;
	public static final boolean START_4_PLAYERS = !RELEASE_MODE && false;
	public static final boolean TEST_MODEL = !RELEASE_MODE && true;
	public static final boolean TEST_ALT_TREX = !RELEASE_MODE && false;
	public static final boolean SMALL_MAP = !RELEASE_MODE && true;
	public static final boolean TEST_SCREEN_COORDS = !RELEASE_MODE && false;
	public static final boolean SHOW_FPS = !RELEASE_MODE && false;
	
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
			e.printStackTrace();
		}
		
		switch (CURRENT_MODE) {
		case MODE_TAG:
			TITLE = "Tag";
			break;
		case MODE_MM:
			TITLE = "3D Monster Maze";
			break;
		case MODE_FARM:
			TITLE = "The Funny Farm";
			break;
		case MODE_FTL:
			TITLE = "FTL";
			break;
		case MODE_CAR_PARK:
			TITLE = "Crazy car Park";
			break;
		default:
			throw new RuntimeException("Unknown Mode: " + CURRENT_MODE);
		}
		
	}

	
	public static final void p(String s) {
		System.out.println(s);
	}


	public static final void pe(String s) {
		System.err.println(s);
	}

}
