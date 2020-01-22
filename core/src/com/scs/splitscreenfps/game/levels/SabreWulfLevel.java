package com.scs.splitscreenfps.game.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.scs.basicecs.AbstractEntity;
import com.scs.splitscreenfps.Maze;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.World;
import com.scs.splitscreenfps.game.data.WorldSquare;
import com.scs.splitscreenfps.game.entities.Wall;

public class SabreWulfLevel extends AbstractLevel {

	public SabreWulfLevel(int difficulty) {
		super(difficulty);
	}


	@Override
	public void load(Game game) {
		//loadMapFromImage(game);
		//loadTestMap(game);
		loadMapFromMazegen(game);

	}


	public void setBackgroundColour() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
	}


	private void loadMapFromMazegen(Game game) {
		this.map_width = 16 + (this.difficulty * 3);
		this.map_height = 16 + (this.difficulty * 3);

		Game.world.world = new WorldSquare[map_width][map_height];

		Maze maze = new Maze(map_width, map_height, 10);

		this.playerStartMapX = maze.start_pos.x;
		this.playerStartMapY = maze.start_pos.y;

		for (int z=0 ; z<map_height ; z++) {
			for (int x=0 ; x<map_width ; x++) {
				Game.world.world[x][z] = new WorldSquare();
				Game.world.world[x][z].blocked = maze.map[x][z] == Maze.WALL;
				if (Game.world.world[x][z].blocked) {
					Wall wall = new Wall("todo", x, z);
					game.ecs.addEntity(wall);
				}
			}
		}

	}

	@Override
	public void update(Game game, World world) {
	}


	@Override
	public void entityCollected(AbstractEntity collector, AbstractEntity collectable) {
	}


	@Override
	public void renderUI(SpriteBatch batch, BitmapFont font_white, BitmapFont font_black) {
	}


	@Override
	public String GetName() {
		return "SABREWULF";
	}


	@Override
	public String getInstructions() {
		return "Find the Amulet";
	}


	@Override
	public String getMusicFilename() {
		return ""; // todo
	}
}
