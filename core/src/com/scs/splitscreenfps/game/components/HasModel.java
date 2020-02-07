package com.scs.splitscreenfps.game.components;

import com.badlogic.gdx.graphics.g3d.ModelInstance;

public class HasModel {

	public String name;
	public ModelInstance model;
	public float yOffset;
	public int dontDrawInViewId = -1; // Don't draw the player's own avatar!
	
	public HasModel(String name, ModelInstance _model) {
		this(name, _model, 0);
	}
	
	public HasModel(String _name, ModelInstance _model, float _yOffset) {
		model = _model;
		yOffset = _yOffset;
		name = _name;
	}

	
	@Override
	public String toString() {
		return this.name;
	}
}
