package com.scs.splitscreenfps.game.systems;

import java.util.Iterator;

import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.Settings;
import com.scs.splitscreenfps.game.components.CollidesComponent;
import com.scs.splitscreenfps.game.components.PositionData;
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


	public CollisionResultsList collided(AbstractEntity mover, float offX, float offZ) {
		CollisionResultsList cr = new CollisionResultsList();

		// Move bounding box to correct position
		PositionData posData = (PositionData)mover.getComponent(PositionData.class);
		CollidesComponent moverCC = (CollidesComponent)mover.getComponent(CollidesComponent.class);
		moverCC.bb.setCentre(posData.position.x + offX, posData.position.y, posData.position.z + offZ);

		Iterator<AbstractEntity> it = entities.iterator();
		while (it.hasNext()) {
			AbstractEntity e = it.next();
			if (e != mover) {
				CollidesComponent cc = (CollidesComponent)e.getComponent(CollidesComponent.class);
				if (cc != null) {
					if (moverCC.bb.intersects(cc.bb)) {
						cr.AddCollisionResult(new CollisionResult(e, cc.blocksMovement));
						if (cc.blocksMovement) {
							Settings.p("Blocked by " + e);
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
