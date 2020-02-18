package com.scs.splitscreenfps.game.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.GridPoint2;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.MapData;
import com.scs.splitscreenfps.game.data.MapSquare;
import com.scs.splitscreenfps.game.entities.Floor;
import com.scs.splitscreenfps.game.entities.Wall;
import com.scs.splitscreenfps.mapgen.DungeonGen1;
import com.scs.splitscreenfps.mapgen.AbstractDungeon.SqType;

public class FTLLevel extends AbstractLevel {

	public FTLLevel(Game _game) {
		super(_game);
	}

	@Override
	public void load() {
		loadMapFromFile("");
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


	@Override
	public void addSystems(BasicECS ecs) {

	}

	@Override
	public void update() {

	}

}
