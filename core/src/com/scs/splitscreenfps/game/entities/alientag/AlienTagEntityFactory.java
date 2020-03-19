package com.scs.splitscreenfps.game.entities.alientag;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.game.components.CollidesComponent;
import com.scs.splitscreenfps.game.components.HasModelComponent;
import com.scs.splitscreenfps.game.components.PositionComponent;

import ssmith.lang.NumberFunctions;

public class AlienTagEntityFactory {

	private AlienTagEntityFactory() {
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

		HasModelComponent model = new HasModelComponent("CrateModel", instance);
		//model.yOffset += SIZE/2;
		model.angleOffset = NumberFunctions.rnd(0, 90);
		entity.addComponent(model);

		CollidesComponent cc = new CollidesComponent(true, instance);
		entity.addComponent(cc);

		return entity;	

	}


}
