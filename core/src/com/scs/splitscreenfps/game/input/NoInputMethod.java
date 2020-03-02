package com.scs.splitscreenfps.game.input;

public class NoInputMethod implements IInputMethod {

	public NoInputMethod() {
	}

	@Override
	public float isForwardsPressed() {
		return 0;
	}

	@Override
	public float isBackwardsPressed() {
		return 0;
	}

	@Override
	public float isStrafeLeftPressed() {
		return 0;
	}

	@Override
	public float isStrafeRightPressed() {
		return 0;
	}

	@Override
	public boolean isCirclePressed() {
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

	@Override
	public boolean isCrossPressed() {
		return false;
	}

}
