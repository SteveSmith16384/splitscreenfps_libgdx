package com.scs.splitscreenfps.game.systems;

import java.util.Iterator;

import com.badlogic.gdx.math.Vector3;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.game.components.CollisionComponent;
import com.scs.splitscreenfps.game.components.PositionData;
import com.scs.splitscreenfps.game.data.CollisionResults;

public class CollisionCheckSystem extends AbstractSystem {

	private Vector3 tmp = new Vector3();

	public CollisionCheckSystem(BasicECS ecs) {
		super(ecs);
	}


	@Override
	public Class<?> getComponentClass() {
		return CollisionComponent.class;
	}


	public CollisionResults collided(AbstractEntity mover, float offX, float offZ) {
		CollisionResults cr = null;

		// Move bounding box to correct position
		PositionData posData = (PositionData)mover.getComponent(PositionData.class);
		CollisionComponent moverCC = (CollisionComponent)mover.getComponent(CollisionComponent.class);
		moverCC.bb.getCenter(tmp);
		tmp.x = posData.position.x + offX;
		tmp.y = posData.position.y;
		tmp.z = posData.position.z + offZ;

		Iterator<AbstractEntity> it = entities.iterator();
		while (it.hasNext()) {
			AbstractEntity e = it.next();
			if (e != mover) {
				CollisionComponent cc = (CollisionComponent)e.getComponent(CollisionComponent.class);
				if (cc != null) {
					if (cc.bb.intersects(moverCC.bb)) {
						cr = new CollisionResults(e, cc.blocksMovement);
						break;
					}
				}
			}
		}

		// If we failed to move, move bounding box back
		if (cr != null) {
			if (cr.blocksMovement) {
				// Move us back
				moverCC.bb.getCenter(tmp);
				tmp.x = posData.position.x;
				tmp.y = posData.position.y;
				tmp.z = posData.position.z;
			}
		}

		return cr;
	}

}
