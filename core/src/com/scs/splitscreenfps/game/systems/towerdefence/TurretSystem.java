package com.scs.splitscreenfps.game.systems.towerdefence;

import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.game.components.towerdefence.IsTurretComponent;

public class TurretSystem extends AbstractSystem {

	public TurretSystem(BasicECS ecs) {
		super(ecs, IsTurretComponent.class);
	}


	@Override
	public void processEntity(AbstractEntity entity) {
	}

	
}
