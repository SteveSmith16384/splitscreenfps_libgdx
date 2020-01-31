package com.scs.splitscreenfps.game.levels;

import com.badlogic.gdx.math.GridPoint2;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.MapData;
import com.scs.splitscreenfps.game.data.MapSquare;
import com.scs.splitscreenfps.game.entities.Wall;

public class TestLevel extends AbstractLevel {

	private Game game;

	public TestLevel(Game _game) {
		game = _game;
	}
	

	@Override
	public void load() {
		for (int i=0 ; i<this.startPositions.length ;i++) {
			this.startPositions[i] = new GridPoint2(2, 2);
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


}
