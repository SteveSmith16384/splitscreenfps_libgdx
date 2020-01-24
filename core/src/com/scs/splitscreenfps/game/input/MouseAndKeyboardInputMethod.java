package com.scs.splitscreenfps.game.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class MouseAndKeyboardInputMethod implements IInputMethod {

	public MouseAndKeyboardInputMethod() {
	}

	@Override
	public boolean isForwardsPressed() {
		return Gdx.input.isKeyPressed(Keys.W);
	}

	@Override
	public boolean isBackwardsPressed() {
		return Gdx.input.isKeyPressed(Keys.S);
	}

	@Override
	public boolean isStrafeLeftPressed() {
		return Gdx.input.isKeyPressed(Keys.A);
	}

	@Override
	public boolean isStrafeRightPressed() {
		return Gdx.input.isKeyPressed(Keys.D);
	}

	@Override
	public boolean isShootPressed() {
		return Gdx.input.isKeyPressed(Keys.SPACE);
	}

	@Override
	public boolean isMouse() {
		return true;
	}

	@Override
	public float getLookLeft() {
		// Not used in this implementation
		return 0;
	}

	@Override
	public float getLookRight() {
		// Not used in this implementation
		return 0;
	}

	@Override
	public float getLookUp() {
		// Not used in this implementation
		return 0;
	}

	@Override
	public float getLookDown() {
		// Not used in this implementation
		return 0;
	}

}
