package com.scs.basicecs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractSystem implements ISystem {

	protected BasicECS ecs;
	protected List<AbstractEntity> entities;
	private String name;
	protected Class<?> component_class;

	public AbstractSystem(BasicECS _ecs) {
		this(_ecs, null);
	}
	
	
	/**
	 * 
	 * @param _ecs
	 * @param _component_class The component that this system is interested in.
	 */
	public AbstractSystem(BasicECS _ecs,  Class<?> _component_class) {
		this.ecs = _ecs;
		component_class = _component_class;
		
		name = this.getClass().getSimpleName();

		this.ecs.addSystem(this);

		if (this.getComponentClass() != null) {
			entities = new ArrayList<AbstractEntity>();
		}
	}


	/**
	 * Note to future self: Do NOT change this to handle multiple component types.  If that is
	 * needed, create a separate system!
	 */
	public Class<?> getComponentClass() { // todo - make final when sure it works
		return component_class;
	}


	public String getName() {
		return this.getClass().getSimpleName();
	}


	// Override if required to run against specific entities specified by getComponentClass()
	public void process() {
		if (this.entities == null) {
			Iterator<AbstractEntity> it = ecs.getEntityIterator();
			while (it.hasNext()) {
				AbstractEntity entity = it.next();
				if (entity.isMarkedForRemoval()) {
					continue;
				}
				this.processEntity(entity);
			}
		} else {
			Iterator<AbstractEntity> it = entities.iterator();
			while (it.hasNext()) {
				AbstractEntity entity = it.next();
				if (entity.isMarkedForRemoval()) {
					continue;
				}
				this.processEntity(entity);
			}
		}
	}


	// Override if required to run against all entities.
	public void processEntity(AbstractEntity entity) {
	}

	
	public String toString() {
		return name;
	}
	
}
