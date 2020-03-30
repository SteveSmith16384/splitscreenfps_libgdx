package com.scs.splitscreenfps.game.entities.monstermaze;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector3;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.game.components.HasModelComponent;

import ssmith.lang.NumberFunctions;

public class RandomFloor extends AbstractEntity {

	public RandomFloor(BasicECS ecs, float x, float z) {
		super(ecs, RandomFloor.class.getSimpleName());

		Texture tex = new Texture("monstermaze/wall.png");
		tex.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		Material white_material = new Material(TextureAttribute.createDiffuse(tex));
		
		float xOff = NumberFunctions.rndFloat(0f, .4f);
		float zOff = NumberFunctions.rndFloat(0f, .4f);
		float wOff = NumberFunctions.rndFloat(0.2f, .8f);
		float dOff = NumberFunctions.rndFloat(0.2f, .8f);

		ModelBuilder modelBuilder = new ModelBuilder();
		Model floor = modelBuilder.createRect(
				0f, 0.01f, (float) dOff,
				(float)wOff, 0.01f, (float)dOff,
				(float)wOff, 0.01f, 0f,
				0f,0.01f,0f,
				1f,1f,1f,
				white_material,
				VertexAttributes.Usage.Position | VertexAttributes.Usage.TextureCoordinates);

		Matrix3 mat = new Matrix3();
		floor.meshes.get(0).transformUV(mat);

		ModelInstance instance = new ModelInstance(floor, new Vector3(x+xOff, 0, z+zOff));
		//instance.transform.translate(Game.UNIT/2, 0, Game.UNIT/2);
		//instance.calculateTransforms();

		HasModelComponent model = new HasModelComponent("Floor", instance);
		this.addComponent(model);
	}

}
