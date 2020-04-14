package com.scs.splitscreenfps.game.entities.ql;

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
import com.scs.splitscreenfps.game.components.ql.QLPlayerData;

import ssmith.libgdx.ModelFunctions;

public class QuantumLeagueEntityFactory {

	public static AbstractEntity createShadow(BasicECS ecs, int side, int phase, float x, float z) {
		AbstractEntity e = new AbstractEntity(ecs, "P" + side + "_Phase" + phase + "_Shadow");

		PositionComponent pos = new PositionComponent(x+0.5f, 0, z+0.5f);
		e.addComponent(pos);

		ModelInstance instance = ModelFunctions.loadModel("towerdefence/models/Alien.g3db", false);
		float scale = ModelFunctions.getScaleForHeight(instance, .8f);
		instance.transform.scl(scale);		
		Vector3 offset = ModelFunctions.getOrigin(instance);
		HasModelComponent hasModel = new HasModelComponent("Alien", instance, offset, -90, scale);
		e.addComponent(hasModel);

		AnimationController animation = new AnimationController(instance);
		AnimatedComponent anim = new AnimatedComponent(animation, "AlienArmature|Alien_Walk", "AlienArmature|Alien_Idle");
		anim.animationController = animation;
		e.addComponent(anim);

		e.addComponent(new MovementData());
		e.addComponent(new CollidesComponent(false, 0.3f));
		e.addComponent(new QLPlayerData(side));

		return e;
	}
	
}
