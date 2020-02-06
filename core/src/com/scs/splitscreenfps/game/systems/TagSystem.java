package com.scs.splitscreenfps.game.systems;

import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.Settings;
import com.scs.splitscreenfps.game.components.CanTagComponent;
import com.scs.splitscreenfps.game.components.CollidesComponent;
import com.scs.splitscreenfps.game.components.IsCollectable;
import com.scs.splitscreenfps.game.data.CollisionResult;

public class TagSystem extends AbstractSystem {

	public TagSystem(BasicECS ecs) {
		super(ecs);
	}


	@Override
	public Class<?> getComponentClass() {
		return CanTagComponent.class;
	}


	@Override
	public void processEntity(AbstractEntity entity) {
		CollidesComponent cc = (CollidesComponent)entity.getComponent(CollidesComponent.class);
		for (CollisionResult cr : cc.results) {
			AbstractEntity e = cr.collidedWith;
			IsCollectable ic = (IsCollectable)e.getComponent(IsCollectable.class);
			if (ic != null) {
				// todo - what happens when collected?
				Settings.p(e + " collected");
				e.remove();
			}
		}

	}

}
