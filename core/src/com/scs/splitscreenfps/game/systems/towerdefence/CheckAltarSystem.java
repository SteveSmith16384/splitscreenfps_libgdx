package com.scs.splitscreenfps.game.systems.towerdefence;

import java.util.ArrayList;
import java.util.List;

import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.BasicECS;
import com.scs.basicecs.ISystem;
import com.scs.splitscreenfps.game.Game;

public class CheckAltarSystem implements ISystem {

	private Game game;
	public List<AbstractEntity> altars = new ArrayList<AbstractEntity>();

	public CheckAltarSystem(BasicECS ecs, Game _game) {
		//super(ecs, CanBeDamagedByEnemyComponent.class);

		game = _game;
	}


	@Override
	public void process() {
		for (AbstractEntity altar : altars) {
			if (altar.isMarkedForRemoval() == false) {
				return;
			}
		}
		game.playerHasWon(null);
	}

}
