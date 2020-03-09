package com.scs.splitscreenfps.game.entities.bladerunner;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.math.Vector3;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.game.components.AnimatedComponent;
import com.scs.splitscreenfps.game.components.CollidesComponent;
import com.scs.splitscreenfps.game.components.HasModelComponent;
import com.scs.splitscreenfps.game.components.MovementData;
import com.scs.splitscreenfps.game.components.PositionComponent;
import com.scs.splitscreenfps.game.components.bladerunner.IsCivilianComponent;

import ssmith.libgdx.ModelFunctions;

public class BladeRunnerEntityFactory {

	private BladeRunnerEntityFactory() {
	}


	public static AbstractEntity createCiv(BasicECS ecs, float x, float z) {
		AbstractEntity e = new AbstractEntity(ecs, "BR_Civilian");

		PositionComponent pos = new PositionComponent();
		pos.position = new Vector3(x+0.5f, 0, z+0.5f);
		e.addComponent(pos);

		ModelInstance instance = ModelFunctions.loadModel("shared/models/Smooth_Male_Shirt.g3db", false);
		float scale = ModelFunctions.getScaleForHeight(instance, .8f);
		instance.transform.scl(scale);		
		Vector3 offset = ModelFunctions.getOrigin(instance);
		HasModelComponent hasModel = new HasModelComponent("BR_Civilian", instance, offset, -90, scale);
		e.addComponent(hasModel);

		AnimationController animation = new AnimationController(instance);
		AnimatedComponent anim = new AnimatedComponent(animation, "HumanArmature|Man_Walk", "HumanArmature|Man_Idle");
		anim.animationController = animation;
		e.addComponent(anim);

		float DIAM = .4f;
		e.addComponent(new MovementData(DIAM));
		e.addComponent(new CollidesComponent(false, DIAM+.2f));
		e.addComponent(new IsCivilianComponent());
		
		return e;
	}


}
