package com.scs.splitscreenfps.game.components;

import com.badlogic.gdx.math.Vector3;

public class VehicleComponent {

	public float max_speed = 5;
	public float traction = 2f;//0.2f;
	public float current_speed;
	public float angle_rads;
	public int playerId;
	public Vector3 momentum = new Vector3();

	public VehicleComponent(int _playerId) {
		playerId = _playerId;
	}
}
