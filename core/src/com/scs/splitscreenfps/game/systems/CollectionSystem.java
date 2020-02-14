package com.scs.splitscreenfps.game.systems;
/*
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.Settings;
import com.scs.splitscreenfps.game.components.CanCollectComponent;
import com.scs.splitscreenfps.game.components.CollidesComponent;
import com.scs.splitscreenfps.game.components.IsCollectableComponent;
import com.scs.splitscreenfps.game.data.CollisionResult;

public class CollectionSystem extends AbstractSystem {

	public CollectionSystem(BasicECS ecs) {
		super(ecs, CanCollectComponent.class);
	}

	
	@Override
	public void processEntity(AbstractEntity entity) {
		CollidesComponent cc = (CollidesComponent)entity.getComponent(CollidesComponent.class);
		for (CollisionResult cr : cc.results) {
			AbstractEntity collectable = cr.collidedWith;
			IsCollectableComponent ic = (IsCollectableComponent)collectable.getComponent(IsCollectableComponent.class);
			if (ic != null) {
				CanCollectComponent ccc = (CanCollectComponent)entity.getComponent(CanCollectComponent.class);
				Settings.p(collectable + " collected");
				ccc.items_collected.add(collectable);
				if (ic.disappearsWhenCollected) {
					entity.remove();
				}
			}
		}
	}

}
*/