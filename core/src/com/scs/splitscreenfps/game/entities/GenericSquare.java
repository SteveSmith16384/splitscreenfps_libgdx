package com.scs.splitscreenfps.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.game.components.HasModel;

public class GenericSquare extends AbstractEntity {

	//private static Model floor;
	
	public GenericSquare(BasicECS ecs, int map_x, int map_y, String filename) {
		super(ecs, GenericSquare.class.getSimpleName());

		//if (floor == null) {
		BlendingAttribute blendingAttribute = new BlendingAttribute();
		blendingAttribute.opacity = 1f;
		
		Material material = new Material(TextureAttribute.createDiffuse(new Texture(Gdx.files.internal(filename))), blendingAttribute);		
			ModelBuilder modelBuilder = new ModelBuilder();
			Model floor = modelBuilder.createRect(
					0f, 0f, 1,
					1, 0f, 1f,
					1f, 0f, 0f,
					0f, 0f,0f,
					1f, 1f,1f,
					material,
					VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates);
		//}
		
		ModelInstance instance = new ModelInstance(floor);
		//instance.transform.translate((map_x*Game.UNIT)-(Game.UNIT/2), 0.1f, (map_y*Game.UNIT)-(Game.UNIT/2));
		instance.transform.translate(map_x, 0.05f, map_y); // Raise it slightly
		//instance.calculateTransforms();
		this.addComponent(new HasModel(this.getClass().getSimpleName(), instance));

	}

}
