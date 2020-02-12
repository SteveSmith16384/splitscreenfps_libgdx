package com.scs.splitscreenfps.game.systems;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector3;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.components.MoveAStarComponent;
import com.scs.splitscreenfps.game.components.MovementData;
import com.scs.splitscreenfps.game.components.PositionComponent;

import ssmith.astar.AStar_LibGDX;
import ssmith.lang.GeometryFuncs;

public class MoveAStarSystem extends AbstractSystem {
	
	private Game game;

	public MoveAStarSystem(BasicECS ecs, Game _game) {
		super(ecs);
		
		game = _game;
	}


	@Override
	public Class<?> getComponentClass() {
		return MoveAStarComponent.class;
	}


	@Override
	public void processEntity(AbstractEntity entity) {
		MoveAStarComponent mac = (MoveAStarComponent)entity.getComponent(MoveAStarComponent.class);
		PositionComponent posdata = (PositionComponent)entity.getComponent(PositionComponent.class);
		if (mac.route == null || mac.route.size() == 0) {
			AStar_LibGDX astar = new AStar_LibGDX(game.mapData);
			GridPoint2 dest = game.mapData.getRandomFloorPos();
			mac.route = astar.findPath((int)posdata.position.x, (int)posdata.position.z, dest.x, dest.y);
		} else {
			GridPoint2 destpos = mac.route.get(0);
			double dist = GeometryFuncs.distance(posdata.position.x, posdata.position.z, destpos.x+.5f, destpos.y+.5f);
			if (dist < 0.2) {
				mac.route.remove(0);
			} else {
				if (mac.rotate) {
					// todo
				}
				MovementData moveData = (MovementData)entity.getComponent(MovementData.class);
				moveData.offset = new Vector3(destpos.x+.5f-posdata.position.x, 0, destpos.y+.5f-posdata.position.z);
				moveData.offset.nor().scl(mac.speed);
			}
		}
	}
	
}
