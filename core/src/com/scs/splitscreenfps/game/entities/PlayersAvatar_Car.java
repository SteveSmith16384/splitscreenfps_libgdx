package com.scs.splitscreenfps.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.scs.splitscreenfps.Settings;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.ViewportData;
import com.scs.splitscreenfps.game.components.CollidesComponent;
import com.scs.splitscreenfps.game.components.HasModel;
import com.scs.splitscreenfps.game.components.MovementData;
import com.scs.splitscreenfps.game.components.PositionComponent;
import com.scs.splitscreenfps.game.input.IInputMethod;
import com.scs.splitscreenfps.game.input.NoInputMethod;

public class PlayersAvatar_Car extends AbstractPlayersAvatar {

	private static final float MIN_AXIS = 0.2f; // Movement less than this is ignored
	private static float rotSpeedY = 10f;
	private static final float max_speed = 10;
	
	private Vector3 tmpVector = new Vector3();

	private float current_speed;
	private float angle;

	public PlayersAvatar_Car(Game _game, int playerIdx, ViewportData _viewportData, IInputMethod _inputMethod) {
		super(_game.ecs, PlayersAvatar_Person.class.getSimpleName() + "_" + playerIdx);

		game = _game;
		inputMethod = _inputMethod;

		this.addComponent(new MovementData(0.5f));
		this.addComponent(new PositionComponent());

		// Model stuff
		//todo this.addCar(playerIdx);

		this.addComponent(new CollidesComponent(false, .3f, Settings.PLAYER_HEIGHT, .3f));

		camera = _viewportData.camera;
	}


	public void update() {
		float dt = Gdx.graphics.getDeltaTime();

		if (this.inputMethod.isMouse()) {
			//Rotation
			if (inputMethod.isStrafeLeftPressed()) {
				this.angle += rotSpeedY * dt;
			} else if (inputMethod.isStrafeRightPressed()) {
				this.angle -= rotSpeedY * dt;
			}
		} else if (inputMethod instanceof NoInputMethod) {
		} else {
			//Rotation
			if (inputMethod.getLookLeft() > MIN_AXIS) {
				this.angle += rotSpeedY * inputMethod.getLookLeft() * dt;
			} else if (inputMethod.getLookRight() > MIN_AXIS) {
				this.angle -= rotSpeedY * inputMethod.getLookRight() * dt;
			}
		}
		checkMovementInput();
		camera.update();

		// Rotate model to direction of camera
		HasModel hasModel = (HasModel)this.getComponent(HasModel.class);
		if (hasModel != null) {
			PositionComponent pos = (PositionComponent)getComponent(PositionComponent.class);
			Vector2 v2 = new Vector2(camera.direction.x, camera.direction.z);
			pos.angle = -v2.angle();
		}

	}


	private void checkMovementInput() {
		MovementData movementData = (MovementData)this.getComponent(MovementData.class);
		movementData.offset.setZero();

		if (this.inputMethod.isPickupDropPressed()) {
			this.current_speed += Gdx.graphics.getDeltaTime() * 10;
			Settings.p("Speed=" + this.current_speed);
		} else {
			//this.current_speed -= Gdx.graphics.getDeltaTime() * 2;
		}

		if (this.current_speed > this.max_speed) {
			this.current_speed = this.max_speed;
		} else if (this.current_speed < 0) {
			this.current_speed = 0;
		}
		
		tmpVector.set((float)Math.sin(this.angle), 0, (float)Math.cos(this.angle));
		camera.direction.x = tmpVector.x;
		camera.direction.z = tmpVector.z;
		
		movementData.offset.add(tmpVector.nor().scl(this.current_speed));

		PositionComponent posData = (PositionComponent)this.getComponent(PositionComponent.class);
		camera.position.set(posData.position.x, posData.position.y + (Settings.PLAYER_HEIGHT/2)+Settings.CAM_OFFSET, posData.position.z);

	}

}


