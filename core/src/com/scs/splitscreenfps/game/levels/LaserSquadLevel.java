package com.scs.splitscreenfps.game.levels;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.scs.basicecs.AbstractEntity;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.World;
import com.scs.splitscreenfps.game.data.WorldSquare;
import com.scs.splitscreenfps.game.entities.Floor;
import com.scs.splitscreenfps.game.entities.GenericScenery;
import com.scs.splitscreenfps.game.entities.GenericWallScenery;
import com.scs.splitscreenfps.game.entities.Wall;
import com.scs.splitscreenfps.game.entities.androids.SlidingDoor;
import com.scs.splitscreenfps.game.player.weapons.PlayersLaserGun;

public class LaserSquadLevel extends AbstractLevel {

	public LaserSquadLevel(int difficulty) {
		super(difficulty);
	}


	@Override
	public void load(Game game) {
		loadMap(game);

		createWalls(game);
		
		game.player.setWeapon(new PlayersLaserGun("todo"));
	}


	private void loadMap(Game game) {
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
				} else if (x == 3 && z == 1) {
					GenericWallScenery s = new GenericWallScenery("VidScreen", "lasersquad/vidscreen.png", x, z, GenericWallScenery.Side.Back);
					game.ecs.addEntity(s);
				} else if (x == 3 && z == 2) {
					GenericScenery s = new GenericScenery("Barrel", "lasersquad/barrel.png", x, z, true);
					game.ecs.addEntity(s);
				} else if (x == 3 && z == 3) {
					GenericScenery s = new GenericScenery("GasCan", "lasersquad/gas_cannisters.png", x, z, true);
					game.ecs.addEntity(s);
				} else if (x == 1 && z == 2) {
					SlidingDoor door = new SlidingDoor(x, z, "lasersquad/interior_door.png");
					game.ecs.addEntity(door);
				}

				Game.world.world[x][z] = new WorldSquare();
				Game.world.world[x][z].blocked = type == World.WALL;
			}
		}
	}


	private void createWalls(Game game) {
		for (int y = 0; y < map_height; y++) {
			for (int x = 0; x < map_width; x++) {
				try {
					boolean block = Game.world.world[x][y].blocked;
					if (block) {
						game.ecs.addEntity(new Wall("lasersquad/moonbase_wall.png", x, y));
					}
				} catch (NullPointerException ex) {
					ex.printStackTrace();
				}
			}
		}

		game.ecs.addEntity(new Floor("lasersquad/moonbase_interior_floor.png", map_width, map_height, true));
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
		return "LSRSQUAD";
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
