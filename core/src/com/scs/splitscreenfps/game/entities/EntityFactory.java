package com.scs.splitscreenfps.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.scs.basicecs.AbstractEntity;
import com.scs.splitscreenfps.game.components.CombinesWithLitterComponent;
import com.scs.splitscreenfps.game.components.HasDecal;
import com.scs.splitscreenfps.game.components.PositionData;

public class EntityFactory {

	public EntityFactory() {
	}
	
	
	public AbstractEntity createLitter(float map_x, float map_z) {
		AbstractEntity entity = new AbstractEntity("Litter");

		PositionData posData = new PositionData((map_x)+(0.5f), (map_z)+(0.5f));
		entity.addComponent(posData);

		HasDecal hasDecal = new HasDecal();
		Texture tex = new Texture(Gdx.files.internal("monstermaze/exit1.png")); // todo
		TextureRegion tr = new TextureRegion(tex, 0, 0, tex.getWidth(), tex.getHeight());
        hasDecal.decal = Decal.newDecal(tr, true);
        hasDecal.decal.setScale(1f / tr.getRegionWidth());
        hasDecal.decal.setPosition(posData.position);
        hasDecal.faceCamera = true;
        hasDecal.faceCameraTilted = true;        
        entity.addComponent(hasDecal);	
		
        entity.addComponent(new CombinesWithLitterComponent(true));
		
		return entity;	
		
	}


	public AbstractEntity createLitterBin(float map_x, float map_z) {
		AbstractEntity entity = new AbstractEntity("LitterBin");

		PositionData posData = new PositionData((map_x)+(0.5f), (map_z)+(0.5f));
		entity.addComponent(posData);

		HasDecal hasDecal = new HasDecal();
		Texture tex = new Texture(Gdx.files.internal("monstermaze/exit1.png")); // todo
		TextureRegion tr = new TextureRegion(tex, 0, 0, tex.getWidth(), tex.getHeight());
        hasDecal.decal = Decal.newDecal(tr, true);
        hasDecal.decal.setScale(1f / tr.getRegionWidth());
        hasDecal.decal.setPosition(posData.position);
        hasDecal.faceCamera = true;
        hasDecal.faceCameraTilted = true;        
        entity.addComponent(hasDecal);	
		
        entity.addComponent(new CombinesWithLitterComponent(false));
		
		return entity;	
		
	}
}
