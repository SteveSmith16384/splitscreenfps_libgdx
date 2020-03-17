package com.scs.splitscreenfps.game.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.MapData;
import com.scs.splitscreenfps.game.data.MapSquare;
import com.scs.splitscreenfps.game.entities.EntityFactory;
import com.scs.splitscreenfps.game.entities.Floor;
import com.scs.splitscreenfps.game.entities.crazycarpark.CarParkEntityFactory;

import ssmith.libgdx.GridPoint2Static;

public class CarParkLevel extends AbstractLevel {

	public CarParkLevel(Game _game) {
		super(_game);
	}


	@Override
	public void setBackgroundColour() {
		//Gdx.gl.glClearColor(.6f, .6f, 1, 1);
		Gdx.gl.glClearColor(0, 0, 0, 1);
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
			this.startPositions[i] = new GridPoint2Static(i+1, i+1);
		}

		game.ecs.addEntity(new Floor(game.ecs, "farm/grass.jpg", 0, 0, map_width-1, map_height-1, true));

		AbstractEntity car = CarParkEntityFactory.createCar1(game, 3, 0, 0);
		game.ecs.addEntity(car);
		
		//AbstractEntity text3d = EntityFactory.create3DText_TEST(game.ecs, "A car!", new Vector3(0, 1, 0));
		//game.ecs.addEntity(text3d);
		

		//AbstractEntity car = CarParkEntityFactory.createAmbulance(game, 3, 0, 0);
		//game.ecs.addEntity(car);
}

	
	@Override
	public void addSystems(BasicECS ecs) {
		//ecs.addSystem(new WanderingAnimalSystem(ecs));

	}

	
	@Override
	public void update() {
		//game.ecs.processSystem(GrowCropsSystem.class);
	}

}
