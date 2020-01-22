package com.scs.splitscreenfps.game.levels;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.GridPoint2;
import com.scs.basicecs.AbstractEntity;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.World;
import com.scs.splitscreenfps.game.components.PositionData;
import com.scs.splitscreenfps.game.data.WorldSquare;
import com.scs.splitscreenfps.game.entities.GenericSquare;

public class PippoLevel extends AbstractLevel {

	private int MAP[][] = {
			{1,1,1,1,1,1,1,1,1},
			{1,0,0,0,0,0,0,0,1},
			{1,0,1,0,0,0,1,0,1},
			{1,0,0,0,0,0,0,0,1},
			{1,0,1,0,0,0,1,0,1},
			{1,0,0,0,0,0,0,0,1},
			{1,1,1,1,1,1,1,1,1}
	};

	private boolean[][] square_covered;

	public PippoLevel(int difficulty) {
		super(difficulty);
	}


	@Override
	public void load(Game game) {
		loadMap(game);
	}


	private void loadMap(Game game) {
		this.map_width = 9;
		this.map_height = 7;

		this.playerStartMapX = 1;
		this.playerStartMapY = 1;

		square_covered = new boolean[map_width][map_height];
		Game.world.world = new WorldSquare[map_width][map_height];

		for (int z=0 ; z<map_height ; z++) {
			for (int x=0 ; x<map_width ; x++) {
				Game.world.world[x][z] = new WorldSquare();
				if (MAP[z][x] == 1) {
					Game.world.world[x][z].blocked = true;
				} else {
					GenericSquare trail = new GenericSquare(x, z, "colours/yellow.png");
					game.ecs.addEntity(trail);
					//Game.world.world[x][z].wall = trail;
				}
				/*if (x == 0 || z == 0 || x >= map_width-1 || z >= map_height-1) {
					Game.world.world[x][z].blocked = true;
				} else {
					GenericSquare trail = new GenericSquare(x, z, "colours/yellow.png");
					game.ecs.addEntity(trail);
					Game.world.world[x][z].wall = trail;
				}*/
			}
		}
	}


	@Override
	public void entityCollected(AbstractEntity collector, AbstractEntity collectable) {
	}


	@Override
	public void renderUI(SpriteBatch batch, BitmapFont font_white, BitmapFont font_black) {
	}


	@Override
	public void update(Game game, World world) {
		// Player leave a path
		PositionData posData = (PositionData)game.player.getComponent(PositionData.class);
		GridPoint2 mapPos = posData.getMapPos();
		if (square_covered[mapPos.x][mapPos.y] == false) {
			GenericSquare trail = new GenericSquare(mapPos.x, mapPos.y, "colours/green.png");
			game.ecs.addEntity(trail);
			Game.world.world[mapPos.x][mapPos.y].wall = trail;

			square_covered[mapPos.x][mapPos.y] = true;
			checkIfLevelComplete();
		}
	}

	
	private void checkIfLevelComplete() {
		for (int z=0 ; z<map_height ; z++) {
			for (int x=0 ; x<map_width ; x++) {
				if (MAP[z][x] == 0 && this.square_covered[x][z] == false) {
					return;
				}
			}
		}
		Game.levelComplete = true;
	}

	@Override
	public String GetName() {
		return "PIPPO";
	}


	@Override
	public String getInstructions() {
		return "";
	}


	@Override
	public String getMusicFilename() {
		return "audio/music/Red Doors 2.0 (GameClosure Edition).mp3"; // todo - change
	}

}
