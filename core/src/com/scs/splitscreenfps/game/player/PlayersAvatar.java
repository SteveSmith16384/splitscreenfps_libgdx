package com.scs.splitscreenfps.game.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.math.Vector3;
import com.scs.basicecs.AbstractEntity;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.ViewportData;
import com.scs.splitscreenfps.game.components.AnimatedComponent;
import com.scs.splitscreenfps.game.components.AnimatedForAvatarComponent;
import com.scs.splitscreenfps.game.components.CanCarry;
import com.scs.splitscreenfps.game.components.CanCollect;
import com.scs.splitscreenfps.game.components.CollidesComponent;
import com.scs.splitscreenfps.game.components.HasModel;
import com.scs.splitscreenfps.game.components.MovementData;
import com.scs.splitscreenfps.game.components.PositionComponent;
import com.scs.splitscreenfps.game.components.TagableComponent;
import com.scs.splitscreenfps.game.input.IInputMethod;

public class PlayersAvatar extends AbstractEntity {

	private static final float moveSpeed = 1.5f;
	public static final float playerHeight = 0.26f;

	private Game game;
	public Camera camera;
	public CameraController cameraController;
	private Vector3 tmpVector = new Vector3();
	private float footstepTimer;

	private MovementData movementData;
	private PositionComponent positionData;
	private IInputMethod inputMethod;

	public PlayersAvatar(Game _game, int idx, ViewportData _viewportData, IInputMethod _inputMethod) {
		super(_game.ecs, PlayersAvatar.class.getSimpleName() + "_" + idx);

		game = _game;
		inputMethod = _inputMethod;

		this.movementData = new MovementData(0.5f);
		this.addComponent(movementData);
		this.positionData = new PositionComponent(); // Centre of the player, but NOT where the camera is!
		this.addComponent(positionData);
		this.addComponent(new CanCollect());
		this.addComponent(new CanCarry());

		TagableComponent taggable = new TagableComponent(idx);
		addSkeletonForTagged(idx, taggable);
		this.addComponent(taggable);

		// Model stuff
		//ModelInstance instance = this.addKnightComponents(idx);
		//ModelInstance instance = this.addZombieComponents(idx);
		ModelInstance instance = this.addSmooth_Male_ShirtComponents(idx);
		//ModelInstance instance = this.addSkeletonComponents(idx);

		this.addComponent(new CollidesComponent(false, .15f));

		camera = _viewportData.camera;

		cameraController = new CameraController(game, camera, inputMethod);
	}


	private ModelInstance addSkeletonForTagged(int idx, TagableComponent taggable) {
		AssetManager am = game.assetManager;
		
		am.load("models/Skeleton.g3dj", Model.class);
		am.finishLoading();
		Model model = am.get("models/Skeleton.g3dj");

		ModelInstance instance = new ModelInstance(model);
		instance.transform.scl(.0015f);
		instance.transform.rotate(Vector3.Y, 90f); // Model is facing the wrong way
		HasModel hasModel = new HasModel("Skeleton", instance, -.3f);
		hasModel.dontDrawInViewId = idx;
		taggable.hasModel = hasModel;

		AnimatedForAvatarComponent avatarAnim = new AnimatedForAvatarComponent();
		avatarAnim.idle_anim = "SkeletonArmature|Skeleton_Idle";
		avatarAnim.walk_anim = "SkeletonArmature|Skeleton_Running";
		taggable.avatarAnim = avatarAnim;

		AnimationController animation = new AnimationController(instance);
		AnimatedComponent anim = new AnimatedComponent(animation, avatarAnim.idle_anim);
		anim.animationController = animation;
		taggable.animated = anim;

		return instance;
	}

/*
	private ModelInstance addSkeletonComponents(int idx) {
		AssetManager am = game.assetManager;

		am.load("models/Skeleton.g3dj", Model.class);
		am.finishLoading();
		Model model = am.get("models/Skeleton.g3dj");

		ModelInstance instance = new ModelInstance(model);
		instance.transform.scl(.0013f);
		instance.transform.rotate(Vector3.Y, 90f); // Model is facing the wrong way
		HasModel hasModel = new HasModel(instance);
		hasModel.dontDrawInViewId = idx;
		this.addComponent(hasModel);

		AnimatedForAvatarComponent avatarAnim = new AnimatedForAvatarComponent();
		avatarAnim.idle_anim = "SkeletonArmature|Skeleton_Idle";
		avatarAnim.walk_anim = "SkeletonArmature|Skeleton_Running";
		this.addComponent(avatarAnim);

		AnimationController animation = new AnimationController(instance);
		AnimatedComponent anim = new AnimatedComponent(animation, avatarAnim.idle_anim);
		anim.animationController = animation;
		this.addComponent(anim);

		return instance;
	}
*/

	private ModelInstance addSmooth_Male_ShirtComponents(int idx) {
		AssetManager am = game.assetManager;

		am.load("models/Smooth_Male_Shirt.g3db", Model.class);
		am.finishLoading();
		Model model = am.get("models/Smooth_Male_Shirt.g3db");

		ModelInstance instance = new ModelInstance(model);
		instance.transform.scl(.0016f);
		instance.transform.rotate(Vector3.Y, 90f); // Model is facing the wrong way
		HasModel hasModel = new HasModel("SmoothMale", instance, -.3f);
		hasModel.dontDrawInViewId = idx;
		this.addComponent(hasModel);

		AnimatedForAvatarComponent avatarAnim = new AnimatedForAvatarComponent();
		avatarAnim.idle_anim = "HumanArmature|Man_Idle"; // Standing
		avatarAnim.walk_anim = "HumanArmature|Man_Walk";
		this.addComponent(avatarAnim);

		AnimationController animation = new AnimationController(instance);
		AnimatedComponent anim = new AnimatedComponent(animation, avatarAnim.idle_anim);
		anim.animationController = animation;
		this.addComponent(anim);

		return instance;
	}


