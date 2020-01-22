package com.scs.splitscreenfps.game.levels;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.GridPoint2;
import com.scs.basicecs.AbstractEntity;
import com.scs.splitscreenfps.Settings;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.World;
import com.scs.splitscreenfps.game.components.PositionData;
import com.scs.splitscreenfps.game.data.WorldSquare;
import com.scs.splitscreenfps.game.entities.Floor;
import com.scs.splitscreenfps.game.entities.TextEntity;
import com.scs.splitscreenfps.game.entities.Wall;
import com.scs.splitscreenfps.game.entities.minedout.Damsel;
import com.scs.splitscreenfps.game.entities.minedout.Mine;
import com.scs.splitscreenfps.game.entities.minedout.MinedOutExit;
import com.scs.splitscreenfps.game.entities.minedout.MinedOutTrail;
import com.scs.splitscreenfps.game.systems.CountMinesSystem;

import ssmith.astar.AStar_LibGDX;
import ssmith.astar.IAStarMapInterface;
import ssmith.lang.NumberFunctions;

public class MinedOutLevel extends AbstractLevel implements IAStarMapInterface {

	private int num_damsels = 0;
	private CountMinesSystem countMinesSystem;
	private GridPoint2 exit_pos;
	private boolean[][] mine_map;

	public MinedOutLevel(int difficulty) {
		super(difficulty);
	}


	@Override
	public void load(Game game) {
		loadMap(game);

		this.countMinesSystem = new CountMinesSystem(game.ecs, game.player);
		game.ecs.addSystem(this.countMinesSystem);
	}


	private void loadMap(Game game) {
		this.map_width = 25;
		this.map_height = 15;

		int num_mines = Settings.DEBUG_MINES ? 1 : 30 + (this.difficulty * 10);
		num_damsels = 2 + this.difficulty;

		this.playerStartMapX = map_width/2;
		this.playerStartMapY = 1;

		exit_pos = new GridPoint2(map_width/2, map_height-1);

		List<AbstractEntity> newEnts = new ArrayList<AbstractEntity>(); 
		
		while (true) {
			mine_map = new boolean[map_width][map_height];
			Game.world.world = new WorldSquare[map_width][map_height];

			for (int z=0 ; z<map_height ; z++) {
				for (int x=0 ; x<map_width ; x++) {
					Game.world.world[x][z] = new WorldSquare();
					if (x == 0 || z == 0 || x >= map_width-1 || z >= map_height-1) {
						AbstractEntity wall = new Wall("minedout/wall.png", x, z); 
						game.ecs.addEntity(wall);
						newEnts.add(wall);
						Game.world.world[x][z].wall = wall;
						Game.world.world[x][z].blocked = true;
					}

				}
			}

			// Add mines
			for (int i=0 ; i<num_mines ; i++) {
				int x = NumberFunctions.rnd(2, map_width-2);
				int y = NumberFunctions.rnd(2, map_height-2);
				if (mine_map[x][y] == false) {
					AbstractEntity mine = new Mine(x, y); 
					game.ecs.addEntity(mine);
					newEnts.add(mine);
					mine_map[x][y] = true;
				} else {
					i--;
				}
			}

			for (int i=0 ; i<num_damsels ; i++) {
				int x = NumberFunctions.rnd(2,  map_width-2);
				int y = NumberFunctions.rnd(2,  map_height-2);
				if (mine_map[x][y] == false) {
					AbstractEntity damsel = new Damsel(x, y); 
					game.ecs.addEntity(damsel);
					newEnts.add(damsel);
				} else {
					i--;
				}
			}

			AStar_LibGDX astar = new AStar_LibGDX(this);
			astar.findPath(this.playerStartMapX, this.playerStartMapY, this.exit_pos.x, this.exit_pos.y);
			if (astar.wasSuccessful()) {
				break;
			} else {
				for(AbstractEntity e : newEnts) {
					e.remove();
				}
			}
		}

		game.ecs.addEntity(new Floor("colours/cyan.png", map_width, map_height, false));
	}


	@Override
	public void entityCollected(AbstractEntity collector, AbstractEntity collectable) {
		if (collectable instanceof Damsel) {
			this.num_damsels--;
			if (this.num_damsels <= 0) {
				Game.world.world[exit_pos.x][exit_pos.y].wall.remove();
				Game.world.world[exit_pos.x][exit_pos.y].blocked = false;
				MinedOutExit exit = new MinedOutExit(exit_pos.x, exit_pos.y);
				Game.ecs.addEntity(exit);

				AbstractEntity text = new TextEntity("THE EXIT HAS APPEARED", Gdx.graphics.getHeight()/2, 4);
				Game.ecs.addEntity(text);

			}
		}
	}


	@Override
	public void renderUI(SpriteBatch batch, BitmapFont font_white, BitmapFont font_black) {
		font_white.draw(batch, "Adjacent Mines: " + this.countMinesSystem.getNumMines(), 10, Settings.WINDOW_HEIGHT_PIXELS-40);
	}


	@Override
	public void update(Game game, World world) {
		countMinesSystem.process();

		// Player leave a path
		PositionData posData = (PositionData)game.player.getComponent(PositionData.class);
		GridPoint2 mapPos = posData.getMapPos();
		if (Game.world.world[mapPos.x][mapPos.y].wall == null) {
			MinedOutTrail trail = new MinedOutTrail(mapPos.x, mapPos.y);
			game.ecs.addEntity(trail);
			Game.world.world[mapPos.x][mapPos.y].wall = trail;
		}
	}


	@Override
	public String GetName() {
		return "MINEDOUT";
	}


	// AStar

	@Override
	public int getMapWidth() {
		return super.map_width;
	}


	@Override
	public int getMapHeight() {
		return super.map_height;
	}


	@Override
	public boolean isMapSquareTraversable(int x, int z) {
		return mine_map[x][z] == false;
	}


	@Override
	public float getMapSquareDifficulty(int x, int z) {
		return 1;
	}


	@Override
	public String getInstructions() {
		return "Collect the damsels and then get to the exit";
	}


	@Override
	public String getMusicFilename() {
		return "audio/music/Red Doors 2.0 (GameClosure Edition).mp3";
	}
}
