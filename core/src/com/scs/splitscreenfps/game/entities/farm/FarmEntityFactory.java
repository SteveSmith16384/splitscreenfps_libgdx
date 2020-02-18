package com.scs.splitscreenfps.game.entities.farm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Vector3;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.components.CollidesComponent;
import com.scs.splitscreenfps.game.components.HasDecal;
import com.scs.splitscreenfps.game.components.HasModel;
import com.scs.splitscreenfps.game.components.farm.CanGrowComponent;

public class FarmEntityFactory {

	private FarmEntityFactory() {
	}
	
	
	public static AbstractEntity createPlant(Game game, float posX, float posZ) {
		AbstractEntity plant = new AbstractEntity(game.ecs, "Plant");
		
		/*AssetManager am = game.assetManager;
		am.load("models/farm/Plant.g3dj", Model.class);
		am.finishLoading();
		Model model = am.get("models/Spider.g3dj");
		ModelInstance instance = new ModelInstance(model, new Vector3(posX, 0, posZ));		
		HasModel hasModel = new HasModel(plant.name, instance);
		plant.addComponent(hasModel);*/
		
		HasDecal hasDecal = new HasDecal();
		Texture tex = new Texture(Gdx.files.internal("farm/plant1.png"));
		TextureRegion tr = new TextureRegion(tex, 0, 0, tex.getWidth(), tex.getHeight());
		hasDecal.decal = Decal.newDecal(tr, true);
		hasDecal.decal.setScale(1f / tr.getRegionWidth()); // Scale to sq size by default
		hasDecal.faceCamera = true;
		hasDecal.dontLockYAxis = true;        
		plant.addComponent(hasDecal);

		CollidesComponent cc = new CollidesComponent(true, 0.1f);
		plant.addComponent(cc);
		
		CanGrowComponent cgc = new CanGrowComponent(.1f, 100f);
		plant.addComponent(cgc);

		return plant;

	}

}
