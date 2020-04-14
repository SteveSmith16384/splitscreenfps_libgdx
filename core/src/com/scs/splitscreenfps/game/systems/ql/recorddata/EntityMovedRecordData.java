package com.scs.splitscreenfps.game.systems.ql.recorddata;

import com.badlogic.gdx.math.Vector3;

public class EntityMovedRecordData extends AbstractRecordData {

	public int entityId;
	public Vector3 position = new Vector3();
	public float direction;
	
	public EntityMovedRecordData(int _entityId, long _time, Vector3 pos, float dir) {
		super(CMD_MOVED, _time);
		entityId = _entityId;
		position.set(pos);
		direction = dir;
	}
	

}
