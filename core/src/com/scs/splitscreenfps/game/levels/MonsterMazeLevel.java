package com.scs.splitscreenfps.game.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.MapData;
import com.scs.splitscreenfps.game.data.MapSquare;
import com.scs.splitscreenfps.game.entities.Floor;
import com.scs.splitscreenfps.game.entities.Wall;
import com.scs.splitscreenfps.game.entities.monstermaze.MonsterMazeExit;
import com.scs.splitscreenfps.game.entities.monstermaze.TRex;
import com.scs.splitscreenfps.game.player.PlayersAvatar;
import com.scs.splitscreenfps.mapgen.MazeGen1;

import ssmith.lang.NumberFunctions;

public class MonsterMazeLevel extends AbstractLevel {

	private TRex trex;
	private Game game;

	public MonsterMazeLevel(Game _game) {
		super();
		
		game = _game;
	}


	@Override
	public void load() {
		loadMapFromMazegen(game);

	}


	public void setBackgroundColour() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
	}


	private void loadMapFromMazegen(Game game) {
		this.map_width = 16;
		this.map_height = 16;

		game.mapData = new MapData(map_width, map_height);//.map = new MapSquare[map_width][map_height];

		MazeGen1 maze = new MazeGen1(map_width, map_height, 10);

		for (int i=0 ; i<this.startPositions.length ;i++) {
			this.startPositions[i] = maze.start_pos;
		}

		for (int z=0 ; z<map_height ; z++) {
			for (int x=0 ; x<map_width ; x++) {
				game.mapData.map[x][z] = new MapSquare();
				game.mapData.map[x][z].blocked = maze.map[x][z] == MazeGen1.WALL;
				if (game.mapData.map[x][z].blocked) {
					Wall wall = new Wall(game.ecs, "monstermaze/wall.png", x, z, false);
					game.ecs.addEntity(wall);
				}
			}
		}

		PlayersAvatar target = game.players[NumberFunctions.rnd(0,  game.players.length-1)];
		trex = new TRex(game.ecs, maze.middle_pos.x, maze.middle_pos.y, target);
		game.ecs.addEntity(trex);

		MonsterMazeExit exit = new MonsterMazeExit(game.ecs, maze.end_pos.x, maze.end_pos.y);
		game.ecs.addEntity(exit);

		game.ecs.addEntity(new Floor(game.ecs, "colours/white.png", 0, 0, map_width, map_height, false));
	}

	/*
	private void loadTestMap(Game game) {
		this.map_width = 5;
		this.map_height = 5;

		Game.world.world = new WorldSquare[map_width][map_height];

		this.playerStartMapX = 1;
		this.playerStartMapY = 1;

		for (int z=0 ; z<map_height ; z++) {
			for (int x=0 ; x<map_width ; x++) {
				int type = World.NOTHING;
				Game.world.world[x][z] = new WorldSquare();
				if (x == 0 || z == 0 || x >= map_width-1 || z >= map_height-1) {
					type = World.WALL;
					Wall wall = new Wall("monstermaze/wall.png", x, z);
					game.ecs.addEntity(wall);
				} else if (x == 2 && z == 2) {
					type = World.WALL;
					Wall wall = new Wall("monstermaze/wall.png", x, z);
					game.ecs.addEntity(wall);
				} else if (x == 3 && z == 1) {
					trex = new TRex(x, z);
					game.ecs.addEntity(trex);
				} else if (x == 3 && z == 3) {
					MonsterMazeExit exit = new MonsterMazeExit(x, z);
					game.ecs.addEntity(exit);
				}

				Game.world.world[x][z].blocked = type == World.WALL;
			}
		}
	}
	 */


	@Override
	public void renderUI(SpriteBatch batch, BitmapFont font_white, BitmapFont font_black) {		
	}


}
