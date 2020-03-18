package com.scs.splitscreenfps.game.entities.farm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.components.CanBeCarried;
import com.scs.splitscreenfps.game.components.CollidesComponent;
import com.scs.splitscreenfps.game.components.HasDecal;
import com.scs.splitscreenfps.game.components.HasGuiSpriteComponent;
import com.scs.splitscreenfps.game.components.PositionComponent;
import com.scs.splitscreenfps.game.components.farm.CanGrowComponent;
import com.scs.splitscreenfps.game.components.monstermaze.MonsterMazeKeyComponent;

import ssmith.libgdx.GraphicsHelper;

public class FarmEntityFactory {

	private FarmEntityFactory() {
	}
	
	
	public static AbstractEntity createSeed(BasicECS ecs, int type, float map_x, float map_z) {
		AbstractEntity entity = new AbstractEntity(ecs, "Seed");

		PositionComponent posData = new PositionComponent((map_x)+(0.5f), (map_z)+(0.5f));
		entity.addComponent(posData);

		HasDecal hasDecal = new HasDecal();
		Texture tex = new Texture(Gdx.files.internal("heart.png"));
		TextureRegion tr = new TextureRegion(tex, 0, 0, tex.getWidth(), tex.getHeight());
        hasDecal.decal = Decal.newDecal(tr, true);
        hasDecal.decal.setScale(1f / tr.getRegionWidth());
        hasDecal.decal.setPosition(posData.position);
        hasDecal.faceCamera = true;
        hasDecal.dontLockYAxis = true;
        entity.addComponent(hasDecal);	
		
        CollidesComponent cc = new CollidesComponent(false, .5f);
        entity.addComponent(cc);	
		
        entity.addComponent(new CanBeCarried());

		Texture weaponTex = new Texture(Gdx.files.internal("monstermaze/key.png"));		
		Sprite sprite = new Sprite(weaponTex);
		sprite.setPosition((Gdx.graphics.getWidth()-sprite.getWidth())/2, 0);		
		HasGuiSpriteComponent hgsc = new HasGuiSpriteComponent(sprite, HasGuiSpriteComponent.Z_CARRIED, new Rectangle(0.4f, 0.1f, 0.2f, 0.3f));
		entity.addComponent(hgsc);
		
		return entity;	
		
	}


	public static AbstractEntity createPlant(Game game, float map_x, float map_z) {
		AbstractEntity plant = new AbstractEntity(game.ecs, "Plant");
		
		PositionComponent posData = new PositionComponent((map_x)+(0.5f), (map_z)+(0.5f));
		plant.addComponent(posData);

		HasDecal hasDecal = new HasDecal();
		TextureRegion[][] trs = GraphicsHelper.createSheet("farm/FarmingCrops16x16/Crop_Spritesheet.png", 16, 16);
		hasDecal.decal = Decal.newDecal(trs[0][0], true);
		//hasDecal.decal.setScale(3f / trs[0][0].getRegionWidth()); // Scale to sq size by default
		hasDecal.faceCamera = true;
		hasDecal.dontLockYAxis = true;
		hasDecal.decal.transformationOffset = new Vector2(0, -.35f);
		plant.addComponent(hasDecal);

		CollidesComponent cc = new CollidesComponent(true, 0.1f);
		plant.addComponent(cc);
		
		CanGrowComponent cgc = new CanGrowComponent(.1f, .25f);
		plant.addComponent(cgc);

		return plant;

	}


	public static AbstractEntity createSeedBag(BasicECS ecs, float map_x, float map_z) {
		AbstractEntity entity = new AbstractEntity(ecs, "SeedBag");//MonsterMazeLevel.KEY_NAME);

		PositionComponent posData = new PositionComponent((map_x)+(0.5f), (map_z)+(0.5f));
		entity.addComponent(posData);

		HasDecal hasDecal = new HasDecal();
		Texture tex = new Texture(Gdx.files.internal("farm/todo"));
		TextureRegion tr = new TextureRegion(tex, 0, 0, tex.getWidth(), tex.getHeight());
		hasDecal.decal = Decal.newDecal(tr, true);
		hasDecal.decal.setScale(1f / tr.getRegionWidth() / 2f);
		hasDecal.decal.setPosition(posData.position);
		hasDecal.faceCamera = true;
		hasDecal.dontLockYAxis = true;
		entity.addComponent(hasDecal);

		Texture weaponTex = new Texture(Gdx.files.internal("farm/todo"));		
		Sprite sprite = new Sprite(weaponTex);
		sprite.setPosition((Gdx.graphics.getWidth()-sprite.getWidth())/2, 0);		
		HasGuiSpriteComponent hgsc = new HasGuiSpriteComponent(sprite, HasGuiSpriteComponent.Z_CARRIED, new Rectangle(0.4f, 0.1f, 0.3f, 0.3f));
		entity.addComponent(hgsc);
		entity.hideComponent(HasGuiSpriteComponent.class); // Don't show it until picked up!

		CollidesComponent cc = new CollidesComponent(false, .5f);
		entity.addComponent(cc);	

		CanBeCarried cbc = new CanBeCarried();
		cbc.auto_pickedup = true;
		entity.addComponent(cbc);

		return entity;	

	}

}
