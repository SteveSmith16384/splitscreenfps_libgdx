package com.scs.splitscreenfps.game.levels;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.scs.basicecs.AbstractEntity;
import com.scs.splitscreenfps.Maze;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.World;
import com.scs.splitscreenfps.game.data.WorldSquare;
import com.scs.splitscreenfps.game.entities.Floor;
import com.scs.splitscreenfps.game.entities.Wall;
import com.scs.splitscreenfps.game.entities.maziacs.Gold;
import com.scs.splitscreenfps.game.entities.maziacs.Maziac;
import com.scs.splitscreenfps.game.entities.maziacs.SwordPickup;
import com.scs.splitscreenfps.game.player.Player;
import com.scs.splitscreenfps.game.player.weapons.PlayersSword;

public class MaziacsLevel extends AbstractLevel {

	public MaziacsLevel(int difficulty) {
		super(difficulty);
	}


	@Override
	public void load(Game game) {
		//loadTestMap(game);
		loadMapFromMazegen(game);

		game.ecs.addEntity(new Floor("colours/white.png", map_width, map_height, false));
	}


	private void loadMapFromMazegen(Game game) {
		this.map_width = 20;
		this.map_height = 20;

		Game.world.world = new WorldSquare[map_width][map_height];

		Maze maze = new Maze(map_width, map_height, 10);

		this.playerStartMapX = maze.start_pos.x;
		this.playerStartMapY = maze.start_pos.y;

		for (int z=0 ; z<map_height ; z++) {
			for (int x=0 ; x<map_width ; x++) {
				Game.world.world[x][z] = new WorldSquare();
				Game.world.world[x][z].blocked = maze.map[x][z] == Maze.WALL;

				if (Game.world.world[x][z].blocked) {
					Wall wall = new Wall("colours/blue.png", x, z);
					game.ecs.addEntity(wall);
				}
			}
		}
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
					SwordPickup sword = new SwordPickup(x, z);
					game.ecs.addEntity(sword);
				} else if (x == 3 && z == 3) {
					Maziac m = new Maziac(x, z);
					game.ecs.addEntity(m);
				} else if (x == 3 && z == 3) {
					Gold gold = new Gold(x, z);
					game.ecs.addEntity(gold);
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
		if (collectable instanceof SwordPickup) {
			Player player = (Player)collector;
			player.setWeapon(new PlayersSword(true));
		}
	}


	@Override
	public void renderUI(SpriteBatch batch, BitmapFont font_white, BitmapFont font_black) {
	}


	@Override
	public String GetName() {
		return "MAZIACS";
	}


	@Override
	public String getInstructions() {
		return "";
	}


	@Override
	public String getMusicFilename() {
		return "todo";
	}
}
