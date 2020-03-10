package com.scs.splitscreenfps.game.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.game.EventCollision;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.MapData;
import com.scs.splitscreenfps.game.components.AnimatedComponent;
import com.scs.splitscreenfps.game.components.AutoMove;
import com.scs.splitscreenfps.game.components.MovementData;
import com.scs.splitscreenfps.game.components.PositionComponent;

public class MovementSystem extends AbstractSystem {

	private Game game;

	public MovementSystem(Game _game, BasicECS ecs) {
		super(ecs, MovementData.class);

		game = _game;
	}


	@Override
	public void processEntity(AbstractEntity entity) {
		MovementData movementData = (MovementData)entity.getComponent(MovementData.class);
		
		AutoMove auto = (AutoMove)entity.getComponent(AutoMove.class);
		if (auto != null) {
			movementData.offset = auto.dir.cpy();
		}
		movementData.offset.scl(Gdx.graphics.getDeltaTime());

		if (movementData.offset.x != 0 || movementData.offset.y != 0 || movementData.offset.z != 0) {
			if (movementData.frozenUntil < System.currentTimeMillis()) {
				/*CollidesComponent cc = (CollidesComponent)entity.getComponent(CollidesComponent.class);
				if (cc != null) {
					cc.bb_dirty = true;
				}*/
				if (movementData.must_move_x_and_z) {
					this.tryMoveXAndZ(entity, game.mapData, movementData.offset, movementData.diameter);
				} else {
					this.tryMoveXOrZ(entity, game.mapData, movementData.offset, movementData.diameter);
				}
				//movementData.blocked_on_last_move = moved;
			}
		}

		// Animate
		AnimatedComponent anim = (AnimatedComponent)entity.getComponent(AnimatedComponent.class);
		if (anim != null) {
			if (movementData.offset.len2() > 0) {
				anim.next_animation = anim.walk_anim_name;
			} else {
				anim.next_animation = anim.idle_anim_name;
			}
		}

		movementData.offset.setZero(); // Ready for next loop
	}
	

	/**
	 * Returns true if entity moved successfully on either axis.
	 */
	private boolean tryMoveXAndZ(AbstractEntity mover, MapData world, Vector3 offset, float diameter) {
		PositionComponent pos = (PositionComponent)mover.getComponent(PositionComponent.class);
		pos.originalPosition.set(pos.position);
		Vector3 position = pos.position;

		boolean result = false;
		if (world.rectangleFree(position.x+offset.x, position.z+offset.z, diameter, diameter)) {
			if (this.movementBlockedByEntity(mover, offset.x, offset.z) == false) {
				position.x += offset.x;
				position.z += offset.z;
				result = true;
			}
		} else {
			ecs.events.add(new EventCollision(mover, null));
		}

		return result;
	}


	private boolean tryMoveXOrZ(AbstractEntity mover, MapData world, Vector3 offset, float diameter) {
		PositionComponent pos = (PositionComponent)mover.getComponent(PositionComponent.class);
		pos.originalPosition.set(pos.position);
		Vector3 position = pos.position;

		boolean resultX = false;
		if (world.rectangleFree(position.x+offset.x, position.z, diameter, diameter)) {
			if (this.movementBlockedByEntity(mover, offset.x, 0) == false) {
				position.x += offset.x;
				resultX = true;
			}
		} else {
			ecs.events.add(new EventCollision(mover, null));
		}

		boolean resultZ = false;
		if (world.rectangleFree(position.x, position.z+offset.z, diameter, diameter)) {
			if (this.movementBlockedByEntity(mover, 0, offset.z) == false) {
				position.z += offset.z;
				resultZ = true;
			}
		} else {
			ecs.events.add(new EventCollision(mover, null));
		}
		return resultX || resultZ;
	}


	private boolean movementBlockedByEntity(AbstractEntity mover, float offX, float offZ) { // todo - rename
		return game.collCheckSystem.collided(mover, offX, offZ, true);
	}

}
