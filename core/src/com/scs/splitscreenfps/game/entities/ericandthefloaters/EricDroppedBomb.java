package com.scs.splitscreenfps.game.entities.ericandthefloaters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Vector3;
import com.scs.basicecs.AbstractEntity;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.components.EricAndTheFloatersBombData;
import com.scs.splitscreenfps.game.components.HasDecal;

public class EricDroppedBomb extends AbstractEntity {

	public EricDroppedBomb(float px, float py) {
		super(EricDroppedBomb.class.getSimpleName());
		
		HasDecal hasDecal = new HasDecal();
		Texture tex = new Texture(Gdx.files.internal("ericandthefloaters/bomb.png"));
		TextureRegion tr = new TextureRegion(tex, 0, 0, tex.getWidth(), tex.getHeight());
        hasDecal.decal = Decal.newDecal(tr, true);
        hasDecal.decal.setScale(Game.UNIT / tr.getRegionWidth() / 2);
        hasDecal.decal.setPosition(new Vector3(px, -Game.UNIT/5, py));
        hasDecal.faceCamera = true;
        hasDecal.faceCameraTilted = true;
        this.addComponent(hasDecal);

		this.addComponent(new EricAndTheFloatersBombData(3));
		
	}

}
