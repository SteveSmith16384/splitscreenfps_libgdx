package com.scs.splitscreenfps.game.input;

public interface IInputMethod {
	
	boolean isMouse(); // Mouse has extra features like capturing the window

	boolean isForwardsPressed();

	boolean isBackwardsPressed();

	boolean isStrafeLeftPressed();

	boolean isStrafeRightPressed();
	
	boolean isPickupDropPressed(); // todo - rename to "button X"
	
	float getLookLeft();

	float getLookRight();

	float getLookUp();

	float getLookDown();

}
