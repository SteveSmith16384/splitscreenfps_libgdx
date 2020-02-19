package com.scs.splitscreenfps.game.systems.monstermaze;

import com.scs.basicecs.ISystem;
import com.scs.splitscreenfps.BillBoardFPS_Main;
import com.scs.splitscreenfps.Settings;

import ssmith.lang.NumberFunctions;

public class MonsterGrowlsSystem implements ISystem {

	private long next_time = 0;

	public MonsterGrowlsSystem() {
	}

	@Override
	public void process() {
		if (System.currentTimeMillis() > next_time) {
			if (Settings.TEST_ALT_TREX == false) {
				BillBoardFPS_Main.audio.play("audio/Large Monster_1/Large Monster " + NumberFunctions.rnd(1, 4) + ".wav");
			} else {
				BillBoardFPS_Main.audio.play("monstermaze/Mudchute_cow_1.ogg");
			}

			next_time = System.currentTimeMillis() + NumberFunctions.rnd(3000, 6000);
		}

	}

}