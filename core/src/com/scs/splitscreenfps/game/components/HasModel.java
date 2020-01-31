package com.scs.splitscreenfps.game.components;

import com.badlogic.gdx.graphics.g3d.ModelInstance;

public class HasModel {

	public ModelInstance model;
	public int playerViewId = -1; // Don't draw the player's own avatar!
	
	public HasModel(ModelInstance _model) {
		model = _model;
	}

}
