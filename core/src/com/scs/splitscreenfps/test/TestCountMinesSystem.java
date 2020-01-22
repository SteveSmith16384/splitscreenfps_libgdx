package com.scs.splitscreenfps.test;

import com.scs.splitscreenfps.game.entities.minedout.Mine;
import com.scs.splitscreenfps.game.player.Player;
import com.scs.splitscreenfps.game.systems.CountMinesSystem;

public class TestCountMinesSystem {

	public TestCountMinesSystem() {
		Player player = new Player(null, null, 0, 1);
		CountMinesSystem cms = new CountMinesSystem(null, player);
		
		Mine mine = new Mine(1, 1);
		cms.processEntity(mine);
	}

}
