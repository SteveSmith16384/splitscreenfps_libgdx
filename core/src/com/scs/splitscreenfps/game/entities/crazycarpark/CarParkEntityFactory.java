package com.scs.splitscreenfps.game.entities.crazycarpark;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.scs.basicecs.AbstractEntity;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.components.CollidesComponent;
import com.scs.splitscreenfps.game.components.HasModelComponent;
import com.scs.splitscreenfps.game.components.PositionComponent;

import ssmith.libgdx.ModelFunctions;

public class CarParkEntityFactory {

	private CarParkEntityFactory() {
	}


	// Looks good
	public static AbstractEntity createCar1(Game game, float posX, float posY, float posZ) {
		float scl = .01f;
		
		AbstractEntity entity = new AbstractEntity(game.ecs, "Car");

		PositionComponent posData = new PositionComponent(posX, posY, posZ);
		entity.addComponent(posData);

		ModelInstance instance = ModelFunctions.loadModel("carpark/quaternius/NormalCar1.g3db", true);

		HasModelComponent hasModel = new HasModelComponent("model", instance);
		
		instance.transform.scl(scl);
		ModelFunctions.getOrigin(instance, hasModel.offset);		
		hasModel.offset.scl(-1f);
		hasModel.scale = scl;
		hasModel.always_draw = true;
		entity.addComponent(hasModel);

		CollidesComponent cc = new CollidesComponent(true, instance);
		entity.addComponent(cc);

		return entity;

	}


	// ONly one colour
	public static AbstractEntity createCar2(Game game, float posX, float posY, float posZ) {
		float scl = 1f;//.02f;
		
		AbstractEntity entity = new AbstractEntity(game.ecs, "Car");

		PositionComponent posData = new PositionComponent(posX, posY, posZ);
		entity.addComponent(posData);

		ModelInstance instance = ModelFunctions.loadModel("carpark/quaternius/Cop.obj", true);

		HasModelComponent hasModel = new HasModelComponent("model", instance);
		
		instance.transform.scl(scl);
		ModelFunctions.getOrigin(instance, hasModel.offset);		
		hasModel.offset.scl(-1f);
		hasModel.scale = scl;
		hasModel.always_draw = true;
		entity.addComponent(hasModel);

		CollidesComponent cc = new CollidesComponent(true, instance);
		entity.addComponent(cc);

		return entity;

	}


	// Only one colour
	public static AbstractEntity createAmbulance(Game game, float posX, float posY, float posZ) {
		float scl = 1;//.2f;
		
		AbstractEntity entity = new AbstractEntity(game.ecs, "Car");

		PositionComponent posData = new PositionComponent(posX, posY, posZ);
		entity.addComponent(posData);

		ModelInstance instance = ModelFunctions.loadModel("carpark/kenney/ambulance.obj", true);

		HasModelComponent hasModel = new HasModelComponent("model", instance);
		
		instance.transform.scl(scl);
		ModelFunctions.getOrigin(instance, hasModel.offset);		
		hasModel.offset.scl(-1f);
		hasModel.scale = scl;
		hasModel.always_draw = true;
		entity.addComponent(hasModel);

		CollidesComponent cc = new CollidesComponent(true, instance);
		entity.addComponent(cc);

		return entity;

	}


}
