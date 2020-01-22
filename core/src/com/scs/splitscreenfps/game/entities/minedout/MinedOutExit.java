package com.scs.splitscreenfps.game.entities.minedout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.scs.basicecs.AbstractEntity;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.components.CompletesLevelData;
import com.scs.splitscreenfps.game.components.PositionData;

public class MinedOutExit extends AbstractEntity {

	private static Model floor;

	static {
		Material material = new Material(TextureAttribute.createDiffuse(new Texture(Gdx.files.internal("colours/yellow.png"))));		
		ModelBuilder modelBuilder = new ModelBuilder();
		floor = modelBuilder.createRect(
				0f, 0f, Game.UNIT,
				Game.UNIT, 0f, Game.UNIT,
				Game.UNIT, 0f, 0f,
				0f, 0f,0f,
				1f, 1f,1f,
				material,
				VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates);

	}


	public MinedOutExit(int map_x, int map_y) {
		super(MinedOutExit.class.getSimpleName());

		this.addComponent(new PositionData((map_x*Game.UNIT)+(Game.UNIT/2), (map_y*Game.UNIT)+(Game.UNIT/2)));

		/*if (Settings.RELEASE_MODE == false) {
			ModelInstance instance = new ModelInstance(floor);
			instance.transform.translate((map_x*Game.UNIT)-(Game.UNIT/2), 0.1f, (map_y*Game.UNIT)-(Game.UNIT/2));
			this.addComponent(new HasModel(instance));
		}*/

		this.addComponent(new CompletesLevelData());

	}

}
