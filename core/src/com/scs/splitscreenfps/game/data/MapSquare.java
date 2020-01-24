package com.scs.splitscreenfps.game.data;

import com.scs.basicecs.AbstractEntity;

public class MapSquare {

	public boolean blocked = false;
	public AbstractEntity wall;
	
	public MapSquare() {

	}

	public MapSquare(boolean _blocked) {
		blocked = _blocked;
	}

}
