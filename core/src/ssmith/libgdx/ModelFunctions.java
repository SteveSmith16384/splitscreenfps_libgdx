package ssmith.libgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.utils.UBJsonReader;

public class ModelFunctions {

	private ModelFunctions() {
	}
	
	
	public static ModelInstance loadModel(String filename) {
		Model model = null;
		if (filename.endsWith(".obj")) {
			ModelLoader loader = new ObjLoader();
			model = loader.loadModel(Gdx.files.internal(filename));
		} else if (filename.endsWith(".g3db")) {
			G3dModelLoader g3dbModelLoader;
			g3dbModelLoader = new G3dModelLoader(new UBJsonReader());
			model = g3dbModelLoader.loadModel(Gdx.files.absolute(filename));
		} else {
			throw new RuntimeException("todo");
		}

		ModelInstance instance = new ModelInstance(model);//, new Vector3(posX, posY, posZ));
	
		for(int m=0;m<instance.materials.size;m++) {
			Material mat = instance.materials.get(m);
			mat.remove(BlendingAttribute.Type);
		}
		
		return instance;
	}
	
	
	public static void getOrigin(ModelInstance model, Vector3 out) {
		BoundingBox bb = new BoundingBox();
		model.calculateBoundingBox(bb);
		bb.mul(model.transform);
		bb.getCenter(out);
		out.y -= bb.getHeight()/2; // Make origin at the bottom of the model
	}

}
