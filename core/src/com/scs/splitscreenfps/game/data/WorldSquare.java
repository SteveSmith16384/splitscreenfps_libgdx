package com.scs.splitscreenfps.game.data;

import com.scs.basicecs.AbstractEntity;

public class WorldSquare {

	//public int type;
	public boolean blocked = false;
	public AbstractEntity wall;
	
	public WorldSquare() {

	}

/*	public WorldSquare(int _type) {
		type = _type;
	}
*/
	public WorldSquare(boolean _blocked) {
		blocked = _blocked;
	}

}
