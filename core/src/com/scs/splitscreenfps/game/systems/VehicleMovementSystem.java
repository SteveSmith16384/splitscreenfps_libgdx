package com.scs.splitscreenfps.game.systems;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractEvent;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.game.EventCollision;
import com.scs.splitscreenfps.game.components.MovementData;
import com.scs.splitscreenfps.game.components.VehicleComponent;
import com.scs.splitscreenfps.game.entities.PlayersAvatar_Car;

public class VehicleMovementSystem extends AbstractSystem {

	public static final float MAX_SPEED = 5;

	private Vector3 tmpVector = new Vector3();

	public VehicleMovementSystem(BasicECS ecs) {
		super(ecs, VehicleComponent.class);
	}


	@Override
	public void processEntity(AbstractEntity entity) {
		VehicleComponent veh = (VehicleComponent)entity.getComponent(VehicleComponent.class);
		MovementData movementData = (MovementData)entity.getComponent(MovementData.class);
		movementData.offset.setZero();
		
		// todo - wrap angle
		//while (angle > )

		List<AbstractEvent> it = ecs.getEvents(EventCollision.class);
		for (AbstractEvent e : it) {
			EventCollision evt = (EventCollision)e;
			if (evt.movingEntity == entity) {
				veh.current_speed = 0;
				return;
			}
		}

		if (veh.current_speed > MAX_SPEED) {
			veh.current_speed = MAX_SPEED;
		} else if (veh.current_speed < -MAX_SPEED) {
			veh.current_speed = -MAX_SPEED;
		} else {
			// set speed to 0 if close enough
			if (Math.abs(veh.current_speed) < PlayersAvatar_Car.ACC * .5f * Gdx.graphics.getDeltaTime()) {
				veh.current_speed = 0;
			}			
		}
		if (veh.current_speed != 0) {
			tmpVector.set((float)Math.sin(veh.angle_rads), 0, (float)Math.cos(veh.angle_rads));
			movementData.offset.set(tmpVector.nor().scl(veh.current_speed));
		}
	}

}
