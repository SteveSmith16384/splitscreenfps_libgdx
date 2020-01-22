package com.scs.splitscreenfps.game.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.scs.basicecs.AbstractEntity;
import com.scs.splitscreenfps.Settings;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.World;
import com.scs.splitscreenfps.game.data.WorldSquare;
import com.scs.splitscreenfps.game.entities.Floor;
import com.scs.splitscreenfps.game.entities.Wall;
import com.scs.splitscreenfps.game.entities.androids.AndroidsAndroid;
import com.scs.splitscreenfps.game.entities.androids.GSquare;
import com.scs.splitscreenfps.game.entities.androids.SSquare;
import com.scs.splitscreenfps.game.entities.androids.SlidingDoor;
import com.scs.splitscreenfps.game.player.weapons.PlayersLaserGun;

public class AndroidsLevel extends AbstractLevel {

	public AndroidsLevel(int difficulty) {
		super(difficulty);
	}


	@Override
	public void load(Game game) {
		loadMapFromImage(game);
		//loadTestMap(game);
		//loadMapFromMazegen(game);

		createWalls(game);

		game.player.setWeapon(new PlayersLaserGun("todo"));
	}

/*
	private void loadMapFromMazegen(Game game) {
		this.map_width = 20;
		this.map_height = 20;

		Game.world.world = new WorldSquare[map_width][map_height];
		
		Maze maze = new Maze(map_width, map_height);

		this.playerStartMapX = maze.start_pos.x;
		this.playerStartMapY = maze.start_pos.y;
		
		// todo - add exit and

		for (int z=0 ; z<map_height ; z++) {
			for (int x=0 ; x<map_width ; x++) {
				Game.world.world[x][z] = new WorldSquare();
				Game.world.world[x][z].blocked = !maze.map[x][z];
			}
		}
	}
*/

	private void loadMapFromImage(Game game) {
		Texture texture = new Texture(Gdx.files.internal("androids/androids_map.png"));
		texture.getTextureData().prepare();
		Pixmap pixmap = texture.getTextureData().consumePixmap();

		this.map_width = pixmap.getWidth() / 16;
		this.map_height = pixmap.getHeight() / 16;
		Game.world.world = new WorldSquare[map_width+1][map_height+1];

		playerStartMapX = map_width/2;
		this.playerStartMapY = map_height/2;

		int image_width = pixmap.getWidth();
		int image_height = pixmap.getHeight();
		int mapZ = 0;
		for (int pixel_z=8 ; pixel_z<image_height ; pixel_z+=16) {
			int mapX = 0;
			for (int pixel_x=8 ; pixel_x<image_width ; pixel_x+=16) {
				int col = pixmap.getPixel(pixel_x, pixel_z);
				int type = World.NOTHING;
				switch (col) {
				case 65791: // Wall
					type = World.WALL;
					break;

				case -825437953: // floor
					// Do nothing
					break;

				case 247909119: // door?
				case 336565503:
					SlidingDoor door = new SlidingDoor(mapX, mapZ, "colours/cyan.png");
					game.ecs.addEntity(door);
					break;

				case 1338133247: // baddy?
				case -1798385153:
					AbstractEntity e = new AndroidsAndroid(mapX, mapZ);//playerStartMapX-1, playerStartMapY-1);
					game.ecs.addEntity(e);
					Settings.p("Created " + e.name + " at " + mapX + "," + mapZ);
					break;

				case 437736447: // G square
				case -1706648833:
					GSquare gs = new GSquare(mapX, mapZ);
					game.ecs.addEntity(gs);
					break;

				case -15462508: // start
					playerStartMapX = mapX;
					this.playerStartMapY = mapZ;
					break;

				case -155191809: // S square
					SSquare ss = new SSquare(mapX, mapZ);
					game.ecs.addEntity(ss);
					break;

				default:
					Settings.p("Unknown colour at " + mapX + "," + mapZ + ":" + col);
				}

				Game.world.world[mapX][mapZ] = new WorldSquare();
				Game.world.world[mapX][mapZ].blocked = type == World.WALL;

				mapX++;
			}
			mapZ++;
		}

	}


	private void createWalls(Game game) {
		for (int y = 0; y < map_height; y++) {
			for (int x = 0; x < map_width; x++) {
				try {
					boolean block = Game.world.world[x][y].blocked;
					if (block) {
						game.ecs.addEntity(new Wall("androids/wall.png", x, y));
					}
				} catch (NullPointerException ex) {
					ex.printStackTrace();
				}
			}
		}
		
		game.ecs.addEntity(new Floor("colours/white.png", map_width, map_height, false));
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
		return "ANDROIDS";
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
