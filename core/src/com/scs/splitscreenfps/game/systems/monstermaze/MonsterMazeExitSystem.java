package com.scs.splitscreenfps.game.systems.monstermaze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
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
import com.scs.splitscreenfps.game.entities.TextEntity;
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
		CollisionResultsList crl = collCheckSystem.collided(entity, 0, 0, false);
		for (CollisionResult cr : crl.results) {
			AbstractEntity player = cr.collidedWith;
			CanUseMonsterMazeExitComponent cumme = (CanUseMonsterMazeExitComponent)player.getComponent(CanUseMonsterMazeExitComponent.class);
			if (cumme != null) {
				CanCarryComponent cc = (CanCarryComponent)player.getComponent(CanCarryComponent.class);
				if (cc != null) {
					if (cc.carrying != null) {
						MonsterMazeKeyComponent key = (MonsterMazeKeyComponent)cc.carrying.getComponent(MonsterMazeKeyComponent.class);
						if (key != null) {
							game.ecs.removeSystem(MonsterMazeExitSystem.class);
							game.ecs.removeSystem(MonsterMazeExitSystem.class);
							game.ecs.removeSystem(TRexHarmsPlayerSystem.class);
							game.playerHasWon(player);
							return;
						}
					}
				}
				TextEntity te = new TextEntity(ecs, "YOU NEED THE KEY!", Gdx.graphics.getBackBufferHeight()/2, 4, new Color(1, 1, 0, 1), cumme.playerIdx, 2);
				ecs.addEntity(te);
				
			}
		}
	}

}