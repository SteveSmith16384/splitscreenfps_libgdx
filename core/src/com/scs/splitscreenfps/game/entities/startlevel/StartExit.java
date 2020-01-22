package com.scs.splitscreenfps.game.entities.startlevel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.scs.basicecs.AbstractEntity;
import com.scs.splitscreenfps.game.Art;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.components.CompletesLevelData;
import com.scs.splitscreenfps.game.components.HasDecal;
import com.scs.splitscreenfps.game.components.HasDecalCycle;
import com.scs.splitscreenfps.game.components.PositionData;

public class StartExit extends AbstractEntity {

	public StartExit(int map_x, int map_y) {
		super(StartExit.class.getSimpleName());
		
		//PositionData posData = new PositionData((map_x*Game.UNIT)-(Game.UNIT/2), (map_y*Game.UNIT)-(Game.UNIT/2));
		PositionData posData = new PositionData((map_x*Game.UNIT)+(Game.UNIT/2), (map_y*Game.UNIT)+(Game.UNIT/2));
		this.addComponent(posData);

		HasDecal hasDecal = new HasDecal();
		Texture tex = new Texture(Gdx.files.internal("start/loadingscreen1.png"));
		TextureRegion tr = new TextureRegion(tex, 0, 0, tex.getWidth(), tex.getHeight());
        hasDecal.decal = Decal.newDecal(tr, true);
        hasDecal.decal.setScale(Game.UNIT / tr.getRegionWidth());
        hasDecal.decal.setPosition(posData.position);
        hasDecal.faceCamera = true;
        hasDecal.faceCameraTilted = true;        
        this.addComponent(hasDecal);

        HasDecalCycle cycle = new HasDecalCycle(.1f, 2);
        cycle.decals[0] = hasDecal.decal;
        cycle.decals[1] = Art.DecalHelper("start/loadingscreen2.png", 1f);
        this.addComponent(cycle);
        
		this.addComponent(new CompletesLevelData());

	}

}
