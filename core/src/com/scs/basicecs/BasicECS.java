package com.scs.basicecs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class BasicECS {

	private HashMap<Class<?>, ISystem> systems = new HashMap<Class<?>, ISystem>();
	private List<AbstractEntity> entities = new ArrayList<AbstractEntity>();
	private List<AbstractEntity> to_add_entities = new ArrayList<AbstractEntity>();

	public BasicECS() {
	}


	public void addSystem(ISystem system) {
		this.systems.put(system.getClass(), system);
	}


	public void removeSystem(Class<?> clazz) {
		this.systems.remove(clazz);
	}


	public ISystem getSystem(Class<?> clazz) {
		return this.systems.get(clazz);
	}

	
	public void addEntityToSystems(AbstractEntity e, Class<?> component_class) {
		//e.addComponent(component);
		
		// Add to appropriate systems
		for(ISystem isystem : this.systems.values()) {
			if (isystem instanceof AbstractSystem) {
				AbstractSystem system = (AbstractSystem)isystem;
				Class<?> system_clazz = system.getComponentClass();
				if (system_clazz != null) {
					if (component_class.equals(system_clazz)) {
						if (system.entities.contains(e) == false) {
							system.entities.add(e);
						} else {
							throw new RuntimeException("Entity " + e + " already exists in " + system);
						}
					}
				}
			}
		}
	}
	
	

	public void removeEntityFromSystems(AbstractEntity e, Class<?> component_class) {
		// Remove from appropriate systems
		for(ISystem isystem : this.systems.values()) {
			if (isystem instanceof AbstractSystem) {
				AbstractSystem system = (AbstractSystem)isystem;
				Class<?> system_clazz = system.getComponentClass();
				if (system_clazz != null) {
					if (component_class.equals(system_clazz)) {
						system.entities.remove(e);
					}
				}
			}
		}
	}
	
	

	public void addAndRemoveEntities() {
		// Remove any entities
		for (int i = this.entities.size()-1 ; i >= 0; i--) {
			AbstractEntity entity = this.entities.get(i);
			if (entity.isMarkedForRemoval()) {
				this.entities.remove(entity);

				// Remove from systems
				for(ISystem isystem : this.systems.values()) {
					if (isystem instanceof AbstractSystem) {
						AbstractSystem system = (AbstractSystem)isystem;
						Class<?> clazz = system.getComponentClass();
						if (clazz != null) {
							if (entity.getComponents().containsKey(clazz)) {
								system.entities.remove(entity);
							}
						}
					}
				}
			}
		}

		for(AbstractEntity e : this.to_add_entities) {
			for(ISystem isystem : this.systems.values()) {
				if (isystem instanceof AbstractSystem) {
					AbstractSystem system = (AbstractSystem)isystem;
					Class<?> clazz = system.getComponentClass();
					if (clazz != null) {
						if (e.getComponents().containsKey(clazz)) {
							if (system.entities.contains(e) == false) {
								system.entities.add(e);
							} else {
								// Entity might already exist since we add components to systems immediately
							}
						}
					}
				}
			}
			this.entities.add(e);			
		}

		to_add_entities.clear();
	}


	public void addEntity(AbstractEntity e) {
		this.to_add_entities.add(e);
	}


	public AbstractEntity get(int i) {
		return this.entities.get(i);
	}


	public Iterator<AbstractEntity> getIterator() {
		return this.entities.iterator();
	}

	
	public void removeAllEntities() {
		for(AbstractEntity e : this.entities) {
			e.remove();
		}
		this.to_add_entities.clear();
	}

}
