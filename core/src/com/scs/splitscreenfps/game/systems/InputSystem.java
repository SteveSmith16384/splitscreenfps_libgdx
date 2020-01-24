package com.scs.splitscreenfps.game.systems;

import com.scs.basicecs.ISystem;
import com.scs.splitscreenfps.game.Game;

public class InputSystem implements ISystem {

	private Game game;
	
	public InputSystem(Game _game) {
		game = _game;
	}

	
	@Override
	public void process() {
		for (int i=0 ; i<4 ; i++) {
			if (game.players[i] != null) {
				game.players[i].update();
				game.players[i].camera.update();
			}
		}

		/*for (int i=0 ; i<viewports.length ; i++) {
			this.viewports[i].camera.update();
		}*/
	}
}
