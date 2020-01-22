package com.scs.splitscreenfps.game.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.scs.basicecs.AbstractEntity;
import com.scs.splitscreenfps.Maze;
import com.scs.splitscreenfps.Settings;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.World;
import com.scs.splitscreenfps.game.components.HasAI;
import com.scs.splitscreenfps.game.components.PositionData;
import com.scs.splitscreenfps.game.data.WorldSquare;
import com.scs.splitscreenfps.game.entities.Floor;
import com.scs.splitscreenfps.game.entities.Wall;
import com.scs.splitscreenfps.game.entities.monstermaze.MonsterMazeExit;
import com.scs.splitscreenfps.game.entities.monstermaze.TRex;

public class MonsterMazeLevel extends AbstractLevel {

	private TRex trex;
	private String trex_msg = "REX LIES IN WAIT";
	private boolean has_seen = false;
	private float next_check = 0;

	public MonsterMazeLevel(int difficulty) {
		super(difficulty);
	}


	@Override
	public void load(Game game) {
		//loadMapFromImage(game);
		//loadTestMap(game);
		loadMapFromMazegen(game);

	}


	public void setBackgroundColour() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
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
					Wall wall = new Wall("monstermaze/wall.png", x, z);
					game.ecs.addEntity(wall);
				}
			}
		}

		trex = new TRex(maze.middle_pos.x, maze.middle_pos.y);
		game.ecs.addEntity(trex);

		MonsterMazeExit exit = new MonsterMazeExit(maze.end_pos.x, maze.end_pos.y);
		game.ecs.addEntity(exit);

		game.ecs.addEntity(new Floor("colours/white.png", map_width, map_height, false));
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
	public void update(Game game, World world) {
		if (next_check > 0) {
			next_check -= Gdx.graphics.getDeltaTime();
			return;
		}
		next_check = 3;

		// left to do - ", or RUN HE IS BEHIND YOU"
		PositionData trexPos = (PositionData)trex.getComponent(PositionData.class);
		PositionData playerPos = (PositionData)game.player.getComponent(PositionData.class);
		float dist = trexPos.position.dst(playerPos.position);
		if (dist < Game.UNIT*2) {
			Game.audio.play("beepfx_samples/58_grr.wav");
			trex_msg = "RUN HE IS BESIDE YOU";
			return;
		}
		HasAI ai = (HasAI)trex.getComponent(HasAI.class);
		if (ai.can_see_player) {
			Game.audio.play("beepfx_samples/58_grr.wav");

			trex_msg = "REX HAS SEEN YOU";
			has_seen = true;
			return;
		}

		if (dist < Game.UNIT*5) {
			trex_msg = "FOOTSTEPS APPROACHING";
			return;
		}

		if (has_seen) {
			trex_msg = "HE IS HUNTING FOR YOU";
			return;
		}
	}


	@Override
	public void entityCollected(AbstractEntity collector, AbstractEntity collectable) {
	}


	@Override
	public void renderUI(SpriteBatch batch, BitmapFont font_white, BitmapFont font_black) {		
		font_black.draw(batch, trex_msg, 10-1, Settings.WINDOW_HEIGHT_PIXELS-40);
		font_black.draw(batch, trex_msg, 10, Settings.WINDOW_HEIGHT_PIXELS-40-1);
		font_black.draw(batch, trex_msg, 10+1, Settings.WINDOW_HEIGHT_PIXELS-40);
		font_black.draw(batch, trex_msg, 10, Settings.WINDOW_HEIGHT_PIXELS-40+1);

		font_white.draw(batch, trex_msg, 10, Settings.WINDOW_HEIGHT_PIXELS-40);
}


	@Override
	public String GetName() {
		return "3D MONSTER MAZE";
	}


	@Override
	public String getInstructions() {
		return "Find the exit";
	}


	@Override
	public String getMusicFilename() {
		return "";
	}
}
