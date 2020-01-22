package com.scs.splitscreenfps.game.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.scs.basicecs.AbstractEntity;
import com.scs.splitscreenfps.Maze;
import com.scs.splitscreenfps.Settings;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.World;
import com.scs.splitscreenfps.game.data.WorldSquare;
import com.scs.splitscreenfps.game.entities.Floor;
import com.scs.splitscreenfps.game.entities.Wall;
import com.scs.splitscreenfps.game.entities.gulpman.Cherry;
import com.scs.splitscreenfps.game.entities.gulpman.GulpmanNasty;

public class GulpmanLevel extends AbstractLevel {

	private int num_cherries = 0;

	public GulpmanLevel(int difficulty) {
		super(difficulty);
	}


	@Override
	public void load(Game game) {
		//loadTestMap(game);
		loadMapFromMazegen(game);

		game.ecs.addEntity(new Floor("colours/cyan.png", map_width, map_height, false));
		//game.ecs.addEntity(new Ceiling("colours/cyan.png", map_width, map_height));

		//game.player.setWeapon(new PlayersLaserGun("gulpman/lasergun1.png"));
	}


	private void loadMapFromMazegen(Game game) {
		this.map_width = 10 + (this.difficulty * 3);
		this.map_height = 10 + (this.difficulty * 3);

		Game.world.world = new WorldSquare[map_width][map_height];

		Maze maze = new Maze(map_width, map_height, 5);

		this.playerStartMapX = maze.start_pos.x;
		this.playerStartMapY = maze.start_pos.y;

		for (int z=0 ; z<map_height ; z++) {
			for (int x=0 ; x<map_width ; x++) {
				Game.world.world[x][z] = new WorldSquare();
				Game.world.world[x][z].blocked = maze.map[x][z] == Maze.WALL;

				if (Game.world.world[x][z].blocked) {
					Wall wall = new Wall("colours/blue.png", x, z);
					game.ecs.addEntity(wall);
				} else {
					Cherry ch = new Cherry(x, z);
					game.ecs.addEntity(ch);
					this.num_cherries++;
				}
			}
		}
		
		GulpmanNasty n = new GulpmanNasty(maze.middle_pos.x, maze.middle_pos.y);
		game.ecs.addEntity(n);

		GulpmanNasty n2 = new GulpmanNasty(maze.end_pos.x, maze.end_pos.y);
		game.ecs.addEntity(n2);


	}


	public void setBackgroundColour() {
		Gdx.gl.glClearColor(0, 1, 1, 1);
	}


	private void loadTestMap(Game game) {
		this.map_width = 5;
		this.map_height = 5;

		Game.world.world = new WorldSquare[map_width][map_height];

		this.playerStartMapX = 1;
		this.playerStartMapY = 1;

		for (int z=0 ; z<map_height ; z++) {
			for (int x=0 ; x<map_width ; x++) {
				int type = World.NOTHING;
				if (x == 0 || z == 0 || x >= map_width-1 || z >= map_height-1) {
					type = World.WALL;
				} else if (x == 2 && z == 2) {
					type = World.WALL;
				} else if (x == 3 && z == 3) {
					GulpmanNasty n = new GulpmanNasty(x, z);
					game.ecs.addEntity(n);
				} else {
					Cherry ch = new Cherry(x, z);
					game.ecs.addEntity(ch);
					this.num_cherries++;
				}

				Game.world.world[x][z] = new WorldSquare();
				Game.world.world[x][z].blocked = type == World.WALL;
			}
		}
		createWalls(game);
	}


	private void createWalls(Game game) {
		for (int y = 0; y < map_height; y++) {
			for (int x = 0; x < map_width; x++) {
				try {
					boolean block = Game.world.world[x][y].blocked;
					if (block) {
						game.ecs.addEntity(new Wall("colours/blue.png", x, y));
					}
				} catch (NullPointerException ex) {
					ex.printStackTrace();
				}
			}
		}

	}


	@Override
	public void update(Game game, World world) {
	}


	@Override
	public void entityCollected(AbstractEntity collector, AbstractEntity collectable) {
		if (collectable instanceof Cherry) {
			this.num_cherries--;
			if (this.num_cherries <= 0) {
				Game.levelComplete = true;
			}
		}
	}


	@Override
	public void renderUI(SpriteBatch batch, BitmapFont font_white, BitmapFont font_black) {
		font_white.draw(batch, num_cherries + " remaining", 10, Settings.WINDOW_HEIGHT_PIXELS-40);
	}


	@Override
	public String GetName() {
		return "GULPMAN";
	}


	@Override
	public String getInstructions() {
		return "Collect all the Cherries";
	}


	@Override
	public String getMusicFilename() {
		return "audio/music/Thrust Sequence.mp3";
	}

}
