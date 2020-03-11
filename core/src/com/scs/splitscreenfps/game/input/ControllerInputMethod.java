package com.scs.splitscreenfps.game.input;

import com.badlogic.gdx.controllers.Controller;

public class ControllerInputMethod implements IInputMethod {

	public Controller controller;

	public ControllerInputMethod(Controller _controller) {
		controller = _controller;
	}

	@Override
	public float isForwardsPressed() {
		return -controller.getAxis(1);
	}

	@Override
	public float isBackwardsPressed() {
		return controller.getAxis(1);
	}

	@Override
	public float isStrafeLeftPressed() {
		return -controller.getAxis(0);
	}

	@Override
	public float isStrafeRightPressed() {
		return controller.getAxis(0);
	}

	@Override
	public boolean isMouse() {
		return false;
	}

	@Override
	public float getLookLeft() {
		return -controller.getAxis(2);
	}

	@Override
	public float getLookRight() {
		return controller.getAxis(2);
	}

	@Override
	public float getLookUp() {
		return -controller.getAxis(3);
	}

	@Override
	public float getLookDown() {
		return controller.getAxis(3);
	}

	@Override
	public boolean isCrossPressed() {
		return this.controller.getButton(0); // todo - check	
	}

	@Override
	public boolean isCirclePressed() {
		return this.controller.getButton(1); // todo - check	
	}

	@Override
	public boolean isTrianglePressed() {
		return this.controller.getButton(2); // todo - check	
	}

	@Override
	public boolean isL1ressed() {
		return this.controller.getButton(9); // todo - check	
	}

	@Override
	public boolean isR1Pressed() {
		return this.controller.getButton(10); // todo - check	
	}

}
