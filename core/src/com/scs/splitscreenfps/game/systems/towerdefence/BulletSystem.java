package com.scs.splitscreenfps.game.systems.towerdefence;

import java.util.List;

import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractEvent;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.Settings;
import com.scs.splitscreenfps.game.EventCollision;
import com.scs.splitscreenfps.game.components.PositionComponent;
import com.scs.splitscreenfps.game.components.towerdefence.IsBulletComponent;
import com.scs.splitscreenfps.game.components.towerdefence.IsTurretComponent;
import com.scs.splitscreenfps.game.components.towerdefence.TowerEnemyComponent;
import com.scs.splitscreenfps.game.entities.EntityFactory;

public class BulletSystem extends AbstractSystem {

	public BulletSystem(BasicECS ecs) {
		super(ecs, IsBulletComponent.class);
	}


	@Override
	public void processEntity(AbstractEntity entity) {
		//IsBulletComponent bullet = (IsBulletComponent)entity.getComponent(IsBulletComponent.class);

		PositionComponent pos = (PositionComponent)entity.getComponent(PositionComponent.class);

		// Check collisions
		List<AbstractEvent> colls = ecs.getEventsForEntity(EventCollision.class, entity);
		for (AbstractEvent evt : colls) {
			EventCollision coll = (EventCollision)evt;

			if (coll.hitEntity == null) { // Hit wall
				//Settings.p("Bullet removed after hitting wall");
				entity.remove();
				AbstractEntity expl = EntityFactory.createBlueExplosion(ecs, pos.position);
				ecs.addEntity(expl);
				continue;
			}

			AbstractEntity[] ents = coll.getEntitiesByComponent(IsBulletComponent.class, TowerEnemyComponent.class);
			if (ents != null) {
				ents[0].remove();
				ents[1].remove();

				AbstractEntity expl = EntityFactory.createNormalExplosion(ecs, pos.position);
				ecs.addEntity(expl);

				return;
			} else {
				ents = coll.getEntitiesByComponent(IsBulletComponent.class, IsTurretComponent.class);
				if (ents == null) {	// don't remove if hit ANY tower
					// Remove us 
					Settings.p("Bullet removed after hitting " + coll.hitEntity);
					entity.remove();

					AbstractEntity expl = EntityFactory.createBlueExplosion(ecs, pos.position);
					ecs.addEntity(expl);
				}				
			}
		}
	}

}
