package com.scs.splitscreenfps.game.systems;

import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.game.components.MoveAStarComponent;

public class MoveAStarSystem extends AbstractSystem {

	public MoveAStarSystem(BasicECS ecs) {
		super(ecs);
	}


	@Override
	public Class<?> getComponentClass() {
		return MoveAStarComponent.class;
	}


	@Override
	public void processEntity(AbstractEntity entity) {
	}
	
}
