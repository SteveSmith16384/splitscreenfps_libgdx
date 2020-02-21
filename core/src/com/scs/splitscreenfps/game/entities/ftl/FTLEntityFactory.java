package com.scs.splitscreenfps.game.entities.ftl;

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
import com.scs.splitscreenfps.game.components.HasModel;

public class FTLEntityFactory {

	private FTLEntityFactory() {
	}
	
	
	public static AbstractEntity createBlockThing(BasicECS ecs, float mapPosX, float mapPosZ) {
		float thickness = .1f;
		
		AbstractEntity entity = new AbstractEntity(ecs, "Block");
		
		Material black_material = new Material(TextureAttribute.createDiffuse(new Texture("sf/wall2.jpg")));
		ModelBuilder modelBuilder = new ModelBuilder();
		Model box_model = modelBuilder.createBox(1f, thickness, thickness, black_material, VertexAttributes.Usage.Position | VertexAttributes.Usage.TextureCoordinates);

		ModelInstance instance = new ModelInstance(box_model, new Vector3(mapPosX, 1-thickness, mapPosZ));
		//instance.transform.rotate(Vector3.Z, 90); // Rotates cube so textures are upright

		HasModel model = new HasModel("Block", instance);
		entity.addComponent(model);
		
		return entity;
	}

}
