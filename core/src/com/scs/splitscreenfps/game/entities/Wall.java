package com.scs.splitscreenfps.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.scs.basicecs.AbstractEntity;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.components.HasModel;

public class Wall extends AbstractEntity {

	public Wall(String tex_filename, int map_width, int map_height) {
		super(Wall.class.getSimpleName());
		
		Material black_material = new Material(TextureAttribute.createDiffuse(new Texture(tex_filename)));
		ModelBuilder modelBuilder = new ModelBuilder();
		Model box_model = modelBuilder.createBox(Game.UNIT, Game.UNIT, Game.UNIT, black_material, VertexAttributes.Usage.Position | VertexAttributes.Usage.TextureCoordinates);

		ModelInstance instance = new ModelInstance(box_model);
		//instance.transform.translate(map_width*Game.UNIT, Game.UNIT/2f, map_height*Game.UNIT);
		//instance.transform.translate(map_width*Game.UNIT-(Game.UNIT/2), Game.UNIT/2f, map_height*Game.UNIT-(Game.UNIT/2));
		instance.transform.translate(map_width*Game.UNIT+(Game.UNIT/2), Game.UNIT/2f, map_height*Game.UNIT+(Game.UNIT/2));
		instance.transform.rotate(Vector3.Z, 90);

		HasModel model = new HasModel(instance);
		this.addComponent(model);
	}

}
