package com.scs.splitscreenfps.game.components;

import com.badlogic.gdx.math.Vector3;
import com.scs.splitscreenfps.Settings;

public class MovementData {

	public Vector3 offset = new Vector3();
	public float sizeAsFracOfMapsquare;
	public boolean removeIfHitWall = false;
	public boolean hitWall = false;
	//public boolean blocksMovement = false;
	
	public MovementData(float _sizeAsFracOfMapsquare) {
		sizeAsFracOfMapsquare = _sizeAsFracOfMapsquare;
		
		if (_sizeAsFracOfMapsquare > 1) {
			Settings.p("WARNING: Size is " + _sizeAsFracOfMapsquare);
		}
	}

}
