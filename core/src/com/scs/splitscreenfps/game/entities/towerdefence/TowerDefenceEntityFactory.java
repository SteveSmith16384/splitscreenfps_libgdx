package com.scs.splitscreenfps.game.entities.towerdefence;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Vector3;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.game.components.CollidesComponent;
import com.scs.splitscreenfps.game.components.HasDecal;
import com.scs.splitscreenfps.game.components.HasDecalCycle;
import com.scs.splitscreenfps.game.components.PositionComponent;

import ssmith.libgdx.GraphicsHelper;

public class TowerDefenceEntityFactory {

	private TowerDefenceEntityFactory() {
	}


	public static AbstractEntity createCoin(BasicECS ecs, float x, float y) {
		AbstractEntity e = new AbstractEntity(ecs, "Coin");

		PositionComponent pos = new PositionComponent();
		pos.position = new Vector3(x, 0, y);
		e.addComponent(pos);

		TextureRegion[][] trs = GraphicsHelper.createSheet("towerdefence/coins.png", 8, 4);

		HasDecal hasDecal = new HasDecal();
		TextureRegion tr = trs[0][0];
		hasDecal.decal = Decal.newDecal(tr, true);
		hasDecal.decal.setScale(1f / tr.getRegionWidth());
		hasDecal.decal.setPosition(pos.position);
		hasDecal.faceCamera = true;
		hasDecal.dontLockYAxis = false;
		e.addComponent(hasDecal);

		HasDecalCycle cycle = new HasDecalCycle(.05f, 8);
		for (int i=0 ; i<trs.length ; i++) {
			cycle.decals[i] = GraphicsHelper.DecalHelper(trs[i][0], 1);
		}
		e.addComponent(cycle);

		e.addComponent(new CollidesComponent(false, 0.2f));//.5f, .5f, .5f));

		return e;
	}
}
