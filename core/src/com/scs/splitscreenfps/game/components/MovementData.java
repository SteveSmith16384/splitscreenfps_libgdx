package com.scs.splitscreenfps.game.components;

import com.badlogic.gdx.math.Vector3;
import com.scs.splitscreenfps.Settings;

public class MovementData {

	public Vector3 offset = new Vector3();
	public float diameter; // For collisions against walls.  Todo - move to CollisionComponent!
	public long frozenUntil = 0;
	public boolean must_move_x_and_z = false;  // Movement is only successful if they can move on both axis
	
	public MovementData(float _diameter) {
		diameter = _diameter;
		
		if (diameter > 1) {
			Settings.pe("WARNING: Size is " + diameter);
		}
	}

}
