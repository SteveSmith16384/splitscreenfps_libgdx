package com.scs.splitscreenfps.game.levels;

import com.badlogic.gdx.Gdx;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.Settings;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.MapData;
import com.scs.splitscreenfps.game.data.MapSquare;
import com.scs.splitscreenfps.game.entities.Floor;
import com.scs.splitscreenfps.game.entities.funnyfarm.FunnyFarmEntityFactory;
import com.scs.splitscreenfps.game.systems.farm.GrowCropsSystem;
import com.scs.splitscreenfps.game.systems.farm.WanderingAnimalSystem;

import ssmith.libgdx.GridPoint2Static;

public class FunnyFarmLevel extends AbstractLevel {

	public FunnyFarmLevel(Game _game) {
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

		for (int i=0 ; i<this.startPositions.size() ;i++) {
			this.startPositions.add(new GridPoint2Static(i+1, i+1));
		}

		AbstractEntity plant = FunnyFarmEntityFactory.createPlant(game, 3, 1);
		game.ecs.addEntity(plant);

		//game.ecs.addEntity(new Cow(game, game.ecs, map_width-2, map_height-2));

		game.ecs.addEntity(new Floor(game.ecs, "farm/grass.jpg", 1, 1, map_width-1, map_height-1, true));
		
		if (Settings.TEST_MODEL) {
			//AbstractEntity model = EntityFactory.createModel(game, "Test", "ftl/models/space-kit-1.0/Models/consoleScreen.g3db", 1, 0, 1, .15f);
			//AbstractEntity model = EntityFactory.createModel(game, "ftl/models/space-kit-1.0/Models/spaceCraft5.g3db", 3, 0, 1, 10f);
			
			//AbstractEntity model = EntityFactory.createModel(game, "ftl/models/space-kit-1.0/Models/consoleScreen.obj", 2, 0, 2, .1f);
			//AbstractEntity model = EntityFactory.createModel(game, "ftl/models/space-kit-1.0/Models/spaceCraft6.obj", 2, 0, 1, .1f);
			//AbstractEntity model = EntityFactory.createModel(game, "ftl/models/space-kit-1.0/Models/spaceCraft5b.obj", 2, 0, 1, .5f);
			
			//AbstractEntity model = EntityFactory.createModel(game, "Track", "racing/racingkit/obj format/roadCurved.obj", 2, -2, 2, .1f);
			//game.ecs.addEntity(model);
		}
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

	
	public void renderHelp() {
		
	}
	

}
