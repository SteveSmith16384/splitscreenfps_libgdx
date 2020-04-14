package com.scs.splitscreenfps.game.levels;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.MapData;
import com.scs.splitscreenfps.game.components.PositionComponent;
import com.scs.splitscreenfps.game.components.ql.IsRecordable;
import com.scs.splitscreenfps.game.components.ql.QLCanShoot;
import com.scs.splitscreenfps.game.components.ql.QLPlayerData;
import com.scs.splitscreenfps.game.data.MapSquare;
import com.scs.splitscreenfps.game.entities.Floor;
import com.scs.splitscreenfps.game.entities.Wall;
import com.scs.splitscreenfps.game.entities.ql.QuantumLeagueEntityFactory;
import com.scs.splitscreenfps.game.systems.ql.QLBulletSystem;
import com.scs.splitscreenfps.game.systems.ql.QLPhaseSystem;
import com.scs.splitscreenfps.game.systems.ql.QLRecordAndPlaySystem;
import com.scs.splitscreenfps.game.systems.ql.QLShootingSystem;

import ssmith.libgdx.GridPoint2Static;

public class QuantumLeagueLevel extends AbstractLevel {

	public static Properties prop;

	private List<String> instructions = new ArrayList<String>(); 
	public QLPhaseSystem qlPhaseSystem;
	public QLRecordAndPlaySystem qlRecordAndPlaySystem;
	private AbstractEntity[][] shadows; // Player, phase

	public QuantumLeagueLevel(Game _game) {
		super(_game);

		prop = new Properties();
		/*try {
			prop.load(new FileInputStream("quantumleague/ql_config.txt"));
		} catch (Exception e) {
			e.printStackTrace();
		}*/

		instructions.add("Todo");

		this.shadows = new AbstractEntity[game.players.length][2];

		this.qlPhaseSystem = new QLPhaseSystem(this);
		this.qlRecordAndPlaySystem = new QLRecordAndPlaySystem(game.ecs, this);
	}


	@Override
	public void setupAvatars(AbstractEntity player, int playerIdx) {
		player.addComponent(new QLPlayerData(playerIdx));

		// Create shadows ready for phase
		GridPoint2Static start = this.startPositions.get(playerIdx);
		for (int phase=0 ; phase<2 ; phase++) {
			AbstractEntity shadow = QuantumLeagueEntityFactory.createShadow(game.ecs, playerIdx, phase, start.x, start.y);
			this.shadows[playerIdx][phase] = shadow;
		}

		player.addComponent(new IsRecordable("Player " + playerIdx + "_recordable", this.shadows[playerIdx][0]));
		player.addComponent(new QLCanShoot());
	}


	@Override
	public void setBackgroundColour() {
		Gdx.gl.glClearColor(.6f, .6f, 1, 1);
	}


	public long getPhaseTime() {
		return System.currentTimeMillis() - this.qlPhaseSystem.this_phase_start_time;
	}


	@Override
	public void load() {
		loadMapFromFile("quantumleague/map1.csv");
	}


	private void loadMapFromFile(String file) {
		String str = Gdx.files.internal(file).readString();
		String[] str2 = str.split("\n");

		this.map_width = str2[0].split("\t").length;
		this.map_height = str2.length;

		game.mapData = new MapData(map_width, map_height);

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
							this.startPositions.add(new GridPoint2Static(col, row));
							Floor floor = new Floor(game.ecs, "quantumleague/textures/corridor.jpg", col, row, 1, 1, false);
							game.ecs.addEntity(floor);
						} else if (token.equals("W")) { // Wall
							game.mapData.map[col][row].blocked = true;
							Wall wall = new Wall(game.ecs, "quantumleague/textures/ufo2_03.png", col, 0, row, false);
							game.ecs.addEntity(wall);
						} else if (token.equals("C")) { // Chasm
							game.mapData.map[col][row].blocked = true;
						} else if (token.equals("F")) { // Floor
							Floor floor = new Floor(game.ecs, "quantumleague/textures/corridor.jpg", col, row, 1, 1, false);
							game.ecs.addEntity(floor);
						} else if (token.equals("G")) { // Goal point
							Floor floor = new Floor(game.ecs, "quantumleague/textures/deploy_sq.png", col, row, 1, 1, false);
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
		ecs.addSystem(new QLBulletSystem(ecs));
		ecs.addSystem(new QLShootingSystem(ecs, game, this));

	}


	@Override
	public void update() {
		game.ecs.processSystem(QLBulletSystem.class);
		game.ecs.processSystem(QLShootingSystem.class);
		qlRecordAndPlaySystem.process(); // Must be before phase system!ddddd
		this.qlPhaseSystem.process();
	}


	public void renderUI(SpriteBatch batch2d, int viewIndex) {
		game.font_med.setColor(1, 1, 1, 1);
		game.font_med.draw(batch2d, "In-Game?: " + this.qlPhaseSystem.game_phase, 10, 30);
		game.font_med.draw(batch2d, "Time: " + (int)(this.getPhaseTime() / 1000), 10, 60);

		QLPlayerData playerData = (QLPlayerData)game.players[viewIndex].getComponent(QLPlayerData.class);
		game.font_med.draw(batch2d, "Health: " + (int)(playerData.health), 10, 90);
	}


	public boolean isGamePhase() {
		return this.qlPhaseSystem.game_phase;
	}


	public void nextRewindPhase() {
	}


	public void nextGamePhase() {
		this.qlRecordAndPlaySystem.loadNewRecordData();
		for (int playerIdx=0 ; playerIdx<game.players.length ; playerIdx++) {
			// Reset all health
			QLPlayerData playerData = (QLPlayerData)game.players[playerIdx].getComponent(QLPlayerData.class);
			playerData.health = 100;
			for (int phase = 0 ; phase<2 ; phase++) {
				//try {
				playerData = (QLPlayerData)this.shadows[playerIdx][phase].getComponent(QLPlayerData.class);
				playerData.health = 100;
				/*} catch (ArrayIndexOutOfBoundsException ex) {
					ex.printStackTrace();
				}*/
			}

			GridPoint2Static start = this.startPositions.get(playerIdx);
			// Add shadow avatars to ECS
			if (this.qlPhaseSystem.phase_num_012 > 0) {
				AbstractEntity shadow = this.shadows[playerIdx][this.qlPhaseSystem.phase_num_012-1];
				game.ecs.addEntity(shadow);
			}

			// Record against next shadow
			IsRecordable isRecordable = (IsRecordable)game.players[playerIdx].getComponent(IsRecordable.class);
			if (this.qlPhaseSystem.phase_num_012 <= 1) {
				isRecordable.entity = this.shadows[playerIdx][this.qlPhaseSystem.phase_num_012];
			} else {
				isRecordable.entity = null; // No need to record any more.
			}

			// Move players avatars back to start
			PositionComponent posData = (PositionComponent)game.players[playerIdx].getComponent(PositionComponent.class);
			posData.position.x = start.x;
			posData.position.z = start.y;
		}
	}


	public void allPhasesOver() {
		// todo - calc winner and game over
		game.playerHasWon(null);
	}


	@Override
	public void renderHelp(SpriteBatch batch2d, int viewIndex) {
		game.font_med.setColor(1, 1, 1, 1);
		int x = (int)(Gdx.graphics.getWidth() * 0.4);
		int y = (int)(Gdx.graphics.getHeight() * 0.8);
		for (String s : this.instructions) {
			game.font_med.draw(batch2d, s, x, y);
			y -= this.game.font_med.getLineHeight();
		}
	}


}