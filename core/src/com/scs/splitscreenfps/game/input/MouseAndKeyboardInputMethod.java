package com.scs.splitscreenfps.game.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class MouseAndKeyboardInputMethod implements IInputMethod {

	public MouseAndKeyboardInputMethod() {
	}

	@Override
	public float isForwardsPressed() {
		return Gdx.input.isKeyPressed(Keys.W) ? 1 : 0;
	}

	@Override
	public float isBackwardsPressed() {
		return Gdx.input.isKeyPressed(Keys.S) ? 1 : 0;
	}

	@Override
	public float isStrafeLeftPressed() {
		return Gdx.input.isKeyPressed(Keys.A) ? 1 : 0;
	}

	@Override
	public float isStrafeRightPressed() {
		return Gdx.input.isKeyPressed(Keys.D) ? 1 : 0;
	}

	@Override
	public boolean isCirclePressed() {
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

	@Override
	public boolean isCrossPressed() {
		return Gdx.input.isKeyPressed(Keys.ENTER);
	}

	@Override
	public boolean isL1ressed() {
		return Gdx.input.isKeyPressed(Keys.NUM_1);
	}

	@Override
	public boolean isR1Pressed() {
		return Gdx.input.isKeyPressed(Keys.NUM_2);
	}

}
