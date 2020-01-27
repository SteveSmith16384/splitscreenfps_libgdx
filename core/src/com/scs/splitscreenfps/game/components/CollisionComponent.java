package com.scs.splitscreenfps.game.components;

import com.badlogic.gdx.math.collision.BoundingBox;

public class CollisionComponent {

	public BoundingBox bb;
	public boolean blocksMovement = true;

	public CollisionComponent(BoundingBox _bb) {
		bb = _bb;//new BoundingBox();
	}

}
