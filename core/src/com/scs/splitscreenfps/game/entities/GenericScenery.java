package com.scs.splitscreenfps.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Vector3;
import com.scs.basicecs.AbstractEntity;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.components.HasDecal;
import com.scs.splitscreenfps.game.components.MovementData;
import com.scs.splitscreenfps.game.components.PositionData;

public class GenericScenery extends AbstractEntity {

	public GenericScenery(String name, String filename, int x, int y, boolean blocks_movement) {
		super(name);
		
		HasDecal hasDecal = new HasDecal();
		Texture tex = new Texture(Gdx.files.internal(filename));
		TextureRegion tr = new TextureRegion(tex, 0, 0, tex.getWidth(), tex.getHeight());
        hasDecal.decal = Decal.newDecal(tr, true);
        hasDecal.decal.setScale(Game.UNIT / tr.getRegionWidth() / 2);
        hasDecal.decal.setPosition(new Vector3(x*Game.UNIT, -Game.UNIT/5, y*Game.UNIT));
        hasDecal.faceCamera = true;
        hasDecal.faceCameraTilted = true;        
        this.addComponent(hasDecal);
        
        if (blocks_movement) {
        	MovementData md = new MovementData(.5f);
        	md.blocksMovement = true;
            this.addComponent(md);

            PositionData pos = new PositionData();
            pos.position = new Vector3(x*Game.UNIT+(Game.UNIT/2), -Game.UNIT/5, y*Game.UNIT+(Game.UNIT/2));
            this.addComponent(pos);
            
        }
	}

}
