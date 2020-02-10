package com.scs.splitscreenfps.game.components;

import com.scs.basicecs.AbstractEntity;

public class TagableComponent {

	public float timeLeftAsIt = 100; // When down to 0, game over!
	
	public AbstractEntity playerId;
	
	// Fields for when swapping models
	public HasModel hasModel;
	public AnimatedForAvatarComponent avatarAnim;
	public AnimatedComponent animated;
	
	public TagableComponent(AbstractEntity _playerId) {
		playerId = _playerId;
	}
}
