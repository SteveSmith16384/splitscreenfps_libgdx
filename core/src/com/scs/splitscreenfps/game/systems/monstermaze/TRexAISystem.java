package com.scs.splitscreenfps.game.systems.monstermaze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.Settings;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.components.MoveAStarComponent;
import com.scs.splitscreenfps.game.components.PositionComponent;
import com.scs.splitscreenfps.game.components.monstermaze.IsTRex;
import com.scs.splitscreenfps.game.entities.TextEntity;

import ssmith.astar.AStar_LibGDX;
import ssmith.libgdx.GridPoint2Static;

public class TRexAISystem extends AbstractSystem {

	private Game game;
	private long next_check_can_see_time = 0;
	private boolean check_if_see_player = false;
	//private Vector2 tmpVec2 = new Vector2();
	
	public TRexAISystem(BasicECS ecs, Game _game) {
		super(ecs, IsTRex.class);

		game = _game;
	}


	@Override
	public void processEntity(AbstractEntity entity) {
		PositionComponent posdata = (PositionComponent)entity.getComponent(PositionComponent.class);
		MoveAStarComponent mac = (MoveAStarComponent)entity.getComponent(MoveAStarComponent.class);

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

		if (mac.route == null || mac.route.size() == 0) {
			Settings.p("TRex getting new path!");
			check_if_see_player = true;
			AStar_LibGDX astar = new AStar_LibGDX(game.mapData);
			GridPoint2Static dest = game.mapData.getRandomFloorPos();
			mac.route = astar.findPath((int)posdata.position.x, (int)posdata.position.z, dest.x, dest.y);
		}
	}


	private Vector3 canSeePlayer(Vector3 trexPos, int idx) {
		PositionComponent playerPosData = (PositionComponent)game.players[idx].getComponent(PositionComponent.class);

		if (game.mapData.canSee(trexPos, playerPosData.position)) {
			return playerPosData.position;
		} else {
			return null;
		}
	}

}
