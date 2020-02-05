package com.scs.splitscreenfps.game.systems;

import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.components.CanBeCarried;
import com.scs.splitscreenfps.game.components.CanCarry;
import com.scs.splitscreenfps.game.components.CollidesComponent;
import com.scs.splitscreenfps.game.components.HasDecal;
import com.scs.splitscreenfps.game.data.CollisionResult;
import com.scs.splitscreenfps.game.data.CollisionResultsList;

public class PickupSystem extends AbstractSystem {

	private Game game;

	public PickupSystem(BasicECS ecs, Game _game) {
		super(ecs);

		game = _game;
	}


	@Override
	public Class<?> getComponentClass() {
		return CanCarry.class;
	}


	@Override
	public void processEntity(AbstractEntity entity) {
		CanCarry cc = (CanCarry)entity.getComponent(CanCarry.class);
		if (cc.wantsToCarry) {
			cc.wantsToCarry = false;

			if (cc.carrying == null) {
				CollisionCheckSystem collCheckSystem = (CollisionCheckSystem)game.ecs.getSystem(CollisionCheckSystem.class);

				CollisionResultsList crl = collCheckSystem.collided(entity, 0, 0);
				for (CollisionResult cr : crl.results) {
					CanBeCarried cbc = (CanBeCarried)cr.collidedWith.getComponent(CanBeCarried.class);
					if (cbc != null) {
						cc.carrying = cr.collidedWith;
						cr.collidedWith.hideComponent(CollidesComponent.class);
						cr.collidedWith.hideComponent(HasDecal.class);
					}
				}
			} else {
				// Drop!
			}
		}
	}

}
