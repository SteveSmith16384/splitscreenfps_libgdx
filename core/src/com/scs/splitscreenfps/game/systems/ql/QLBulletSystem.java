package com.scs.splitscreenfps.game.systems.ql;

import java.util.List;

import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractEvent;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.game.EventCollision;
import com.scs.splitscreenfps.game.components.PositionComponent;
import com.scs.splitscreenfps.game.components.ql.QLPlayerData;
import com.scs.splitscreenfps.game.components.towerdefence.IsBulletComponent;
import com.scs.splitscreenfps.game.entities.EntityFactory;

public class QLBulletSystem extends AbstractSystem {

	public QLBulletSystem(BasicECS ecs) {
		super(ecs, IsBulletComponent.class);
	}


	@Override
	public void processEntity(AbstractEntity entity) {
		PositionComponent pos = (PositionComponent)entity.getComponent(PositionComponent.class);

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

			AbstractEntity[] ents = coll.getEntitiesByComponent(IsBulletComponent.class, QLPlayerData.class);
			if (ents != null) {
				IsBulletComponent bullet = (IsBulletComponent)entity.getComponent(IsBulletComponent.class);
				// todo - check if shooter is alive
				QLPlayerData shooterData = (QLPlayerData)bullet.shooter.getComponent(QLPlayerData.class);
				if (shooterData.health > 0) {
					QLPlayerData playerData = (QLPlayerData)ents[1].getComponent(QLPlayerData.class);
					if (playerData.side != bullet.side) {
						ents[0].remove();
						playerData.health -= 10;

						AbstractEntity expl = EntityFactory.createNormalExplosion(ecs, pos.position);
						ecs.addEntity(expl);

						return;
					}
				}
			}
		}
	}

}
