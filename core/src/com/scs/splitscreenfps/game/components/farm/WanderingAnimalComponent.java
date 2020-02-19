package com.scs.splitscreenfps.game.components.farm;

import com.badlogic.gdx.math.Vector2;

public class WanderingAnimalComponent {

	public Vector2 movementOffset = new Vector2();
	public float speed;
	
	public WanderingAnimalComponent(float _speed) {
		speed = _speed;
	}

}
