package com.scs.splitscreenfps.game.input;

public interface IInputMethod {
	
	boolean isMouse(); // Mouse has extra features like capturing the window

	float isForwardsPressed(); // todo - rename these

	float isBackwardsPressed();

	float isStrafeLeftPressed();

	float isStrafeRightPressed();
	
	float getLookLeft();

	float getLookRight();

	float getLookUp();

	float getLookDown();

	boolean isCirclePressed();
	
	boolean isCrossPressed();
	
	boolean isTrianglePressed();
	
	boolean isL1ressed();

	boolean isR1Pressed();
	
}
