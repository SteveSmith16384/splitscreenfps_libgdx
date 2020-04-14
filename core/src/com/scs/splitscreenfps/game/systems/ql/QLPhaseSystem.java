package com.scs.splitscreenfps.game.systems.ql;

import com.scs.basicecs.ISystem;
import com.scs.splitscreenfps.Settings;
import com.scs.splitscreenfps.game.levels.QuantumLeagueLevel;

public class QLPhaseSystem implements ISystem {

	private static final long GAME_PHASE_DURATION = 15000;
	private static final long REST_PHASE_DURATION = 5000;

	public boolean game_phase = false; // otherwise, rest phase
	public long this_phase_start_time;
	private long next_phase_time;
	private QuantumLeagueLevel qlLevel;
	public int phase_num;

	public QLPhaseSystem(QuantumLeagueLevel _level) {
		qlLevel = _level;
		next_phase_time = System.currentTimeMillis() + REST_PHASE_DURATION;
	}


	@Override
	public void process() {
		if (this.next_phase_time < System.currentTimeMillis()) {
			this.game_phase = !this.game_phase;
			this_phase_start_time = System.currentTimeMillis();
			if (this.game_phase) {
				if (phase_num <= 2) {
					qlLevel.nextPhase();
					Settings.p("Game phase!");
					next_phase_time = System.currentTimeMillis() + GAME_PHASE_DURATION;
					phase_num++;
				} else {
					qlLevel.allPhasesOver();
				}
			} else {
				Settings.p("Rest phase!");
				next_phase_time = System.currentTimeMillis() + REST_PHASE_DURATION;
			}
		}
	}

}
