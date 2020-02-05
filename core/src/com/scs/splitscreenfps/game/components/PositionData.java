package com.scs.splitscreenfps.game.components;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector3;

public class PositionData {

	public Vector3 position; // Used to determine the centre of the bounding box for collisions!
	public Vector3 originalPosition = new Vector3();

	public PositionData() {
		this.position = new Vector3();
	}
	
	
	public PositionData(float x, float z) {
		this.position = new Vector3(x, 0, z);
	}
	
	
	public GridPoint2 getMapPos() {
		float x = (position.x);// + 0.5f;
		float y = position.z;// + 0.5f;
		
		return new GridPoint2((int)x, (int)y) ;

	}
	
}
