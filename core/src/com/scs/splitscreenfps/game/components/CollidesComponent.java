package com.scs.splitscreenfps.game.components;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.collision.BoundingBox;

import ssmith.libgdx.MyBoundingBox;

public class CollidesComponent {

	public MyBoundingBox bb;
	public boolean blocksMovement = true;

	public CollidesComponent(boolean _blocks, MyBoundingBox _bb) {
		this.blocksMovement = _blocks;
		bb = _bb;
	}


	public CollidesComponent(boolean _blocks, ModelInstance instance) {
		this.blocksMovement = _blocks;
		
		BoundingBox bbx = new BoundingBox();
		instance.calculateBoundingBox(bbx);
		bbx.mul(instance.transform);
		/*Vector3 minOffset = new Vector3(bbx.min);
		minOffset.add(instance.transform.getTranslation(new Vector3()));
		Vector3 maxOffset = new Vector3(bbx.max);
		maxOffset.add(instance.transform.getTranslation(new Vector3()));
		bb = new MyBoundingBox(minOffset, maxOffset);*/
		bb = new MyBoundingBox(bbx.min, bbx.max);
	}
}
