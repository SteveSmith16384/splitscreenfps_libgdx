package com.scs.splitscreenfps.game.levels;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.scs.basicecs.AbstractEntity;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.World;
import com.scs.splitscreenfps.game.data.WorldSquare;
import com.scs.splitscreenfps.game.entities.Wall;
import com.scs.splitscreenfps.game.entities.chaos.ChaosWraith;
import com.scs.splitscreenfps.game.entities.chaos.Ghost;
import com.scs.splitscreenfps.game.entities.chaos.Giant;
import com.scs.splitscreenfps.game.entities.chaos.Hydra;
import com.scs.splitscreenfps.game.entities.chaos.MagicFire;
import com.scs.splitscreenfps.game.entities.chaos.MagicTree;
import com.scs.splitscreenfps.game.entities.chaos.Ogre;
import com.scs.splitscreenfps.game.entities.startlevel.StartExit;
import com.scs.splitscreenfps.game.player.weapons.Wand;

import ssmith.lang.NumberFunctions;

public class ChaosLevel extends AbstractLevel {

	public ChaosLevel(int difficulty) {
		super(difficulty);
	}


	@Override
	public void load(Game game) {
		loadMap(game);

		//createWalls(game);

		game.player.setWeapon(new Wand());
	}


	private void loadMap(Game game) {
		this.map_width = 14;
		this.map_height = 10;

		Game.world.world = new WorldSquare[map_width][map_height];

		this.playerStartMapX = 1;
		this.playerStartMapY = 1;

		for (int z=0 ; z<map_height ; z++) {
			for (int x=0 ; x<map_width ; x++) {
				Game.world.world[x][z] = new WorldSquare();
				//int type = World.NOTHING;
				if (x == 0 || z == 0 || x >= map_width-1 || z >= map_height-1) {
					//Game.world.world[x][z] = new WorldSquare();
					Game.world.world[x][z].blocked = true;
					game.ecs.addEntity(new Wall("chaos/chaoswall.png", x, z));
				} else if ( x >= map_width / 2 || z >= map_height/2) {
					if (NumberFunctions.rnd(1, 10) == 1) {
						int i = NumberFunctions.rnd(1, 8);
						switch (i) {
						case 1:
							Game.world.world[x][z].blocked = true;
							game.ecs.addEntity(new Wall("chaos/brickwall.png", x, z));
							break;
						case 2:
							game.ecs.addEntity(new ChaosWraith(x, z));
							break;
						case 3:
							game.ecs.addEntity(new Hydra(x, z));
							break;
						case 4:
							game.ecs.addEntity(new Ghost(x, z));
							break;
						case 5:
							game.ecs.addEntity(new Giant(x, z));
							break;
						case 6:
							game.ecs.addEntity(new Ogre(x, z));
							break;
						case 7:
							game.ecs.addEntity(new MagicFire(x, z));
							break;
						case 8:
							game.ecs.addEntity(new MagicTree(x, z));
							break;
						}
					}
				}

				//Game.world.world[x][z].blocked = type == World.WALL;
			}
		}

		game.ecs.addEntity(new StartExit(map_width-2, map_height-2));

	}

	/*
	private void createWalls(Game game) {
		for (int y = 0; y < map_height; y++) {
			for (int x = 0; x < map_width; x++) {
				try {
					boolean block = Game.world.world[x][y].blocked;
					if (block) {
					}
				} catch (NullPointerException ex) {
					ex.printStackTrace();
				}
			}
		}

		game.ecs.addEntity(new Floor("lasersquad/moonbase_interior_floor.png", map_width, map_height, true));
	}
	 */

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
		return "CHAOS";
	}


	@Override
	public String getInstructions() {
		return "";
	}


	@Override
	public String getMusicFilename() {
		return "audio/music/Undead Rising.mp3";
	}

}
