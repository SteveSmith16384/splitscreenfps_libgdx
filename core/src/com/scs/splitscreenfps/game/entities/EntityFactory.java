package com.scs.splitscreenfps.game.entities;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.UBJsonReader;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.Settings;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.components.CollidesComponent;
import com.scs.splitscreenfps.game.components.DoorComponent;
import com.scs.splitscreenfps.game.components.HasDecal;
import com.scs.splitscreenfps.game.components.HasGuiSpriteComponent;
import com.scs.splitscreenfps.game.components.HasModel;
import com.scs.splitscreenfps.game.components.PositionComponent;
import com.scs.splitscreenfps.game.components.RemoveEntityAfterTimeComponent;

import ssmith.lang.NumberFunctions;

public class EntityFactory {

	private EntityFactory() {
	}


	public static AbstractEntity createCrate(BasicECS ecs, float map_x, float map_z) {
		float SIZE = 0.3f;
		AbstractEntity entity = new AbstractEntity(ecs, "Crate");

		Material black_material = new Material(TextureAttribute.createDiffuse(new Texture("tag/scifi_crate.jpg")));
		ModelBuilder modelBuilder = new ModelBuilder();
		Model box_model = modelBuilder.createBox(SIZE, SIZE, SIZE, black_material, VertexAttributes.Usage.Position | VertexAttributes.Usage.TextureCoordinates);

		PositionComponent posData = new PositionComponent(map_x+(SIZE/2), SIZE/2, map_z+(SIZE/2));
		entity.addComponent(posData);

		ModelInstance instance = new ModelInstance(box_model, new Vector3(map_x+SIZE/2, SIZE/2, map_z+SIZE/2));
		//instance.transform.rotate(Vector3.Z, 90); // Rotates cube so textures are upright
		//instance.transform.rotate(Vector3.Y, NumberFunctions.rnd(0, 90));

		HasModel model = new HasModel("CrateModel", instance);
		//model.yOffset += SIZE/2;
		model.angleOffset = NumberFunctions.rnd(0, 90);
		entity.addComponent(model);

		CollidesComponent cc = new CollidesComponent(true, instance);
		entity.addComponent(cc);

		return entity;	

	}


	public static AbstractEntity createDoor(BasicECS ecs, float map_x, float map_z, boolean rot90) {
		AbstractEntity entity = new AbstractEntity(ecs, "Door");

		PositionComponent posData = new PositionComponent((map_x)+(0.5f), (map_z)+(0.5f));
		entity.addComponent(posData);

		HasDecal hasDecal = new HasDecal();
		Texture tex = new Texture(Gdx.files.internal("sf/door1.jpg"));
		TextureRegion tr = new TextureRegion(tex, 0, 0, tex.getWidth(), tex.getHeight());
		hasDecal.decal = Decal.newDecal(tr, true);
		hasDecal.decal.setScale(1f / tr.getRegionWidth());
		hasDecal.decal.setPosition(posData.position);
		hasDecal.faceCamera = false;
		hasDecal.dontLockYAxis = false;
		if (rot90) {
			hasDecal.rotation = 90;
		}
		entity.addComponent(hasDecal);	

		CollidesComponent cc = new CollidesComponent(true, .5f);
		entity.addComponent(cc);

		DoorComponent dc = new DoorComponent();
		dc.max_height = .9f;
		entity.addComponent(dc);

		return entity;	

	}


	public static AbstractEntity createRedFilter(BasicECS ecs, int viewId) {
		AbstractEntity entity = new AbstractEntity(ecs, "RedFilter");

		Texture weaponTex = new Texture(Gdx.files.internal("colours/white.png"));		
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


	public static AbstractEntity createModel_New(Game game, String filename, float posX, float posY, float posZ, float scl) {
		AbstractEntity entity = new AbstractEntity(game.ecs, "todo");

		PositionComponent posData = new PositionComponent(posX, posY, posZ);
		entity.addComponent(posData);

		Model model = null;
		if (filename.endsWith(".obj")) {
			ModelLoader loader = new ObjLoader();
			model = loader.loadModel(Gdx.files.internal(filename));
		} else {
			G3dModelLoader g3dbModelLoader;
			g3dbModelLoader = new G3dModelLoader(new UBJsonReader());

			model = g3dbModelLoader.loadModel(Gdx.files.absolute(filename));
		}

		ModelInstance instance = new ModelInstance(model, new Vector3(posX, posY, posZ));
		
		HasModel hasModel = new HasModel("model", instance);
		hasModel.scale = scl;
		hasModel.always_draw = true;
		entity.addComponent(hasModel);

		return entity;

	}


	public static AbstractEntity createModel(Game game, String filename, float posX, float posY, float posZ, float scl) {
		AbstractEntity entity = new AbstractEntity(game.ecs, "todo");

		AssetManager am = game.assetManager;

		PositionComponent posData = new PositionComponent(posX, posY, posZ);
		entity.addComponent(posData);

		Model model = null;
		if (filename.endsWith(".obj")) {
			ModelLoader loader = new ObjLoader();
			model = loader.loadModel(Gdx.files.internal(filename));
		} else {
			/*am.load(filename, Model.class);
			am.finishLoading();
			model = am.get(filename);*/
			G3dModelLoader g3dbModelLoader;
			g3dbModelLoader = new G3dModelLoader(new UBJsonReader());

			model = g3dbModelLoader.loadModel(Gdx.files.absolute(filename));
		}

		ModelInstance instance = new ModelInstance(model, new Vector3(posX, posY, posZ));
		
		//Texture tex = new Texture("sf/corridor.jpg");
		for(int m=0;m<instance.materials.size;m++) {
			Material mat = instance.materials.get(m);
			mat.remove(BlendingAttribute.Type);

			/*for (Iterator<Attribute> ai = mat.iterator(); ai.hasNext();){
				Attribute att=ai.next();
				if (att.type == 1) { // colour
					((ColorAttribute)att).color.set(1,  0,  0,  1);
				} else if (att.type==TextureAttribute.Diffuse) {
					((TextureAttribute)att).textureDescription.set(tex,TextureFilter.Linear,TextureFilter.Linear,TextureWrap.ClampToEdge,TextureWrap.ClampToEdge);
				} else {
					Settings.p("Unhandled type:" + att.type + ": " + att);
				}
			}*/
		}

		HasModel hasModel = new HasModel("model", instance);
		hasModel.scale = scl;
		hasModel.always_draw = true;
		entity.addComponent(hasModel);

		//CollidesComponent cc = new CollidesComponent(true, instance);
		//entity.addComponent(cc);

		return entity;

	}
}
