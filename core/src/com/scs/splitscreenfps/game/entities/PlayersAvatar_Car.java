package com.scs.splitscreenfps.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.scs.splitscreenfps.Settings;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.ViewportData;
import com.scs.splitscreenfps.game.components.CollidesComponent;
import com.scs.splitscreenfps.game.components.HasModel;
import com.scs.splitscreenfps.game.components.MovementData;
import com.scs.splitscreenfps.game.components.PositionComponent;
import com.scs.splitscreenfps.game.components.VehicleComponent;
import com.scs.splitscreenfps.game.input.IInputMethod;
import com.scs.splitscreenfps.game.input.NoInputMethod;

import ssmith.libgdx.ModelFunctions;

public class PlayersAvatar_Car extends AbstractPlayersAvatar {

	private static final float ROT_SPEED_Y = 4f;
	private static final float ACC = 2;

	public PlayersAvatar_Car(Game _game, int playerIdx, ViewportData _viewportData, IInputMethod _inputMethod) {
		super(_game.ecs, PlayersAvatar_Car.class.getSimpleName() + "_" + playerIdx);

		game = _game;
		inputMethod = _inputMethod;

		MovementData md = new MovementData(0.5f);
		md.must_move_x_and_z = true;
		this.addComponent(md);
		this.addComponent(new PositionComponent());

		this.addCar(playerIdx);

		this.addComponent(new VehicleComponent(playerIdx));
		this.addComponent(new CollidesComponent(true, .5f));

		camera = _viewportData.camera;
	}


	private void addCar(int idx) {
		ModelInstance instance = ModelFunctions.loadModel("deathchase/models/van.g3db", true);
		float scale = ModelFunctions.getScaleForHeight(instance, 1f);
		instance.transform.scl(scale);

		Vector3 offset = ModelFunctions.getOrigin(instance);
		offset.y -= 0.2f; // Put wheels on floor
		HasModel hasModel = new HasModel("Van", instance, offset, 0, scale); // was -90
		hasModel.dontDrawInViewId = idx;
		this.addComponent(hasModel);
	}


	public void update() {
		VehicleComponent veh = (VehicleComponent)this.getComponent(VehicleComponent.class);
		float dt = Gdx.graphics.getDeltaTime();

		//Rotation
		if (veh.current_speed != 0) {
			if (this.inputMethod.isMouse()) {
				if (inputMethod.isStrafeLeftPressed() > Settings.MIN_AXIS) {
					veh.angle += inputMethod.isStrafeLeftPressed() * ROT_SPEED_Y * dt;
				} else if (inputMethod.isStrafeRightPressed() > Settings.MIN_AXIS) {
					veh.angle -= inputMethod.isStrafeRightPressed() * ROT_SPEED_Y * dt;
				}
			} else if (inputMethod instanceof NoInputMethod) {
			} else {
				if (inputMethod.getLookLeft() > Settings.MIN_AXIS) {
					veh.angle += ROT_SPEED_Y * inputMethod.getLookLeft() * dt;
				} else if (inputMethod.getLookRight() > Settings.MIN_AXIS) {
					veh.angle -= ROT_SPEED_Y * inputMethod.getLookRight() * dt;
				}
			}
		}

		// Acc/dec
		if (this.inputMethod.isCirclePressed()) {
			veh.current_speed += dt * ACC;
			//Settings.p("Speed=" + veh.current_speed);
		} else if (this.inputMethod.isCrossPressed()) {
			veh.current_speed -= dt * ACC * 2;
			//Settings.p("Speed=" + veh.current_speed);
		} else {
			//todo - tend towards 0  = veh.current_speed -= dt * 1;
		}

		// Update camera pos and dir
		camera.direction.x = (float)Math.sin(veh.angle);
		camera.direction.z = (float)Math.cos(veh.angle);
		PositionComponent posData = (PositionComponent)this.getComponent(PositionComponent.class);
		camera.position.set(posData.position.x, posData.position.y + (Settings.PLAYER_HEIGHT/2)+Settings.CAM_OFFSET, posData.position.z);
		camera.update();

		// Rotate model to direction of camera
		HasModel hasModel = (HasModel)this.getComponent(HasModel.class);
		if (hasModel != null) {
			//Settings.p("Angle=" + veh.angle);
			posData.angle = (float)(Math.toDegrees((double)veh.angle));//v2.angle();
		}

	}

}


