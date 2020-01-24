package com.scs.splitscreenfps.game.components;

import com.badlogic.gdx.math.Vector3;
import com.scs.splitscreenfps.game.player.Player;
import com.scs.splitscreenfps.game.systems.MobAISystem.Mode;

public class HasAI {

	public Vector3 direction = new Vector3();
	public float speed;
	public Mode mode;
	public boolean can_see_player;
	public float moveRange; // Doesn't move unless player is in range
	public int changeDirTimer = 0;
	public Player player; // target
	
	public HasAI(Mode _mode, float _speed, float _moveRange, Player _player) {
		mode = _mode;
		speed = _speed;
		moveRange = _moveRange;
		player = _player;
	}
}
