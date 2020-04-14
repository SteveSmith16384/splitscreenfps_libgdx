package com.scs.splitscreenfps.game.systems.ql;

import com.badlogic.gdx.math.Vector3;

public class EntityRecordData {

	public static final int CMD_CREATED = 1;
	public static final int CMD_MOVED = 2;
	public static final int CMD_DESTROYED = 3;
	
	public int cmd;
	public int entityId;
	public long time;
	public Vector3 position = new Vector3();
	public float direction;
	
	public EntityRecordData(int _cmd, int _entityId, long _time, Vector3 pos, float dir) {
		cmd = _cmd;
		entityId = _entityId;
		time = _time;
		this.position.set(pos);
		this.direction = dir;
	}
	
}
