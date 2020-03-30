package com.scs.splitscreenfps.game.levels;

import com.badlogic.gdx.Gdx;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.MapData;
import com.scs.splitscreenfps.game.data.MapSquare;
import com.scs.splitscreenfps.game.entities.Floor;
import com.scs.splitscreenfps.game.entities.Wall;
import com.scs.splitscreenfps.mapgen.AbstractDungeon.SqType;
import com.scs.splitscreenfps.mapgen.DungeonGen1;

import ssmith.libgdx.GridPoint2Static;

public class DungeonLevel extends AbstractLevel {

	private DungeonGen1 maze;

	public DungeonLevel(Game _game) {
		super(_game);
	}

	@Override
	public void load() {
		loadMapFromDungeonGen(game);
		//loadMapFromFile("");
	}


	private void loadMapFromFile(String file) {
		String str = Gdx.files.internal(file).readString();
		String[] str2 = str.split("\n");
		int row = 0;
		for (String s : str2) {
			s = s.trim();
			if (s.length() > 0 && s.startsWith("#") == false) {
				String tokens[] = s.split("\t");
				for (int col=0 ; col<tokens.length ; col++) {
					String cell = tokens[col];
					if (cell == "F") { // Floor
					} else if (cell == "W") { // Wall
					} else if (cell == "C") { // Chasm
					} else if (cell == "D1") { // Door 1
					} else if (cell == "D2") { // Door 2
						
					} else {
						throw new RuntimeException("Unknown cell type: " + cell);
					}
				}
				row++;
			}
		}
	}


	private void loadMapFromDungeonGen(Game game) {
		this.map_width = 50;
		/*if (Settings.SMALL_MAP) {
			this.map_width = 9;
		}*/
		this.map_height = map_width;

		game.mapData = new MapData(map_width, map_height);

		maze = new DungeonGen1(map_width, 5, 10, 1);
		maze.showMap();

		for (int z=0 ; z<map_height ; z++) {
			for (int x=0 ; x<map_width ; x++) {
				game.mapData.map[x][z] = new MapSquare();
				game.mapData.map[x][z].blocked = maze.map[x][z] == SqType.WALL;
				if (game.mapData.map[x][z].blocked) {
					Wall wall = new Wall(game.ecs, "monstermaze/wall.png", x, 0, z, false);
					game.ecs.addEntity(wall);
				}
			}
		}

		for (int i=0 ; i<this.startPositions.size() ; i++) {
			GridPoint2Static pos = maze.centres.get(0);
			this.startPositions.add(pos);
		}

		game.ecs.addEntity(new Floor(game.ecs, "colours/white.png", 0, 0, map_width, map_height, false));
	}


	@Override
	public void addSystems(BasicECS ecs) {

	}

	@Override
	public void update() {

	}

}
