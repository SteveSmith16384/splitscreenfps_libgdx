package com.scs.splitscreenfps.game.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.MapData;
import com.scs.splitscreenfps.game.components.AutoMove;
import com.scs.splitscreenfps.game.components.CollidesComponent;
import com.scs.splitscreenfps.game.components.MovementData;
import com.scs.splitscreenfps.game.components.PositionData;
import com.scs.splitscreenfps.game.data.CollisionResults;

public class MovementSystem extends AbstractSystem {

	private Vector3 tmp = new Vector3();
	private Game game;
	private CollisionCheckSystem collCheck;
	
	public MovementSystem(Game _game, BasicECS ecs) {
		super(ecs);

		game = _game;
	}


	@Override
	public Class<?> getComponentClass() {
		return MovementData.class;
	}


	@Override
	public void processEntity(AbstractEntity entity) {
		if (entity.isMarkedForRemoval()) {
			return;
		}
		if (collCheck == null) {
			this.collCheck = (CollisionCheckSystem)game.ecs.getSystem(CollisionCheckSystem.class);
		}

		MovementData movementData = (MovementData)entity.getComponent(MovementData.class);
		movementData.hitWall = false;

		AutoMove auto = (AutoMove)entity.getComponent(AutoMove.class);
		if (auto != null) {
			movementData.offset = auto.dir.cpy().scl(Gdx.graphics.getDeltaTime());
		}
		if (movementData.offset.x != 0 || movementData.offset.y != 0 || movementData.offset.z != 0) {
			boolean has_moved = this.tryMoveXAndZ(entity, game.mapData, movementData.offset, movementData.sizeAsFracOfMapsquare);
			if (has_moved) {
			} else {
				movementData.hitWall = true;
				if (movementData.removeIfHitWall) {
					entity.remove();
					//Settings.p(entity + " removed");
				}
			}
		}
	}


	/**
	 * Returns false if entity fails to move on any axis.
	 */
	private boolean tryMoveXAndZ(AbstractEntity mover, MapData world, Vector3 offset, float sizeAsFracOfMapsquare) {
		PositionData pos = (PositionData)mover.getComponent(PositionData.class);
		pos.originalPosition.set(pos.position);
		Vector3 position = pos.position;

		boolean resultX = false;
		if (world.rectangleFree(position.x+offset.x, position.z, sizeAsFracOfMapsquare, sizeAsFracOfMapsquare)) {
			if (this.checkForEntityCollisions(mover, offset.x, 0) == false) {
				position.x += offset.x;
				resultX = true;
			}
		}

		boolean resultZ = false;
		if (world.rectangleFree(position.x, position.z+offset.z, sizeAsFracOfMapsquare, sizeAsFracOfMapsquare)) {
			if (this.checkForEntityCollisions(mover, 0, offset.z) == false) {
				position.z += offset.z;
				resultZ = true;
			}
		}

		if (offset.y != 0) {
			position.y += offset.y;
		}
		
		// Reset collision just in case it's got out of sync
		//if (resultX || resultZ) {
		CollidesComponent moverCC = (CollidesComponent)mover.getComponent(CollidesComponent.class);
		if (moverCC != null) {
			moverCC.bb.getCenter(tmp);
			tmp.add(offset);
		}					
		//}

		return resultX && resultZ;
	}


	private boolean checkForEntityCollisions(AbstractEntity mover, float offX, float offZ) {
		CollisionResults cr = this.collCheck.collided(mover, offX, offZ);
		return cr == null || !cr.blocksMovement;
	}

}
