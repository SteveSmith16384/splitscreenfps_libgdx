package com.scs.splitscreenfps.game.systems.towerdefence;

import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.components.towerdefence.CanBeDamagedByEnemyComponent;

public class CheckAltarSystem extends AbstractSystem {

	private Game game;
	
	public CheckAltarSystem(BasicECS ecs, Game _game) {
		super(ecs, CanBeDamagedByEnemyComponent.class);
		
		game = _game;
	}


	@Override
	public void processEntity(AbstractEntity entity) {
		if (entity.isMarkedForRemoval()) {
			game.playerHasWon(null);
		}
	}

}
