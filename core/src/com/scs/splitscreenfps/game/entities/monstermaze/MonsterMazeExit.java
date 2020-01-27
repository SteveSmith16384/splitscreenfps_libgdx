package com.scs.splitscreenfps.game.entities.monstermaze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.scs.basicecs.AbstractEntity;
import com.scs.splitscreenfps.game.Art;
import com.scs.splitscreenfps.game.components.CollisionComponent;
import com.scs.splitscreenfps.game.components.CompletesLevelData;
import com.scs.splitscreenfps.game.components.HasDecal;
import com.scs.splitscreenfps.game.components.HasDecalCycle;
import com.scs.splitscreenfps.game.components.PositionData;

import ssmith.libgdx.LibGDXHelper;

public class MonsterMazeExit extends AbstractEntity {

	public MonsterMazeExit(int map_x, int map_y) {
		super(MonsterMazeExit.class.getSimpleName());
		
		//PositionData posData = new PositionData((map_x*Game.UNIT)-(Game.UNIT/2), (map_y*Game.UNIT)-(Game.UNIT/2));
		PositionData posData = new PositionData((map_x)+(0.5f), (map_y)+(0.5f));
		this.addComponent(posData);

		HasDecal hasDecal = new HasDecal();
		Texture tex = new Texture(Gdx.files.internal("monstermaze/exit1.png"));
		TextureRegion tr = new TextureRegion(tex, 0, 0, tex.getWidth(), tex.getHeight());
        hasDecal.decal = Decal.newDecal(tr, true);
        hasDecal.decal.setScale(1f / tr.getRegionWidth());
        hasDecal.decal.setPosition(posData.position);
        hasDecal.faceCamera = true;
        hasDecal.faceCameraTilted = true;        
        this.addComponent(hasDecal);

        HasDecalCycle cycle = new HasDecalCycle(.5f, 2);
        cycle.decals[0] = hasDecal.decal;
        cycle.decals[1] = Art.DecalHelper("monstermaze/exit2.png", 1f);
        this.addComponent(cycle);
        
		this.addComponent(new CompletesLevelData());

        this.addComponent(new CollisionComponent(LibGDXHelper.createFromCentreAndExtents(posData.position, .3f, .3f, .3f)));
	}

}
