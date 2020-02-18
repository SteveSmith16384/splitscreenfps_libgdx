package com.scs.splitscreenfps.game.systems.farm;

import com.badlogic.gdx.Gdx;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.game.components.HasModel;
import com.scs.splitscreenfps.game.components.farm.CanGrowComponent;

public class GrowCropsSystem extends AbstractSystem {

	public GrowCropsSystem(BasicECS ecs) {
		super(ecs, CanGrowComponent.class);
	}


	@Override
	public void processEntity(AbstractEntity entity) {
		CanGrowComponent cgc = (CanGrowComponent)entity.getComponent(CanGrowComponent.class);
		if (cgc.current_growth < cgc.max_growth) {
			cgc.current_growth += cgc.grow_speed * Gdx.graphics.getDeltaTime();
			
			// Scale the model, if it has one
			HasModel hasModel = (HasModel)entity.getComponent(HasModel.class);
			if (hasModel != null) {
				hasModel.scale = cgc.current_growth / cgc.max_growth;
			}
		}
	}
	
}
