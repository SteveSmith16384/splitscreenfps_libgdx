package com.scs.splitscreenfps.game.levels;

import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.MapData;
import com.scs.splitscreenfps.game.data.MapSquare;
import com.scs.splitscreenfps.game.entities.Floor;
import com.scs.splitscreenfps.game.entities.litter.LitterEntityFactory;

import ssmith.libgdx.GridPoint2Static;

public class OpenRoomLevel extends AbstractLevel {

	public OpenRoomLevel(Game _game) {
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
				Floor floor = new Floor(game.ecs, "", "sf/floor0041.png", x, z, 1f, 1f);
				game.ecs.addEntity(floor);
			}
		}

		/*Wall wall = new Wall("heart.png", 10, 10, true);
		game.ecs.addEntity(wall);*/

		//ModelEntity spider = new ModelEntity("soldier", 3, 0, 3);
		//game.ecs.addEntity(spider);

		AbstractEntity litter = LitterEntityFactory.createLitter(game.ecs, 0, 2,  2);
		game.ecs.addEntity(litter);

		AbstractEntity litter_bin = LitterEntityFactory.createLitterBin(game.ecs, 0, 4, 4);
		game.ecs.addEntity(litter_bin);
	}


	@Override
	public void addSystems(BasicECS ecs) {
		
	}


	@Override
	public void update() {
		
	}

}

