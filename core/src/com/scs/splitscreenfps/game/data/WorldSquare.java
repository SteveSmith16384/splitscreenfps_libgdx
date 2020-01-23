package com.scs.splitscreenfps.game.data;

import com.scs.basicecs.AbstractEntity;

public class WorldSquare {

	public boolean blocked = false;
	public AbstractEntity wall;
	
	public WorldSquare() {

	}

	public WorldSquare(boolean _blocked) {
		blocked = _blocked;
	}

}
