package com.scs.splitscreenfps.game.levels;

import com.badlogic.gdx.Gdx;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.MapData;
import com.scs.splitscreenfps.game.data.MapSquare;
import com.scs.splitscreenfps.game.entities.Floor;
import com.scs.splitscreenfps.game.entities.farm.Cow;
import com.scs.splitscreenfps.game.entities.farm.FarmEntityFactory;
import com.scs.splitscreenfps.game.systems.farm.GrowCropsSystem;
import com.scs.splitscreenfps.game.systems.farm.WanderingAnimalSystem;

import ssmith.libgdx.GridPoint2Static;

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
		this.map_width = 10;
		this.map_height = map_width;

		game.mapData = new MapData(map_width, map_height);

		for (int z=0 ; z<map_height ; z++) {
			for (int x=0 ; x<map_width ; x++) {
				game.mapData.map[x][z] = new MapSquare();
				if (z == 0 || x == 0 || z == map_height-1 || x == map_width-1) {
					game.mapData.map[x][z].blocked = true;
				} else {
					game.mapData.map[x][z].blocked = false;
				}
			}
		}

		for (int i=0 ; i<this.startPositions.length ;i++) {
			this.startPositions[i] = new GridPoint2Static(i, i);
		}

		AbstractEntity plant = FarmEntityFactory.createPlant(game, 3, 3);
		game.ecs.addEntity(plant);

		game.ecs.addEntity(new Cow(game, game.ecs, 5, 5));

		game.ecs.addEntity(new Floor(game.ecs, "farm/grass.jpg", 0, 0, map_width, map_height, true));
	}

	
	@Override
	public void addSystems(BasicECS ecs) {
		ecs.addSystem(new GrowCropsSystem(ecs));
		ecs.addSystem(new WanderingAnimalSystem(ecs));

	}

	
	@Override
	public void update() {
		game.ecs.processSystem(GrowCropsSystem.class);
		game.ecs.processSystem(WanderingAnimalSystem.class);
	}

}
