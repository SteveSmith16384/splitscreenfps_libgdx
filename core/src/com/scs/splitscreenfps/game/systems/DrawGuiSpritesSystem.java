package com.scs.splitscreenfps.game.systems;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.game.components.HasGuiSpriteComponent;

public class DrawGuiSpritesSystem extends AbstractSystem {

	private SpriteBatch batch2d;
	
	public DrawGuiSpritesSystem(BasicECS ecs, SpriteBatch _batch2d) {
		super(ecs);
		
		batch2d = _batch2d;
	}


	@Override
	public Class<?> getComponentClass() {
		return HasGuiSpriteComponent.class;
	}


	@Override
	public void processEntity(AbstractEntity entity) {
		HasGuiSpriteComponent hgs = (HasGuiSpriteComponent)entity.getComponent(HasGuiSpriteComponent.class);
		hgs.sprite.draw(batch2d);
	}

}
