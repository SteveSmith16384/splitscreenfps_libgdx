package com.scs.splitscreenfps.game.levels;

import java.util.regex.Pattern;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.MapData;
import com.scs.splitscreenfps.game.components.towerdefence.ShowFloorSelectorComponent;
import com.scs.splitscreenfps.game.components.towerdefence.TowerDefencePlayerData;
import com.scs.splitscreenfps.game.data.MapSquare;
import com.scs.splitscreenfps.game.entities.Ceiling;
import com.scs.splitscreenfps.game.entities.EntityFactory;
import com.scs.splitscreenfps.game.entities.Floor;
import com.scs.splitscreenfps.game.entities.Wall;
import com.scs.splitscreenfps.game.entities.ftl.FTLEntityFactory;
import com.scs.splitscreenfps.game.systems.towerdefence.ShowFloorSelectorSystem;

import ssmith.libgdx.GridPoint2Static;

public class TowerDefenceLevel extends AbstractLevel {

	public TowerDefenceLevel(Game _game) {
		super(_game);
	}


	@Override
	public void setupAvatars(AbstractEntity player, int playerIdx) {
		player.addComponent(new ShowFloorSelectorComponent());
		player.addComponent(new TowerDefencePlayerData());
	}


	@Override
	public void setBackgroundColour() {
		Gdx.gl.glClearColor(.6f, .6f, 1, 1);
	}


	@Override
	public void load() {
		loadMapFromFile("towerdefence/map1.csv");

		//game.ecs.addEntity(new Floor(game.ecs, "farm/grass.jpg", 1, 1, map_width-1, map_height-1, true));
		
	}

	
	private void loadMapFromFile(String file) {
		String str = Gdx.files.internal(file).readString();
		String[] str2 = str.split("\n");

		this.map_width = str2[0].split("\t").length;
		this.map_height = str2.length;

		game.mapData = new MapData(map_width, map_height);

		Floor floor = new Floor(game.ecs, "ftl/textures/corridor.jpg", 0, 0, map_width, map_height, true);
		game.ecs.addEntity(floor);

		Ceiling ceiling = new Ceiling(game.ecs, "ftl/textures/corridor.jpg", 0, 0, map_width, map_height, true, 1f);
		game.ecs.addEntity(ceiling);

		int row = 0;
		for (String s : str2) {
			s = s.trim();
			if (s.length() > 0 && s.startsWith("#") == false) {
				String cells[] = s.split("\t");
				for (int col=0 ; col<cells.length ; col++) {
					game.mapData.map[col][row] = new MapSquare();

					String cell = cells[col];
					String tokens[] = cell.split(Pattern.quote("+"));
					for (String token : tokens) {
						if (token.equals("S1")) { // Start pos
							this.startPositions[0] = new GridPoint2Static(col, row);
						} else if (token.equals("S2")) { // Start pos
							this.startPositions[1] = new GridPoint2Static(col, row);
						} else if (token.equals("S3")) { // Start pos
							this.startPositions[2] = new GridPoint2Static(col, row);
						} else if (token.equals("S4")) { // Start pos
							this.startPositions[3] = new GridPoint2Static(col, row);
						} else if (token.equals("F")) { // Floor
							// Do nothing
						} else if (token.equals("W")) { // Wall
							game.mapData.map[col][row].blocked = true;
							Wall wall = new Wall(game.ecs, "ftl/textures/ufo2_03.png", col, 0, row, false);
							game.ecs.addEntity(wall);
						} else if (token.equals("C")) { // Chasm
						} else if (token.equals("D1")) { // Door 1
							AbstractEntity door = EntityFactory.createDoor(game.ecs, col, row, false);
							game.ecs.addEntity(door);
						} else if (token.equals("D2")) { // Door 2
							AbstractEntity door = EntityFactory.createDoor(game.ecs, col, row, true);
							game.ecs.addEntity(door);
						} else if (token.equals("B")) { // Door 2
							AbstractEntity battery = FTLEntityFactory.createBattery(game.ecs, col, row);
							game.ecs.addEntity(battery);
						} else {
							throw new RuntimeException("Unknown cell type: " + token);
						}
					}
				}
				row++;
			}
		}

	}


	@Override
	public void addSystems(BasicECS ecs) {
		ecs.addSystem(new ShowFloorSelectorSystem(ecs));
	}

	
	@Override
	public void update() {
		game.ecs.processSystem(ShowFloorSelectorSystem.class);
	}


	public void renderUI(SpriteBatch batch2d, int viewIndex) {
		AbstractEntity playerAvatar = game.players[viewIndex];
		TowerDefencePlayerData tc = (TowerDefencePlayerData)playerAvatar.getComponent(TowerDefencePlayerData.class);
		if (tc != null) {
			game.font_med.setColor(0, 0, 0, 1);
			game.font_med.draw(batch2d, "Coins: " + (int)tc.coins, 10, game.font_med.getLineHeight());
		}
	}

}
