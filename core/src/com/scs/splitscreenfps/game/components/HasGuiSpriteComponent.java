package com.scs.splitscreenfps.game.components;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class HasGuiSpriteComponent {
	
	public static final int Z_FILTER = 99;
	public static final int Z_CARRIED = 50;
	public static final int Z_NORMAL = 0;

	public Sprite sprite;
	public int onlyViewId = -1;
	public int zOrder;

	public HasGuiSpriteComponent(Sprite _sprite, int _zOrder) {
		sprite = _sprite;
		zOrder = _zOrder;
	}

}
