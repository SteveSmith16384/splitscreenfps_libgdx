package com.scs.splitscreenfps.game.levels;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.MapData;
import com.scs.splitscreenfps.game.data.MapSquare;
import com.scs.splitscreenfps.game.entities.Floor;
import com.scs.splitscreenfps.game.entities.Wall;
import com.scs.splitscreenfps.game.entities.stockcar.TrackComponent;

import ssmith.libgdx.GridPoint2Static;

public class CarParkLevel extends AbstractLevel {

	protected List<GridPoint2Static> rndStartPositions = new ArrayList<GridPoint2Static>();
	private List<String> instructions = new ArrayList<String>(); 

	public CarParkLevel(Game _game) {
		super(_game);
	}


	@Override
	public void setBackgroundColour() {
		//Gdx.gl.glClearColor(.6f, .6f, 1, 1);
		Gdx.gl.glClearColor(0, 0, 0, 1);
	}


	@Override
	public void load() {
		loadMapFromFile("carpark/maps/map1.csv");
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
					int itoken = Integer.parseInt(cell);
					if (itoken < 0) { // Start pos
						game.mapData.map[col][row].entity.addComponent(new TrackComponent(1, 1));
						this.rndStartPositions.add(new GridPoint2Static(col, row));
					} else if (itoken == 0 || itoken == -1 || itoken == 5 || itoken == 6) { // Road!
						game.mapData.map[col][row].entity.addComponent(new TrackComponent(1, 1));
						Floor floor = new Floor(game.ecs, "carpark/textures/road2.png", col, row, 1, 1, false);
						game.ecs.addEntity(floor);
						if (itoken == -1) {
							rndStartPositions.add(new GridPoint2Static(col, row));
						}
					} else if (itoken == 99) { // Starting grid
						game.mapData.map[col][row].entity.addComponent(new TrackComponent(1, 1));
						Floor floor = new Floor(game.ecs, "stockcar/textures/street010_lr.jpg", col, row, 1, 1, false);
						game.ecs.addEntity(floor);
					} else if (itoken == 2) { // track edge!
						game.mapData.map[col][row].entity.addComponent(new TrackComponent(1, .7f));
						//game.mapData.map[col][row].blocked = true;
						Floor floor = new Floor(game.ecs, "stockcar/textures/track_edge.png", col, row, 1, 1, false);
						game.ecs.addEntity(floor);
					} else if (itoken == 1 || itoken == 3) { // Grass?
						game.mapData.map[col][row].entity.addComponent(new TrackComponent(.5f, .5f));
						Floor floor = new Floor(game.ecs, "stockcar/textures/grass.jpg", col, row, 1, 1, false);
						game.ecs.addEntity(floor);
					} else if (itoken == 4) { // wall
						game.mapData.map[col][row].blocked = true;
						Wall wall = new Wall(game.ecs, "stockcar/textures/wall2.jpg", col, 0, row, false);
						game.ecs.addEntity(wall);
					} else {
						throw new RuntimeException("Unknown cell type: " + itoken);
					}
				}
				row++;
			}
		}
	}

	
	@Override
	public void addSystems(BasicECS ecs) {
		//ecs.addSystem(new WanderingAnimalSystem(ecs));

	}

	
	@Override
	public void update() {
		//game.ecs.processSystem(GrowCropsSystem.class);
	}

}
