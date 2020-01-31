package com.scs.splitscreenfps.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;
import com.scs.basicecs.AbstractEntity;
import com.scs.splitscreenfps.game.components.HasModel;
import com.scs.splitscreenfps.game.components.HasModelCycle;

public class Floor extends AbstractEntity {

	public Floor(String tex_filename, int map_width, int map_height, boolean tile) {
		this(tex_filename, null, map_width, map_height, tile);
	}


	public Floor(String tex_filename1, String tex_filename2, int map_width, int map_height, boolean tile) {
		super(Floor.class.getSimpleName());

		HasModelCycle model_cycle = new HasModelCycle(.5f, 2);

		{
			Texture tex = new Texture(tex_filename1);
			tex.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
			Material white_material = new Material(TextureAttribute.createDiffuse(tex));		

			ModelBuilder modelBuilder = new ModelBuilder();
			Model floor = modelBuilder.createRect(
					0f,0f, (float) map_height,
					(float)map_width, 0f, (float)map_height,
					(float)map_width, 0f, 0f,
					0f,0f,0f,
					1f,1f,1f,
					white_material,
					VertexAttributes.Usage.Position | VertexAttributes.Usage.TextureCoordinates);

			Matrix3 mat = new Matrix3();
			if (tile) {
				mat.scl(new Vector2(map_width, map_height));
			}
			floor.meshes.get(0).transformUV(mat);

			ModelInstance instance = new ModelInstance(floor);
			//instance.transform.translate(Game.UNIT/2, 0, Game.UNIT/2);
			//instance.calculateTransforms();
			
			HasModel model = new HasModel(instance);
			this.addComponent(model);

			model_cycle.models[0] = instance;
		}

		if (tex_filename2 != null)
		{
			Texture tex = new Texture(tex_filename2);
			tex.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
			Material white_material = new Material(TextureAttribute.createDiffuse(tex));		

			ModelBuilder modelBuilder = new ModelBuilder();
			Model floor = modelBuilder.createRect(
					0f,0f, (float) map_height,
					(float)map_width, 0f, (float)map_height,
					(float)map_width, 0f, 0f,
					0f,0f,0f,
					1f,1f,1f,
					white_material,
					VertexAttributes.Usage.Position | VertexAttributes.Usage.TextureCoordinates);

			Matrix3 mat = new Matrix3();
			if (tile) {
				mat.scl(new Vector2(map_width, map_height));
			}
			floor.meshes.get(0).transformUV(mat);

			ModelInstance instance = new ModelInstance(floor);
			model_cycle.models[1] = instance;

			this.addComponent(model_cycle);
		}

	}

}
