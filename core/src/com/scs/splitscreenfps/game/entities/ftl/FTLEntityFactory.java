package com.scs.splitscreenfps.game.entities.ftl;

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
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.game.components.AnimatedComponent;
import com.scs.splitscreenfps.game.components.CanBeCarried;
import com.scs.splitscreenfps.game.components.CanShootComponent;
import com.scs.splitscreenfps.game.components.CollidesComponent;
import com.scs.splitscreenfps.game.components.DoorComponent;
import com.scs.splitscreenfps.game.components.HasDecal;
import com.scs.splitscreenfps.game.components.HasGuiSpriteComponent;
import com.scs.splitscreenfps.game.components.HasModelComponent;
import com.scs.splitscreenfps.game.components.MovementData;
import com.scs.splitscreenfps.game.components.PositionComponent;
import com.scs.splitscreenfps.game.components.ftl.IsBatteryComponent;

import ssmith.libgdx.ModelFunctions;

public class FTLEntityFactory {

	private FTLEntityFactory() {
	}
	
	
	public static AbstractEntity createDoor(BasicECS ecs, float map_x, float map_z, boolean rot90) {
		AbstractEntity entity = new AbstractEntity(ecs, "Door");

		PositionComponent posData = new PositionComponent((map_x)+(0.5f), (map_z)+(0.5f));
		entity.addComponent(posData);

		HasDecal hasDecal = new HasDecal();
		Texture tex = new Texture(Gdx.files.internal("ftl/textures/door1.jpg"));
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


	public static AbstractEntity createAlien(BasicECS ecs, float x, float z) {
		AbstractEntity e = new AbstractEntity(ecs, "Alien");

		PositionComponent pos = new PositionComponent();
		pos.position = new Vector3(x+0.5f, 0, z+0.5f);
		e.addComponent(pos);

		ModelInstance instance = ModelFunctions.loadModel("ftl/models/Alien_Helmet.g3db", true);
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
		e.addComponent(new CollidesComponent(false, DIAM+.2f));//.5f, .5f, .5f));
		
		return e;
	}


	public static AbstractEntity createBattery(BasicECS ecs, float map_x, float map_z) {
		AbstractEntity entity = new AbstractEntity(ecs, "Battery");

		PositionComponent posData = new PositionComponent((map_x)+(0.5f), (map_z)+(0.5f));
		entity.addComponent(posData);

		HasDecal hasDecal = new HasDecal();
		Texture tex = new Texture(Gdx.files.internal("ftl/green-battery.png"));
		TextureRegion tr = new TextureRegion(tex, 0, 0, tex.getWidth(), tex.getHeight());
        hasDecal.decal = Decal.newDecal(tr, true);
        hasDecal.decal.setScale(1f / tr.getRegionWidth() / 2);
        //posData.position.y = -0.3f;
		hasDecal.decal.transformationOffset = new Vector2(0, -.3f);
        hasDecal.decal.setPosition(posData.position);
        hasDecal.faceCamera = true;
        hasDecal.dontLockYAxis = true;
        entity.addComponent(hasDecal);	
		
        entity.addComponent(new IsBatteryComponent());
        
        CollidesComponent cc = new CollidesComponent(false, .5f);
        entity.addComponent(cc);	
		
        entity.addComponent(new CanBeCarried());

        Texture weaponTex = new Texture(Gdx.files.internal("ftl/green-battery.png"));
		Sprite sprite = new Sprite(weaponTex);
		sprite.setPosition((Gdx.graphics.getWidth()-sprite.getWidth())/2, 0);		
		HasGuiSpriteComponent hgsc = new HasGuiSpriteComponent(sprite, HasGuiSpriteComponent.Z_CARRIED, new Rectangle(0.4f, 0.1f, 0.2f, 0.3f));
        entity.addComponent(hgsc);
        entity.hideComponent(HasGuiSpriteComponent.class); // Don't show it until picked up!
		
		return entity;	
		
	}
	
	
	public static AbstractEntity createGun(BasicECS ecs, float map_x, float map_z) {
		AbstractEntity entity = new AbstractEntity(ecs, "Gun");

		PositionComponent posData = new PositionComponent((map_x)+(0.5f), (map_z)+(0.5f));
		entity.addComponent(posData);

		HasDecal hasDecal = new HasDecal();
		Texture tex = new Texture(Gdx.files.internal("gun.png"));
		TextureRegion tr = new TextureRegion(tex, 0, 0, tex.getWidth(), tex.getHeight());
        hasDecal.decal = Decal.newDecal(tr, true);
        hasDecal.decal.setScale(1f / tr.getRegionWidth() / 2);
        hasDecal.decal.setPosition(posData.position);
        hasDecal.faceCamera = true;
        hasDecal.dontLockYAxis = true;
        entity.addComponent(hasDecal);	
		
        entity.addComponent(new CanShootComponent());
        
        CollidesComponent cc = new CollidesComponent(false, .5f);
        entity.addComponent(cc);	
		
        entity.addComponent(new CanBeCarried());

        Texture weaponTex = new Texture(Gdx.files.internal("gun.png"));
		Sprite sprite = new Sprite(weaponTex);
		sprite.setPosition((Gdx.graphics.getWidth()-sprite.getWidth())/2, 0);		
		HasGuiSpriteComponent hgsc = new HasGuiSpriteComponent(sprite, HasGuiSpriteComponent.Z_CARRIED, new Rectangle(0.4f, 0.1f, 0.2f, 0.3f));
        entity.addComponent(hgsc);
        entity.hideComponent(HasGuiSpriteComponent.class); // Don't show it until picked up!
		
		return entity;	
		
	}
	
	
	public static AbstractEntity createBlockThing(BasicECS ecs, float mapPosX, float mapPosZ) {
		float thickness = .1f;
		
		AbstractEntity entity = new AbstractEntity(ecs, "Block");
		
		Material black_material = new Material(TextureAttribute.createDiffuse(new Texture("ftl/textures/wall2.jpg")));
		ModelBuilder modelBuilder = new ModelBuilder();
		Model box_model = modelBuilder.createBox(1f, thickness, thickness, black_material, VertexAttributes.Usage.Position | VertexAttributes.Usage.TextureCoordinates);

		ModelInstance instance = new ModelInstance(box_model, new Vector3(mapPosX, 1-thickness, mapPosZ));
		//instance.transform.rotate(Vector3.Z, 90); // Rotates cube so textures are upright

		HasModelComponent model = new HasModelComponent("Block", instance);
		entity.addComponent(model);
		
		return entity;
	}

}
