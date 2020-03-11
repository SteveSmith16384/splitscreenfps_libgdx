package com.scs.splitscreenfps.game.systems.towerdefence;

import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.game.components.towerdefence.CanBeDamagedByEnemyComponent;

public class CheckAltarSystem extends AbstractSystem {

	public CheckAltarSystem(BasicECS ecs) {
		super(ecs, CanBeDamagedByEnemyComponent.class);
	}


	@Override
	public void processEntity(AbstractEntity entity) {
		// todo
	}

}
