package com.scs.splitscreenfps.game.components;

import com.badlogic.gdx.math.Vector3;
import com.scs.splitscreenfps.Settings;

public class MovementData {

	public Vector3 offset = new Vector3();
	public float diameter; // For collisions against walls
	public long frozenUntil = 0;
	public boolean blocked_on_last_move;
	
	public MovementData(float _diameter) {
		diameter = _diameter;
		
		if (diameter > 1) {
			Settings.pe("WARNING: Size is " + diameter);
		}
	}

}
