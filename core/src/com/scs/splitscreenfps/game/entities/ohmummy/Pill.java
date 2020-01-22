package com.scs.splitscreenfps.game.entities.ohmummy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Vector3;
import com.scs.basicecs.AbstractEntity;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.components.HasDecal;
import com.scs.splitscreenfps.game.components.PositionData;

public class Pill extends AbstractEntity {

	public Pill(int x, int y) {
		super(Pill.class.getSimpleName());
		
        PositionData pos = new PositionData();
        pos.position = new Vector3(x*Game.UNIT+(Game.UNIT/2), -Game.UNIT/4, y*Game.UNIT+(Game.UNIT/2));
        this.addComponent(pos);
        
		HasDecal hasDecal = new HasDecal();
		Texture tex = new Texture(Gdx.files.internal("ohmummy/pill.png"));
		TextureRegion tr = new TextureRegion(tex, 0, 0, tex.getWidth(), tex.getHeight());
        hasDecal.decal = Decal.newDecal(tr, true);
        hasDecal.decal.setScale(Game.UNIT / tr.getRegionWidth() / 4);
        hasDecal.decal.setPosition(pos.position);
        hasDecal.decal.setColor(1f,  1f,  1f, .6f);
        hasDecal.faceCamera = true;
        hasDecal.faceCameraTilted = true;
        this.addComponent(hasDecal);
	}

}
