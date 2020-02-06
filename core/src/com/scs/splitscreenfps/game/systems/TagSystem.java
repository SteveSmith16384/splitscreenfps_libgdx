package com.scs.splitscreenfps.game.systems;

import com.badlogic.gdx.Gdx;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.game.components.CanTagComponent;
import com.scs.splitscreenfps.game.components.CollidesComponent;
import com.scs.splitscreenfps.game.data.CollisionResult;

public class TagSystem extends AbstractSystem {

	private static final long TAG_INTERVAL = 4000;
	
	public AbstractEntity currentIt;
	public long lastTagTime = 0;

	public TagSystem(BasicECS ecs) {
		super(ecs);
	}


	@Override
	public Class<?> getComponentClass() {
		return CanTagComponent.class;
	}


	@Override
	public void processEntity(AbstractEntity entity) {
		if (System.currentTimeMillis() < this.lastTagTime + TAG_INTERVAL) {
			return;
		}
		if (entity != this.currentIt) {
			return;
		}
		CanTagComponent ctc = (CanTagComponent)entity.getComponent(CanTagComponent.class);
		ctc.timeAsIt += Gdx.graphics.getDeltaTime();
		
		CollidesComponent cc = (CollidesComponent)entity.getComponent(CollidesComponent.class);
		for (CollisionResult cr : cc.results) {
			AbstractEntity e = cr.collidedWith;
			CanTagComponent ic = (CanTagComponent)e.getComponent(CanTagComponent.class);
			if (ic != null) {
				this.currentIt = cr.collidedWith;
				lastTagTime = System.currentTimeMillis();
			}
		}

	}

}
