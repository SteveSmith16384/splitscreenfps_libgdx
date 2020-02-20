package com.scs.splitscreenfps.game;

import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractEvent;

public class EvtCollision extends AbstractEvent {

	public AbstractEntity movingEntity;
	public AbstractEntity blockingEntity;
	
	public EvtCollision() {
		// TODO Auto-generated constructor stub
	}

}
