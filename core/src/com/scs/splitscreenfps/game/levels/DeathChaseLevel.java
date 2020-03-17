package com.scs.splitscreenfps.game.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.Settings;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.MapData;
import com.scs.splitscreenfps.game.components.CanRespawnComponent;
import com.scs.splitscreenfps.game.data.MapSquare;
import com.scs.splitscreenfps.game.entities.Floor;
import com.scs.splitscreenfps.game.entities.PlayersAvatar_Car;
import com.scs.splitscreenfps.game.entities.Wall;
import com.scs.splitscreenfps.game.entities.deathchase.DeathchaseEntityFactory;
import com.scs.splitscreenfps.game.systems.VehicleMovementSystem;
import com.scs.splitscreenfps.game.systems.deathchase.DeathchaseCrashSystem;

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
		this.map_width = 30;
		this.map_height = map_width;

		game.mapData = new MapData(map_width, map_height);

		for (int z=0 ; z<map_height ; z++) {
			for (int x=0 ; x<map_width ; x++) {
				game.mapData.map[x][z] = new MapSquare();
				if (z == 0 || x == 0 || z == map_height-1 || x == map_width-1) {
					game.mapData.map[x][z].blocked = true;
					if (Settings.DARKMODE == false) {
						Wall wall = new Wall(game.ecs, "deathchase/ForestTrees1.png", x, 0, z, false);
						game.ecs.addEntity(wall);
					}
				} else {
					game.mapData.map[x][z].blocked = false;
					if (NumberFunctions.rnd(1,  10) == 1) {
						if (z >= 2 && x >= 2 && z <= map_height-3 && x <= map_width-3) {
							if (Settings.DARKMODE == false) {
								//AbstractEntity tree = DeathchaseEntityFactory.createTree(game, x, z);
								//game.ecs.addEntity(tree);
							}
						}
					} else {
						if (NumberFunctions.rnd(1,  10) == 1) {
							AbstractEntity weed = DeathchaseEntityFactory.createWeed(game, x, z);
							game.ecs.addEntity(weed);
						}
					}
				}
			}
		}

		this.startPositions[0] = new GridPoint2Static(1, 1);
		this.startPositions[1] = new GridPoint2Static(map_width-2, map_height-2);
		this.startPositions[2] = new GridPoint2Static(map_width-2, 1);
		this.startPositions[3] = new GridPoint2Static(1, map_height-2);

		for (int i=0 ; i<this.game.players.length ; i++) {
			game.players[i].addComponent(new CanRespawnComponent(new Vector3(this.startPositions[i].x+.5f, Settings.PLAYER_HEIGHT/2, this.startPositions[i].y+0.5f)));
		}

		if (Settings.DARKMODE == false) {
			game.ecs.addEntity(new Floor(game.ecs, "deathchase/grass.jpg", 1, 1, map_width-1, map_height-1, true));
		}
	}


	@Override
	public void addSystems(BasicECS ecs) {
		ecs.addSystem(new VehicleMovementSystem(ecs, .0004f));
		ecs.addSystem(new DeathchaseCrashSystem(ecs, game));
	}


	@Override
	public void update() {
		game.ecs.processSystem(VehicleMovementSystem.class);
		game.ecs.processSystem(DeathchaseCrashSystem.class);
	}

}
