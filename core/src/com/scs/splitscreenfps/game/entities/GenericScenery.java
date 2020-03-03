package com.scs.splitscreenfps.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Vector3;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.game.components.CollidesComponent;
import com.scs.splitscreenfps.game.components.HasDecal;
import com.scs.splitscreenfps.game.components.PositionComponent;

import ssmith.libgdx.MyBoundingBox;

public class GenericScenery extends AbstractEntity {

	public GenericScenery(BasicECS ecs, String name, String filename, int x, int y, boolean blocks_movement) {
		super(ecs, name);
		
		HasDecal hasDecal = new HasDecal();
		Texture tex = new Texture(Gdx.files.internal(filename));
		TextureRegion tr = new TextureRegion(tex, 0, 0, tex.getWidth(), tex.getHeight());
        hasDecal.decal = Decal.newDecal(tr, true);
        hasDecal.decal.setScale(1f / tr.getRegionWidth() / 2);
        hasDecal.decal.setPosition(new Vector3(x, -.5f, y));
        hasDecal.faceCamera = true;
        hasDecal.dontLockYAxis = true;        
        this.addComponent(hasDecal);
        
        if (blocks_movement) {
            PositionComponent pos = new PositionComponent();
            pos.position = new Vector3(x+0.5f, -.2f, y+0.5f);
            this.addComponent(pos);
            
            this.addComponent(new CollidesComponent(blocks_movement, new MyBoundingBox(pos.position, .3f, .3f, .3f)));

        }
	}

}
