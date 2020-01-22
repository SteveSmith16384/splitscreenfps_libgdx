package com.scs.splitscreenfps.game;

import com.scs.basicecs.AbstractEntity;

public interface ICollectionHandler {

	void entityCollected(AbstractEntity collector, AbstractEntity collectable);
}
