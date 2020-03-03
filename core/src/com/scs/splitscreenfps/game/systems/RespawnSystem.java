package com.scs.splitscreenfps.game.systems;

import java.util.ArrayList;
import java.util.List;

import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.BasicECS;
import com.scs.basicecs.ISystem;
import com.scs.splitscreenfps.game.components.WillRespawnComponent;

public class RespawnSystem implements ISystem {

	private List<AbstractEntity> entities = new ArrayList<AbstractEntity>();
	private BasicECS ecs;

	public RespawnSystem(BasicECS _ecs) {
		ecs = _ecs;
	}


	public void addEntity(AbstractEntity e) {
		e.addComponent(new WillRespawnComponent());
		this.entities.add(e);
		e.remove();
	}	


	@Override
	public void process() {
		for (int i=this.entities.size()-1 ; i>= 0 ; i--) {
			AbstractEntity e = this.entities.get(i);
			WillRespawnComponent wrc = (WillRespawnComponent)e.getComponent(WillRespawnComponent.class);
			if (wrc.respawn_time < System.currentTimeMillis()) {
				e.removeComponent(WillRespawnComponent.class);
				ecs.addEntity(e);
				this.entities.remove(i);
			}
		}
	}
	
}