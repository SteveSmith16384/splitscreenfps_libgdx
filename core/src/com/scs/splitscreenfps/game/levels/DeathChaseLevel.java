package com.scs.splitscreenfps.game.levels;

import com.badlogic.gdx.Gdx;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.Settings;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.MapData;
import com.scs.splitscreenfps.game.data.MapSquare;
import com.scs.splitscreenfps.game.entities.Floor;
import com.scs.splitscreenfps.game.entities.PlayersAvatar_Car;
import com.scs.splitscreenfps.game.entities.deathchase.DeathchaseEntityFactory;
import com.scs.splitscreenfps.game.systems.VehicleMovementSystem;

import ssmith.lang.NumberFunctions;
import ssmith.libgdx.GridPoint2Static;

public class DeathChaseLevel extends AbstractLevel {

	public DeathChaseLevel(Game _game) {
		super(_game);
	}


	@Override
	public void loadAvatars() {
		for (int i=0 ; i<game.players.length ; i++) {
			game.players[i] = new PlayersAvatar_Car(game, i, game.viewports[i], game.inputs.get(i));
			game.ecs.addEntity(game.players[i]);
		}	
	}


	@Override
	public void setBackgroundColour() {
		if (Settings.DARKMODE) {
			Gdx.gl.glClearColor(0, 0, 0, 1);
		} else {
			Gdx.gl.glClearColor(.6f, .6f, 1, 1);
		}
	}


	@Override
	public void load() {
		this.map_width = 100;
		this.map_height = map_width;

		game.mapData = new MapData(map_width, map_height);

		for (int z=0 ; z<map_height ; z++) {
			for (int x=0 ; x<map_width ; x++) {
				game.mapData.map[x][z] = new MapSquare();
				if (z == 0 || x == 0 || z == map_height-1 || x == map_width-1) {
					game.mapData.map[x][z].blocked = true;
				} else {
					if (NumberFunctions.rnd(1,  4) == 1) {
						AbstractEntity tree = DeathchaseEntityFactory.createTree(game, x, z);
						game.ecs.addEntity(tree);
						game.mapData.map[x][z].blocked = true;
					} else {
						game.mapData.map[x][z].blocked = false;
					}
				}
			}
		}

		for (int i=0 ; i<this.startPositions.length ;i++) {
			this.startPositions[i] = new GridPoint2Static(i*10+1, i*10+1);
		}

		if (Settings.DARKMODE == false) {
			//game.ecs.addEntity(new Floor(game.ecs, "deathchase/grass.jpg", 1, 1, map_width-1, map_height-1, true));
			game.ecs.addEntity(new Floor(game.ecs, "sf/crate.png", 1, 1, map_width-1, map_height-1, true));
		}
	}


	@Override
	public void addSystems(BasicECS ecs) {
		ecs.addSystem(new VehicleMovementSystem(ecs));

	}


	@Override
	public void update() {
		game.ecs.processSystem(VehicleMovementSystem.class);
	}

}
