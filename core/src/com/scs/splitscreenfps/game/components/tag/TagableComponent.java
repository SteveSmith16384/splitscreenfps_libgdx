package com.scs.splitscreenfps.game.components.tag;

import com.scs.basicecs.AbstractEntity;
import com.scs.splitscreenfps.game.components.AnimatedComponent;
import com.scs.splitscreenfps.game.components.HasModelComponent;

public class TagableComponent {

	public float timeLeftAsIt = 100; // When down to 0, game over!
	
	public AbstractEntity player;
	public int playerIdx;
	
	// Fields for when swapping models
	public HasModelComponent storedHasModel;
	//public AnimatedComponent storedAvatarAnim;
	public AnimatedComponent storedAnimated;
	
	public TagableComponent(AbstractEntity _player, int _playerIdx) {
		player = _player;
		playerIdx = _playerIdx;
	}
}
