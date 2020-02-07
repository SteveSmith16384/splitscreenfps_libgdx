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
import com.scs.splitscreenfps.game.components.HasModel;
import com.scs.splitscreenfps.game.components.MovementData;
import com.scs.splitscreenfps.game.components.PositionComponent;
import com.scs.splitscreenfps.game.data.CollisionResultsList;

public class MovementSystem extends AbstractSystem {

	private Game game;
	private CollisionCheckSystem collCheckSystem;
	
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
		if (collCheckSystem == null) {
			this.collCheckSystem = (CollisionCheckSystem)game.ecs.getSystem(CollisionCheckSystem.class);
		}

		MovementData movementData = (MovementData)entity.getComponent(MovementData.class);
		
		CollidesComponent cc = (CollidesComponent)entity.getComponent(CollidesComponent.class);
		if (cc != null) {
			cc.bb_dirty = true;
			cc.results.clear();
		}

		AutoMove auto = (AutoMove)entity.getComponent(AutoMove.class);
		if (auto != null) {
			movementData.offset = auto.dir.cpy().scl(Gdx.graphics.getDeltaTime());
		}
		if (movementData.offset.x != 0 || movementData.offset.y != 0 || movementData.offset.z != 0) {
			TagSystem tagSystem = (TagSystem)game.ecs.getSystem(TagSystem.class);
			if (tagSystem.currentIt == entity) {
				if (System.currentTimeMillis() < tagSystem.lastTagTime + TagSystem.TAG_INTERVAL) {
					return; // Frozen after being tagged
				} else {
					movementData.offset.scl(1.2f);
				}
			}
			
			this.tryMoveXAndZ(entity, game.mapData, movementData.offset, movementData.diameter, cc);
		}
	}


	/**
	 * Returns true if entity moved successfully on BOTH axis.
	 */
	private boolean tryMoveXAndZ(AbstractEntity mover, MapData world, Vector3 offset, float diameter, CollidesComponent cc) {
		PositionComponent pos = (PositionComponent)mover.getComponent(PositionComponent.class);
		pos.originalPosition.set(pos.position);
		Vector3 position = pos.position;

		boolean resultX = false;
		if (world.rectangleFree(position.x+offset.x, position.z, diameter, diameter)) {
			if (this.movementBlockedByEntity(mover, offset.x, 0, cc) == false) {
				position.x += offset.x;
				resultX = true;
			}
		}

		boolean resultZ = false;
		if (world.rectangleFree(position.x, position.z+offset.z, diameter, diameter)) {
			if (this.movementBlockedByEntity(mover, 0, offset.z, cc) == false) {
				position.z += offset.z;
				resultZ = true;
			}
		}

		if (resultX || resultZ) {
			// Move model if it has one
			HasModel hasModel = (HasModel)mover.getComponent(HasModel.class);
			if (hasModel != null) {
				hasModel.model.transform.setTranslation(position.x, position.y + hasModel.yOffset, position.z);
			}
		}
		return resultX && resultZ;
	}


	private boolean movementBlockedByEntity(AbstractEntity mover, float offX, float offZ, CollidesComponent cc) {
		CollisionResultsList cr = this.collCheckSystem.collided(mover, offX, offZ);
		if (cc != null) {
			cc.results.addAll(cr.results);
		}
		return cr.blocksMovement;
	}

}
