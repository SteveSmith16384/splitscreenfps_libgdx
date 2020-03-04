package com.scs.splitscreenfps.game.systems.litter;

import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.game.components.CollidesComponent;
import com.scs.splitscreenfps.game.components.litter.CombinesWithLitterComponent;

public class CheckForLitterInBinSystem extends AbstractSystem {

	public CheckForLitterInBinSystem(BasicECS ecs) {
		super(ecs, CombinesWithLitterComponent.class);
	}


	@Override
	public void processEntity(AbstractEntity entity) {
		CollidesComponent cc = (CollidesComponent)entity.getComponent(CollidesComponent.class);
		/*for (CollisionResult cr : cc.results) { // todo - check for events instead!
			AbstractEntity e = cr.collidedWith;
			CombinesWithLitterComponent ic = (CombinesWithLitterComponent)e.getComponent(CombinesWithLitterComponent.class);
			if (ic != null) {
				// todo
				 // todo - match certain types of litter with certain types of bin
			}
		}*/
	}

}
