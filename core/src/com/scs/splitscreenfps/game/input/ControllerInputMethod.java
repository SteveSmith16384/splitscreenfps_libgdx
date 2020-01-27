package com.scs.splitscreenfps.game.input;

import com.badlogic.gdx.controllers.Controller;

public class ControllerInputMethod implements IInputMethod {

	public Controller controller;

	public ControllerInputMethod(Controller _controller) {
		controller = _controller;
	}

	@Override
	public boolean isForwardsPressed() {
		return controller.getAxis(1) < -0.5f;
	}

	@Override
	public boolean isBackwardsPressed() {
		return controller.getAxis(1) > -0.5f;
	}

	@Override
	public boolean isStrafeLeftPressed() {
		return controller.getAxis(0) < -0.5f;
	}

	@Override
	public boolean isStrafeRightPressed() {
		return controller.getAxis(0) > -0.5f;
	}

	@Override
	public boolean isShootPressed() {
		return this.controller.getButton(1);	
	}

	@Override
	public boolean isMouse() {
		return false;
	}

	@Override
	public float getLookLeft() {
		return controller.getAxis(2);
	}

	@Override
	public float getLookRight() {
		return -controller.getAxis(2);
	}

	@Override
	public float getLookUp() {
		return controller.getAxis(3);
	}

	@Override
	public float getLookDown() {
		return -controller.getAxis(3);
	}

}
