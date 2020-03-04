package com.scs.splitscreenfps.game.systems.monstermaze;

import com.scs.basicecs.ISystem;
import com.scs.splitscreenfps.BillBoardFPS_Main;

import ssmith.lang.NumberFunctions;

public class MonsterGrowlsSystem implements ISystem {

	private long next_time = 0;

	public MonsterGrowlsSystem() {
	}

	@Override
	public void process() {
		if (System.currentTimeMillis() > next_time) {
			BillBoardFPS_Main.audio.play("monstermaze/sfx/Large Monster_1/Large Monster " + NumberFunctions.rnd(1, 4) + ".wav");
			next_time = System.currentTimeMillis() + NumberFunctions.rnd(4000, 8000);
		}

	}

}