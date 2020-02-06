package com.scs.splitscreenfps.game.systems;

import java.util.Iterator;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.math.Vector3;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.components.HasDecal;
import com.scs.splitscreenfps.game.components.PositionData;

public class DrawDecalSystem extends AbstractSystem {

	private Vector3 tmp = new Vector3();
	private Game game;

	public DrawDecalSystem(Game _game, BasicECS ecs) {
		super(ecs);

		game = _game;
	}


	@Override
	public Class<?> getComponentClass() {
		return HasDecal.class;
	}


	@Override
	public void process() {
		int i = game.currentViewId;
		Camera camera = game.players[i].camera;
		DecalBatch batch = game.viewports[i].decalBatch;

		Iterator<AbstractEntity> it = entities.iterator();
		while (it.hasNext()) {
			AbstractEntity entity = it.next();
			this.processEntity(entity, camera, batch);
		}

		batch.flush();
	}


	//@Override
	public void processEntity(AbstractEntity entity, Camera camera, DecalBatch batch) {
		HasDecal hasDecal = (HasDecal)entity.getComponent(HasDecal.class);
		PositionData hasPosition = (PositionData)entity.getComponent(PositionData.class);
		updateTransform(camera, hasDecal, hasPosition);

		if(!camera.frustum.sphereInFrustum(hasPosition.position, 1f)) {
			return;
		}

		batch.add(hasDecal.decal);
	}


	private void updateTransform(Camera cam, HasDecal hasDecal, PositionData pos) {
		if(hasDecal.faceCamera) {
			tmp.set(cam.direction).scl(-1);
			if(!hasDecal.faceCameraTilted) {
				tmp.y = 0;
			}
			hasDecal.decal.setRotation(tmp, Vector3.Y);
			hasDecal.decal.rotateY(hasDecal.rotation);
		} else {
			hasDecal.decal.setRotationY(hasDecal.rotation);
		}

		hasDecal.decal.setPosition(pos.position);
		hasDecal.decal.translateY(.5f);

	}


}