	// Model doesn't show
	/*private ModelInstance addZombieComponents(int idx) {
		AssetManager am = game.assetManager;

		am.load("models/Zombie.g3db", Model.class);
		am.finishLoading();
		Model model = am.get("models/Zombie.g3db");

		ModelInstance instance = new ModelInstance(model);
		instance.transform.scl(.002f);
		instance.transform.rotate(Vector3.Y, 90f); // Model is facing the wrong way
		instance.calculateTransforms();
		HasModel hasModel = new HasModel(instance);
		hasModel.dontDrawInViewId = idx;
		this.addComponent(hasModel);

		AnimatedForAvatarComponent avatarAnim = new AnimatedForAvatarComponent();
		avatarAnim.idle_anim = "Zombie|ZombieIdle";
		avatarAnim.walk_anim = "Zombie|ZombieWalk"; // Zombie|ZombieRun			
		this.addComponent(avatarAnim);

		AnimationController animation = new AnimationController(instance);
		AnimatedComponent anim = new AnimatedComponent(animation, avatarAnim.idle_anim);
		anim.animationController = animation;
		this.addComponent(anim);

		return instance;
	}*/


	private ModelInstance addKnightComponents(int idx) {
		AssetManager am = game.assetManager;

		am.load("models/KnightCharacter.g3dj", Model.class);
		am.finishLoading();
		Model model = am.get("models/KnightCharacter.g3dj");

		ModelInstance instance = new ModelInstance(model);
		instance.transform.scl(.002f);
		instance.transform.rotate(Vector3.Y, 90f); // Model is facing the wrong way
		HasModel hasModel = new HasModel("Knight", instance, -.2f);
		hasModel.dontDrawInViewId = idx;
		this.addComponent(hasModel);

		AnimatedForAvatarComponent avatarAnim = new AnimatedForAvatarComponent();
		avatarAnim.idle_anim = "HumanArmature|Idle";
		avatarAnim.walk_anim = "HumanArmature|Walking";			
		this.addComponent(avatarAnim);

		AnimationController animation = new AnimationController(instance);
		AnimatedComponent anim = new AnimatedComponent(animation, avatarAnim.idle_anim);
		anim.animationController = animation;
		this.addComponent(anim);

		return instance;
	}


	public Vector3 getPosition() {
		return this.positionData.position;
	}


	public void update() {
		checkMovementInput();
		cameraController.update();

		// Rotate model to direction of camera
		HasModel hasModel = (HasModel)getComponent(HasModel.class);
		if (hasModel != null) {
			PositionComponent pos = (PositionComponent)getComponent(PositionComponent.class);

			hasModel.model.transform.setTranslation(pos.position.x, pos.position.y + hasModel.yOffset, pos.position.z);

			//Settings.p("-------------------");

			//Vector2 v2 = new Vector2(camera.direction.x, camera.direction.z);
			//float cam_ang = v2.angle();
			/*if (cam_ang == 0) {
				return; // dont process nonPC cams
			}*/
			//Settings.p("cam_ang=" + cam_ang);

			float turn = this.cameraController.camAngleChange;
			hasModel.model.transform.rotate(Vector3.Y, turn);
		}
	}


	private void checkMovementInput() {
		float delta = Gdx.graphics.getDeltaTime();

		this.movementData.offset.setZero();

		if (this.inputMethod.isForwardsPressed()) {
			tmpVector.set(camera.direction);
			tmpVector.y = 0;
			this.movementData.offset.add(tmpVector.nor().scl(delta * moveSpeed));
		} else if (this.inputMethod.isBackwardsPressed()) {
			tmpVector.set(camera.direction);
			tmpVector.y = 0;
			this.movementData.offset.add(tmpVector.nor().scl(delta * -moveSpeed));
		}
		if (this.inputMethod.isStrafeLeftPressed()) {
			tmpVector.set(camera.direction).crs(camera.up);
			tmpVector.y = 0;
			this.movementData.offset.add(tmpVector.nor().scl(delta * -moveSpeed));
		} else if (this.inputMethod.isStrafeRightPressed()) {
			tmpVector.set(camera.direction).crs(camera.up);
			tmpVector.y = 0;
			this.movementData.offset.add(tmpVector.nor().scl(delta * moveSpeed));
		}

		if (this.inputMethod.isPickupDropPressed()) {
			CanCarry cc = (CanCarry)this.getComponent(CanCarry.class);
			if (cc != null) {
				cc.wantsToCarry = true;
			}
		}

		camera.position.set(getPosition().x, getPosition().y + playerHeight, getPosition().z);

		// Animate and footstep sfx
		AnimatedComponent anim = (AnimatedComponent)this.getComponent(AnimatedComponent.class);
		AnimatedForAvatarComponent avatarAnim = (AnimatedForAvatarComponent)this.getComponent(AnimatedForAvatarComponent.class);
		if (this.movementData.offset.len2() > 0) {
			if (anim != null) {
				anim.new_animation = avatarAnim.walk_anim;
			}
			footstepTimer += Gdx.graphics.getDeltaTime();
			if (footstepTimer > 0.45f) {
				footstepTimer -= 0.45f;
				Game.audio.play("step");
			}
		} else {
			if (anim != null) {
				anim.new_animation = avatarAnim.idle_anim;
			}
		}
	}

	
	public void renderUI(SpriteBatch batch, BitmapFont font) {
		TagableComponent tc = (TagableComponent)this.getComponent(TagableComponent.class);
		font.draw(batch, "Time Left: " + (int)tc.timeLeftAsIt, 10, game.viewports[this.id].viewPos.y+20);
	}


}

