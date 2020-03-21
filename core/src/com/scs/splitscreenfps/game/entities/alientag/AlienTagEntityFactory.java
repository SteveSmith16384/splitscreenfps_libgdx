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


	public static AbstractEntity createCrate(BasicECS ecs, float cx, float cz) {
		float SIZE = 0.3f;
		AbstractEntity entity = new AbstractEntity(ecs, "Crate");

		Material black_material = new Material(TextureAttribute.createDiffuse(new Texture("tag/scifi_crate.jpg")));
		ModelBuilder modelBuilder = new ModelBuilder();
		Model box_model = modelBuilder.createBox(SIZE, SIZE, SIZE, black_material, VertexAttributes.Usage.Position | VertexAttributes.Usage.TextureCoordinates);

		//PositionComponent posData = new PositionComponent(cx+(SIZE/2), SIZE/2, cz+(SIZE/2));
		PositionComponent posData = new PositionComponent(cx, SIZE/2, cz);
		entity.addComponent(posData);

		ModelInstance instance = new ModelInstance(box_model, posData.position);
		//instance.transform.rotate(Vector3.Z, 90); // Rotates cube so textures are upright

		HasModelComponent model = new HasModelComponent("CrateModel", instance);
		model.angleOffset = NumberFunctions.rnd(0, 90);
		entity.addComponent(model);

		CollidesComponent cc = new CollidesComponent(true, SIZE/2);
		entity.addComponent(cc);

		return entity;	

	}


}
