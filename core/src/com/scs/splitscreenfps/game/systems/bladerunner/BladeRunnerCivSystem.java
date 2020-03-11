package com.scs.splitscreenfps.game.systems.bladerunner;

import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.components.PositionComponent;
import com.scs.splitscreenfps.game.components.bladerunner.IsCivilianComponent;

public class BladeRunnerCivSystem extends AbstractSystem {

	private Game game;

	public BladeRunnerCivSystem(BasicECS ecs,Game _game) {
		super(ecs, IsCivilianComponent.class);
		
		game = _game;
	}


	@Override
	public void processEntity(AbstractEntity entity) {
		PositionComponent posdata = (PositionComponent)entity.getComponent(PositionComponent.class);
		//MoveAStarComponent mac = (MoveAStarComponent)entity.getComponent(MoveAStarComponent.class);

		/*if (mac.route == null || mac.route.size() == 0) {
			//Settings.p("TRex getting new path!");
			AStar_LibGDX astar = new AStar_LibGDX(game.mapData);
			GridPoint2Static dest = game.mapData.getRandomFloorPos();
			mac.route = astar.findPath((int)posdata.position.x, (int)posdata.position.z, dest.x, dest.y);
		}*/
	}

}
