package com.scs.splitscreenfps.game.levels;

import java.util.regex.Pattern;

import com.badlogic.gdx.Gdx;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.MapData;
import com.scs.splitscreenfps.game.data.MapSquare;
import com.scs.splitscreenfps.game.entities.Ceiling;
import com.scs.splitscreenfps.game.entities.EntityFactory;
import com.scs.splitscreenfps.game.entities.Floor;
import com.scs.splitscreenfps.game.entities.Wall;

import ssmith.libgdx.GridPoint2Static;

public class FTLLevel extends AbstractLevel {

	public FTLLevel(Game _game) {
		super(_game);
	}

	@Override
	public void load() {
		loadMapFromFile("ftl/map1.csv");
	}


	private void loadMapFromFile(String file) {
		String str = Gdx.files.internal(file).readString();
		String[] str2 = str.split("\n");

		this.map_width = str2[0].split("\t").length;
		this.map_height = str2.length;

		game.mapData = new MapData(map_width, map_height);

		Floor floor = new Floor(game.ecs, "sf/corridor.jpg", 0, 0, map_width, map_height, true);
		game.ecs.addEntity(floor);

		Ceiling ceiling = new Ceiling(game.ecs, "sf/corridor.jpg", 0, 0, map_width, map_height, true, 1.1f);
		game.ecs.addEntity(ceiling);

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
						if (token.equals("S")) { // Start pos
							for (int i=0 ; i<this.startPositions.length ;i++) {
								this.startPositions[i] = new GridPoint2Static(col, row);
							}

						} else if (token.equals("F")) { // Floor
							//Floor floor = new Floor(game.ecs, "colours/red.png", col, row, 1, 1, false);
							//game.ecs.addEntity(floor);
						} else if (token.equals("W")) { // Wall
							game.mapData.map[col][row].blocked = true;
							Wall wall = new Wall(game.ecs, "sf/ufo2_03.png", col, 0, row, false);
							game.ecs.addEntity(wall);
						} else if (token.equals("C")) { // Chasm
						} else if (token.equals("D1")) { // Door 1
							//Floor floor = new Floor(game.ecs, "colours/red.png", col, row, 1, 1, false);
							//game.ecs.addEntity(floor);

							AbstractEntity door = EntityFactory.createDoor(game.ecs, col, row, false);
							game.ecs.addEntity(door);

						} else if (token.equals("D2")) { // Door 2
							//Floor floor = new Floor(game.ecs, "colours/red.png", col, row, 1, 1, false);
							//game.ecs.addEntity(floor);

							AbstractEntity door = EntityFactory.createDoor(game.ecs, col, row, true);
							game.ecs.addEntity(door);

						} else {
							throw new RuntimeException("Unknown cell type: " + token);
						}
					}
				}
				row++;
			}
		}
	}


	@Override
	public void addSystems(BasicECS ecs) {

	}

	@Override
	public void update() {

	}

}
