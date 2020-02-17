package com.scs.splitscreenfps.game.systems;

import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.Settings;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.components.CanBeCarried;
import com.scs.splitscreenfps.game.components.CanCarryComponent;
import com.scs.splitscreenfps.game.components.CollidesComponent;
import com.scs.splitscreenfps.game.components.HasDecal;
import com.scs.splitscreenfps.game.components.HasGuiSpriteComponent;
import com.scs.splitscreenfps.game.components.litter.CombinesWithLitterComponent;
import com.scs.splitscreenfps.game.data.CollisionResult;
import com.scs.splitscreenfps.game.data.CollisionResultsList;

public class PickupDropSystem extends AbstractSystem {

	private Game game;

	public PickupDropSystem(BasicECS ecs, Game _game) {
		super(ecs);

		game = _game;
	}


	@Override
	public Class<?> getComponentClass() {
		return CanCarryComponent.class;
	}


	@Override
	public void processEntity(AbstractEntity entity) {
		CanCarryComponent cc = (CanCarryComponent)entity.getComponent(CanCarryComponent.class);
		if (cc.carrying == null) {
			CollisionCheckSystem collCheckSystem = (CollisionCheckSystem)game.ecs.getSystem(CollisionCheckSystem.class);
			CollisionResultsList crl = collCheckSystem.collided(entity, 0, 0);
			for (CollisionResult cr : crl.results) {
				AbstractEntity key = cr.collidedWith;
				CanBeCarried cbc = (CanBeCarried)key.getComponent(CanBeCarried.class);
				if (cbc != null) {
					if (cc.wantsToCarry || cbc.auto_pickedup) {
						//cc.wantsToCarry = false;
						Settings.p(cr.collidedWith + " picked up");
						key.hideComponent(CollidesComponent.class);
						key.hideComponent(HasDecal.class);
						key.hideComponent(CombinesWithLitterComponent.class);
						key.restoreComponent(HasGuiSpriteComponent.class);
						
						HasGuiSpriteComponent hgsc = (HasGuiSpriteComponent)cr.collidedWith.getComponent(HasGuiSpriteComponent.class);
						hgsc.onlyViewId = cc.viewId;
						cc.carrying = cr.collidedWith;
						break;
					}
				}
			}
		} else {
			// Drop!
			/*todo CanBeCarried cbc = (CanBeCarried)cc.carrying.getComponent(CanBeCarried.class);
			if (cbc != null) {
				Settings.p(cc.carrying + " dropped");
				cc.carrying.restoreComponent(CollidesComponent.class);
				cc.carrying.restoreComponent(HasDecal.class);
				cc.carrying.restoreComponent(CombinesWithLitterComponent.class);
				cc.carrying.hideComponent(HasGuiSpriteComponent.class);

				// Set position
				PositionComponent carrierPos = (PositionComponent)entity.getComponent(PositionComponent.class);
				PositionComponent pos = (PositionComponent)cc.carrying.getComponent(PositionComponent.class);
				pos.originalPosition.set(carrierPos.originalPosition);

				cc.carrying = null;*/
		}
	}

}
