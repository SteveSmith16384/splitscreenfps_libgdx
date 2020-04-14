package com.scs.splitscreenfps.game.systems.ql.recorddata;

public abstract class AbstractRecordData {

	public static final int CMD_BULLET_FIRED = 1; // todo - make enums
	public static final int CMD_MOVED = 2;
	public static final int CMD_REMOVED = 3;
	
	public int cmd;
	public long time;
	
	public AbstractRecordData(int _cmd, long _time) {
		cmd = _cmd;
		time = _time;
	}

}
