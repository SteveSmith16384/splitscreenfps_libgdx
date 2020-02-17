package com.scs.splitscreenfps.game.entities.monstermaze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Rectangle;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.game.components.CanBeCarried;
import com.scs.splitscreenfps.game.components.CollidesComponent;
import com.scs.splitscreenfps.game.components.HasDecal;
import com.scs.splitscreenfps.game.components.HasGuiSpriteComponent;
import com.scs.splitscreenfps.game.components.PositionComponent;
import com.scs.splitscreenfps.game.components.monstermaze.MonsterMazeKeyComponent;

public class MonsterMazeEntityFactory {

	private MonsterMazeEntityFactory() {
	}

	
	public static AbstractEntity createKey(BasicECS ecs, float map_x, float map_z) {
		AbstractEntity entity = new AbstractEntity(ecs, "Key");//MonsterMazeLevel.KEY_NAME);

		PositionComponent posData = new PositionComponent((map_x)+(0.5f), (map_z)+(0.5f));
		entity.addComponent(posData);

		HasDecal hasDecal = new HasDecal();
		Texture tex = new Texture(Gdx.files.internal("monstermaze/key.png"));
		TextureRegion tr = new TextureRegion(tex, 0, 0, tex.getWidth(), tex.getHeight());
        hasDecal.decal = Decal.newDecal(tr, true);
        hasDecal.decal.setScale(1f / tr.getRegionWidth() / 2f);
        hasDecal.decal.setPosition(posData.position);
        hasDecal.faceCamera = true;
        hasDecal.dontLockYAxis = true;
        entity.addComponent(hasDecal);
        
        Texture weaponTex = new Texture(Gdx.files.internal("monstermaze/key.png"));		
		Sprite sprite = new Sprite(weaponTex);
		sprite.setOrigin(sprite.getWidth()/2f, 0);
		sprite.setScale(3);
		sprite.setPosition((Gdx.graphics.getWidth()-sprite.getWidth())/2, 0);		

		HasGuiSpriteComponent hgsc = new HasGuiSpriteComponent(sprite, HasGuiSpriteComponent.Z_CARRIED, new Rectangle(.4f, 0, .2f, .2f));
        entity.addComponent(hgsc);
        entity.hideComponent(HasGuiSpriteComponent.class); // Don't show it until picked up!

        CollidesComponent cc = new CollidesComponent(false, .5f);
        entity.addComponent(cc);	
		
        CanBeCarried cbc = new CanBeCarried();
        cbc.auto_pickedup = true;
        entity.addComponent(cbc);
        
        entity.addComponent(new MonsterMazeKeyComponent());

        return entity;	
		
	}

}
