package com.scs.splitscreenfps.game.components;

public class TagableComponent {

	public float timeLeftAsIt = 100; // When down to 0, game over!
	
	public int playerId;
	
	// Fields for when swapping models
	public HasModel hasModel;
	public AnimatedForAvatarComponent avatarAnim;
	public AnimatedComponent animated;
	
	public TagableComponent(int _playerId) {
		playerId = _playerId;
	}
}
