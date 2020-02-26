package com.scs.splitscreenfps.game.components;

import com.badlogic.gdx.math.Vector3;

public class DrawTextIn3DSpaceComponent {

	public float range;
	public Vector3 pos;
	public String text;
	
	public DrawTextIn3DSpaceComponent(String _text, float x, float z, float _range) {
		text = _text;
		pos = new Vector3(x, .5f, z);
		range = _range;
	}

}
