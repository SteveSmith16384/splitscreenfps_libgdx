package com.scs.splitscreenfps.game.entities;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.scs.splitscreenfps.Settings;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.PersonCameraController;
import com.scs.splitscreenfps.game.ViewportData;
import com.scs.splitscreenfps.game.components.AnimatedComponent;
import com.scs.splitscreenfps.game.components.CanCarryComponent;
import com.scs.splitscreenfps.game.components.CollidesComponent;
import com.scs.splitscreenfps.game.components.HasModel;
import com.scs.splitscreenfps.game.components.MovementData;
import com.scs.splitscreenfps.game.components.PositionComponent;
import com.scs.splitscreenfps.game.input.IInputMethod;

public class PlayersAvatar_Person extends AbstractPlayersAvatar {

	private static final float MOVE_SPEED = 1.5f;

	public PersonCameraController cameraController;
	private Vector3 tmpVector = new Vector3();
	private Vector2 tmpVec2 = new Vector2();

	public PlayersAvatar_Person(Game _game, int playerIdx, ViewportData _viewportData, IInputMethod _inputMethod) {
		super(_game.ecs, PlayersAvatar_Person.class.getSimpleName() + "_" + playerIdx);

		game = _game;
		inputMethod = _inputMethod;

		this.addComponent(new MovementData(0.5f));
		this.addComponent(new PositionComponent());
		this.addComponent(new CanCarryComponent(playerIdx));

		// Model stuff
		this.addSmooth_Male_ShirtComponents(playerIdx);

		this.addComponent(new CollidesComponent(false, .3f, Settings.PLAYER_HEIGHT, .3f));

		camera = _viewportData.camera;

		cameraController = new PersonCameraController(camera, inputMethod);
	}


	private ModelInstance addSmooth_Male_ShirtComponents(int idx) {
		AssetManager am = game.assetManager;

		Model model = null;

		switch (idx) {
		case 0:
			am.load("shared/models/Smooth_Male_Shirt.g3db", Model.class);
			am.finishLoading();
			model = am.get("shared/models/Smooth_Male_Shirt.g3db");
			break;
		case 1:
			am.load("shared/models/Male_Casual.g3db", Model.class);
			am.finishLoading();
			model = am.get("shared/models/Male_Casual.g3db");
			/*am.load("space-kit-1.0/Models/station.g3db", Model.class);
			am.finishLoading();
			model = am.get("space-kit-1.0/Models/station.g3db");*/
			break;
		case 2:
			am.load("shared/models/Male_LongSleeve.g3db", Model.class);
			am.finishLoading();
			model = am.get("shared/models/Male_LongSleeve.g3db");
			break;
		case 3:
			am.load("shared/models/Male_Shirt.g3db", Model.class);
			am.finishLoading();
			model = am.get("shared/models/Male_Shirt.g3db");
			break;
		}
		ModelInstance instance = new ModelInstance(model);

		HasModel hasModel = new HasModel("SmoothMale", instance, -.3f, 90, 0.0016f);
		hasModel.dontDrawInViewId = idx;
		this.addComponent(hasModel);
		
		AnimationController animation = new AnimationController(instance);
		AnimatedComponent anim = new AnimatedComponent(animation, "HumanArmature|Man_Walk", "HumanArmature|Man_Idle");
		anim.animationController = animation;
		this.addComponent(anim);

		return instance;
	}


	public void update() {
		checkMovementInput();
		cameraController.update();

		// Rotate model to direction of camera
		HasModel hasModel = (HasModel)this.getComponent(HasModel.class);
		if (hasModel != null) {
			PositionComponent pos = (PositionComponent)getComponent(PositionComponent.class);
			tmpVec2.set(camera.direction.x, camera.direction.z);
			pos.angle = -tmpVec2.angle();
		}

	}


	private void checkMovementInput() {
		MovementData movementData = (MovementData)this.getComponent(MovementData.class);
		movementData.offset.setZero();

		if (this.inputMethod.isForwardsPressed()) {
			tmpVector.set(camera.direction);
			tmpVector.y = 0;
			movementData.offset.add(tmpVector.nor().scl(MOVE_SPEED));
		} else if (this.inputMethod.isBackwardsPressed()) {
			tmpVector.set(camera.direction);
			tmpVector.y = 0;
			movementData.offset.add(tmpVector.nor().scl(-MOVE_SPEED));
		}
		if (this.inputMethod.isStrafeLeftPressed()) {
			tmpVector.set(camera.direction).crs(camera.up);
			tmpVector.y = 0;
			movementData.offset.add(tmpVector.nor().scl(-MOVE_SPEED));
		} else if (this.inputMethod.isStrafeRightPressed()) {
			tmpVector.set(camera.direction).crs(camera.up);
			tmpVector.y = 0;
			movementData.offset.add(tmpVector.nor().scl(MOVE_SPEED));
		}

		if (this.inputMethod.isPickupDropPressed()) {
			CanCarryComponent cc = (CanCarryComponent)this.getComponent(CanCarryComponent.class);
			if (cc != null) {
				cc.wantsToCarry = true;
			}
		} else {
			CanCarryComponent cc = (CanCarryComponent)this.getComponent(CanCarryComponent.class);
			if (cc != null) {
				cc.wantsToCarry = false;
			}
		}

		PositionComponent posData = (PositionComponent)this.getComponent(PositionComponent.class);
		camera.position.set(posData.position.x, posData.position.y + (Settings.PLAYER_HEIGHT/2)+Settings.CAM_OFFSET, posData.position.z);

	}
}

