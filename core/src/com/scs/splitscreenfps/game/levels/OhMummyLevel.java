package com.scs.splitscreenfps.game.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.GridPoint2;
import com.scs.basicecs.AbstractEntity;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.World;
import com.scs.splitscreenfps.game.components.PositionData;
import com.scs.splitscreenfps.game.data.WorldSquare;
import com.scs.splitscreenfps.game.entities.TextEntity;
import com.scs.splitscreenfps.game.entities.Wall;
import com.scs.splitscreenfps.game.entities.ohmummy.OhMummyExit;
import com.scs.splitscreenfps.game.entities.ohmummy.OhMummyNasty;
import com.scs.splitscreenfps.game.entities.ohmummy.Pill;

import ssmith.lang.NumberFunctions;

public class OhMummyLevel extends AbstractLevel {

	private static final int RECT_SIZE_EXCLUDING_EDGES = 3;

	private int[][] pill_map; // And rect types - 1=pill, or >0=tex code for walls
	private boolean exit_created = false;

	public OhMummyLevel(int diff) {
		super(diff);
	}


	@Override
	public void load(Game game) {
		createMap(game);

		createWalls(game);
	}


	private void createMap(Game game) {
		int NUM_RECTS = 3;
		this.map_width = 3 + ((RECT_SIZE_EXCLUDING_EDGES+1)*NUM_RECTS);
		this.map_height = 3 + ((RECT_SIZE_EXCLUDING_EDGES+1)*NUM_RECTS);

		Game.world.world = new WorldSquare[map_width][map_height];
		pill_map = new int[map_width][map_height];

		this.playerStartMapX = 1;
		this.playerStartMapY = 1;

		for (int z=0 ; z<map_height ; z++) {
			for (int x=0 ; x<map_width ; x++) {
				int type = World.NOTHING;
				if (x == 0 || z == 0 || x >= map_width-1 || z >= map_height-1) {
					type = World.WALL;
				}

				Game.world.world[x][z] = new WorldSquare();
				Game.world.world[x][z].blocked = type == World.WALL;

				/*if (type == World.WALL) {
					Wall wall = null;
					if (x == 0 || z == 0 || x >= map_width-1 || z >= map_height-1) {
						wall = new Wall("colours/white.png", x, z);
					} else {
						wall = new Wall("ohmummy/wall1.png", x, z);
					}
					game.ecs.addEntity(wall);
					Game.world.world[x][z].wall = wall;
				}*/
			}
		}

		for (int z=2 ; z<map_height-2 ; z+=RECT_SIZE_EXCLUDING_EDGES+1) {
			for (int x=2 ; x<map_width-2 ; x+=RECT_SIZE_EXCLUDING_EDGES+1) {
				createRect(x, z);
			}
		}

		// Add baddies
		AbstractEntity nasty = new OhMummyNasty(1, map_height-2);
		game.ecs.addEntity(nasty);
		AbstractEntity nasty2 = new OhMummyNasty(map_width-2, map_height-2);
		game.ecs.addEntity(nasty2);

	}


	private void createRect(int sx, int sz) {
		for (int z=sz ; z<sz+RECT_SIZE_EXCLUDING_EDGES ; z++) {
			for (int x=sx ; x<sx+RECT_SIZE_EXCLUDING_EDGES ; x++) {
				try {
					Game.world.world[x][z].blocked = true;
				} catch (ArrayIndexOutOfBoundsException ex) {
					ex.printStackTrace();
				}
			}
		}
	}


	private void createWalls(Game game) {
		for (int y = 0; y < map_height; y++) {
			for (int x = 0; x < map_width; x++) {
				try {
					boolean block = Game.world.world[x][y].blocked;
					if (block) {
						Wall wall = null;
						if (x == 0 || y == 0 || x >= map_width-1 || y >= map_height-1) {
							wall = new Wall("colours/white.png", x, y);
						} else {
							wall = new Wall("ohmummy/wall.png", x, y);
						}
						game.ecs.addEntity(wall);
						Game.world.world[x][y].wall = wall;
					}
				} catch (NullPointerException ex) {
					ex.printStackTrace();
				}
			}
		}
	}


