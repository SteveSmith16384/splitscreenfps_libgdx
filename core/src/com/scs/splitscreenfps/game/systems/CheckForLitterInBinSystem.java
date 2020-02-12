package com.scs.splitscreenfps.game.systems;

import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.Settings;
import com.scs.splitscreenfps.game.components.CollidesComponent;
import com.scs.splitscreenfps.game.components.CombinesWithLitterComponent;
import com.scs.splitscreenfps.game.components.IsCollectable;
import com.scs.splitscreenfps.game.data.CollisionResult;

public class CheckForLitterInBinSystem extends AbstractSystem {

	public CheckForLitterInBinSystem(BasicECS ecs) {
		super(ecs);
	}


	@Override
	public Class<?> getComponentClass() {
		return CombinesWithLitterComponent.class;
	}


	@Override
	public void processEntity(AbstractEntity entity) {
		CollidesComponent cc = (CollidesComponent)entity.getComponent(CollidesComponent.class);
		/*if (cc == null) {
			Settings.p("null!");
		}*/
		for (CollisionResult cr : cc.results) {
			AbstractEntity e = cr.collidedWith;
			CombinesWithLitterComponent ic = (CombinesWithLitterComponent)e.getComponent(IsCollectable.class);
			if (ic != null) {
				// todo
				 // todo - match certain types of litter with certain types of bin
			}
		}
	}

}
