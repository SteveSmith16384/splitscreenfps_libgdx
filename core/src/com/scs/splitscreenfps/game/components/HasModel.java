package com.scs.splitscreenfps.game.components;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.collision.BoundingBox;

public class HasModel {

	public String name;
	public ModelInstance model;
	public float yOffset;
	public int angleOffset;
	public float scale;
	public int dontDrawInViewId = -1; // Don't draw the player's own avatar!
	public BoundingBox bb; // For checking if in frustum  
	public boolean always_draw = false;
	
	public HasModel(String name, ModelInstance _model) {
		this(name, _model, 0, 0, 1);
	}
	
	public HasModel(String _name, ModelInstance _model, float _yOffset, int _angleOffset, float _scale) {
		name = _name;
		model = _model;
		yOffset = _yOffset;
		angleOffset = _angleOffset;
		scale = _scale;
	}

	
	@Override
	public String toString() {
		return this.name;
	}
}