	@Override
	public void update(Game game, World world) {
		// Drop pill?
		boolean checkForCircled = false;
		PositionData posData = (PositionData)game.player.getComponent(PositionData.class);
		GridPoint2 map_pos = posData.getMapPos();
		if (this.pill_map[map_pos.x][map_pos.y] == 0) {
			Pill ch = new Pill(map_pos.x, map_pos.y);
			game.ecs.addEntity(ch);
			this.pill_map[map_pos.x][map_pos.y] = 1;
			//Settings.p("Adding pill to " + map_pos.x + "," + map_pos.y);
			checkForCircled = true;
			Game.audio.play("beepfx_samples/00_shot_1.wav");
		}


		if (checkForCircled) {
			for (int z=1 ; z<map_height-2 ; z+=RECT_SIZE_EXCLUDING_EDGES+1) {
				for (int x=1 ; x<map_width-2 ; x+=RECT_SIZE_EXCLUDING_EDGES+1) {
					checkIfRectSurrounded(game, x, z); 
				}
			}
		}
	}


	private void checkIfRectSurrounded(Game game, int sx, int sz) {
		boolean covered = true;
		for (int z=sz ; z<=sz+RECT_SIZE_EXCLUDING_EDGES+1 ; z++) {
			for (int x=sx ; x<=sx+RECT_SIZE_EXCLUDING_EDGES+1; x++) {
				if (x == sx || z == sz || x >= sx+RECT_SIZE_EXCLUDING_EDGES+1 || z >= sz+RECT_SIZE_EXCLUDING_EDGES+1) {
					if (this.pill_map[x][z] == 0) {
						covered = false;
						break;
					}
				}
			}
		}
		if (covered) {
			int texid = NumberFunctions.rnd(1, 3);
			String tex = this.getTexFilename(texid);
			for (int z=sz+1 ; z<=sz+RECT_SIZE_EXCLUDING_EDGES ; z++) {
				for (int x=sx+1 ; x<=sx+RECT_SIZE_EXCLUDING_EDGES; x++) {
					if (this.pill_map[x][z] == 0) {
						Game.audio.play("beepfx_samples/49_alarm_3.wav");
						this.pill_map[x][z] = texid;
						AbstractEntity wall = Game.world.world[x][z].wall;
						wall.remove();
						wall = new Wall("ohmummy/"+tex, x, z);
						game.ecs.addEntity(wall);
						Game.world.world[x][z].wall = wall;
					}
				}
			}

			checkForCompletion(game);
		}
	}


	private String getTexFilename(int i) {
		switch (i) {
		case 1: return "mummy.png";
		case 2: return "scroll.png";
		case 3: return "key.png";
		default: throw new RuntimeException("Unknown tex id: " + i);
		}
	}


	private void checkForCompletion(Game game) {
		if (exit_created) {
			return;
		}

		boolean complete = true;
		for (int z=0 ; z<map_height ; z++) {
			for (int x=0 ; x<map_width ; x++) {
				if (Game.world.world[x][z].blocked == false) {
					if (this.pill_map[x][z] == 0) {
						complete = false;
						break;
					}
				}
			}
		}
		if (complete) {
			AbstractEntity text = new TextEntity("THE EXIT HAS APPEARED", Gdx.graphics.getHeight()/2, 4);
			game.ecs.addEntity(text);

			OhMummyExit exit = new OhMummyExit(map_width-2, map_height-4);
			game.ecs.addEntity(exit);

			exit_created = true;
		}
	}


	@Override
	public void entityCollected(AbstractEntity collector, AbstractEntity collectable) {

	}


	@Override
	public void renderUI(SpriteBatch batch, BitmapFont font_white, BitmapFont font_black) {
	}


	@Override
	public String GetName() {
		return "OH MUMMY!";
	}


	@Override
	public String getInstructions() {
		return "Surround all the columns then go to exit";
	}


	@Override
	public String getMusicFilename() {
		return "audio/music/orbital_colossus.mp3";
	}
}
