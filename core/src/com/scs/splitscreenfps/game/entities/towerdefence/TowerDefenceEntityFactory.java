package com.scs.splitscreenfps.game.entities.towerdefence;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.game.components.AnimatedComponent;
import com.scs.splitscreenfps.game.components.AutoMoveComponent;
import com.scs.splitscreenfps.game.components.CollidesComponent;
import com.scs.splitscreenfps.game.components.DrawTextIn3DSpaceComponent;
import com.scs.splitscreenfps.game.components.HasDecal;
import com.scs.splitscreenfps.game.components.HasDecalCycle;
import com.scs.splitscreenfps.game.components.HasModelComponent;
import com.scs.splitscreenfps.game.components.MoveAStarComponent;
import com.scs.splitscreenfps.game.components.MovementData;
import com.scs.splitscreenfps.game.components.PositionComponent;
import com.scs.splitscreenfps.game.components.towerdefence.CanBeDamagedByEnemyComponent;
import com.scs.splitscreenfps.game.components.towerdefence.IsBulletComponent;
import com.scs.splitscreenfps.game.components.towerdefence.IsCoinComponent;
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

		//float DIAM = .4f;
		e.addComponent(new MovementData());
		e.addComponent(new CollidesComponent(false, 0.3f));
		e.addComponent(new TowerEnemyComponent());
		e.addComponent(new MoveAStarComponent(1, true));

		return e;
	}


	public static AbstractEntity createCoin(BasicECS ecs, float x, float z) {
		AbstractEntity e = new AbstractEntity(ecs, "Coin");

		PositionComponent pos = new PositionComponent();
		pos.position = new Vector3(x, 0, z);
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
		e.addComponent(new CanBeDamagedByEnemyComponent(10));

		DrawTextIn3DSpaceComponent text = new DrawTextIn3DSpaceComponent("", new Vector3(0, 1, 0), 5);
		e.addComponent(text);

		return e;
	}


	public static AbstractEntity createBullet(BasicECS ecs, AbstractEntity shooter, Vector3 start, Vector3 offset) {
		AbstractEntity e = new AbstractEntity(ecs, "Bullet");

		PositionComponent pos = new PositionComponent();
		pos.position = new Vector3(start);
		e.addComponent(pos);

		HasDecal hasDecal = new HasDecal();
		hasDecal.decal = GraphicsHelper.DecalHelper("towerdefence/laser_bolt.png", .3f);
		hasDecal.decal.setPosition(pos.position);
		hasDecal.faceCamera = true;
		hasDecal.dontLockYAxis = false;
		e.addComponent(hasDecal);

		MovementData md = new MovementData();
		md.must_move_x_and_z = true;
		e.addComponent(md);

		e.addComponent(new AutoMoveComponent(offset));
		
		CollidesComponent cc = new CollidesComponent(false, .1f);
		cc.dont_collide_with = shooter;
		e.addComponent(cc);
		
		e.addComponent(new IsBulletComponent());


		return e;
	}


	public static AbstractEntity createAltar(BasicECS ecs, float x, float z) {
		AbstractEntity e = new AbstractEntity(ecs, "Altar");

		PositionComponent pos = new PositionComponent();
		pos.position = new Vector3(x+.5f, 0, z+.5f);
		e.addComponent(pos);

		ModelInstance instance = ModelFunctions.loadModel("towerdefence/models/kenney_td_kit/detail_crystalLarge.g3db", true);
		float scale = ModelFunctions.getScaleForWidth(instance, 1f);
		instance.transform.scl(scale);
		Vector3 offset = ModelFunctions.getOrigin(instance);
		HasModelComponent hasModel = new HasModelComponent("Altar", instance, offset, -90, scale);
		e.addComponent(hasModel);

		e.addComponent(new CollidesComponent(true, 0.2f));

		e.addComponent(new CanBeDamagedByEnemyComponent(100));

		DrawTextIn3DSpaceComponent text = new DrawTextIn3DSpaceComponent("", new Vector3(0, 1, 0), 5);
		e.addComponent(text);

		return e;
	}


	public static AbstractEntity createLowWall(BasicECS ecs, int mapPosX, int mapPosZ) {
		final float HEIGHT = .4f;
		AbstractEntity e = new AbstractEntity(ecs, "LowWall");

		PositionComponent pos = new PositionComponent();
		pos.position = new Vector3(mapPosX+.5f, 0, mapPosZ+.5f);
		e.addComponent(pos);

		Material black_material = new Material(TextureAttribute.createDiffuse(new Texture("towerdefence/textures/ufo2_03.png")));

		ModelBuilder modelBuilder = new ModelBuilder();
		Model box_model = modelBuilder.createBox(1, HEIGHT, 1, black_material, VertexAttributes.Usage.Position | VertexAttributes.Usage.TextureCoordinates);

		ModelInstance instance = new ModelInstance(box_model, new Vector3(mapPosX+0.5f, HEIGHT*2, mapPosZ+0.5f));
		//instance.transform.rotate(Vector3.Z, 90); // Rotates cube so textures are upright

		HasModelComponent model = new HasModelComponent("LowWall", instance);
		e.addComponent(model);

		e.addComponent(new CollidesComponent(true, .5f));

		return e;
	}


}
