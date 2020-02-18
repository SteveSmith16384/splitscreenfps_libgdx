package com.scs.splitscreenfps.game.components.farm;

public class CanGrowComponent {

	public float grow_speed;
	public float current_growth;
	public float max_growth;
	
	public CanGrowComponent(float _grow_speed, float _max_growth) {
		grow_speed = _grow_speed;
		max_growth = _max_growth;
	}
}
