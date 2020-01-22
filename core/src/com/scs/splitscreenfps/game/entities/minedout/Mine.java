package com.scs.splitscreenfps.game.entities.minedout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Vector3;
import com.scs.basicecs.AbstractEntity;
import com.scs.splitscreenfps.Settings;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.components.HasDecal;
import com.scs.splitscreenfps.game.components.PositionData;
import com.scs.splitscreenfps.game.components.WarnIfAdjacentData;

public class Mine extends AbstractEntity {

	public Mine(int x, int y) {
		super(Mine.class.getSimpleName());

        PositionData pos = new PositionData();
        pos.position = new Vector3(x*Game.UNIT+(Game.UNIT/2), 0, y*Game.UNIT+(Game.UNIT/2));
        this.addComponent(pos);
        
        if (Settings.SHOW_MINES) {
    		HasDecal hasDecal = new HasDecal();
    		Texture tex = new Texture(Gdx.files.internal("gulpman/cherry.png"));
    		TextureRegion tr = new TextureRegion(tex, 0, 0, tex.getWidth(), tex.getHeight());
            hasDecal.decal = Decal.newDecal(tr, true);
            hasDecal.decal.setScale(Game.UNIT / tr.getRegionWidth() / 2);
            hasDecal.decal.setPosition(pos.position);
            hasDecal.faceCamera = true;
            hasDecal.faceCameraTilted = true;        
            this.addComponent(hasDecal);
        }
        
        this.addComponent(new WarnIfAdjacentData());
        
	}

}
