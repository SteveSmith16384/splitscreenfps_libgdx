package com.scs.splitscreenfps.game.systems.ql;

import com.scs.basicecs.ISystem;
import com.scs.splitscreenfps.Settings;
import com.scs.splitscreenfps.game.levels.QuantumLeagueLevel;

public class QLPhaseSystem implements ISystem {

	private static final long GAME_PHASE_DURATION = 15000;
	private static final long REWIND_PHASE_DURATION = 1000;

	public boolean game_phase = false; // otherwise, rewind phase
	public long this_phase_start_time;
	private long next_phase_time;
	private QuantumLeagueLevel qlLevel;
	public int phase_num_012 = -1;

	public QLPhaseSystem(QuantumLeagueLevel _level) {
		qlLevel = _level;
		next_phase_time = System.currentTimeMillis() + REWIND_PHASE_DURATION;
	}


	@Override
	public void process() {
		if (this.next_phase_time < System.currentTimeMillis()) {
			this.game_phase = !this.game_phase;
			this_phase_start_time = System.currentTimeMillis();
			if (this.game_phase) {
				if (phase_num_012 <= 1) {
					Settings.p("Game phase!");
					phase_num_012++;
					qlLevel.nextGamePhase();
					next_phase_time = System.currentTimeMillis() + GAME_PHASE_DURATION;
				} else {
					qlLevel.allPhasesOver();
				}
			} else {
				Settings.p("Rewind phase!");
				qlLevel.nextRewindPhase();
				next_phase_time = System.currentTimeMillis() + REWIND_PHASE_DURATION;
			}
		}
	}

}
