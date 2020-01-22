package com.scs.splitscreenfps.game.components;

import com.badlogic.gdx.math.Vector3;
import com.scs.splitscreenfps.game.systems.MobAISystem.Mode;

public class HasAI {

	public Vector3 direction = new Vector3();
	public float speed;
	public Mode mode;
	public boolean can_see_player;
	public float moveRange; // Doesn't move unless player is in range
	public int changeDirTimer = 0;
	
	public HasAI(Mode _mode, float _speed, float _moveRange) {
		mode = _mode;
		speed = _speed;
		moveRange = _moveRange;
	}
}
