package com.scs.splitscreenfps.game.components;

import ssmith.libgdx.MyBoundingBox;

public class CollidesComponent {

	public MyBoundingBox bb;
	public boolean blocksMovement = true;

	public CollidesComponent(MyBoundingBox _bb) {
		bb = _bb;
	}

}
