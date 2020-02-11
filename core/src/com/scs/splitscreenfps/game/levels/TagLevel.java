package com.scs.splitscreenfps.game.levels;

import com.badlogic.gdx.Gdx;
import com.scs.basicecs.AbstractEntity;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.MapData;
import com.scs.splitscreenfps.game.data.MapSquare;
import com.scs.splitscreenfps.game.entities.Ceiling;
import com.scs.splitscreenfps.game.entities.Floor;
import com.scs.splitscreenfps.game.entities.GenericSquare;
import com.scs.splitscreenfps.game.entities.Wall;
import com.scs.splitscreenfps.mapgen.MazeGen1;

import ssmith.lang.NumberFunctions;

public class TagLevel extends AbstractLevel {

	private Game game;

	public TagLevel(Game _game) {
		super();

		game = _game;
	}


	@Override
	public void load() {
		loadMapFromMazegen(game);

	}


	public void setBackgroundColour() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
	}


	private void loadMapFromMazegen(Game game) {
		this.map_width = 15 + game.players.length;
		this.map_height = map_width;

		game.mapData = new MapData(map_width, map_height);

		MazeGen1 maze = new MazeGen1(map_width, map_height, 10);

		for (int i=0 ; i<this.startPositions.length ;i++) {
			this.startPositions[i] = maze.getStartPos();
		}

		for (int z=0 ; z<map_height ; z++) {
			for (int x=0 ; x<map_width ; x++) {
				game.mapData.map[x][z] = new MapSquare();
				game.mapData.map[x][z].blocked = maze.map[x][z] == MazeGen1.WALL;
				if (game.mapData.map[x][z].blocked) {
					if (NumberFunctions.rnd(1,  3) == 1) {
						Ceiling ceiling= new Ceiling(game.ecs, "sf/corridor.jpg", x, z, 1, 1, false, 1f);
						game.ecs.addEntity(ceiling);
					} else {
						Wall wall = new Wall(game.ecs, "sf/spacewall2.png", x, z, false);
						game.ecs.addEntity(wall);
					}
				} else {
					Floor floor = new Floor(game.ecs, "", "sf/floor3.jpg", x, z, 1f, 1f);
					game.ecs.addEntity(floor);

					int rnd = NumberFunctions.rnd(1,  5);
					if (rnd == 1) {
						GenericSquare sq = new GenericSquare(game.ecs, x, z, "sf/damaged_floor2.png");
						game.ecs.addEntity(sq);
					} else if (rnd == 2) {
						//AbstractEntity door = game.entityFactory.createDoor(x, z, false);
						//game.ecs.addEntity(door);
					} else if (rnd == 3) {
						AbstractEntity crate = game.entityFactory.createCrate(x, z);
						game.ecs.addEntity(crate);
					}

					Ceiling ceiling= new Ceiling(game.ecs, "sf/corridor.jpg", x, z, 1, 1, false, 1f);
					game.ecs.addEntity(ceiling);
				}
			}
		}

		//game.ecs.addEntity(new Floor(game.ecs, "colours/white.png", 0, 0, map_width, map_height, false));
	}


}
