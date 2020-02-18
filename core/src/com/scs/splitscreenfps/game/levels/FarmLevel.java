package com.scs.splitscreenfps.game.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.GridPoint2;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.Settings;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.MapData;
import com.scs.splitscreenfps.game.data.MapSquare;
import com.scs.splitscreenfps.game.entities.Floor;
import com.scs.splitscreenfps.game.entities.Wall;
import com.scs.splitscreenfps.game.entities.farm.FarmEntityFactory;
import com.scs.splitscreenfps.game.systems.farm.GrowCropsSystem;
import com.scs.splitscreenfps.mapgen.MazeGen1;

public class FarmLevel extends AbstractLevel {

	public FarmLevel(Game _game) {
		super(_game);
	}


	@Override
	public void setBackgroundColour() {
		Gdx.gl.glClearColor(.6f, .6f, 1, 1);
	}


	@Override
	public void load() {
		this.map_width = 15 + game.players.length;
		if (Settings.SMALL_MAP) {
			this.map_width = 9;
		}
		this.map_height = map_width;

		game.mapData = new MapData(map_width, map_height);

		for (int z=0 ; z<map_height ; z++) {
			for (int x=0 ; x<map_width ; x++) {
				game.mapData.map[x][z] = new MapSquare();
				game.mapData.map[x][z].blocked = false;;
			}
		}

		for (int i=0 ; i<this.startPositions.length ;i++) {
			this.startPositions[i] = new GridPoint2(1, 1);
		}

		AbstractEntity plant = FarmEntityFactory.createPlant(game, 3, 3);
		game.ecs.addEntity(plant);

		game.ecs.addEntity(new Floor(game.ecs, "farm/grass.png", 0, 0, map_width, map_height, true));
	}

	
	@Override
	public void addSystems(BasicECS ecs) {
		ecs.addSystem(new GrowCropsSystem(ecs));

	}

	
	@Override
	public void update() {
		game.ecs.processSystem(GrowCropsSystem.class);
	}

}
