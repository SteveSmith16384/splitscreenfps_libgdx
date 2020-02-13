package com.scs.splitscreenfps.game.data;

import java.util.ArrayList;
import java.util.List;

public class CollisionResultsList {

	public List<CollisionResult> results = new ArrayList<CollisionResult>();
	public boolean blocksMovement = false; // Do any of the collisions block our movement?

	public void AddCollisionResult(CollisionResult r) {
		if (this.results.contains(r) == false) { // Prevent registering the same collision twice
			this.results.add(r);
			this.blocksMovement = r.blocksMovement || this.blocksMovement;
		}
	}

}
