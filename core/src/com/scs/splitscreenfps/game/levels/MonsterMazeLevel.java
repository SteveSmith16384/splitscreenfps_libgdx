package com.scs.splitscreenfps.game.levels;

import com.badlogic.gdx.Gdx;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.Settings;
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

	public MonsterMazeLevel(Game _game) {
		super(_game);
	}


	@Override
	public void load() {
		loadMapFromMazegen(game);

	}


	public void setBackgroundColour() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
	}


	private void loadMapFromMazegen(Game game) {
		this.map_width = 15 + game.players.length;
		if (Settings.SMALL_MAP) {
			this.map_width = 9;
		}
		this.map_height = map_width;

		game.mapData = new MapData(map_width, map_height);

		MazeGen1 maze = new MazeGen1(map_width, map_height, map_width/2);

		for (int i=0 ; i<this.startPositions.length ;i++) {
			this.startPositions[i] = maze.getStartPos();
		}

		for (int z=0 ; z<map_height ; z++) {
			for (int x=0 ; x<map_width ; x++) {
				game.mapData.map[x][z] = new MapSquare();
				game.mapData.map[x][z].blocked = maze.map[x][z] == MazeGen1.WALL;
				if (game.mapData.map[x][z].blocked) {
					Wall wall = new Wall(game.ecs, "monstermaze/wall.png", x, 0, z, false);
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


	@Override
	public void addSystems(BasicECS ecs) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void update(MapData world) {
		
	}

}
