package com.scs.splitscreenfps.game.systems.monstermaze;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.components.MoveAStarComponent;
import com.scs.splitscreenfps.game.components.MovementData;
import com.scs.splitscreenfps.game.components.PositionComponent;
import com.scs.splitscreenfps.game.components.monstermaze.IsTRex;

import ssmith.astar.AStar_LibGDX;
import ssmith.lang.GeometryFuncs;
import ssmith.libgdx.GridPoint2Static;

public class TRexAISystem extends AbstractSystem {

	private Game game;
	private long next_check_can_see_time = 0;
	private boolean check_if_see_player = false;

	public TRexAISystem(BasicECS ecs, Game _game) {
		super(ecs, IsTRex.class);

		game = _game;
	}


	@Override
	public void processEntity(AbstractEntity entity) {
		PositionComponent posdata = (PositionComponent)entity.getComponent(PositionComponent.class);
		MoveAStarComponent mac = (MoveAStarComponent)entity.getComponent(MoveAStarComponent.class);

		if (check_if_see_player || System.currentTimeMillis() > next_check_can_see_time) {
			for (int i=0 ; i<game.players.length ; i++) {
				Vector3 dest =canSeePlayer(posdata.position, i);
				if (dest != null) {
					// Check the player isn't on spawn point
					if (game.mapData.getMapSquareAt(dest).spawn_point == false) {
						AStar_LibGDX astar = new AStar_LibGDX(game.mapData);
						mac.route = astar.findPath((int)posdata.position.x, (int)posdata.position.z, (int)dest.x, (int)dest.z);
						break;
					}
				}
			}
			check_if_see_player = false;
			next_check_can_see_time = System.currentTimeMillis() + 3000;
		}

		if (mac.route == null || mac.route.size() == 0) {
			AStar_LibGDX astar = new AStar_LibGDX(game.mapData);
			GridPoint2Static dest = game.mapData.getRandomFloorPos();
			mac.route = astar.findPath((int)posdata.position.x, (int)posdata.position.z, dest.x, dest.y);
		} else {
			GridPoint2 destpos = mac.route.get(0);
			double dist = GeometryFuncs.distance(posdata.position.x, posdata.position.z, destpos.x+.5f, destpos.y+.5f);
			if (dist < 0.2) {
				mac.route.remove(0);
			} else {
				if (mac.rotate) {
					Vector2 v = new Vector2(destpos.x+.5f-posdata.position.x, destpos.y+.5f-posdata.position.z);
					float angle_required = v.angle();
					if (Math.abs(angle_required - posdata.angle) > 5) {
						float diff = angle_required - posdata.angle;
						posdata.angle += (diff/250);
					}
					//Settings.p("Required angle: " + angle_required + ", actual=" + posdata.angle);
				}
				MovementData moveData = (MovementData)entity.getComponent(MovementData.class);
				moveData.offset = new Vector3(destpos.x+.5f-posdata.position.x, 0, destpos.y+.5f-posdata.position.z);
				moveData.offset.nor().scl(mac.speed);
			}
		}
	}


	private Vector3 canSeePlayer(Vector3 trexPos, int idx) {
		//PositionComponent trexPosData = (PositionComponent)trex.getComponent(PositionComponent.class);
		PositionComponent playerPosData = (PositionComponent)game.players[idx].getComponent(PositionComponent.class);

		if (game.mapData.canSee(trexPos, playerPosData.position)) {
			return playerPosData.position;
		} else {
			return null;
		}


	}
}
