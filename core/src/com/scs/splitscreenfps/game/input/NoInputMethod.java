package com.scs.splitscreenfps.game.input;

public class NoInputMethod implements IInputMethod {

	public NoInputMethod() {
	}

	@Override
	public boolean isForwardsPressed() {
		return false;
	}

	@Override
	public boolean isBackwardsPressed() {
		return false;
	}

	@Override
	public boolean isStrafeLeftPressed() {
		return false;
	}

	@Override
	public boolean isStrafeRightPressed() {
		return false;
	}

	@Override
	public boolean isShootPressed() {
		return false;
	}

	@Override
	public boolean isMouse() {
		return false;
	}

	@Override
	public float getLookLeft() {
		return 0;
	}

	@Override
	public float getLookRight() {
		return 0;//.1f;
	}

	@Override
	public float getLookUp() {
		return 0;
	}

	@Override
	public float getLookDown() {
		return 0;
	}

}
