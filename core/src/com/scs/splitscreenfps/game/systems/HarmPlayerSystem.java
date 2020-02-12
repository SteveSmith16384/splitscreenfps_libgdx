package com.scs.splitscreenfps.game.systems;

import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.Settings;
import com.scs.splitscreenfps.game.components.CanBeHarmedComponent;
import com.scs.splitscreenfps.game.components.CollidesComponent;
import com.scs.splitscreenfps.game.components.HarmsPlayerComponent;
import com.scs.splitscreenfps.game.components.IsCollectable;
import com.scs.splitscreenfps.game.components.MovementData;
import com.scs.splitscreenfps.game.components.PositionComponent;
import com.scs.splitscreenfps.game.data.CollisionResult;

public class HarmPlayerSystem extends AbstractSystem {

	public HarmPlayerSystem(BasicECS ecs) {
		super(ecs, HarmsPlayerComponent.class);
	}


	@Override
	public void processEntity(AbstractEntity entity) {
		HarmsPlayerComponent hpc = (HarmsPlayerComponent)entity.getComponent(HarmsPlayerComponent.class);
		CollidesComponent cc = (CollidesComponent)entity.getComponent(CollidesComponent.class);
		for (CollisionResult cr : cc.results) {
			AbstractEntity e = cr.collidedWith;
			CanBeHarmedComponent ic = (CanBeHarmedComponent)e.getComponent(IsCollectable.class);
			if (ic != null) {
				// Move player back to start
				PositionComponent posData = (PositionComponent)e.getComponent(PositionComponent.class);
				//todo posData.position.set(currentLevel.getPlayerStartMap(idx).x + 0.5f, Settings.PLAYER_HEIGHT/2, currentLevel.getPlayerStartMap(idx).y + 0.5f); // Start in middle of square

				MovementData movementData = (MovementData)e.getComponent(MovementData.class);
				movementData.frozenUntil = System.currentTimeMillis() + 4000;

			}
		}

	}
}
