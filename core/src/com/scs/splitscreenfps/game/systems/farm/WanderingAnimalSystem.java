package com.scs.splitscreenfps.game.systems.farm;

import java.util.List;

import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractEvent;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.game.EventCollision;
import com.scs.splitscreenfps.game.components.MovementData;
import com.scs.splitscreenfps.game.components.farm.WanderingAnimalComponent;

import ssmith.lang.NumberFunctions;

public class WanderingAnimalSystem extends AbstractSystem {

	public WanderingAnimalSystem(BasicECS ecs) {
		super(ecs, WanderingAnimalComponent.class);
	}


	@Override
	public void processEntity(AbstractEntity entity) {
		WanderingAnimalComponent wac = (WanderingAnimalComponent)entity.getComponent(WanderingAnimalComponent.class);
		MovementData md = (MovementData)entity.getComponent(MovementData.class);
		
		boolean blocked_on_last_move = false;
		List<AbstractEvent> it = ecs.getEventsForEntity(EventCollision.class, entity);
		for (AbstractEvent e : it) {
			EventCollision evt = (EventCollision)e;
			if (evt.movingEntity == entity) {
				blocked_on_last_move = true;
				break;
			}
		}

		if (wac.movementOffset.len2() == 0 || blocked_on_last_move) {
			do {
				wac.movementOffset.x = NumberFunctions.rndFloat(-1,  1);
				wac.movementOffset.y = NumberFunctions.rndFloat(-1,  1);
				wac.movementOffset.nor().scl(wac.speed); //  * Gdx.graphics.getDeltaTime()
			} while (wac.movementOffset.len2() == 0);
		}
		
		md.offset.x = wac.movementOffset.x;
		md.offset.y = 0;
		md.offset.z = wac.movementOffset.y;
	}

}
