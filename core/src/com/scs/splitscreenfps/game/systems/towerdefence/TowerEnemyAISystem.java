package com.scs.splitscreenfps.game.systems.towerdefence;

import java.util.List;

import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractEvent;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.game.EventCollision;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.components.MoveAStarComponent;
import com.scs.splitscreenfps.game.components.PositionComponent;
import com.scs.splitscreenfps.game.components.towerdefence.TowerEnemyComponent;

import ssmith.astar.AStar_LibGDX;
import ssmith.libgdx.GridPoint2Static;

public final class TowerEnemyAISystem extends AbstractSystem {

	private final Game game;
	//private final Vector2 tmpVec2 = new Vector2();
	private final GridPoint2Static targetPos;
	private final AStar_LibGDX astar;

	public TowerEnemyAISystem(BasicECS ecs, Game _game, GridPoint2Static _targetPos) {
		super(ecs, TowerEnemyComponent.class);

		game = _game;
		targetPos = _targetPos;
		astar = new AStar_LibGDX(game.mapData);
	}


	public void processEntity(AbstractEntity entity) {
		PositionComponent posdata = (PositionComponent)entity.getComponent(PositionComponent.class);
		MoveAStarComponent mac = (MoveAStarComponent)entity.getComponent(MoveAStarComponent.class);
		/*
		if (check_if_see_player || System.currentTimeMillis() > next_check_can_see_time) {
			//Settings.p("TRex is checking if can see players...");
			check_if_see_player = false;
			next_check_can_see_time = System.currentTimeMillis() + 1000;
			boolean found = false;
			for (int i=0 ; i<game.players.length ; i++) {
				Vector3 dest = canSeePlayer(posdata.position, i);
				if (dest != null) {
					//Settings.p("TRex has seen player " + i + "!");
					found = true;
					next_check_can_see_time = System.currentTimeMillis() + 500; // Check again sooner
					// Check the player isn't on spawn point
					if (game.mapData.getMapSquareAt(dest).spawn_point == false) {
						AStar_LibGDX astar = new AStar_LibGDX(game.mapData);
						mac.route = astar.findPath((int)posdata.position.x, (int)posdata.position.z, (int)dest.x, (int)dest.z);

						TextEntity te = new TextEntity(ecs, "T-REX HAS SEEN YOU!", Gdx.graphics.getBackBufferHeight()/5, 1, new Color(0, 0, 0, 1), i, 3);
						ecs.addEntity(te);
						break;
					}
				}
			}
			if (!found) {
				//Settings.p("TRex cant see any player");
			}
		}
		 */
		
		List<AbstractEvent> it = ecs.getEvents(EventCollision.class);
		for (AbstractEvent e : it) {
			EventCollision evt = (EventCollision)e;
			if (evt.movingEntity == entity && evt.hitEntity == null) {
				// Hit wall;
				mac.route.clear();
			}
		}
			
		if (mac.route == null || mac.route.size() == 0) {
			//Settings.p("TRex getting new path!");
			//GridPoint2Static dest = game.mapData.getRandomFloorPos();
			mac.route = astar.findPath((int)posdata.position.x, (int)posdata.position.z, targetPos.x, targetPos.y);
		} else {/*
			GridPoint2 destpos = mac.route.get(0);
			double dist = GeometryFuncs.distance(posdata.position.x, posdata.position.z, destpos.x+.5f, destpos.y+.5f);
			if (dist < 0.2) {
				mac.route.remove(0);
			} else {
				if (mac.rotate) {
					tmpVec2.set(destpos.x+.5f-posdata.position.x, destpos.y+.5f-posdata.position.z);
					float angle_required = tmpVec2.angle();
					if (Math.abs(angle_required - posdata.angle_degs) > 5) {
						float diff = angle_required - posdata.angle_degs;
						posdata.angle_degs += (diff/250);
					}
					//Settings.p("Required angle: " + angle_required + ", actual=" + posdata.angle);
				}
				MovementData moveData = (MovementData)entity.getComponent(MovementData.class);
				moveData.offset.set(destpos.x+.5f-posdata.position.x, 0, destpos.y+.5f-posdata.position.z);
				moveData.offset.nor().scl(mac.speed);
			}*/
		}
	}

}
