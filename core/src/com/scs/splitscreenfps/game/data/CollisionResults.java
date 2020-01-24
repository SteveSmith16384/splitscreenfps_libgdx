package com.scs.splitscreenfps.game.data;

import com.scs.basicecs.AbstractEntity;

public class CollisionResults {

	public AbstractEntity collidedWith;
	public boolean blocksMovement;
	
	public CollisionResults(AbstractEntity _collidedWith, boolean _blocksMovement) {
		collidedWith = _collidedWith;
		blocksMovement = _blocksMovement;
	}

}
