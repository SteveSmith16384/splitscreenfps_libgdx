package com.scs.splitscreenfps.game.levels;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.Settings;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.MapData;
import com.scs.splitscreenfps.game.data.MapSquare;
import com.scs.splitscreenfps.game.entities.Floor;
import com.scs.splitscreenfps.game.entities.Wall;

import ssmith.libgdx.GridPoint2Static;

public class LoadMapDynamicallyLevel extends AbstractLevel {

	private static final String DATA_FILE = "leveldata/test_level.csv";
	private static final String ENAME = "TEST_";

	private long lastFileTime = -1;

	public LoadMapDynamicallyLevel(Game _game) {
		super(_game);
	}


	@Override
	public void load() {
		for (int i=0 ; i<this.game.players.length ;i++) {
			this.startPositions.add(new GridPoint2Static(i, 0));
		}

		this.map_width = 16;
		this.map_height = 16;

		game.mapData = new MapData(map_width, map_height);
		for (int z=0 ; z<map_height ; z++) {
			for (int x=0 ; x<map_width ; x++) {
				game.mapData.map[x][z] = new MapSquare(false);
				//Floor floor = new Floor("", "heart.png", x, z, 1f, 1f);
				//game.ecs.addEntity(floor);
			}
		}

		/*Wall wall = new Wall("heart.png", 10, 10, true);
		game.ecs.addEntity(wall);*/
		
		//ModelEntity spider = new ModelEntity("soldier", 3, 0, 3);
		//game.ecs.addEntity(spider);

	}


	@Override
	public void update() {
		readFile();
	}
	
	
	private void readFile() {
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
			s = s.trim();
			if (s.length() > 0 && s.startsWith("#") == false) {
				String tokens[] = s.split("\t");
				String name = ENAME + tokens[0];
				Iterator<AbstractEntity> it = game.ecs.getEntityIterator();
				while (it.hasNext()) {
					AbstractEntity entity = it.next();
					if (entity.name.equalsIgnoreCase(name)) {
						Settings.p("Replacing " + entity);
						entity.remove();
						break;
					}
				}

				int type = Integer.parseInt(tokens[1]);
				switch (type) { 
				case 1: // Block
				{
					float x = Float.parseFloat(tokens[2]);
					float y = Float.parseFloat(tokens[3]);
					float z = Float.parseFloat(tokens[4]);
					float w = Float.parseFloat(tokens[5]);
					float h = Float.parseFloat(tokens[6]);
					float d = Float.parseFloat(tokens[7]);
					String tex = tokens[8];
					Wall wall = new Wall(game.ecs, name, tex, x, y, z, w, h, d, true);
					game.ecs.addEntity(wall);
					break;
				}
				case 2: // Floor
				{
					float x = Float.parseFloat(tokens[2]);
					float z = Float.parseFloat(tokens[3]);
					float w = Float.parseFloat(tokens[4]);
					float d = Float.parseFloat(tokens[5]);
					String tex = tokens[6];
					Floor floor = new Floor(game.ecs,name, tex, x, z, w, d);
					game.ecs.addEntity(floor);
					break;
				}
				case 3: // Model
				{
					/*float x = Float.parseFloat(tokens[2]);
					float y = Float.parseFloat(tokens[3]);
					float z = Float.parseFloat(tokens[4]);
					String tex = tokens[6];
					Floor wall = new Floor(name, tex, x, z, w, d);
					game.ecs.addEntity(wall);*/
					break;
				}
				}
			}
		}
	}


	@Override
	public void addSystems(BasicECS ecs) {
		
	}

}
