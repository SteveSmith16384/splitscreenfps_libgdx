package com.scs.splitscreenfps.game.entities.farm;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Vector2;
import com.scs.basicecs.AbstractEntity;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.components.CollidesComponent;
import com.scs.splitscreenfps.game.components.HasDecal;
import com.scs.splitscreenfps.game.components.PositionComponent;
import com.scs.splitscreenfps.game.components.farm.CanGrowComponent;

import ssmith.libgdx.GraphicsHelper;

public class FarmEntityFactory {

	private FarmEntityFactory() {
	}
	
	
	public static AbstractEntity createPlant(Game game, float map_x, float map_z) {
		AbstractEntity plant = new AbstractEntity(game.ecs, "Plant");
		
		PositionComponent posData = new PositionComponent((map_x)+(0.5f), (map_z)+(0.5f));
		plant.addComponent(posData);

		/*AssetManager am = game.assetManager;
		am.load("models/farm/Plant.g3dj", Model.class);
		am.finishLoading();
		Model model = am.get("models/Spider.g3dj");
		ModelInstance instance = new ModelInstance(model, new Vector3(posX, 0, posZ));		
		HasModel hasModel = new HasModel(plant.name, instance);
		plant.addComponent(hasModel);*/
		
		HasDecal hasDecal = new HasDecal();
		TextureRegion[][] trs = GraphicsHelper.createSheet("farm/FarmingCrops16x16/Crop_Spritesheet.png", 16, 16);
		hasDecal.decal = Decal.newDecal(trs[0][0], true);
		//hasDecal.decal.setScale(3f / trs[0][0].getRegionWidth()); // Scale to sq size by default
		hasDecal.faceCamera = true;
		hasDecal.dontLockYAxis = true;
		hasDecal.decal.transformationOffset = new Vector2(0, -.35f); // todo - check
		plant.addComponent(hasDecal);

		CollidesComponent cc = new CollidesComponent(true, 0.1f);
		plant.addComponent(cc);
		
		CanGrowComponent cgc = new CanGrowComponent(.1f, .25f);
		plant.addComponent(cgc);

		return plant;

	}

}
