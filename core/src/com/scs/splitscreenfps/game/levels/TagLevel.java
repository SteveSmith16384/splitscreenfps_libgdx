package com.scs.splitscreenfps.game.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.Settings;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.MapData;
import com.scs.splitscreenfps.game.ViewportData;
import com.scs.splitscreenfps.game.data.MapSquare;
import com.scs.splitscreenfps.game.entities.Ceiling;
import com.scs.splitscreenfps.game.entities.GenericSquare;
import com.scs.splitscreenfps.game.entities.Wall;
import com.scs.splitscreenfps.game.systems.TagSystem;
import com.scs.splitscreenfps.mapgen.MazeGen1;

import ssmith.lang.NumberFunctions;

public class TagLevel extends AbstractLevel {

	private Texture slime;

	public TagLevel(Game _game) {
		super(_game);

		slime = new Texture(Gdx.files.internal("tag/slime.jpg"));
	}


	@Override
	public void load() {
		loadMapFromMazegen(game);
	}


	public void setBackgroundColour() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
	}


	private void loadMapFromMazegen(Game game) {
		this.map_width = 15 + game.players.length;
		if (Settings.SMALL_MAP) {
			this.map_width = 9;
		}
		this.map_height = map_width;

		game.mapData = new MapData(map_width, map_height);

		MazeGen1 maze = new MazeGen1(map_width, map_height, map_width/2);

		for (int z=-1 ; z<=map_height ; z++) {
			for (int x=-1 ; x<=map_width ; x++) {
				if (x == -1 || z == -1 || x == map_width || z == map_height) {
					Wall wall = new Wall(game.ecs, "sf/spacewall2.png", x, 0, z, false);
					game.ecs.addEntity(wall);

					// underground floor
					Wall floor = new Wall(game.ecs, "sf/floor3.jpg", x, -1, z, false);
					game.ecs.addEntity(floor);

					continue;
				}

				game.mapData.map[x][z] = new MapSquare();
				game.mapData.map[x][z].blocked = maze.map[x][z] == MazeGen1.WALL;
				if (game.mapData.map[x][z].blocked) {
					if (NumberFunctions.rnd(1,  3) == 1) {
						Ceiling ceiling = new Ceiling(game.ecs, "sf/corridor.jpg", x, z, 1, 1, false, 1f);
						game.ecs.addEntity(ceiling);
						// Don't draw floor!
					} else {
						Wall wall = new Wall(game.ecs, "sf/spacewall2.png", x, 0, z, false);
						game.ecs.addEntity(wall);

						// underground floor
						Wall floor = new Wall(game.ecs, "sf/floor3.jpg", x, -1, z, false);
						game.ecs.addEntity(floor);
					}
				} else {
					//Floor floor = new Floor(game.ecs, "", "sf/floor3.jpg", x, z, 1f, 1f);
					//game.ecs.addEntity(floor);

					// underground floor
					Wall floor = new Wall(game.ecs, "sf/floor3.jpg", x, -1, z, false);
					game.ecs.addEntity(floor);

					int rnd = NumberFunctions.rnd(1, 5);
					if (rnd == 1) {
						GenericSquare sq = new GenericSquare(game.ecs, x, z, "sf/damaged_floor2.png");
						game.ecs.addEntity(sq);
					} else if (rnd == 2) {
						//AbstractEntity door = game.entityFactory.createDoor(x, z, false);
						//game.ecs.addEntity(door);
					} else if (rnd == 3) {
						float offX = 0;//NumberFunctions.rndFloat(0, 0.5f);
						float offZ = 0;//NumberFunctions.rndFloat(0, 0.5f);
						AbstractEntity crate = game.entityFactory.createCrate(x+offX, z+offZ);
						game.ecs.addEntity(crate);
					}

					Ceiling ceiling = new Ceiling(game.ecs, "sf/corridor.jpg", x, z, 1, 1, false, 1f);
					game.ecs.addEntity(ceiling);
				}

			}
		}

		for (int i=0 ; i<this.startPositions.length ;i++) {
			this.startPositions[i] = game.mapData.getRandomFloorPos();
		}

		//game.ecs.addEntity(new Floor(game.ecs, "colours/white.png", 0, 0, map_width, map_height, false));
	}


	@Override
	public void addSystems(BasicECS ecs) {
		TagSystem tagSystem = new TagSystem(ecs, game);
		ecs.addSystem(tagSystem);
	}


	@Override
	public void update(MapData world) {
		game.ecs.processSystem(TagSystem.class);
	}


	@Override
	public void renderUI(SpriteBatch batch2d, BitmapFont font_white, ViewportData viewportData) {
		// Draw slime
		TagSystem tagSystem = (TagSystem)game.ecs.getSystem(TagSystem.class);
		if (tagSystem != null) {
			if (tagSystem.currentIt.id == game.players[game.currentViewId].id) {
				if (tagSystem.lastTagTime + TagSystem.TAG_INTERVAL > System.currentTimeMillis()) {
					batch2d.setColor(1, 1, 1 ,1);
				} else {
					batch2d.setColor(1, 1, 1, 0.5f);
				}
				//batch2d.draw(slime, viewportData.viewPos.x, viewportData.viewPos.y+viewportData.viewPos.height, viewportData.viewPos.width, -viewportData.viewPos.height);
				batch2d.draw(slime, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
				batch2d.setColor(1, 1, 1, 1f);
			}
		}

	}

}
