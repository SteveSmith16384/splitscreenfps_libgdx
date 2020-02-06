package com.scs.splitscreenfps.game.systems;

import java.util.Iterator;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.components.HasModel;

public class DrawModelSystem extends AbstractSystem {

	private Game game;
	private ModelBatch modelBatch;

	Environment environment;

	public DrawModelSystem(Game _game, BasicECS ecs) {
		super(ecs);
		game = _game;
		
		this.modelBatch = new ModelBatch();
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, .4f, .4f, .4f, 1f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
		
	}


	@Override
	public Class<?> getComponentClass() {
		return HasModel.class;
	}


	//@Override
	public void process(Camera cam) {
		this.modelBatch.begin(cam);
		
		Iterator<AbstractEntity> it = entities.iterator();
		while (it.hasNext()) {
			AbstractEntity entity = it.next();
			this.processEntity(entity);
		}

		this.modelBatch.end();
	}


	//@Override
	public void processEntity(AbstractEntity entity) {
		/*todo - only draw if in frustum if(!camera.frustum.sphereInFrustum(hasPosition.position, 1f)) {
			return;
		}*/

		HasModel model = (HasModel)entity.getComponent(HasModel.class);
		if (model.dontDrawInViewId != game.currentViewId) {
			modelBatch.render(model.model, environment);
		}
	}

}
