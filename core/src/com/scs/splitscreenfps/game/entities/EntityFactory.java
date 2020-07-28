package com.scs.splitscreenfps.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.components.CollidesComponent;
import com.scs.splitscreenfps.game.components.HasDecal;
import com.scs.splitscreenfps.game.components.HasDecalCycle;
import com.scs.splitscreenfps.game.components.HasGuiSpriteComponent;
import com.scs.splitscreenfps.game.components.HasModelComponent;
import com.scs.splitscreenfps.game.components.PositionComponent;
import com.scs.splitscreenfps.game.components.RemoveEntityAfterTimeComponent;

import ssmith.libgdx.GraphicsHelper;
import ssmith.libgdx.ModelFunctions;

public class EntityFactory {

	private EntityFactory() {
	}


	public static AbstractEntity createRedFilter(BasicECS ecs, int viewId) {
		AbstractEntity entity = new AbstractEntity(ecs, "RedFilter");

		Texture weaponTex = new Texture(Gdx.files.internal("colours/red.png"));		
		Sprite sprite = new Sprite(weaponTex);
		//sprite.setSize(Gdx.graphics.getWidth(),  Gdx.graphics.getHeight());
		sprite.setColor(1, 0, 0, .5f);

		HasGuiSpriteComponent hgsc = new HasGuiSpriteComponent(sprite, HasGuiSpriteComponent.Z_FILTER, new Rectangle(0, 0, 1, 1));
		entity.addComponent(hgsc);
		hgsc.onlyViewId = viewId;

		RemoveEntityAfterTimeComponent rat = new RemoveEntityAfterTimeComponent(3);
		entity.addComponent(rat);

		return entity;	

	}


	public static AbstractEntity createModel(Game game, String name, String filename, float posX, float posY, float posZ, float height) {
		AbstractEntity entity = new AbstractEntity(game.ecs, name);

		PositionComponent posData = new PositionComponent(posX, posY, posZ);
		entity.addComponent(posData);

		ModelInstance instance = ModelFunctions.loadModel(filename, false);

		HasModelComponent hasModel = new HasModelComponent("model", instance);
		float scl = ModelFunctions.getScaleForHeight(instance, height);				
		instance.transform.scl(scl);
		ModelFunctions.getOrigin(instance, hasModel.offset);
		hasModel.offset.scl(-1f);
		hasModel.scale = scl;
		hasModel.always_draw = true;
		entity.addComponent(hasModel);

		//CollidesComponent cc = new CollidesComponent(true, instance);
		//entity.addComponent(cc);

		return entity;

	}


	public static AbstractEntity createFire(BasicECS ecs, float map_x, float map_z) {
		AbstractEntity entity = new AbstractEntity(ecs, "Fire");

		PositionComponent posData = new PositionComponent((map_x)+(0.5f), (map_z)+(0.5f));
		entity.addComponent(posData);

		TextureRegion[][] trs = GraphicsHelper.createSheet("shared/fire.png", 8, 4);

		HasDecal hasDecal = new HasDecal();
		TextureRegion tr = trs[0][0];
		hasDecal.decal = Decal.newDecal(tr, true);
		hasDecal.decal.setScale(1f / tr.getRegionWidth());
		hasDecal.decal.setPosition(posData.position);
		hasDecal.faceCamera = true;
		hasDecal.dontLockYAxis = false;
		entity.addComponent(hasDecal);

		HasDecalCycle cycle = new HasDecalCycle(.05f, 8*4);
		int idx = 0;
		for (int y=0 ; y<trs[0].length ; y++) {
			for (int x=0 ; x<trs.length ; x++) {
				cycle.decals[idx] = GraphicsHelper.DecalHelper(trs[x][y], 1);
				idx++;
			}
		}
		entity.addComponent(cycle);

		CollidesComponent cc = new CollidesComponent(true, .5f);
		entity.addComponent(cc);

		return entity;	

	}


	public static AbstractEntity createNormalExplosion(BasicECS ecs, Vector3 pos) {
		AbstractEntity entity = new AbstractEntity(ecs, "Explosion");

		PositionComponent posData = new PositionComponent(pos.x, pos.y, pos.z);
		entity.addComponent(posData);

		TextureRegion[][] trs = GraphicsHelper.createSheet("shared/Explosion21.png", 4, 4);

		HasDecal hasDecal = new HasDecal();
		TextureRegion tr = trs[0][0];
		hasDecal.decal = Decal.newDecal(tr, true);
		hasDecal.decal.setScale(1f / tr.getRegionWidth());
		hasDecal.decal.setPosition(posData.position);
		hasDecal.faceCamera = true;
		hasDecal.dontLockYAxis = false;
		entity.addComponent(hasDecal);

		HasDecalCycle cycle = new HasDecalCycle(.05f, 4*4);
		cycle.remove_at_end_of_cycle = true;
		int idx = 0;
		for (int y=0 ; y<trs[0].length ; y++) {
			for (int x=0 ; x<trs.length ; x++) {
				cycle.decals[idx] = GraphicsHelper.DecalHelper(trs[x][y], 1);
				idx++;
			}
		}
		entity.addComponent(cycle);

		return entity;	

	}


	public static AbstractEntity createBlueExplosion(BasicECS ecs, Vector3 pos) {
		AbstractEntity entity = new AbstractEntity(ecs, "BlueExplosion");

		PositionComponent posData = new PositionComponent(pos.x, pos.y, pos.z);
		entity.addComponent(posData);

		TextureRegion[][] trs = GraphicsHelper.createSheet("shared/Effect95.png", 4, 4);

		HasDecal hasDecal = new HasDecal();
		TextureRegion tr = trs[0][0];
		hasDecal.decal = Decal.newDecal(tr, true);
		hasDecal.decal.setScale(1f / tr.getRegionWidth());
		hasDecal.decal.setPosition(posData.position);
		hasDecal.faceCamera = true;
		hasDecal.dontLockYAxis = false;
		entity.addComponent(hasDecal);

		HasDecalCycle cycle = new HasDecalCycle(.05f, 4*4);
		cycle.remove_at_end_of_cycle = true;
		int idx = 0;
		for (int y=0 ; y<trs[0].length ; y++) {
			for (int x=0 ; x<trs.length ; x++) {
				cycle.decals[idx] = GraphicsHelper.DecalHelper(trs[x][y], 1);
				idx++;
			}
		}
		entity.addComponent(cycle);

		return entity;	

	}


	public static AbstractEntity createDebugSPhere(BasicECS ecs, float x, float y, float z, float diam) {
		AbstractEntity e = new AbstractEntity(ecs, "LowWall");

		PositionComponent pos = new PositionComponent(x, y, z);
		pos.position = new Vector3(x, y, z);
		e.addComponent(pos);

		Material black_material = new Material(TextureAttribute.createDiffuse(new Texture("towerdefence/textures/ufo2_03.png")));

		ModelBuilder modelBuilder = new ModelBuilder();
		Model box_model = modelBuilder.createSphere(diam, diam, diam, 5, 5,  black_material, VertexAttributes.Usage.Position | VertexAttributes.Usage.TextureCoordinates);

		ModelInstance instance = new ModelInstance(box_model, pos.position);
		//instance.transform.rotate(Vector3.Z, 90); // Rotates cube so textures are upright

		HasModelComponent model = new HasModelComponent("LowWall", instance);
		e.addComponent(model);

		return e;
	}
}
