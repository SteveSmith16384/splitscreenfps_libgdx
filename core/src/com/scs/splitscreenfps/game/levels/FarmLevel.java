package com.scs.splitscreenfps.game.levels;

import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.systems.farm.GrowCropsSystem;

public class FarmLevel extends AbstractLevel {

	public FarmLevel(Game _game) {
		super(_game);
	}

	@Override
	public void load() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addSystems(BasicECS ecs) {
		ecs.addSystem(new GrowCropsSystem(ecs));
		
	}

	@Override
	public void update() {
		game.ecs.processSystem(GrowCropsSystem.class);
	}

}
