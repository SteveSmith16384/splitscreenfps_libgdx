package com.scs.splitscreenfps.game.entities;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.math.Vector3;
import com.scs.basicecs.AbstractEntity;
import com.scs.splitscreenfps.game.components.AnimatedComponent;
import com.scs.splitscreenfps.game.components.CollidesComponent;
import com.scs.splitscreenfps.game.components.HasModel;

public class ModelEntity extends AbstractEntity {

	public ModelEntity(String name, float posX, float posY, float posZ) {
		super(name);

		AssetManager am = new AssetManager();
		am.load("models/Spider.g3dj", Model.class);
		am.finishLoading();
		Model model = am.get("models/Spider.g3dj");
		//ModelLoader loader = new ObjLoader();
		//Model model = loader.loadModel(Gdx.files.internal("data/ship.obj"));

		ModelInstance instance = new ModelInstance(model, new Vector3(posX, posY, posZ));
		instance.transform.scl(.002f);
		
		HasModel hasModel = new HasModel(instance);
		this.addComponent(hasModel);
		
		AnimationController animation = new AnimationController(instance);
		String id = model.animations.get(0).id;
		animation.animate(id, 200, 1f, null, 0.2f); // First anim
		AnimatedComponent anim = new AnimatedComponent();
		anim.animationController = animation;
		this.addComponent(anim);

		CollidesComponent cc = new CollidesComponent(true, instance);
		this.addComponent(cc);
	}

}
