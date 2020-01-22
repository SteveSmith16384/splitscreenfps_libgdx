package com.scs.splitscreenfps.game.levels;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.scs.basicecs.AbstractEntity;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.World;
import com.scs.splitscreenfps.game.data.WorldSquare;
import com.scs.splitscreenfps.game.entities.Wall;
import com.scs.splitscreenfps.game.entities.ericandthefloaters.EricBombDropper;
import com.scs.splitscreenfps.game.entities.ericandthefloaters.Floater;
import com.scs.splitscreenfps.game.systems.EricAndTheFloatersExplosionSystem;

public class EricAndTheFloatersLevel extends AbstractLevel {

	public EricAndTheFloatersLevel(int difficulty) {
		super(difficulty);
	}


	@Override
	public void load(Game game) {
		//loadMapFromImage(game);
		loadTestMap(game);

		//createWalls(game);

		game.player.setWeapon(new EricBombDropper());
		game.ecs.addSystem(new EricAndTheFloatersExplosionSystem(game.ecs));
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
				Game.world.world[x][z] = new WorldSquare();
				if (x == 0 || z == 0 || x >= map_width-1 || z >= map_height-1) {
					type = World.WALL;
					Wall wall = new Wall("ericandthefloaters/ericouterwall.png", x, z);
					game.ecs.addEntity(wall);
				} else if (x == 2 && z == 2) {
					type = World.WALL;
					Wall wall = new Wall("ericandthefloaters/ericsolidwall.png", x, z);
					game.ecs.addEntity(wall);
				} else if (x == 3 && z == 3) {
					type = World.WALL;
					Wall wall = new Wall("ericandthefloaters/ericdestroyablewall.png", x, z);
					game.ecs.addEntity(wall);
				} else if (x == 3 && z == 1) {
					Floater floater = new Floater(x, z);
					game.ecs.addEntity(floater);
				}

				Game.world.world[x][z].blocked = type == World.WALL;
			}
		}
	}

/*
	private void createWalls(Game game) {
		for (int z = 0; z < map_height; z++) {
			for (int x = 0; x < map_width; x++) {
				try {
					int block = Game.world.world[x][z].type;
					if (block == World.WALL) {
						if (x == 0 || z == 0 || x >= map_width-1 || z >= map_height-1) {
							game.ecs.addEntity(new Wall("ericandthefloaters/ericouterwall.png", x, z));
						} else {
							game.ecs.addEntity(new Wall("ericandthefloaters/ericwall.png", x, z));
						}
					}
				} catch (NullPointerException ex) {
					ex.printStackTrace();
				}
			}
		}
	}
*/

	@Override
	public void update(Game game, World world) {
		game.ecs.getSystem(EricAndTheFloatersExplosionSystem.class).process();
	}

	
	@Override
	public void entityCollected(AbstractEntity collector, AbstractEntity collectable) {
	}


	@Override
	public void renderUI(SpriteBatch batch, BitmapFont font_white, BitmapFont font_black) {
	}


	@Override
	public String GetName() {
		return "ERICANDTHEFLOATERS";
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
