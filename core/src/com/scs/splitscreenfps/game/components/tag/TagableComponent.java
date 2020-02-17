package com.scs.splitscreenfps.game.components.tag;

import com.scs.basicecs.AbstractEntity;
import com.scs.splitscreenfps.game.components.AnimatedComponent;
import com.scs.splitscreenfps.game.components.AnimatedForAvatarComponent;
import com.scs.splitscreenfps.game.components.HasModel;

public class TagableComponent {

	public float timeLeftAsIt = 100; // When down to 0, game over!
	
	public AbstractEntity player;
	public int playerIdx;
	
	// Fields for when swapping models
	public HasModel storedHasModel;
	public AnimatedForAvatarComponent storedAvatarAnim;
	public AnimatedComponent storedAnimated;
	
	public TagableComponent(AbstractEntity _player, int _playerIdx) {
		player = _player;
		playerIdx = _playerIdx;
	}
}
