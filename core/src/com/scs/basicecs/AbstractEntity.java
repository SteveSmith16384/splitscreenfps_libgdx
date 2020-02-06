package com.scs.basicecs;

import java.util.HashMap;

public class AbstractEntity {

	private static int next_id = 0;

	private BasicECS ecs;

	public int id;
	public String name;
	private HashMap<Class<?>, Object> components = new HashMap<Class<?>, Object>();
	private HashMap<Class<?>, Object> hiddenComponents = new HashMap<Class<?>, Object>(); // For temporarily removing components, e.g. collision
	private boolean markForRemoval = false;
	
	public AbstractEntity(BasicECS _ecs, String _name) {
		ecs = _ecs;
		this.id = next_id++;
		this.name = _name;
	}


	public void addComponent(Object component) {
		this.components.put(component.getClass(), component);
		ecs.addComponentToSystems(this, component);
	}


	public void removeComponent(Object component) {
		this.components.remove(component.getClass());
		ecs.removeComponentFromSystems(this, component);
	}


	public void hideComponent(Class clazz) {
		Object component = this.components.remove(clazz);
		this.hiddenComponents.put(clazz, component);
		ecs.removeComponentFromSystems(this, component);
	}


	public void restoreComponent(Class clazz) {
		Object component = this.hiddenComponents.remove(clazz);
		this.components.put(clazz, component);
		ecs.addComponentToSystems(this, component);
	}


	public Object getComponent(Class<?> name) {
		if (this.components.containsKey(name)) {
			return this.components.get(name);
		} else {
			return null;
		}
	}


	public HashMap<Class<?>, Object> getComponents() {
		return this.components;
	}


	public boolean isMarkedForRemoval() {
		return this.markForRemoval;
	}


	public void remove() {
		this.markForRemoval = true;
	}


	@Override
	public String toString() {
		return name;
	}

}
