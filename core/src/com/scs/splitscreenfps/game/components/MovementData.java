package com.scs.splitscreenfps.game.components;

import com.badlogic.gdx.math.Vector3;
import com.scs.splitscreenfps.Settings;

public class MovementData {

	public Vector3 offset = new Vector3();
	public float diameter;
	//public boolean removeIfHitWall = false;
	public boolean hitWall = false;
	
	public MovementData(float _diameter) {
		diameter = _diameter;
		
		if (diameter > 1) {
			Settings.p("WARNING: Size is " + diameter);
		}
	}

}
