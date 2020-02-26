package com.scs.splitscreenfps.game.components;

import com.badlogic.gdx.math.Vector3;

public class DrawTextIn3DSpaceComponent {

	public float range;
	public Vector3 pos;
	public String text;
	
	public DrawTextIn3DSpaceComponent(String _text, Vector3 _pos, float _range) {
		text = _text;
		pos = _pos;
		range = _range;
	}

}
