package com.scs.splitscreenfps.game.systems;

import com.scs.basicecs.AbstractEntity;

import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.Settings;
import com.scs.splitscreenfps.game.components.CanBeHarmedComponent;
import com.scs.splitscreenfps.game.components.CollidesComponent;
import com.scs.splitscreenfps.game.components.TRexHarmsPlayerComponent;
import com.scs.splitscreenfps.game.components.MovementData;
import com.scs.splitscreenfps.game.components.PositionComponent;
import com.scs.splitscreenfps.game.data.CollisionResult;

public class TRexHarmsPlayerSystem extends AbstractSystem {

	private int startX, startY;
	
	public TRexHarmsPlayerSystem(BasicECS ecs, int _startX, int _startY) {
		super(ecs, TRexHarmsPlayerComponent.class);
		
		startX = _startX;
		startY = _startY;
	}


	@Override
	public void processEntity(AbstractEntity entity) {
		TRexHarmsPlayerComponent hpc = (TRexHarmsPlayerComponent)entity.getComponent(TRexHarmsPlayerComponent.class);
		CollidesComponent cc = (CollidesComponent)entity.getComponent(CollidesComponent.class);
		for (CollisionResult cr : cc.results) {
			AbstractEntity e = cr.collidedWith;
			CanBeHarmedComponent ic = (CanBeHarmedComponent)e.getComponent(CanBeHarmedComponent.class);
			if (ic != null) {
				// Move player back to start
				PositionComponent posData = (PositionComponent)e.getComponent(PositionComponent.class);
				posData.position.set(startX + 0.5f, Settings.PLAYER_HEIGHT/2, startY + 0.5f); // Start in middle of square

				// Freeze t-rex for a bit
				MovementData movementData = (MovementData)entity.getComponent(MovementData.class);
				movementData.frozenUntil = System.currentTimeMillis() + 4000;
			}
		}
	}
	
}
