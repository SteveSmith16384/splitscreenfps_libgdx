package com.scs.splitscreenfps.game.systems;

import java.util.Iterator;

import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.game.components.CollisionComponent;
import com.scs.splitscreenfps.game.components.PositionData;
import com.scs.splitscreenfps.game.data.CollisionResults;

public class CollisionCheckSystem extends AbstractSystem {

	public CollisionCheckSystem(BasicECS ecs) {
		super(ecs);
	}


	@Override
	public Class<?> getComponentClass() {
		return CollisionComponent.class;
	}


	public CollisionResults collided(AbstractEntity mover, float offX, float offY) {
		PositionData moverPos = (PositionData)mover.getComponent(PositionData.class);
		if (moverPos == null) {
			throw new RuntimeException(mover + " has no " + PositionData.class.getSimpleName());
		}
		Iterator<AbstractEntity> it = entities.iterator();
		while (it.hasNext()) {
			AbstractEntity e = it.next();
			if (e != mover) {
				CollisionComponent cc = (CollisionComponent)e.getComponent(CollisionComponent.class);
				if (cc != null) {
					PositionData pos = (PositionData)e.getComponent(PositionData.class);
					if (pos != null) {
						// todo
						/*if (moverPos.rect.intersects(pos.rect)) {
							return new CollisionResults(e, false, cc.blocksMovement);
						}*/
					}
				}
			}
		}
		return null;
	}

	/*
	public List<AbstractEntity> getEntitiesAt(float x, float y) {
		List<AbstractEntity> ret = new ArrayList<AbstractEntity>();

		Iterator<AbstractEntity> it = this.entities.iterator();
		while (it.hasNext()) {
			AbstractEntity e = it.next();
			PositionData pos = (PositionData)e.getComponent(PositionData.class);
			if (pos != null) {
				if (pos.rect.contains(x, y)) {
					ret.add(e);
				}
			}
		}
		return ret;
	}
	 */
}
