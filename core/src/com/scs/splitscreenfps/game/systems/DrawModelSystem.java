package com.scs.splitscreenfps.game.systems;

import java.util.Iterator;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalShadowLight;
import com.badlogic.gdx.graphics.g3d.utils.DepthShaderProvider;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.components.HasModel;

public class DrawModelSystem extends AbstractSystem {

	private Game game;
	private ModelBatch modelBatch;

	// todo - remove all this as it doesn't work
	private ModelBatch shadowBatch;
	Environment environment;
	DirectionalShadowLight shadowLight;
	
	//private ModelInstance instance;

	public DrawModelSystem(Game _game, BasicECS ecs) {
		super(ecs);
		game = _game;
		
		this.modelBatch = new ModelBatch();
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, .4f, .4f, .4f, 1f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
		
		//environment.add((shadowLight = new DirectionalShadowLight(1024, 1024, 250, 175, 1f, 100f)).set(0.8f, 0.8f, 0.8f, -1f, -.8f, -.2f));
		//environment.shadowMap = shadowLight;
		shadowBatch = new ModelBatch(new DepthShaderProvider());
		/*
		ModelBuilder modelBuilder = new ModelBuilder();
		modelBuilder.begin();
		MeshPartBuilder mpb = modelBuilder.part("parts", GL20.GL_TRIANGLES, Usage.Position | Usage.Normal | Usage.ColorUnpacked,
			new Material(ColorAttribute.createDiffuse(Color.WHITE)));
		mpb.setColor(1f, 1f, 1f, 1f);
		mpb.box(0, -1.5f, 0, 10, 1, 10);
		mpb.setColor(1f, 0f, 1f, 1f);
		mpb.sphere(2f, 2f, 2f, 10, 10);
		Model model = modelBuilder.end();
		instance = new ModelInstance(model);*/
	}


	@Override
	public Class<?> getComponentClass() {
		return HasModel.class;
	}


	//@Override
	public void process(boolean shadows, Camera cam) {
		if (shadows) {
			//create shadow texture
	        shadowLight.begin(Vector3.Zero, cam.direction);
	        shadowBatch.begin(shadowLight.getCamera());
		} else {
			this.modelBatch.begin(cam);
		}
		
		Iterator<AbstractEntity> it = entities.iterator();
		while (it.hasNext()) {
			AbstractEntity entity = it.next();
			this.processEntity(shadows, entity);
		}
		/*
		if (shadows) {
			shadowBatch.render(instance);
		} else {
			modelBatch.render(instance, environment);
		}
*/
		
		if (shadows) {
	        shadowBatch.end();
	        shadowLight.end();
		} else {
			this.modelBatch.end();
		}
	}


	//@Override
	public void processEntity(boolean shadows, AbstractEntity entity) {
		/*todo - only draw if in frustum if(!camera.frustum.sphereInFrustum(hasPosition.position, 1f)) {
			return;
		}*/

		HasModel model = (HasModel)entity.getComponent(HasModel.class);
		if (model.playerViewId != game.currentViewId) {
			if (shadows) {
				shadowBatch.render(model.model, environment);
			} else {
				modelBatch.render(model.model, environment);
			}
		}
	}

}
