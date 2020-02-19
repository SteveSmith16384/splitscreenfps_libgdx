package com.scs.splitscreenfps.game.systems.monstermaze;

import java.util.Iterator;

import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.game.components.HasDecal;
import com.scs.splitscreenfps.game.components.monstermaze.MonsterMazeKeyComponent;
import com.scs.splitscreenfps.game.levels.MonsterMazeLevel;

public class RegenKeySystem extends AbstractSystem {

	private MonsterMazeLevel level;
	private long regen_time;

	public RegenKeySystem(BasicECS ecs, MonsterMazeLevel _level) {
		super(ecs, MonsterMazeKeyComponent.class);

		level = _level;
	}


	@Override
	public void process() {
		if (System.currentTimeMillis() < this.regen_time) {
			return;
		}

		this.regen_time = System.currentTimeMillis() + 5000;

		boolean found = false;

		Iterator<AbstractEntity> it = entities.iterator();
		while (it.hasNext()) {
			AbstractEntity entity = it.next();
			if (entity.isMarkedForRemoval()) {
				continue;
			}
			if (entity.getComponent(HasDecal.class) != null) {
				found = true;
				break;
			}
		}
		
		if (found == false) {
			level.createKey();
		}

	}

}
