package com.scs.splitscreenfps.game.entities.farm;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.components.CollidesComponent;
import com.scs.splitscreenfps.game.components.HasModel;
import com.scs.splitscreenfps.game.components.farm.CanGrowComponent;

public class FarmEntityFactory {

	private FarmEntityFactory() {
	}
	
	
	public static AbstractEntity createPlant(Game game, BasicECS ecs, float posX, float posZ) {
		AbstractEntity plant = new AbstractEntity(ecs, "Plant");
		
		AssetManager am = game.assetManager;

		am.load("models/farm/Plant.g3dj", Model.class);
		am.finishLoading();
		Model model = am.get("models/Spider.g3dj");

		ModelInstance instance = new ModelInstance(model, new Vector3(posX, 0, posZ));
		//instance.transform.scl(.002f);
		
		HasModel hasModel = new HasModel(plant.name, instance);
		plant.addComponent(hasModel);
		
		CollidesComponent cc = new CollidesComponent(true, instance);
		plant.addComponent(cc);
		
		CanGrowComponent cgc = new CanGrowComponent(.1f, 100f);
		plant.addComponent(cgc);

		return plant;

	}

}
