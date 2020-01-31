package com.scs.splitscreenfps.game.levels;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.GridPoint2;
import com.scs.basicecs.AbstractEntity;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.MapData;
import com.scs.splitscreenfps.game.data.MapSquare;
import com.scs.splitscreenfps.game.entities.Wall;

public class TestLevel extends AbstractLevel {

	private static final String DATA_FILE = "leveldata/test_level.csv";
	private static final String ENAME = "TEST_";

	private Game game;
	private long lastFileTime = -1;

	public TestLevel(Game _game) {
		game = _game;
	}


	@Override
	public void load() {
		for (int i=0 ; i<this.startPositions.length ;i++) {
			this.startPositions[i] = new GridPoint2(0, 0);
		}


		this.map_width = 16;
		this.map_height = 16;

		game.mapData = new MapData(map_width, map_height);//.map = new MapSquare[map_width][map_height];
		for (int z=0 ; z<map_height ; z++) {
			for (int x=0 ; x<map_width ; x++) {
				game.mapData.map[x][z] = new MapSquare(false);
			}
		}

		Wall wall = new Wall("heart.png", 10, 10, true);
		game.ecs.addEntity(wall);

	}


	@Override
	public void update(Game game, MapData world) {
		//String s = Gdx.files.getLocalStoragePath();
		FileHandle file = Gdx.files.internal("../../" + DATA_FILE);
		if (file.exists()) {
			long lm = file.lastModified();
			if (lm > this.lastFileTime) {
				this.lastFileTime = lm;
				loadFile(file);
			}
		}
	}


	private void loadFile(FileHandle file) {
		String str = file.readString();
		String[] str2 = str.split("\n");
		for (String s : str2) {
			String tokens[] = s.split("\t");
			String name = ENAME + tokens[0];
			float x = Float.parseFloat(tokens[1]);
			float y = Float.parseFloat(tokens[2]);
			float z = Float.parseFloat(tokens[3]);
			float w = Float.parseFloat(tokens[4]);
			float h = Float.parseFloat(tokens[5]);
			float d = Float.parseFloat(tokens[6]);
			
			Iterator<AbstractEntity> it = game.ecs.getIterator();
			while (it.hasNext()) {
				AbstractEntity entity = it.next();
				if (entity.name.equalsIgnoreCase(name)) {
					entity.remove();
					break;
				}
			}
			
			Wall wall = new Wall(name, "unit_highlighter_nw.png", x, y, z, w, h, d, true);
			game.ecs.addEntity(wall);
		}
	}


}
