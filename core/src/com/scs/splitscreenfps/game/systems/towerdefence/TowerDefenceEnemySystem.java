package com.scs.splitscreenfps.game.systems.towerdefence;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractEvent;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.game.EventCollision;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.components.DrawTextIn3DSpaceComponent;
import com.scs.splitscreenfps.game.components.MoveAStarComponent;
import com.scs.splitscreenfps.game.components.PositionComponent;
import com.scs.splitscreenfps.game.components.towerdefence.CanBeDamagedByEnemyComponent;
import com.scs.splitscreenfps.game.components.towerdefence.TowerEnemyComponent;

import ssmith.astar.AStar_LibGDX;
import ssmith.libgdx.GridPoint2Static;

public final class TowerDefenceEnemySystem extends AbstractSystem {

	private final Game game;
	private final GridPoint2Static targetPos;
	private final AStar_LibGDX astar;

	public TowerDefenceEnemySystem(BasicECS ecs, Game _game, GridPoint2Static _targetPos) {
		super(ecs, TowerEnemyComponent.class);

		game = _game;
		targetPos = _targetPos;
		astar = new AStar_LibGDX(game.mapData);
	}


	public void processEntity(AbstractEntity entity) {
		PositionComponent posdata = (PositionComponent)entity.getComponent(PositionComponent.class);
		MoveAStarComponent mac = (MoveAStarComponent)entity.getComponent(MoveAStarComponent.class);

		List<AbstractEvent> it = ecs.getEventsForEntity(EventCollision.class, entity);
		for (AbstractEvent e : it) {
			EventCollision evt = (EventCollision)e;
			if (evt.movingEntity == entity) {
				if (evt.hitEntity == null) {
					// Hit wall;
					mac.route.clear();
				} else {
					CanBeDamagedByEnemyComponent altar = (CanBeDamagedByEnemyComponent)evt.hitEntity.getComponent(CanBeDamagedByEnemyComponent.class);
					if (altar != null) {
						altar.health -= Gdx.graphics.getDeltaTime();
						//Settings.p("Alter health=" + altar.health);
						if (altar.health <= 0) {
							evt.hitEntity.remove();
						}
						DrawTextIn3DSpaceComponent text = (DrawTextIn3DSpaceComponent)evt.hitEntity.getComponent(DrawTextIn3DSpaceComponent.class);
						if (text != null) {
							text.text = (int)altar.health + "%";
						}
						return;
					}
				}
			}
		}

		if (mac.route == null || mac.route.size() == 0) {
			//Settings.p("Enemy getting new path!");
			mac.route = astar.findPath((int)posdata.position.x, (int)posdata.position.z, targetPos.x, targetPos.y);
		}
	}

}
