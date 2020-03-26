package com.scs.splitscreenfps.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.scs.splitscreenfps.Settings;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.ViewportData;
import com.scs.splitscreenfps.game.components.CollidesComponent;
import com.scs.splitscreenfps.game.components.HasModelComponent;
import com.scs.splitscreenfps.game.components.MovementData;
import com.scs.splitscreenfps.game.components.PositionComponent;
import com.scs.splitscreenfps.game.components.VehicleComponent;
import com.scs.splitscreenfps.game.input.IInputMethod;
import com.scs.splitscreenfps.game.input.NoInputMethod;
import com.scs.splitscreenfps.game.systems.VehicleMovementSystem;

import ssmith.lang.NumberFunctions;
import ssmith.libgdx.ModelFunctions;

public class PlayersAvatar_Car extends AbstractPlayersAvatar {

	private static final boolean SHOULDER_VIEW = true;
	private static final float MAX_CAM_BOUNCE = .04f;
	private static final float ROT_SPEED_Y = 1f;//4f;
	private static final float CAM_BACK = 2f;
	public static final float ACC = 2;
	
	private Vector3 tmpCamPos = new Vector3();

	public PlayersAvatar_Car(Game _game, int playerIdx, ViewportData _viewportData, IInputMethod _inputMethod) {
		super(_game.ecs, playerIdx, PlayersAvatar_Car.class.getSimpleName() + "_" + playerIdx);

		game = _game;
		inputMethod = _inputMethod;

		MovementData md = new MovementData();
		md.must_move_x_and_z = true;
		this.addComponent(md);
		this.addComponent(new PositionComponent());

		this.addCar(playerIdx);

		this.addComponent(new VehicleComponent(playerIdx));
		this.addComponent(new CollidesComponent(true, .45f));

		camera = _viewportData.camera;
	}


	private void addCar(int idx) {
		ModelInstance instance = ModelFunctions.loadModel("shared/models/kenney_car_kit/hatchbackSports.g3db", true); // todo - diff car per player
		float scale = ModelFunctions.getScaleForHeight(instance, .6f);
		instance.transform.scl(scale);

		Vector3 offset = ModelFunctions.getOrigin(instance);
		offset.y -= 0.2f; // Put wheels on floor
		HasModelComponent hasModel = new HasModelComponent("Van", instance, offset, 0, scale); // was -90
		//hasModel.dontDrawInViewId = idx;
		this.addComponent(hasModel);
	}


	public void update() {
		VehicleComponent veh = (VehicleComponent)this.getComponent(VehicleComponent.class);
		float dt = Gdx.graphics.getDeltaTime();

		//Rotation
		if (veh.current_speed != 0) {
			float turn_frac = veh.current_speed / veh.max_speed;
			//if (this.inputMethod.isMouse()) {
				if (inputMethod.getStrafeLeft() > Settings.MIN_AXIS) {
					veh.angle_rads += inputMethod.getStrafeLeft() * ROT_SPEED_Y * dt * turn_frac;
				} else if (inputMethod.getStrafeRight() > Settings.MIN_AXIS) {
					veh.angle_rads -= inputMethod.getStrafeRight() * ROT_SPEED_Y * dt * turn_frac;
				}
			/*} else if (inputMethod instanceof NoInputMethod) {
				// Do nothing
			} else {
				if (inputMethod.getStrafeLeft() > Settings.MIN_AXIS) {
					veh.angle_rads += ROT_SPEED_Y * inputMethod.getStrafeLeft() * dt * turn_frac;
				} else if (inputMethod.getStrafeRight() > Settings.MIN_AXIS) {
					veh.angle_rads -= ROT_SPEED_Y * inputMethod.getStrafeRight() * dt * turn_frac;
				}
			}*/
		}

		// Acc/dec
		if (this.inputMethod.isCrossPressed() || this.inputMethod.isKeyPressed(Keys.ENTER)) {
			veh.current_speed += dt * ACC;
			//Settings.p("Speed=" + veh.current_speed);
		} else if (this.inputMethod.isCirclePressed() || this.inputMethod.isKeyPressed(Keys.SPACE)) {
			veh.current_speed -= dt * ACC * 2;
			//Settings.p("Speed=" + veh.current_speed);
		} else {
			// tend towards 0
			//veh.current_speed -= Math.signum(veh.current_speed) * dt * 0.2f;
			veh.current_speed = veh.current_speed * .99f;
		}

		// Update camera pos and dir
		camera.direction.x = (float)Math.sin(veh.angle_rads);
		camera.direction.z = (float)Math.cos(veh.angle_rads);
		PositionComponent posData = (PositionComponent)this.getComponent(PositionComponent.class);
		tmpCamPos.set(posData.position.x, posData.position.y + (Settings.PLAYER_HEIGHT/2)+Settings.CAM_OFFSET, posData.position.z);
		if (SHOULDER_VIEW) {
			tmpCamPos.set(posData.position.x, posData.position.y + .9f, posData.position.z);
			tmpCamPos.x -= camera.direction.x * CAM_BACK;
			tmpCamPos.z -= camera.direction.z * CAM_BACK;

			camera.direction.y = -0.3f; // look down slightly
			camera.direction.nor();
		}
		camera.position.set(tmpCamPos);
		
		// Bounce camera
		/*float delta = veh.current_speed / VehicleMovementSystem.MAX_SPEED * MAX_CAM_BOUNCE;
		camera.position.y += NumberFunctions.rndFloat(0, delta);*/
		camera.update();

		// Rotate model to direction of camera
		HasModelComponent hasModel = (HasModelComponent)this.getComponent(HasModelComponent.class);
		if (hasModel != null) {
			//Settings.p("Angle=" + veh.angle);
			posData.angle_degs = (float)(Math.toDegrees((double)veh.angle_rads));//v2.angle();
		}

	}

}


