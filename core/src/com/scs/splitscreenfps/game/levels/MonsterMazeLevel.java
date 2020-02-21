package com.scs.splitscreenfps.game.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.Settings;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.MapData;
import com.scs.splitscreenfps.game.data.MapSquare;
import com.scs.splitscreenfps.game.entities.Floor;
import com.scs.splitscreenfps.game.entities.GenericSquare;
import com.scs.splitscreenfps.game.entities.Wall;
import com.scs.splitscreenfps.game.entities.monstermaze.MonsterMazeEntityFactory;
import com.scs.splitscreenfps.game.entities.monstermaze.MonsterMazeExit;
import com.scs.splitscreenfps.game.entities.monstermaze.TRex;
import com.scs.splitscreenfps.game.systems.monstermaze.MonsterGrowlsSystem;
import com.scs.splitscreenfps.game.systems.monstermaze.MonsterMazeExitSystem;
import com.scs.splitscreenfps.game.systems.monstermaze.RegenKeySystem;
import com.scs.splitscreenfps.game.systems.monstermaze.TRexHarmsPlayerSystem;
import com.scs.splitscreenfps.mapgen.MazeGen1;

public class MonsterMazeLevel extends AbstractLevel {
	// todo - sometimes no exit, sometimes cant start!!
	private MazeGen1 maze;

	public MonsterMazeLevel(Game _game) {
		super(_game);
	}


	@Override
	public void load() {
		loadMapFromMazegen(game);
	}


	@Override
	public void loadAssets() {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/zx_spectrum-7.ttf"));

		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = Gdx.graphics.getHeight()/12;
		//Settings.p("Font size=" + parameter.size);
		game.font_small = generator.generateFont(parameter);

		parameter = new FreeTypeFontParameter();
		parameter.size = Gdx.graphics.getHeight()/6;
		//Settings.p("Font size=" + parameter.size);
		game.font_med = generator.generateFont(parameter);

		parameter = new FreeTypeFontParameter();
		parameter.size = Gdx.graphics.getHeight()/4;
		//Settings.p("Font size=" + parameter.size);
		game.font_large = generator.generateFont(parameter);

		generator.dispose(); // don't forget to dispose to avoid memory leaks!
	}


	@Override
	public void setBackgroundColour() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
	}


	private void loadMapFromMazegen(Game game) {
		if (Settings.prop.containsKey("mm.map_width") && Settings.prop.containsKey("mm.map_height")) {
			this.map_width = Integer.parseInt(Settings.prop.getProperty("mm.map_width"));
			this.map_height = Integer.parseInt(Settings.prop.getProperty("mm.map_height"));
		} else {
			this.map_height = 12 + game.players.length;
			if (Settings.SMALL_MAP) {
				this.map_height = 7;
			}
			this.map_width = map_height * 2;
		}

		game.mapData = new MapData(map_width, map_height);

		maze = new MazeGen1(map_width, map_height, map_width/2);

		for (int z=0 ; z<map_height ; z++) {
			for (int x=0 ; x<map_width ; x++) {
				game.mapData.map[x][z] = new MapSquare();
				game.mapData.map[x][z].blocked = maze.map[x][z] == MazeGen1.WALL;
				if (game.mapData.map[x][z].blocked) {
					Wall wall = new Wall(game.ecs, "monstermaze/wall.png", x, 0, z, false);
					game.ecs.addEntity(wall);
				}
			}
		}

		game.mapData.map[maze.start_pos.x][maze.start_pos.y].spawn_point = true;

		for (int i=0 ; i<this.startPositions.length ;i++) {
			this.startPositions[i] = maze.start_pos;
		}

		GenericSquare sq = new GenericSquare(game.ecs, maze.start_pos.x, maze.start_pos.y, "monstermaze/wall.png");
		game.ecs.addEntity(sq);

		TRex trex = new TRex(game, game.ecs, maze.middle_pos.x, maze.middle_pos.y);
		game.ecs.addEntity(trex);

		// The key gets regenerated by the Key system

		MonsterMazeExit exit = new MonsterMazeExit(game.ecs, maze.end_pos.x, maze.end_pos.y);
		game.ecs.addEntity(exit);

		game.ecs.addEntity(new Floor(game.ecs, "colours/white.png", 0, 0, map_width, map_height, false));
	}


	@Override
	public void addSystems(BasicECS ecs) {
		game.ecs.addSystem(new TRexHarmsPlayerSystem(game.ecs, game, this.startPositions[0].x, this.startPositions[0].y));
		game.ecs.addSystem(new MonsterMazeExitSystem(ecs, game));
		game.ecs.addSystem(new MonsterGrowlsSystem());
		game.ecs.addSystem(new RegenKeySystem(ecs, this));
	}


	@Override
	public void update() {
		game.ecs.processSystem(TRexHarmsPlayerSystem.class);
		game.ecs.processSystem(MonsterMazeExitSystem.class);
		game.ecs.processSystem(MonsterGrowlsSystem.class);
		game.ecs.processSystem(RegenKeySystem.class);

	}


	public void createKey() {
		AbstractEntity key = MonsterMazeEntityFactory.createKey(game.ecs, maze.middle_pos.x, maze.middle_pos.y);
		game.ecs.addEntity(key);		
	}

}
