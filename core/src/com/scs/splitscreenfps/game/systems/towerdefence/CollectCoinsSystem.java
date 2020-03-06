package com.scs.splitscreenfps.game.systems.towerdefence;

import java.util.List;

import com.scs.basicecs.AbstractEvent;
import com.scs.basicecs.BasicECS;
import com.scs.basicecs.ISystem;
import com.scs.splitscreenfps.game.EventCollision;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.components.towerdefence.IsCoinComponent;
import com.scs.splitscreenfps.game.components.towerdefence.TowerDefencePlayerData;

public class CollectCoinsSystem implements ISystem {
	
	private final BasicECS ecs;
	private final Game game;

	public CollectCoinsSystem(BasicECS _ecs, Game _game) {
		ecs = _ecs;
		game = _game;
	}


	@Override
	public void process() {
		List<AbstractEvent> colls = ecs.getEvents(EventCollision.class);
		for (AbstractEvent evt : colls) {
			EventCollision coll = (EventCollision)evt;
			
			Object coin = coll.movingEntity.getComponent(IsCoinComponent.class);
			if (coin != null) {
				TowerDefencePlayerData player = (TowerDefencePlayerData)coll.hitEntity.getComponent(TowerDefencePlayerData.class);
				if (player != null) {
					coll.movingEntity.remove();
					player.coins++;
				}
			} else {
				Object coin2 = coll.hitEntity.getComponent(IsCoinComponent.class);
				if (coin2 != null) {
					TowerDefencePlayerData player = (TowerDefencePlayerData)coll.movingEntity.getComponent(TowerDefencePlayerData.class);
					if (player != null) {
						coll.hitEntity.remove();
						player.coins++;
					}
				
				}
			}
		}
		
	}

}
