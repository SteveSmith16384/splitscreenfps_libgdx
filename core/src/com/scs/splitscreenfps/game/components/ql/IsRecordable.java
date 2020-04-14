package com.scs.splitscreenfps.game.components.ql;

import com.scs.basicecs.AbstractEntity;

public class IsRecordable {

	public String name;
	public boolean active = true; // Otherwise, it's been removed from the game (or not added yet?)
	public AbstractEntity entity;
	
	public IsRecordable(String _name, AbstractEntity _entity) {
		name = _name;
		entity = _entity;
		
		if (entity == null) {
			throw new RuntimeException("Null entity!");
		}
	}
	
}
