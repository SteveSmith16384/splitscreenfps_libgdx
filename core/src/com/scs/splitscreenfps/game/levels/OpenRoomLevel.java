package com.scs.splitscreenfps.game.levels;

import com.badlogic.gdx.math.GridPoint2;
import com.scs.basicecs.AbstractEntity;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.MapData;
import com.scs.splitscreenfps.game.data.MapSquare;

public class OpenRoomLevel extends AbstractLevel {

	private Game game;

	public OpenRoomLevel(Game _game) {
		game = _game;
	}


	@Override
	public void load() {
		for (int i=0 ; i<this.startPositions.length ;i++) {
			this.startPositions[i] = new GridPoint2(i, 0);
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

		AbstractEntity litter = game.entityFactory.createLitter(0, 2,  2);
		game.ecs.addEntity(litter);

		AbstractEntity litter_bin = game.entityFactory.createLitterBin(0, 4, 4);
		game.ecs.addEntity(litter_bin);
	}

}

