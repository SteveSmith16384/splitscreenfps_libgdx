package com.scs.splitscreenfps.game.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.Settings;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.components.HasAI;
import com.scs.splitscreenfps.game.components.MovementData;
import com.scs.splitscreenfps.game.components.PositionComponent;

public class MobAISystem extends AbstractSystem {

	public enum Mode {GoForPlayerIfSeen, GoForPlayerIfClose, MoveLikeRook}
	
	private Game game;

	public MobAISystem(Game _game, BasicECS ecs) {
		super(ecs);
		
		game = _game;
	}


	@Override
	public Class<?> getComponentClass() {
		return HasAI.class;
	}


	@Override
	public void processEntity(AbstractEntity entity) {
		PositionComponent pos = (PositionComponent)entity.getComponent(PositionComponent.class);
		HasAI ai = (HasAI)entity.getComponent(HasAI.class);
		MovementData movementData = (MovementData)entity.getComponent(MovementData.class);
		movementData.offset.x = 0;
		movementData.offset.y = 0;
		movementData.offset.z = 0;

		ai.changeDirTimer -= Gdx.graphics.getDeltaTime();
		ai.can_see_player = false;
/*
		if (ai.player.getPosition().dst2(pos.position) < ai.moveRange*ai.moveRange) { //&& Game.world.canSee(pos.position, Game.player.getPosition())) {
			ai.can_see_player = game.mapData.canSee(pos.position, ai.player.getPosition());
			switch (ai.mode) {
			case GoForPlayerIfSeen:
				if (ai.can_see_player) {
					ai.direction.set(ai.player.getPosition()).sub(pos.position).nor();
					ai.direction.scl(Gdx.graphics.getDeltaTime() * ai.speed);
					ai.direction.y = 0f;

					movementData.offset.x = ai.direction.x;
					movementData.offset.z = ai.direction.z;
				}
				break;

			case GoForPlayerIfClose:
				ai.direction.set(ai.player.getPosition()).sub(pos.position).nor();
				ai.direction.scl(Gdx.graphics.getDeltaTime() * ai.speed);
				ai.direction.y = 0f;

				movementData.offset.x = ai.direction.x;
				movementData.offset.z = ai.direction.z;
				break;

			case MoveLikeRook:
				if (ai.direction.len2() == 0) {
					ai.direction = getRandomDirection();
				} else if (ai.changeDirTimer <= 0) {
					ai.changeDirTimer = 1;
					if (Settings.random.nextInt(10) <= 1) {
						//Settings.p("Changing dir");
					}
				}
				movementData.offset.x = ai.direction.x;
				movementData.offset.z = ai.direction.z;
				movementData.offset.scl(Gdx.graphics.getDeltaTime() * ai.speed);
				break;

			default:
				throw new RuntimeException("Unknown AI type: " + ai.mode);
			}

		}*/

	}


	private Vector3 getRandomDirection() {
		int i = Settings.random.nextInt(4);
		switch (i) {
		case 0: return new Vector3(1, 0, 0);
		case 1: return new Vector3(0, 0, 1);
		case 2: return new Vector3(-1, 0, 0);
		default: return new Vector3(0, 0, -1);
		}
	}


}
