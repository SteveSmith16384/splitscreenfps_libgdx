package com.scs.splitscreenfps.game.systems;

import com.badlogic.gdx.Gdx;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.game.components.AnimatedComponent;

public class AnimationSystem extends AbstractSystem {

	public AnimationSystem(BasicECS ecs) {
		super(ecs);
	}
	
	
	@Override
	public Class<?> getComponentClass() {
		return AnimatedComponent.class;
	}


	@Override
	public void processEntity(AbstractEntity entity) {
		AnimatedComponent anim = (AnimatedComponent)entity.getComponent(AnimatedComponent.class);
		if (anim.current_animation != anim.new_animation) {
			anim.new_animation = anim.current_animation;
			anim.animationController.animate(anim.new_animation, -1, 1, null, 0f);
		}
		
		anim.animationController.update(Gdx.graphics.getDeltaTime());
	}

}
