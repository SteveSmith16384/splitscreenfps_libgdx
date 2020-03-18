package com.scs.splitscreenfps.game.systems;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.Settings;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.components.MoveAStarComponent;
import com.scs.splitscreenfps.game.components.MovementData;
import com.scs.splitscreenfps.game.components.PositionComponent;

import ssmith.lang.GeometryFuncs;

public class MoveAStarSystem extends AbstractSystem {

	private final Game game;
	private final Vector2 tmpVec2 = new Vector2();

	public MoveAStarSystem(BasicECS ecs, Game _game) {
		super(ecs, MoveAStarComponent.class);

		game = _game;
	}


	@Override
	public void processEntity(AbstractEntity entity) {
		MoveAStarComponent mac = (MoveAStarComponent)entity.getComponent(MoveAStarComponent.class);
		PositionComponent posdata = (PositionComponent)entity.getComponent(PositionComponent.class);
		if (mac.route == null || mac.route.size() == 0) {
			// DO NOTHING!  A* system should find route
		} else {
			GridPoint2 destpos = mac.route.get(0);
			double dist = GeometryFuncs.distance(posdata.position.x, posdata.position.z, destpos.x+.5f, destpos.y+.5f);
			if (dist < 0.2) {
				mac.route.remove(0);
			} else {
				if (mac.rotate) {
					tmpVec2.set(destpos.x+.5f-posdata.position.x, destpos.y+.5f-posdata.position.z);
					tmpVec2.nor();
					float angle_required = tmpVec2.angle();
					/*if (Math.abs(angle_required - posdata.angle_degs) > 5) { //todo -re-add
						float diff = angle_required - posdata.angle_degs;
						posdata.angle_degs += (diff/250);
					}*/
					Settings.p("Required angle: " + angle_required);// + ", actual=" + posdata.angle_degs);
					posdata.angle_degs = angle_required; //todo
				}
				MovementData moveData = (MovementData)entity.getComponent(MovementData.class);
				moveData.offset.set(destpos.x+.5f-posdata.position.x, 0, destpos.y+.5f-posdata.position.z);
				moveData.offset.nor().scl(mac.speed);
			}
		}
	}

}

