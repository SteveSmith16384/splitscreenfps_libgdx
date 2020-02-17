package com.scs.splitscreenfps.game.systems.monstermaze;

import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.components.CanCarryComponent;
import com.scs.splitscreenfps.game.components.monstermaze.CanUseMonsterMazeExitComponent;
import com.scs.splitscreenfps.game.components.monstermaze.MonsterMazeExitComponent;
import com.scs.splitscreenfps.game.components.monstermaze.MonsterMazeKeyComponent;
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
				CanCarryComponent cc = (CanCarryComponent)e.getComponent(CanCarryComponent.class);
				if (cc != null) {
					if (cc.carrying != null) {
						MonsterMazeKeyComponent key = (MonsterMazeKeyComponent)cc.carrying.getComponent(MonsterMazeKeyComponent.class);
						if (key != null) {
							game.ecs.removeSystem(MonsterMazeExitSystem.class);
							game.ecs.removeSystem(RegenKeySystem.class);
							game.playerHasWon(e);
							return;
						}
					}
				}
				// todo - show text "You need the key"
			}
		}
	}

}