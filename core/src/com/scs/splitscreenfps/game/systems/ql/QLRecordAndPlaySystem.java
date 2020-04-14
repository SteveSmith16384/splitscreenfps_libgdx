package com.scs.splitscreenfps.game.systems.ql;

import java.util.LinkedList;

import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.Settings;
import com.scs.splitscreenfps.game.components.PositionComponent;
import com.scs.splitscreenfps.game.components.ql.IsRecordable;
import com.scs.splitscreenfps.game.entities.ql.QuantumLeagueEntityFactory;
import com.scs.splitscreenfps.game.levels.QuantumLeagueLevel;
import com.scs.splitscreenfps.game.systems.ql.recorddata.AbstractRecordData;
import com.scs.splitscreenfps.game.systems.ql.recorddata.BulletFiredRecordData;
import com.scs.splitscreenfps.game.systems.ql.recorddata.EntityMovedRecordData;

public class QLRecordAndPlaySystem extends AbstractSystem {

	private QuantumLeagueLevel level;
	private LinkedList<AbstractRecordData> thisPhaseRecordData = new LinkedList<AbstractRecordData>(); // todo - rename
	private LinkedList<AbstractRecordData> prevPhaseRecordData = new LinkedList<AbstractRecordData>(); // todo - rename
	private long currentPhaseTime;

	public QLRecordAndPlaySystem(BasicECS _ecs, QuantumLeagueLevel _level) {
		super(_ecs, IsRecordable.class);

		level = _level;
	}


	public void loadNewRecordData() {
		this.prevPhaseRecordData.addAll(this.thisPhaseRecordData);
		this.thisPhaseRecordData.clear();
	}


	@Override
	public void process() {
		currentPhaseTime = level.getPhaseTime();

		super.process();

		// Play from prev recording
		if (this.level.qlPhaseSystem.phase_num_012 > 0) {
			if (this.prevPhaseRecordData.size() > 0) {
				AbstractRecordData next = this.prevPhaseRecordData.getFirst();
				while (next.time < this.currentPhaseTime) {
					next = this.prevPhaseRecordData.removeFirst();
					thisPhaseRecordData.add(next); // Re-add ready for next time
					processEvent(next);
					if (this.prevPhaseRecordData.size() == 0) {
						break;
					}
					next = this.prevPhaseRecordData.getFirst();
				}
			}
		}
	}


	private void processEvent(AbstractRecordData data2) {
		if (data2.cmd == AbstractRecordData.CMD_MOVED) {
			EntityMovedRecordData data = (EntityMovedRecordData)data2;
			if (ecs.containsEntity(data.entityId)) {
				AbstractEntity entity = ecs.get(data.entityId);
				PositionComponent posData = (PositionComponent)entity.getComponent(PositionComponent.class);
				posData.position.set(data.position);
				posData.angle_degs = data.direction;
			} else {
				Settings.p("No entity!");
			}
		} else if (data2.cmd == AbstractRecordData.CMD_BULLET_FIRED) {
			BulletFiredRecordData data = (BulletFiredRecordData)data2;
			AbstractEntity bullet = QuantumLeagueEntityFactory.createBullet(ecs, data.shooter, data.start, data.offset);
			ecs.addEntity(bullet);
		} else if (data2.cmd == AbstractRecordData.CMD_REMOVED) {
			/*AbstractEntity entity = ecs.get(data.entityId);
			IsRecordable isRecordable = (IsRecordable)entity.getComponent(IsRecordable.class);
			isRecordable.active = false;
			entity.remove();*/
		} else {
			throw new RuntimeException("Todo");
		}
	}


	@Override
	public void processEntity(AbstractEntity entity) {
		if (level.isGamePhase()) {
			// Save entities position etc...
			if (level.qlPhaseSystem.phase_num_012 < 2) {
				IsRecordable isRecordable = (IsRecordable)entity.getComponent(IsRecordable.class);
				if (isRecordable.active) {
					PositionComponent posData = (PositionComponent)entity.getComponent(PositionComponent.class);
					EntityMovedRecordData data = new EntityMovedRecordData(isRecordable.entity.entityId, currentPhaseTime, posData.position, posData.angle_degs);
					this.thisPhaseRecordData.add(data);
				}
			}
		}
	}


	public void addEvent(AbstractRecordData data) {
		this.thisPhaseRecordData.add(data);
	}

}
