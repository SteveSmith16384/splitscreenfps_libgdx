package com.scs.splitscreenfps.game.systems;

import java.util.Iterator;

import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.Settings;
import com.scs.splitscreenfps.game.EventCollision;
import com.scs.splitscreenfps.game.components.CollidesComponent;
import com.scs.splitscreenfps.game.components.PositionComponent;
import com.scs.splitscreenfps.game.data.CollisionResult;
import com.scs.splitscreenfps.game.data.CollisionResultsList;

public class CollisionCheckSystem extends AbstractSystem {

	public CollisionCheckSystem(BasicECS ecs) {
		super(ecs);
	}


	@Override
	public Class<?> getComponentClass() {
		return CollidesComponent.class;
	}


	private void setBB(AbstractEntity mover, CollidesComponent moverCC, float offX, float offZ) {
		PositionComponent posData = (PositionComponent)mover.getComponent(PositionComponent.class);
		moverCC.bb.setCentre(posData.position.x + offX, posData.position.y, posData.position.z + offZ);

	}

	public CollisionResultsList collided(AbstractEntity mover, float offX, float offZ, boolean raise_event) {
		CollisionResultsList cr = new CollisionResultsList();

		CollidesComponent moverCC = (CollidesComponent)mover.getComponent(CollidesComponent.class);

		this.setBB(mover, moverCC, offX, offZ);
		moverCC.bb_dirty = true; // So we move it back afterwards

		Iterator<AbstractEntity> it = entities.iterator();
		while (it.hasNext()) {
			AbstractEntity e = it.next();
			if (e != mover) {
				CollidesComponent cc = (CollidesComponent)e.getComponent(CollidesComponent.class);
				if (cc != null) {
					if (cc.bb_dirty) {
						setBB(e, cc, 0, 0);
						cc.bb_dirty = false;
					}
					if (moverCC.bb.intersects(cc.bb)) {
						//Settings.p(mover + " collided with " + e);
						cr.AddCollisionResult(new CollisionResult(e, cc.blocksMovement));

						if (raise_event) {
							ecs.events.add(new EventCollision(mover, e));
						}
					}
				}
			}
		}

		// If we failed to move, move bounding box back
		/*if (cr.blocksMovement) {
			// Move us back
			moverCC.bb.setCentre(posData.position.x, posData.position.y, posData.position.z);
		}*/

		return cr;
	}

}
