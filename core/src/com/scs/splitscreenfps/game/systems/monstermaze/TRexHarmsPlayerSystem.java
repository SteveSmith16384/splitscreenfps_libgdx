package com.scs.splitscreenfps.game.systems.monstermaze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.BillBoardFPS_Main;
import com.scs.splitscreenfps.Settings;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.components.AnimatedComponent;
import com.scs.splitscreenfps.game.components.CanBeHarmedComponent;
import com.scs.splitscreenfps.game.components.CanCarryComponent;
import com.scs.splitscreenfps.game.components.CollidesComponent;
import com.scs.splitscreenfps.game.components.MovementData;
import com.scs.splitscreenfps.game.components.PositionComponent;
import com.scs.splitscreenfps.game.components.monstermaze.TRexHarmsPlayerComponent;
import com.scs.splitscreenfps.game.data.CollisionResult;
import com.scs.splitscreenfps.game.entities.TextEntity;

import ssmith.lang.NumberFunctions;

public class TRexHarmsPlayerSystem extends AbstractSystem {

	private Game game;
	private long last_harm_done;
	private int playerRespawnX, playerRespawnY;
	
	public TRexHarmsPlayerSystem(BasicECS ecs, Game _game, int _startX, int _startY) {
		super(ecs, TRexHarmsPlayerComponent.class);
		
		game = _game;
		
		playerRespawnX = _startX;
		playerRespawnY = _startY;
	}


	@Override
	public void processEntity(AbstractEntity trex) {
		if (System.currentTimeMillis() < this.last_harm_done + 4000) {
			return;
		}

		CollidesComponent cc = (CollidesComponent)trex.getComponent(CollidesComponent.class);
		for (CollisionResult cr : cc.results) {
			AbstractEntity player = cr.collidedWith;
			CanBeHarmedComponent ic = (CanBeHarmedComponent)player.getComponent(CanBeHarmedComponent.class);
			if (ic != null) {
				// Drop key
				CanCarryComponent ccc = (CanCarryComponent)player.getComponent(CanCarryComponent.class);
				if (ccc.carrying != null) {
					ccc.carrying.remove();
					ccc.carrying = null;
				}
				
				// Move player back to start
				PositionComponent posData = (PositionComponent)player.getComponent(PositionComponent.class);
				posData.position.set(playerRespawnX + 0.5f, Settings.PLAYER_HEIGHT/2, playerRespawnY + 0.5f); // Start in middle of square

				TextEntity te = new TextEntity(ecs, "YOU HAVE BEEN EATEN!", Gdx.graphics.getBackBufferHeight()/2, 4, new Color(1, 0, 0, 1), ic.playerId, 2);
				ecs.addEntity(te);
				
				AbstractEntity redfilter = game.entityFactory.createRedFilter(ic.playerId);
				ecs.addEntity(redfilter);
				
				// Freeze t-rex for a bit
				MovementData movementData = (MovementData)trex.getComponent(MovementData.class);
				movementData.frozenUntil = System.currentTimeMillis() + 4000;
				
				AnimatedComponent anim = (AnimatedComponent)trex.getComponent(AnimatedComponent.class);
				if (anim != null) {
					anim.new_animation = anim.idle_anim_name; 
				}
				
				BillBoardFPS_Main.audio.play("audio/aargh/aargh" + NumberFunctions.rnd(0, 7) + ".ogg");
				
				last_harm_done = System.currentTimeMillis();
				
				break;
			}
		}
	}
	
}
