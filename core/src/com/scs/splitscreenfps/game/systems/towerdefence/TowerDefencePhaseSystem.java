package com.scs.splitscreenfps.game.systems.towerdefence;

import com.scs.basicecs.ISystem;
import com.scs.splitscreenfps.Settings;
import com.scs.splitscreenfps.game.levels.TowerDefenceLevel;

public class TowerDefencePhaseSystem implements ISystem {
	
	private static final long REST_PHASE_DURATION = 5000;
	private static final long SPAWN_PHASE_DURATION = 15000;
	
	private boolean spawn_phase = false; // otherwise, rest phase
	private long next_phase_time;
	private TowerDefenceLevel towerDefenceLevel;
	
	public TowerDefencePhaseSystem(TowerDefenceLevel _level) {
		towerDefenceLevel = _level;
		next_phase_time = System.currentTimeMillis() + REST_PHASE_DURATION;
	}

	@Override
	public void process() {
		if (this.next_phase_time < System.currentTimeMillis()) {
			this.spawn_phase = !this.spawn_phase;
			if (this.spawn_phase) {
				Settings.p("Spawn phase!");
				next_phase_time = System.currentTimeMillis() + SPAWN_PHASE_DURATION;
			} else {
				Settings.p("Rest phase!");
				next_phase_time = System.currentTimeMillis() + REST_PHASE_DURATION;
				towerDefenceLevel.levelNum++;
			}
		}
		if (this.spawn_phase) {
			this.towerDefenceLevel.spawnEnemiesSystem.process();
		}		
	}

}
