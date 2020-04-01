package com.scs.splitscreenfps.game.levels;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.regex.Pattern;

import com.badlogic.gdx.Gdx;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.Settings;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.MapData;
import com.scs.splitscreenfps.game.components.start.CanStartNewLevelComponent;
import com.scs.splitscreenfps.game.components.start.StartLevelExitComponent;
import com.scs.splitscreenfps.game.data.MapSquare;
import com.scs.splitscreenfps.game.entities.Floor;
import com.scs.splitscreenfps.game.entities.GenericModel;
import com.scs.splitscreenfps.game.entities.Wall;
import com.scs.splitscreenfps.game.entities.alientag.AlienTagEntityFactory;
import com.scs.splitscreenfps.game.entities.monstermaze.MonsterMazeExit;
import com.scs.splitscreenfps.game.entities.monstermaze.RandomFloor;
import com.scs.splitscreenfps.game.entities.towerdefence.TowerDefenceEntityFactory;
import com.scs.splitscreenfps.game.systems.start.StartSpecificLevelSystem;

import ssmith.libgdx.GridPoint2Static;

public class StartLevel extends AbstractLevel {

	public static Properties prop;

	public StartLevel(Game _game) {
		super(_game);

		prop = new Properties();
		try {
			prop.load(new FileInputStream("start/start_config.txt"));
		} catch (Exception e) {
			//e.printStackTrace();
		}

	}


	@Override
	public void loadAvatars() {
		super.loadAvatars();
		for (int i=0 ; i<game.players.length ; i++) {
			game.players[i].addComponent(new CanStartNewLevelComponent());
		}	
	}


	@Override
	public void load() {
		loadMap(game, "start/map1.csv");
	}


	@Override
	public void setBackgroundColour() {
		Gdx.gl.glClearColor(.6f, .6f, 1, 1);
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
						if (token.equals("1")) { // Grass
							Floor floor = new Floor(game.ecs, "stockcar/textures/grass.jpg", col, row, 1, 1, false);
							game.ecs.addEntity(floor);
						} else if (token.equals("2")) { // Grass and start pos
							Floor floor = new Floor(game.ecs, "stockcar/textures/grass.jpg", col, row, 1, 1, false);
							game.ecs.addEntity(floor);
							this.startPositions.add(new GridPoint2Static(col, row));
						} else if (token.equals("3")) { // MM wall
							Wall wall = new Wall(game.ecs, "monstermaze/wall.png", col, 0, row, false);
							game.ecs.addEntity(wall);
						} else if (token.equals("4")) { // MM floor
							Floor floor = new Floor(game.ecs, "colours/white.png", col, row, 1, 1, false);
							game.ecs.addEntity(floor);
							RandomFloor rndfloor = new RandomFloor(game.ecs, col, row);
							game.ecs.addEntity(rndfloor);
							
						} else if (token.equals("MMS")) { // MM start
							MonsterMazeExit exit = new MonsterMazeExit(game.ecs, col, row);
							exit.addComponent(new StartLevelExitComponent(Settings.MODE_MONSTER_MAZE));
							game.ecs.addEntity(exit);
							
						} else if (token.equals("9")) { // TD floor and turret
							Floor floor = new Floor(game.ecs, "towerdefence/textures/corridor.jpg", col, row, 1, 1, false);
							game.ecs.addEntity(floor);
							AbstractEntity turret = TowerDefenceEntityFactory.createTurret(game.ecs, col, row);
							game.ecs.addEntity(turret);
						} else if (token.equals("8")) { // TD floor
							Floor floor = new Floor(game.ecs, "towerdefence/textures/corridor.jpg", col, row, 1, 1, false);
							game.ecs.addEntity(floor);
						} else if (token.equals("7")) { // TD floor and altar
							Floor floor = new Floor(game.ecs, "towerdefence/textures/corridor.jpg", col, row, 1, 1, false);
							game.ecs.addEntity(floor);
							AbstractEntity altar = TowerDefenceEntityFactory.createAltar(game.ecs, col, row);
							altar.addComponent(new StartLevelExitComponent(Settings.MODE_TOWER_DEFENCE));
							game.ecs.addEntity(altar);
						} else if (token.equals("11")) { // Track edge
							Floor floor = new Floor(game.ecs, "stockcar/textures/track_edge.png", col, row, 1, 1, false);
							game.ecs.addEntity(floor);
						} else if (token.equals("10")) { // Track
							Floor floor = new Floor(game.ecs, "stockcar/textures/road2.png", col, row, 1, 1, false);
							game.ecs.addEntity(floor);
						} else if (token.equals("CAR")) { // Car
							GenericModel car = new GenericModel(game, game.ecs, "Car", "shared/models/kenney_car_kit/hatchbackSports.g3db", col, 0, row, .6f);
							car.addComponent(new StartLevelExitComponent(Settings.MODE_STOCK_CAR));
							game.ecs.addEntity(car);
							
						} else if (token.equals("6")) { // Tag floor
							Wall floor = new Wall(game.ecs, "tag/textures/floor3.jpg", col, -1, row, false);
							game.ecs.addEntity(floor);
						} else if (token.equals("5")) { // Tag wall
							Wall wall = new Wall(game.ecs, "tag/textures/spacewall2.png", col, 0, row, false);
							game.ecs.addEntity(wall);
						} else if (token.equals("ATS")) { // Tag start
							AbstractEntity crate = AlienTagEntityFactory.createCrate(game.ecs, col+.5f, row+.5f);
							crate.addComponent(new StartLevelExitComponent(Settings.MODE_ALIEN_TAG));
							game.ecs.addEntity(crate);

						} else {
							throw new RuntimeException("Unknown cell type: " + token);
						}
					}
				}
				row++;
			}
		}

		//Floor floor = new Floor(game.ecs, "ftl/textures/corridor.jpg", 0, 0, map_width, map_height, true);
		//game.ecs.addEntity(floor);
	}


	@Override
	public void addSystems(BasicECS ecs) {
		game.ecs.addSystem(new StartSpecificLevelSystem(ecs, game));
	}


	@Override
	public void update() {
		game.ecs.processSystem(StartSpecificLevelSystem.class);

	}


}
