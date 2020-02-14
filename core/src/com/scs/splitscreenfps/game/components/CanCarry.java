package com.scs.splitscreenfps.game.components;

import com.scs.basicecs.AbstractEntity;

public class CanCarry {

	public boolean wantsToCarry = false;
	public AbstractEntity carrying;
	public int viewId = -1; // So we know where to draw it when picked up
	
	public CanCarry(int _viewId) {
		viewId = _viewId;
	}

}
