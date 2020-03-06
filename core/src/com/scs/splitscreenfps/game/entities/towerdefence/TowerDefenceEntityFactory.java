package com.scs.splitscreenfps.game.entities.towerdefence;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.math.Vector3;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.game.components.AnimatedComponent;
import com.scs.splitscreenfps.game.components.CollidesComponent;
import com.scs.splitscreenfps.game.components.HasDecal;
import com.scs.splitscreenfps.game.components.HasDecalCycle;
import com.scs.splitscreenfps.game.components.HasModelComponent;
import com.scs.splitscreenfps.game.components.MoveAStarComponent;
import com.scs.splitscreenfps.game.components.MovementData;
import com.scs.splitscreenfps.game.components.PositionComponent;
import com.scs.splitscreenfps.game.components.towerdefence.IsTurretComponent;
import com.scs.splitscreenfps.game.components.towerdefence.TowerEnemyComponent;

import ssmith.libgdx.GraphicsHelper;
import ssmith.libgdx.ModelFunctions;

public class TowerDefenceEntityFactory {

	private TowerDefenceEntityFactory() {
	}


	public static AbstractEntity createAlien(BasicECS ecs, float x, float z) {
		AbstractEntity e = new AbstractEntity(ecs, "TD_Alien");

		PositionComponent pos = new PositionComponent();
		pos.position = new Vector3(x+0.5f, 0, z+0.5f);
		e.addComponent(pos);

		ModelInstance instance = ModelFunctions.loadModel("towerdefence/models/Alien.g3db", false);
		float scale = ModelFunctions.getScaleForHeight(instance, .8f);
		instance.transform.scl(scale);		
		Vector3 offset = ModelFunctions.getOrigin(instance);
		HasModelComponent hasModel = new HasModelComponent("Alien", instance, offset, -90, scale);
		e.addComponent(hasModel);

		AnimationController animation = new AnimationController(instance);
		AnimatedComponent anim = new AnimatedComponent(animation, "AlienArmature|Alien_Walk", "AlienArmature|Alien_Idle");
		anim.animationController = animation;
		e.addComponent(anim);

		float DIAM = .4f;
		e.addComponent(new MovementData(DIAM));
		e.addComponent(new CollidesComponent(false, DIAM+.2f));
		e.addComponent(new TowerEnemyComponent());
		e.addComponent(new MoveAStarComponent(1, true));
		
		return e;
	}


	public static AbstractEntity createCoin(BasicECS ecs, float x, float y) {
		AbstractEntity e = new AbstractEntity(ecs, "Coin");

		PositionComponent pos = new PositionComponent();
		pos.position = new Vector3(x, 0, y);
		e.addComponent(pos);

		TextureRegion[][] trs = GraphicsHelper.createSheet("towerdefence/Coin_16x16_Anim.png", 8, 1);
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
		
		e.addComponent(new CollidesComponent(false, 0.2f));
		e.addComponent(new IsCoinComponent());

		return e;
	}


	public static AbstractEntity createTurret(BasicECS ecs, float x, float z) {
		AbstractEntity e = new AbstractEntity(ecs, "Turret");

		PositionComponent pos = new PositionComponent();
		pos.position = new Vector3(x+.5f, 0, z+.5f);
		e.addComponent(pos);

		ModelInstance instance = ModelFunctions.loadModel("towerdefence/models/kenney_td_kit/weapon_blaster.g3db", true);
		float scale = ModelFunctions.getScaleForHeight(instance, .5f);
		instance.transform.scl(scale);
		Vector3 offset = ModelFunctions.getOrigin(instance);
		HasModelComponent hasModel = new HasModelComponent("Turret", instance, offset, -90, scale);
		e.addComponent(hasModel);

		e.addComponent(new CollidesComponent(true, 0.2f));

		e.addComponent(new IsTurretComponent());

		return e;
	}

}
