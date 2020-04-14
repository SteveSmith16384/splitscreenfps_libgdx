package com.scs.splitscreenfps.game.systems.ql.recorddata;

import com.badlogic.gdx.math.Vector3;
import com.scs.basicecs.AbstractEntity;

public class BulletFiredRecordData extends AbstractRecordData {

	public AbstractEntity shooter;
	public Vector3 start;
	public Vector3 offset;
	
	public BulletFiredRecordData(long time, AbstractEntity _shooter, Vector3 _start, Vector3 _offset) {
		super(CMD_BULLET_FIRED, time);
		
		shooter = _shooter;
		start = _start;
		offset = _offset;
	}
}
