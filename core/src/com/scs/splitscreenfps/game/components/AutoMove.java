package com.scs.splitscreenfps.game.components;

import com.badlogic.gdx.math.Vector3;

public class AutoMove {

	public Vector3 dir;

	public AutoMove(Vector3 _dir) {
		dir = _dir.cpy();
	}

}
