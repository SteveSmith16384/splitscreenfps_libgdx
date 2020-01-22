package com.scs.splitscreenfps.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Vector3;
import com.scs.basicecs.AbstractEntity;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.components.HasDecal;
import com.scs.splitscreenfps.game.components.PositionData;

public class GenericWallScenery extends AbstractEntity {
	
	public enum Side {Left, Right, Front, Back}
	
	private static final float TINY = 0.01f; 

	public GenericWallScenery(String name, String filename, int x, int y, Side side) {
		super(name);
		
        PositionData pos = new PositionData();
        pos.position = new Vector3(x*Game.UNIT, 0, y*Game.UNIT);
        this.addComponent(pos);
        
        switch (side) {
        case Left:
        	pos.position.z -= (Game.UNIT/2) - TINY;
        	break;
        case Right:
        	pos.position.z += (Game.UNIT/2) + TINY;
        	break;
        case Front:
        	pos.position.x -= (Game.UNIT/2) - TINY;
        	break;
        case Back:
        	pos.position.x += (Game.UNIT/2) + TINY;
        	break;
        }
        
		HasDecal hasDecal = new HasDecal();
		Texture tex = new Texture(Gdx.files.internal(filename));
		TextureRegion tr = new TextureRegion(tex, 0, 0, tex.getWidth(), tex.getHeight());
        hasDecal.decal = Decal.newDecal(tr, true);
        hasDecal.decal.setScale(Game.UNIT / tr.getRegionWidth());
        hasDecal.decal.setPosition(pos.position);
        hasDecal.faceCamera = false;
        if (side == Side.Front || side == Side.Back) {
        	hasDecal.rotation = 90f;
        }
        this.addComponent(hasDecal);
	}

}
