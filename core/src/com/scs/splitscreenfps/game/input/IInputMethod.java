package com.scs.splitscreenfps.game.input;

public interface IInputMethod {
	
	boolean isMouse(); // Mouse has extra features like capturing the window

	float isForwardsPressed(); // todo - rename these

	float isBackwardsPressed();

	float isStrafeLeftPressed();

	float isStrafeRightPressed();
	
	boolean isCirclePressed();
	
	boolean isCrossPressed();
	
	float getLookLeft();

	float getLookRight();

	float getLookUp();

	float getLookDown();

}
