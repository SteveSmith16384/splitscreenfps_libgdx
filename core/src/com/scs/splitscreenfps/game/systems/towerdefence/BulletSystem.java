package com.scs.splitscreenfps.game.systems.towerdefence;

import java.util.List;

import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractEvent;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.Settings;
import com.scs.splitscreenfps.game.EventCollision;
import com.scs.splitscreenfps.game.components.towerdefence.IsBulletComponent;
import com.scs.splitscreenfps.game.components.towerdefence.TowerEnemyComponent;

public class BulletSystem extends AbstractSystem {

	public BulletSystem(BasicECS ecs) {
		super(ecs, IsBulletComponent.class);
	}


	@Override
	public void processEntity(AbstractEntity entity) {
		//IsBulletComponent bullet = (IsBulletComponent)entity.getComponent(IsBulletComponent.class);

		// Check collisions
		List<AbstractEvent> colls = ecs.getEvents(EventCollision.class);
		for (AbstractEvent evt : colls) {
			EventCollision coll = (EventCollision)evt;
			
			if (coll.hitEntity == null) { // Hit wall
				entity.remove();
			}

			AbstractEntity[] ents =  coll.getEntitiesByComponent(IsBulletComponent.class, TowerEnemyComponent.class);
			if (ents != null) {
				/*if (ents[1] == null) {
					ents =  coll.getEntitiesByComponent(IsBulletComponent.class, TowerEnemyComponent.class);
				}*/
				
				ents[0].remove();
				ents[1].remove();
				return;
			}
		}
	}
	
}
