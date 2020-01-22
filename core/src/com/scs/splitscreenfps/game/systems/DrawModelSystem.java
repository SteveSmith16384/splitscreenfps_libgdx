package com.scs.splitscreenfps.game.systems;

import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.game.components.HasModel;

public class DrawModelSystem extends AbstractSystem {
	
	private ModelBatch batch;

	public DrawModelSystem(BasicECS ecs, ModelBatch _batch) {
		super(ecs);
		
		this.batch = _batch;
	}

	
	@Override
	public Class<?> getComponentClass() {
		return HasModel.class;
	}


	@Override
	public void processEntity(AbstractEntity entity) {
		HasModel model = (HasModel)entity.getComponent(HasModel.class);
		batch.render(model.model);
	}
	
}
