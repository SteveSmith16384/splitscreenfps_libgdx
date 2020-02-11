package com.scs.splitscreenfps.game.systems;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
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
		//modelBatch.setBlendFunction(GL20.GL_ONE_MINUS_DST_ALPHA, GL20.GL_SRC_ALPHA)
		
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
		
		Gdx.gl.glEnable(GL20.GL_BLEND); // scs todo - do I need this?
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

		Iterator<AbstractEntity> it = entities.iterator();
		while (it.hasNext()) {
			AbstractEntity entity = it.next();
			this.processEntity(entity);
		}

		Gdx.gl.glDisable(GL20.GL_BLEND);

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
