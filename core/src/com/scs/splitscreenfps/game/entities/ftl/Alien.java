package com.scs.splitscreenfps.game.entities.ftl;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.math.Vector3;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.components.AnimatedComponent;
import com.scs.splitscreenfps.game.components.CollidesComponent;
import com.scs.splitscreenfps.game.components.HasModel;
import com.scs.splitscreenfps.game.components.MovementData;
import com.scs.splitscreenfps.game.components.PositionComponent;
import com.scs.splitscreenfps.game.components.farm.WanderingAnimalComponent;
import com.scs.splitscreenfps.game.entities.farm.Cow;

import ssmith.libgdx.ModelFunctions;

public class Alien extends AbstractEntity {

	public Alien(Game game, BasicECS ecs, int x, int y) {
		super(ecs, Alien.class.getSimpleName());

		PositionComponent pos = new PositionComponent();
		pos.position = new Vector3(x+0.5f, 0, y+0.5f);
		this.addComponent(pos);

		loadModel(game);

		this.addComponent(new MovementData(.85f));

		this.addComponent(new CollidesComponent(false, .3f, .3f, .3f));

	}


	private void loadModel(Game game) {
		ModelInstance instance = ModelFunctions.loadModel("ftl/models/alien.g3db", false);

		HasModel hasModel = new HasModel("Alien", instance, -0.3f, -90, 0.0016f);
		this.addComponent(hasModel);

		AnimationController animation = new AnimationController(instance);
		AnimatedComponent anim = new AnimatedComponent(animation, "Armature|WalkSlow", "Armature|Idle");
		anim.animationController = animation;
		this.addComponent(anim);
		
		WanderingAnimalComponent wac = new WanderingAnimalComponent(5f);
		this.addComponent(wac);
	}
	
}
