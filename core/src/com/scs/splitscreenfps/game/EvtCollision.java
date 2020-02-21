package com.scs.splitscreenfps.game;

import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractEvent;

public class EvtCollision extends AbstractEvent {

	public AbstractEntity movingEntity;
	public AbstractEntity hitEntity;
	
	public EvtCollision(AbstractEntity _movingEntity, AbstractEntity _hitEntity) {
		movingEntity = _movingEntity;
		hitEntity = _hitEntity;
	}

}
