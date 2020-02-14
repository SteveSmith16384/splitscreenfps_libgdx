package com.scs.splitscreenfps.game.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.Settings;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.components.CanBeHarmedComponent;
import com.scs.splitscreenfps.game.components.CollidesComponent;
import com.scs.splitscreenfps.game.components.MovementData;
import com.scs.splitscreenfps.game.components.PositionComponent;
import com.scs.splitscreenfps.game.components.TRexHarmsPlayerComponent;
import com.scs.splitscreenfps.game.data.CollisionResult;
import com.scs.splitscreenfps.game.entities.TextEntity;

public class TRexHarmsPlayerSystem extends AbstractSystem {

	private Game game;
	private long last_harm_done;
	private int startX, startY;
	
	public TRexHarmsPlayerSystem(BasicECS ecs, Game _game, int _startX, int _startY) {
		super(ecs, TRexHarmsPlayerComponent.class);
		
		game = _game;
		
		startX = _startX;
		startY = _startY;
	}


	@Override
	public void processEntity(AbstractEntity entity) {
		if (System.currentTimeMillis() < this.last_harm_done + 4000) {
			return;
		}
		//TRexHarmsPlayerComponent hpc = (TRexHarmsPlayerComponent)entity.getComponent(TRexHarmsPlayerComponent.class);
		CollidesComponent cc = (CollidesComponent)entity.getComponent(CollidesComponent.class);
		for (CollisionResult cr : cc.results) {
			AbstractEntity e = cr.collidedWith;
			CanBeHarmedComponent ic = (CanBeHarmedComponent)e.getComponent(CanBeHarmedComponent.class);
			if (ic != null) {
				// Move player back to start
				PositionComponent posData = (PositionComponent)e.getComponent(PositionComponent.class);
				posData.position.set(startX + 0.5f, Settings.PLAYER_HEIGHT/2, startY + 0.5f); // Start in middle of square

				TextEntity te = new TextEntity(ecs, "YOU HAVE BEEN EATEN!", Gdx.graphics.getBackBufferHeight()/2, 3000, new Color(1, 0, 0, 1));
				ecs.addEntity(te);
				
				//TRexHarmsPlayerComponent thp = (TRexHarmsPlayerComponent)e.getComponent(TRexHarmsPlayerComponent.class);
				AbstractEntity redfilter = game.entityFactory.createRedFilter(ic.playerId);
				ecs.addEntity(redfilter);
				
				// Freeze t-rex for a bit
				MovementData movementData = (MovementData)entity.getComponent(MovementData.class);
				movementData.frozenUntil = System.currentTimeMillis() + 4000;
				
				last_harm_done = System.currentTimeMillis();
			}
		}
	}
	
}
