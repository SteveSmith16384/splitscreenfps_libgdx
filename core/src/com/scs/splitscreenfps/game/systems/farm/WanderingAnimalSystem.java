package com.scs.splitscreenfps.game.systems.farm;

import com.badlogic.gdx.Gdx;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
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
		
		if (wac.movementOffset.len2() == 0 || md.blocked_on_last_move) {
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
