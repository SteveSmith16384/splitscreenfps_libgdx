package com.scs.splitscreenfps.game.systems.monstermaze;

import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.components.CanCarry;
import com.scs.splitscreenfps.game.components.monstermaze.CanUseMonsterMazeExitComponent;
import com.scs.splitscreenfps.game.components.monstermaze.MonsterMazeExitComponent;
import com.scs.splitscreenfps.game.data.CollisionResult;
import com.scs.splitscreenfps.game.data.CollisionResultsList;
import com.scs.splitscreenfps.game.levels.MonsterMazeLevel;
import com.scs.splitscreenfps.game.systems.CollisionCheckSystem;

public class MonsterMazeExitSystem extends AbstractSystem {

	private Game game;

	public MonsterMazeExitSystem(BasicECS ecs, Game _game) {
		super(ecs, MonsterMazeExitComponent.class);

		game = _game;
	}


	@Override
	public void processEntity(AbstractEntity entity) {
		CollisionCheckSystem collCheckSystem = (CollisionCheckSystem)game.ecs.getSystem(CollisionCheckSystem.class);		
		CollisionResultsList crl = collCheckSystem.collided(entity, 0, 0);
		for (CollisionResult cr : crl.results) {
			AbstractEntity e = cr.collidedWith;
			CanUseMonsterMazeExitComponent ccl = (CanUseMonsterMazeExitComponent)e.getComponent(CanUseMonsterMazeExitComponent.class);
			if (ccl != null) {
				CanCarry cc = (CanCarry)e.getComponent(CanCarry.class);
				if (cc != null) {
					if (cc.carrying != null && cc.carrying.name == MonsterMazeLevel.KEY_NAME) {
						game.ecs.removeSystem(MonsterMazeExitSystem.class);
						game.playerHasWon(e);
						return;
					}
				}
				// todo - show text "You need the key"
			}
		}
	}

}