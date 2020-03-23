package com.scs.splitscreenfps.game.systems.towerdefence;

import java.util.List;

import com.scs.basicecs.AbstractEntity;
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

			AbstractEntity[] ents = coll.getEntitiesByComponent(TowerDefencePlayerData.class, IsCoinComponent.class);
			if (ents != null) {
				//Object coin = ents[1].getComponent(IsCoinComponent.class);
				TowerDefencePlayerData player = (TowerDefencePlayerData)ents[0].getComponent(TowerDefencePlayerData.class);
				ents[1].remove();
				player.coins++;
			}
		}

	}

}
