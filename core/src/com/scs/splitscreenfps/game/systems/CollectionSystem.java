package com.scs.splitscreenfps.game.systems;

import java.util.Iterator;

import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.Settings;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.components.CanCollect;
import com.scs.splitscreenfps.game.components.IsCollectable;
import com.scs.splitscreenfps.game.components.PositionData;

public class CollectionSystem extends AbstractSystem {

	//private ICollectionHandler collectionHandler;
	
	public CollectionSystem(BasicECS ecs) {//, ICollectionHandler _collectionHandler) {
		super(ecs);
		
		//collectionHandler = _collectionHandler;
	}


	@Override
	public Class<?> getComponentClass() {
		return CanCollect.class;
	}


	@Override
	public void processEntity(AbstractEntity collector) {
		PositionData collector_pos = (PositionData)collector.getComponent(PositionData.class);
		Iterator<AbstractEntity> it = ecs.getIterator();
		while (it.hasNext()) {
			AbstractEntity collectable = it.next();
			IsCollectable ic = (IsCollectable)collectable.getComponent(IsCollectable.class);
			if (ic != null) {
				PositionData collectable_pos = (PositionData)collectable.getComponent(PositionData.class);
				if (collector_pos.position.dst(collectable_pos.position) < Game.UNIT/2f) {
					Settings.p(collectable.name + " collected");
					collectable.remove();
					//Game.gameLevel.entityCollected(collector, collectable);
					//Game.audio.play("beepfx_samples/28_item_1.wav");
				}
			}
		}
	}

}
