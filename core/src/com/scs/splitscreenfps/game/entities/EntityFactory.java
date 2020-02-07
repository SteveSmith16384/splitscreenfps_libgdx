package com.scs.splitscreenfps.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.game.components.CanBeCarried;
import com.scs.splitscreenfps.game.components.CollidesComponent;
import com.scs.splitscreenfps.game.components.CombinesWithLitterComponent;
import com.scs.splitscreenfps.game.components.HasDecal;
import com.scs.splitscreenfps.game.components.HasGuiSpriteComponent;
import com.scs.splitscreenfps.game.components.PositionData;

public class EntityFactory {
	
	private BasicECS ecs;

	public EntityFactory(BasicECS _ecs) {
		this.ecs = _ecs;
	}
	
	
	public AbstractEntity createLitter(int type, float map_x, float map_z) {
		AbstractEntity entity = new AbstractEntity(ecs, "Litter");

		PositionData posData = new PositionData((map_x)+(0.5f), (map_z)+(0.5f));
		entity.addComponent(posData);

		HasDecal hasDecal = new HasDecal();
		Texture tex = new Texture(Gdx.files.internal("heart.png")); // todo
		TextureRegion tr = new TextureRegion(tex, 0, 0, tex.getWidth(), tex.getHeight());
        hasDecal.decal = Decal.newDecal(tr, true);
        hasDecal.decal.setScale(1f / tr.getRegionWidth());
        hasDecal.decal.setPosition(posData.position);
        hasDecal.faceCamera = true;
        hasDecal.faceCameraTilted = true;        
        entity.addComponent(hasDecal);	
		
        entity.addComponent(new CombinesWithLitterComponent(true, type));
        
        CollidesComponent cc = new CollidesComponent(false, .5f);
        entity.addComponent(cc);	
		
        entity.addComponent(new CanBeCarried());

        Texture weaponTex = new Texture(Gdx.files.internal("heart.png"));		
		Sprite sprite = new Sprite(weaponTex);
		sprite.setOrigin(sprite.getWidth()/2f, 0);
		//weaponSprite.setScale(7.5f, 5f);
		//float scale = (float)Settings.WINDOW_WIDTH_PIXELS / (float)weaponTex.getWidth() / 3f;
		sprite.setScale(10);
		//sprite.setPosition((Gdx.graphics.getWidth()-sprite.getWidth())/2, 0);		
		sprite.setPosition(100, 100);		
		HasGuiSpriteComponent hgsc = new HasGuiSpriteComponent(sprite);
        entity.addComponent(hgsc);
        entity.hideComponent(HasGuiSpriteComponent.class); // Don't show it until picked up!
		
		return entity;	
		
	}


	public AbstractEntity createLitterBin(int type, float map_x, float map_z) {
		AbstractEntity entity = new AbstractEntity(ecs, "LitterBin");

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
		
        entity.addComponent(new CombinesWithLitterComponent(false, type));
		
        CollidesComponent cc = new CollidesComponent(false, .5f);
        entity.addComponent(cc);	
		
		return entity;	
		
	}
}
