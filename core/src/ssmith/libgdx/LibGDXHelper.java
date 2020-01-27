package ssmith.libgdx;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;

public class LibGDXHelper {

	public LibGDXHelper() {
	}
	
	
	public static BoundingBox createFromCentreAndExtents(Vector3 centre, float x, float y, float z) {
		Vector3 min = new Vector3(centre.x-x, centre.y-y, centre.z-z);
		Vector3 max = new Vector3(centre.x+x, centre.y+y, centre.z+z);
		BoundingBox bb = new BoundingBox(min, max);
		return bb;
	}

}
