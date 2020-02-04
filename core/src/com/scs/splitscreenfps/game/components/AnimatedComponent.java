package com.scs.splitscreenfps.game.components;

import com.badlogic.gdx.graphics.g3d.utils.AnimationController;

public class AnimatedComponent {

	public String current_animation;
	public String new_animation;
	public AnimationController animationController;
	
	public AnimatedComponent(AnimationController _animationController, String _new_animation) {
		animationController = _animationController;
		new_animation = _new_animation;
	}
}
