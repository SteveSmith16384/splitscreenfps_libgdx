package com.scs.splitscreenfps.game.systems.deathchase;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractEvent;
import com.scs.basicecs.BasicECS;
import com.scs.basicecs.ISystem;
import com.scs.splitscreenfps.game.EventCollision;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.components.CanRespawnComponent;
import com.scs.splitscreenfps.game.components.VehicleComponent;
import com.scs.splitscreenfps.game.entities.EntityFactory;
import com.scs.splitscreenfps.game.entities.TextEntity;
import com.scs.splitscreenfps.game.systems.VehicleMovementSystem;

public class DeathchaseCrashSystem implements ISystem {

	private Game game;
	private BasicECS ecs;

	public DeathchaseCrashSystem(BasicECS _ecs, Game _game) {
		ecs = _ecs;
		game = _game;
	}


	@Override
	public void process() {
		List<AbstractEvent> it = ecs.getEvents(EventCollision.class);
		for (AbstractEvent e : it) {
			EventCollision evt = (EventCollision)e;
			VehicleComponent veh_mover = (VehicleComponent)evt.movingEntity.getComponent(VehicleComponent.class);
			if (veh_mover != null) {
				if (veh_mover.current_speed > veh_mover.max_speed/2) {
					if (evt.hitEntity == null) {
						veh_mover.current_speed = 0;
						this.crash(evt.movingEntity, veh_mover.playerId);
					} else {
						VehicleComponent veh_hit = (VehicleComponent)evt.hitEntity.getComponent(VehicleComponent.class);
						if (veh_hit != null) {
							// Hit another car!
							if (veh_mover.current_speed > veh_hit.current_speed) {
								this.crash(evt.hitEntity, veh_hit.playerId);
								veh_hit.current_speed = 0;
							} else {
								this.crash(evt.movingEntity, veh_mover.playerId);
								veh_mover.current_speed = 0;
							}
						} else {
							// Hit tree
							this.crash(evt.movingEntity, veh_mover.playerId);
							veh_mover.current_speed = 0;
						}
					}
				}
			}
		}
	}


	private void crash(AbstractEntity player, int id) {		
		// Move player back to start
		CanRespawnComponent crc = (CanRespawnComponent)player.getComponent(CanRespawnComponent.class);
		/*PositionComponent posData = (PositionComponent)player.getComponent(PositionComponent.class);
		//posData.position.set(playerRespawnX + 0.5f, Settings.PLAYER_HEIGHT/2, playerRespawnY + 0.5f); // Start in middle of square
		posData.position.set(crc.respawnPoint);
		 */
		if (id >= 0) {
			TextEntity te = new TextEntity(ecs, "YOU HAVE CRASHED!", Gdx.graphics.getBackBufferHeight()/2, 4, new Color(0, 0, 0, 1), id, 2);
			ecs.addEntity(te);

			AbstractEntity redfilter = EntityFactory.createRedFilter(game.ecs, id);
			ecs.addEntity(redfilter);
		}
		game.respawnSystem.addEntity(player, crc.respawnPoint);
	}

}
