package com.scs.splitscreenfps.game.systems.deathchase;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractEvent;
import com.scs.basicecs.BasicECS;
import com.scs.basicecs.ISystem;
import com.scs.splitscreenfps.Settings;
import com.scs.splitscreenfps.game.EventCollision;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.components.PositionComponent;
import com.scs.splitscreenfps.game.components.VehicleComponent;
import com.scs.splitscreenfps.game.entities.EntityFactory;
import com.scs.splitscreenfps.game.entities.TextEntity;
import com.scs.splitscreenfps.game.systems.VehicleMovementSystem;

public class VehicleCrashSystem implements ISystem {

	private Game game;
	private BasicECS ecs;
	private int playerRespawnX = 1;
	private int playerRespawnY = 1;

	public VehicleCrashSystem(BasicECS _ecs, Game _game) {
		ecs = _ecs;
		game = _game;
	}


	@Override
	public void process() {
		List<AbstractEvent> it = ecs.getEvents(EventCollision.class);
		for (AbstractEvent e : it) {
			EventCollision evt = (EventCollision)e;
			VehicleComponent veh = (VehicleComponent)evt.movingEntity.getComponent(VehicleComponent.class);
			if (veh != null) {
				if (veh.current_speed > VehicleMovementSystem.MAX_SPEED/2) {
					this.crash(evt.movingEntity, veh.playerId);
				}
			}
		}
	}


	private void crash(AbstractEntity player, int id) {		
		// Move player back to start
		PositionComponent posData = (PositionComponent)player.getComponent(PositionComponent.class);
		posData.position.set(playerRespawnX + 0.5f, Settings.PLAYER_HEIGHT/2, playerRespawnY + 0.5f); // Start in middle of square

		if (id >= 0) {
			TextEntity te = new TextEntity(ecs, "YOU HAVE BEEN EATEN!", Gdx.graphics.getBackBufferHeight()/2, 4, new Color(0, 0, 0, 1), id, 2);
			ecs.addEntity(te);

			AbstractEntity redfilter = EntityFactory.createRedFilter(game.ecs, id);
			ecs.addEntity(redfilter);
		}
	}

}