package com.scs.splitscreenfps.game.components;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.scs.splitscreenfps.game.data.CollisionResult;

import ssmith.libgdx.MyBoundingBox;

public class CollidesComponent {

	public MyBoundingBox bb;
	public boolean blocksMovement = true;
	public List<CollisionResult> results = new ArrayList<CollisionResult>(); // Results since last movement

	public CollidesComponent(boolean _blocks, MyBoundingBox _bb) {
		this.blocksMovement = _blocks;
		bb = _bb;
	}


	public CollidesComponent(boolean _blocks, float rad) {
		this.blocksMovement = _blocks;
		bb = new MyBoundingBox(new Vector3(), rad, rad, rad);
	}


	public CollidesComponent(boolean _blocks, ModelInstance instance) {
		this.blocksMovement = _blocks;

		BoundingBox bbx = new BoundingBox();
		instance.calculateBoundingBox(bbx);
		bbx.mul(instance.transform); // Move bb to position
		bb = new MyBoundingBox(bbx.min, bbx.max);
	}

}
