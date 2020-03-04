package com.scs.splitscreenfps.game;

import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractEvent;

public class EventCollision extends AbstractEvent {

	public AbstractEntity movingEntity;
	public AbstractEntity hitEntity;
	
	public EventCollision(AbstractEntity _movingEntity, AbstractEntity _hitEntity) {
		movingEntity = _movingEntity;
		hitEntity = _hitEntity;
	}

	@Override
	public boolean isForEntity(AbstractEntity e) {
		return movingEntity == e || hitEntity == e;
	}

}
