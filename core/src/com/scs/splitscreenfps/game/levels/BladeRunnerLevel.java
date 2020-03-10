package com.scs.splitscreenfps.game.levels;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.MapData;
import com.scs.splitscreenfps.game.components.towerdefence.TowerDefencePlayerData;
import com.scs.splitscreenfps.game.data.MapSquare;
import com.scs.splitscreenfps.game.entities.Floor;
import com.scs.splitscreenfps.game.entities.PlayersAvatar_Person;
import com.scs.splitscreenfps.game.entities.Wall;
import com.scs.splitscreenfps.game.entities.bladerunner.BladeRunnerEntityFactory;
import com.scs.splitscreenfps.game.systems.bladerunner.BladeRunnerCivSystem;

import ssmith.lang.NumberFunctions;
import ssmith.libgdx.GridPoint2Static;

public class BladeRunnerLevel extends AbstractLevel {

	protected List<GridPoint2Static> rndStartPositions = new ArrayList<GridPoint2Static>();

	public BladeRunnerLevel(Game _game) {
		super(_game);
	}


	@Override
	public void loadAvatars() {
		for (int i=0 ; i<game.players.length ; i++) {
			game.players[i] = new PlayersAvatar_Person(game, i, game.viewports[i], game.inputs.get(i), 0);
			game.ecs.addEntity(game.players[i]);
		}	
	}
	
	
	@Override
	public void setupAvatars(AbstractEntity player, int playerIdx) {
		//player.addComponent(new ShowFloorSelectorComponent());
	}


	@Override
	public void setBackgroundColour() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
	}


	@Override
	public void load() {
		loadMapFromFile("bladerunner/map1.csv");
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
					String tokens[] = cell.split(Pattern.quote("+"));
					for (String token : tokens) {
						if (token.equals("S")) { // Start pos
							this.rndStartPositions.add(new GridPoint2Static(col, row));
						} else if (token.equals("W")) { // Wall
							game.mapData.map[col][row].blocked = true;
							Wall wall = new Wall(game.ecs, "towerdefence/textures/ufo2_03.png", col, 0, row, false);
							game.ecs.addEntity(wall);
						} else if (token.equals("C")) { // Chasm
							game.mapData.map[col][row].blocked = true;
						} else if (token.equals("P") || token.equals("F")) { // Path
							Floor floor = new Floor(game.ecs, "towerdefence/textures/corridor.jpg", col, row, 1, 1, false);
							game.ecs.addEntity(floor);
							
							if (NumberFunctions.rnd(1,  20) == 1) {
								AbstractEntity civ = BladeRunnerEntityFactory.createCiv(game.ecs, col, row);
								game.ecs.addEntity(civ);
							}
						} else if (token.equals("R")) { // Road
							Floor floor = new Floor(game.ecs, "towerdefence/textures/corridor.jpg", col, row, 1, 1, false);
							game.ecs.addEntity(floor);
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
		ecs.addSystem(new BladeRunnerCivSystem(ecs, game));
	}


	@Override
	public void update() {
		game.ecs.processSystem(BladeRunnerCivSystem.class);
	}


	public void renderUI(SpriteBatch batch2d, int viewIndex) {
		AbstractEntity playerAvatar = game.players[viewIndex];
		TowerDefencePlayerData tc = (TowerDefencePlayerData)playerAvatar.getComponent(TowerDefencePlayerData.class);
		if (tc != null) {
			game.font_med.setColor(0, 0, 0, 1);
			game.font_med.draw(batch2d, "Coins: " + (int)tc.coins, 10, game.font_med.getLineHeight());
		}
	}

	
	public GridPoint2Static getPlayerStartMap(int idx) {
		int pos = NumberFunctions.rnd(0,  this.rndStartPositions.size()-1);
		return this.rndStartPositions.remove(pos);
	}
	

}
