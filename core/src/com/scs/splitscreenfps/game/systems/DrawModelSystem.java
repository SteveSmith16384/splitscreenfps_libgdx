package com.scs.splitscreenfps.game.systems;

import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.components.HasModel;

public class DrawModelSystem extends AbstractSystem {
	
	private Game game;
	private ModelBatch batch;

	public DrawModelSystem(Game _game, BasicECS ecs, ModelBatch _batch) {
		super(ecs);
		game = _game;
		this.batch = _batch;
	}

	
	@Override
	public Class<?> getComponentClass() {
		return HasModel.class;
	}


	@Override
	public void processEntity(AbstractEntity entity) {
		HasModel model = (HasModel)entity.getComponent(HasModel.class);
		if (model.playerViewId != game.currentViewId) {
			batch.render(model.model);
		}
	}
	
}
