package com.scs.splitscreenfps.game.systems;

import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.components.CanCompleteLevelComponent;
import com.scs.splitscreenfps.game.components.CompletesLevelComponent;
import com.scs.splitscreenfps.game.data.CollisionResult;
import com.scs.splitscreenfps.game.data.CollisionResultsList;

public class CompleteLevelSystem extends AbstractSystem {

	private Game game;
	
	public CompleteLevelSystem(BasicECS ecs, Game _game) {
		super(ecs, CompletesLevelComponent.class);
		
		game = _game;
	}


	@Override
	public void processEntity(AbstractEntity entity) {
		CollisionCheckSystem collCheckSystem = (CollisionCheckSystem)game.ecs.getSystem(CollisionCheckSystem.class);
		
		CollisionResultsList crl = collCheckSystem.collided(entity, 0, 0);
		for (CollisionResult cr : crl.results) {
			AbstractEntity e = cr.collidedWith;
			CanCompleteLevelComponent ccl = (CanCompleteLevelComponent)e.getComponent(CanCompleteLevelComponent.class);
			if (ccl != null) {
				game.ecs.removeSystem(CompleteLevelSystem.class);
				game.playerHasWon(e);
				break;
			}
		}
		
	}
	

}
