package com.scs.splitscreenfps.game.levels;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.MapData;
import com.scs.splitscreenfps.game.data.MapSquare;
import com.scs.splitscreenfps.game.entities.Floor;
import com.scs.splitscreenfps.game.entities.PlayersAvatar_Car;
import com.scs.splitscreenfps.game.entities.Wall;
import com.scs.splitscreenfps.game.systems.VehicleMovementSystem;
import com.scs.splitscreenfps.game.systems.VehicleProcessCollisionSystem;

import ssmith.lang.NumberFunctions;
import ssmith.libgdx.GridPoint2Static;

public class StockCarLevel extends AbstractLevel {

	protected List<GridPoint2Static> rndStartPositions = new ArrayList<GridPoint2Static>();
	private List<String> instructions = new ArrayList<String>(); 

	public StockCarLevel(Game _game) {
		super(_game);

		instructions.add("Keyboard:");
		instructions.add("Enter: Accelerate");
		instructions.add("Space: Brake");
		instructions.add("");
		instructions.add("Controllers:");
		instructions.add("Todo");
	}


	@Override
	public void loadAvatars() {
		for (int i=0 ; i<game.players.length ; i++) {
			game.players[i] = new PlayersAvatar_Car(game, i, game.viewports[i], game.inputs.get(i));
			game.ecs.addEntity(game.players[i]);
		}	
	}


	@Override
	public void setupAvatars(AbstractEntity player, int playerIdx) {
		//player.addComponent(new ShowFloorSelectorComponent());
	}


	@Override
	public void setBackgroundColour() {
		Gdx.gl.glClearColor(.6f, .6f, 1, 1);
	}


	@Override
	public void load() {
		loadMapFromFile("stockcar/tracks/stockcarchamp1.csv");
	}


	private void loadMapFromFile(String file) {
		String str = Gdx.files.internal(file).readString();
		String[] str2 = str.split("\n");

		this.map_width = str2[0].split("\t").length;
		this.map_height = str2.length;

		game.mapData = new MapData(map_width, map_height);

		this.rndStartPositions.add(new GridPoint2Static(1, 1));

		int row = 0;
		for (String s : str2) {
			s = s.trim();
			if (s.length() > 0 && s.startsWith("#") == false) {
				String cells[] = s.split("\t");
				for (int col=0 ; col<cells.length ; col++) {
					game.mapData.map[col][row] = new MapSquare(game.ecs);

					String cell = cells[col];
					/*String tokens[] = cell.split(Pattern.quote(","));
					for (String token : tokens) {*/
					int itoken = Integer.parseInt(cell);
					if (itoken < 0) { // Start pos
						this.rndStartPositions.add(new GridPoint2Static(col, row));
					} else if (itoken == 0 || itoken == -1 || itoken == 8 || itoken == 9) { // Road!
						Floor floor = new Floor(game.ecs, "stockcar/textures/road2.png", col, row, 1, 1, false);
						game.ecs.addEntity(floor);
						if (itoken == -1) {
							rndStartPositions.add(new GridPoint2Static(col, row));
						}
					} else if (itoken == 99) { // Starting grid
						Floor floor = new Floor(game.ecs, "stockcar/textures/street010_lr.jpg", col, row, 1, 1, false);
						game.ecs.addEntity(floor);
					} else if (itoken == 2) { // track edge!
						game.mapData.map[col][row].blocked = true;
						Floor floor = new Floor(game.ecs, "stockcar/textures/track_edge.jpg", col, row, 1, 1, false);
						game.ecs.addEntity(floor);
					} else if (itoken == 1 || itoken == 3) { // Grass?
						Floor floor = new Floor(game.ecs, "stockcar/textures/grass.jpg", col, row, 1, 1, false);
						game.ecs.addEntity(floor);
					} else if (itoken == 4) { // wall
						game.mapData.map[col][row].blocked = true;
						Wall wall = new Wall(game.ecs, "stockcar/textures/track_edge.jpg", col, 0, row, false);
						game.ecs.addEntity(wall);
					} else {
						throw new RuntimeException("Unknown cell type: " + itoken);
					}
					//}
				}
				row++;
			}
		}
	}


	@Override
	public void addSystems(BasicECS ecs) {
		ecs.addSystem(new VehicleMovementSystem(ecs, .0008f));
		ecs.addSystem(new VehicleProcessCollisionSystem(ecs, game));
	}


	@Override
	public void update() {
		game.ecs.processSystem(VehicleMovementSystem.class);
		game.ecs.processSystem(VehicleProcessCollisionSystem.class);
	}


	public void renderUI(SpriteBatch batch2d, int viewIndex) {
	}


	public GridPoint2Static getPlayerStartMap(int idx) {
		int pos = NumberFunctions.rnd(0,  this.rndStartPositions.size()-1);
		return this.rndStartPositions.remove(pos);
	}


	@Override
	public void renderHelp(SpriteBatch batch2d, int viewIndex) {
		game.font_med.setColor(1, 1, 1, 1);
		game.font_med.draw(batch2d, "HELP!", 10, game.font_med.getLineHeight()*2);
	
	}

}
