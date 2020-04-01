package com.scs.splitscreenfps.game.entities.bladerunner;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.game.components.AnimatedComponent;
import com.scs.splitscreenfps.game.components.CollidesComponent;
import com.scs.splitscreenfps.game.components.HasModelComponent;
import com.scs.splitscreenfps.game.components.MovementData;
import com.scs.splitscreenfps.game.components.PositionComponent;
import com.scs.splitscreenfps.game.components.bladerunner.IsCivilianComponent;

import ssmith.libgdx.ModelFunctions;

public class BladeRunnerEntityFactory {

	private BladeRunnerEntityFactory() {
	}


	public static AbstractEntity createCiv(BasicECS ecs, float x, float z) {
		AbstractEntity e = new AbstractEntity(ecs, "BR_Civilian");

		PositionComponent pos = new PositionComponent(x+0.5f, 0, z+0.5f);
		e.addComponent(pos);

		ModelInstance instance = ModelFunctions.loadModel("shared/models/quaternius/Smooth_Male_Shirt.g3db", false);
		float scale = ModelFunctions.getScaleForHeight(instance, .8f);
		instance.transform.scl(scale);		
		Vector3 offset = ModelFunctions.getOrigin(instance);
		HasModelComponent hasModel = new HasModelComponent("BR_Civilian", instance, offset, -90, scale);
		e.addComponent(hasModel);

		AnimationController animation = new AnimationController(instance);
		AnimatedComponent anim = new AnimatedComponent(animation, "HumanArmature|Man_Walk", "HumanArmature|Man_Idle");
		anim.animationController = animation;
		e.addComponent(anim);

		//MoveAStarComponent astar = new MoveAStarComponent(1.9f, false);
		//e.addComponent(astar);

		e.addComponent(new MovementData());
		e.addComponent(new CollidesComponent(false, .2f));
		e.addComponent(new IsCivilianComponent());

		return e;
	}


	public static AbstractEntity createSkyScraper(BasicECS ecs, String tex_filename, int mapPosX, int mapPosZ, int w, int d) {
		final float HEIGHT = 10;
		AbstractEntity e = new AbstractEntity(ecs, "Skyscraper");

		Material black_material = new Material(TextureAttribute.createDiffuse(new Texture("bladerunner/textures/SmallSPattern_S.jpg")));
		
		ModelBuilder modelBuilder = new ModelBuilder();
		Model box_model = modelBuilder.createBox(w, HEIGHT, d, black_material, VertexAttributes.Usage.Position | VertexAttributes.Usage.TextureCoordinates);

		// Tile it
		Matrix3 mat = new Matrix3();
		mat.scl(new Vector2(w, d));
		box_model.meshes.get(0).transformUV(mat);

		ModelInstance instance = new ModelInstance(box_model, new Vector3(mapPosX+0.5f, HEIGHT/2, mapPosZ+0.5f));
		instance.transform.rotate(Vector3.Z, 90); // Rotates cube so textures are upright

		HasModelComponent model = new HasModelComponent("Skyscraper", instance);
		e.addComponent(model);
		
		return e;
	}



}
