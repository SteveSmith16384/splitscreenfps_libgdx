package com.scs.splitscreenfps.game.entities.ftl;

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

import ssmith.libgdx.ModelFunctions;

public class Alien extends AbstractEntity {

	public Alien(BasicECS ecs, int x, int y) {
		super(ecs, Alien.class.getSimpleName());

		PositionComponent pos = new PositionComponent();
		pos.position = new Vector3(x+0.5f, 0, y+0.5f);
		this.addComponent(pos);

		loadModel();

		float DIAM = .4f;
		this.addComponent(new MovementData(DIAM));
		this.addComponent(new CollidesComponent(false, DIAM+.2f));//.5f, .5f, .5f));
	}


	private void loadModel() {
		ModelInstance instance = ModelFunctions.loadModel("ftl/models/Alien_Helmet.g3db", true);
		float scale = ModelFunctions.getScaleForHeight(instance, .8f);
		instance.transform.scl(scale);
		
		Vector3 offset = ModelFunctions.getOrigin(instance);
		HasModel hasModel = new HasModel("Alien", instance, offset, -90, scale);
		this.addComponent(hasModel);

		AnimationController animation = new AnimationController(instance);
		AnimatedComponent anim = new AnimatedComponent(animation, "AlienArmature|Alien_Walk", "AlienArmature|Alien_Idle");
		anim.animationController = animation;
		this.addComponent(anim);
	}
	
}
