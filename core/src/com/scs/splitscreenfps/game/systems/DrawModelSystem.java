package com.scs.splitscreenfps.game.systems;

import java.util.Iterator;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.math.Vector3;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.components.HasModel;
import com.scs.splitscreenfps.game.components.PositionComponent;

public class DrawModelSystem extends AbstractSystem {

	private Game game;
	private ModelBatch modelBatch;
	private Environment environment;
	
	private Vector3 tmpOffset = new Vector3();
	private Vector3 tmp = new Vector3();

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

		Iterator<AbstractEntity> it = entities.iterator();
		while (it.hasNext()) {
			AbstractEntity entity = it.next();
			this.processEntity(entity, cam);
		}


		this.modelBatch.end();

	}


	//@Override
	public void processEntity(AbstractEntity entity, Camera camera) {
		/*if (entity.name == "Crate") {
			int dfgdfg = 345;
		}*/
		HasModel model = (HasModel)entity.getComponent(HasModel.class);
		if (model.dontDrawInViewId != game.currentViewId) {
			PositionComponent posData = (PositionComponent)entity.getComponent(PositionComponent.class) ;
			if (posData != null) {
				Vector3 position = posData.position;
				tmpOffset.set(position);
				tmpOffset.y += model.yOffset;
				model.model.transform.setToTranslation(tmpOffset);//.x, position.y + model.yOffset, position.z);
				model.model.transform.scl(model.scale);//.0016f);
				model.model.transform.rotate(Vector3.Y, posData.angle+model.angleOffset);
				tmp.set(position);
			} else {
				model.model.transform.getTranslation(tmp);
			}
			// Only draw if in frustum 
			if (!camera.frustum.sphereInFrustum(tmp, 1f)) {
				return;
			}
			modelBatch.render(model.model, environment);
		}
	}

}
