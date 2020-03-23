package com.scs.splitscreenfps.game.systems;

import java.util.List;

import com.badlogic.gdx.math.Vector3;
import com.scs.basicecs.AbstractEvent;
import com.scs.basicecs.BasicECS;
import com.scs.basicecs.ISystem;
import com.scs.splitscreenfps.game.EventCollision;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.components.VehicleComponent;

public class VehicleProcessCollisionSystem implements ISystem {

	private Game game;
	private BasicECS ecs;

	public VehicleProcessCollisionSystem(BasicECS _ecs, Game _game) {
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
				if (evt.hitEntity == null) {
					veh_mover.current_speed = 0;
					veh_mover.momentum.setZero();
				} else {
					VehicleComponent veh_hit = (VehicleComponent)evt.hitEntity.getComponent(VehicleComponent.class);
					if (veh_hit != null) {
						// Hit another car!
						Vector3 mom_diff = new Vector3(veh_mover.momentum);
						//mom_diff.sub(veh_hit.momentum);

						veh_mover.momentum.set(veh_hit.momentum);
						veh_hit.momentum.set(mom_diff);
					} else {
						// Hit tree or something
						veh_mover.current_speed = 0;
						veh_mover.momentum.setZero();
					}
				}
			}
		}
	}

}
