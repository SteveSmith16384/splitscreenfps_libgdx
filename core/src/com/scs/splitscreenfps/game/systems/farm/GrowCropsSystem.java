package com.scs.splitscreenfps.game.systems.farm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.Settings;
import com.scs.splitscreenfps.game.components.HasDecal;
import com.scs.splitscreenfps.game.components.HasModel;
import com.scs.splitscreenfps.game.components.farm.CanGrowComponent;

public class GrowCropsSystem extends AbstractSystem {
	
	private long last_grow_time = 0;

	public GrowCropsSystem(BasicECS ecs) {
		super(ecs, CanGrowComponent.class);
	}


	@Override
	public void processEntity(AbstractEntity entity) {
		if (System.currentTimeMillis() < last_grow_time) {
			return;
		}
		last_grow_time = System.currentTimeMillis() + 1000;

		CanGrowComponent cgc = (CanGrowComponent)entity.getComponent(CanGrowComponent.class);
		if (cgc.current_growth < cgc.max_growth) {
			cgc.current_growth += cgc.grow_speed * Gdx.graphics.getDeltaTime();
			
			// Scale the model, if it has one
			HasModel hasModel = (HasModel)entity.getComponent(HasModel.class);
			if (hasModel != null) {
				hasModel.scale = cgc.current_growth / cgc.max_growth;
			}

			HasDecal hasDecal = (HasDecal)entity.getComponent(HasDecal.class);
			if (hasDecal != null) {
				hasDecal.decal.setScale(cgc.current_growth / cgc.max_growth);
				//hasDecal.decal.transformationOffset.y = -.35f;//35f; // todo - check
				//hasDecal.decal.transformationOffset.
				//Settings.p("Scale = "+hasDecal.decal.getScaleY());
			}
		}
	}
	
}
