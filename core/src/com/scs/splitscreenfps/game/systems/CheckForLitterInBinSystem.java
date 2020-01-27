package com.scs.splitscreenfps.game.systems;

import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;

public class CheckForLitterInBinSystem extends AbstractSystem {

	public CheckForLitterInBinSystem(BasicECS ecs) {
		super(ecs);
	}


	@Override
	public Class<?> getComponentClass() {
		return null;
	}

}
