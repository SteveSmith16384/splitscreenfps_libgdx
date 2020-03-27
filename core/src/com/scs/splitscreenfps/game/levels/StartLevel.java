package com.scs.splitscreenfps.game.levels;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.regex.Pattern;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.Settings;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.MapData;
import com.scs.splitscreenfps.game.components.CanBeHarmedComponent;
import com.scs.splitscreenfps.game.components.monstermaze.CanUseMonsterMazeExitComponent;
import com.scs.splitscreenfps.game.data.MapSquare;
import com.scs.splitscreenfps.game.entities.Floor;
import com.scs.splitscreenfps.game.entities.GenericSquare;
import com.scs.splitscreenfps.game.entities.Wall;
import com.scs.splitscreenfps.game.entities.ftl.FTLEntityFactory;
import com.scs.splitscreenfps.game.entities.ftl.RandomFloor;
import com.scs.splitscreenfps.game.entities.monstermaze.MonsterMazeEntityFactory;
import com.scs.splitscreenfps.game.entities.monstermaze.MonsterMazeExit;
import com.scs.splitscreenfps.game.entities.monstermaze.TRex;
import com.scs.splitscreenfps.game.systems.monstermaze.MonsterGrowlsSystem;
import com.scs.splitscreenfps.game.systems.monstermaze.MonsterMazeExitSystem;
import com.scs.splitscreenfps.game.systems.monstermaze.RegenKeySystem;
import com.scs.splitscreenfps.game.systems.monstermaze.TRexAISystem;
import com.scs.splitscreenfps.game.systems.monstermaze.TRexHarmsPlayerSystem;
import com.scs.splitscreenfps.mapgen.MazeGen1;

import ssmith.libgdx.GridPoint2Static;

public class StartLevel extends AbstractLevel {

	public static Properties prop;

	public StartLevel(Game _game) {
		super(_game);

		prop = new Properties();
		try {
			prop.load(new FileInputStream("monstermaze/mm_config.txt"));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	@Override
	public void loadAvatars() {
		super.loadAvatars();
		for (int i=0 ; i<game.players.length ; i++) {
			//game.players[i].addComponent(new CanUseMonsterMazeExitComponent(i));
		}	
	}


	@Override
	public void load() {
		loadMap(game, "start/map1.csv");
	}


	@Override
	public void loadAssets() {
	}


	@Override
	public void setBackgroundColour() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
	}


	private void loadMap(Game game, String file) {
		String str = Gdx.files.internal(file).readString();
		String[] str2 = str.split("\n");

		this.map_width = str2[0].split("\t").length;
		this.map_height = str2.length;

		game.mapData = new MapData(map_width, map_height);

		int row = 0;
		for (String s : str2) {
			s = s.trim();
			if (s.length() > 0 && s.startsWith("#") == false) {
				String cells[] = s.split("\t");
				for (int col=0 ; col<cells.length ; col++) {
					game.mapData.map[col][row] = new MapSquare();

					String cell = cells[col];
					String tokens[] = cell.split(Pattern.quote("+"));
					for (String token : tokens) {
						if (token.equals("S1")) { // Start pos
							this.startPositions[0] = new GridPoint2Static(col, row);
						} else if (token.equals("S2")) { // Start pos
							this.startPositions[1] = new GridPoint2Static(col, row);
						} else if (token.equals("S3")) { // Start pos
							this.startPositions[2] = new GridPoint2Static(col, row);
						} else if (token.equals("S4")) { // Start pos
							this.startPositions[3] = new GridPoint2Static(col, row);
						} else if (token.equals("F")) { // Floor
							// Do nothing
						} else if (token.equals("W")) { // Wall
							game.mapData.map[col][row].blocked = true;
							Wall wall = new Wall(game.ecs, "ftl/textures/ufo2_03.png", col, 0, row, false);
							game.ecs.addEntity(wall);
						} else if (token.equals("C")) { // Chasm
						} else if (token.equals("D1")) { // Door 1
							AbstractEntity door = FTLEntityFactory.createDoor(game.ecs, col, row, false);
							game.ecs.addEntity(door);
						} else if (token.equals("D2")) { // Door 2
							AbstractEntity door = FTLEntityFactory.createDoor(game.ecs, col, row, true);
							game.ecs.addEntity(door);
						} else if (token.equals("B")) { // Door 2
							AbstractEntity battery = FTLEntityFactory.createBattery(game.ecs, col, row);
							game.ecs.addEntity(battery);
						} else {
							throw new RuntimeException("Unknown cell type: " + token);
						}
					}
				}
				row++;
			}
		}

		Floor floor = new Floor(game.ecs, "ftl/textures/corridor.jpg", 0, 0, map_width, map_height, true);
		game.ecs.addEntity(floor);
	}


	@Override
	public void addSystems(BasicECS ecs) {
		//game.ecs.addSystem(new TRexAISystem(ecs, game));
	}


	@Override
	public void update() {
		//game.ecs.processSystem(TRexAISystem.class);

	}


}
