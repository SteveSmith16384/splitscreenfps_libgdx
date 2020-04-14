package com.scs.splitscreenfps.game.systems.ql;

import java.util.LinkedList;

import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.Settings;
import com.scs.splitscreenfps.game.components.AnimatedComponent;
import com.scs.splitscreenfps.game.components.PositionComponent;
import com.scs.splitscreenfps.game.components.ql.IsRecordable;
import com.scs.splitscreenfps.game.entities.ql.QuantumLeagueEntityFactory;
import com.scs.splitscreenfps.game.levels.QuantumLeagueLevel;
import com.scs.splitscreenfps.game.systems.ql.recorddata.AbstractRecordData;
import com.scs.splitscreenfps.game.systems.ql.recorddata.BulletFiredRecordData;
import com.scs.splitscreenfps.game.systems.ql.recorddata.EntityMovedRecordData;

public class QLRecordAndPlaySystem extends AbstractSystem {

	private QuantumLeagueLevel level;
	private LinkedList<AbstractRecordData> dataBeingRecorded = new LinkedList<AbstractRecordData>();
	private LinkedList<AbstractRecordData> dataBeingPlayedBack = new LinkedList<AbstractRecordData>();
	private long currentPhaseTime;

	public QLRecordAndPlaySystem(BasicECS _ecs, QuantumLeagueLevel _level) {
		super(_ecs, IsRecordable.class);

		level = _level;
	}


	public void loadNewRecordData() {
		this.dataBeingPlayedBack.addAll(this.dataBeingRecorded);
		this.dataBeingRecorded.clear();
	}


	@Override
	public void process() {
		currentPhaseTime = level.getPhaseTime();

		super.process();

		// Play from prev recording
		if (this.level.qlPhaseSystem.phase_num_012 > 0) {
			if (this.dataBeingPlayedBack.size() > 0) {
				AbstractRecordData next = this.dataBeingPlayedBack.getFirst();
				while (next.time < this.currentPhaseTime) {
					next = this.dataBeingPlayedBack.removeFirst();
					dataBeingRecorded.add(next); // Re-add ready for next time
					processEvent(next);
					if (this.dataBeingPlayedBack.size() == 0) {
						break;
					}
					next = this.dataBeingPlayedBack.getFirst();
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
				AnimatedComponent anim = (AnimatedComponent)entity.getComponent(AnimatedComponent.class);
				if (posData.position.equals(data.position)) {
					anim.next_animation = anim.walk_anim_name;
				} else {
					anim.next_animation = anim.idle_anim_name;
				}
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
					this.dataBeingRecorded.add(data);
				}
			}
		}
	}


	public void addEvent(AbstractRecordData data) {
		this.dataBeingRecorded.add(data);
	}

}
