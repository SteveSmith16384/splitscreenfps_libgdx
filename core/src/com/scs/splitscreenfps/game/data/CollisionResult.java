package com.scs.splitscreenfps.game.data;

import com.scs.basicecs.AbstractEntity;

public class CollisionResult {

	public AbstractEntity collidedWith;
	public boolean blocksMovement;
	
	public CollisionResult(AbstractEntity _collidedWith, boolean _blocksMovement) {
		collidedWith = _collidedWith;
		blocksMovement = _blocksMovement;
	}

}
