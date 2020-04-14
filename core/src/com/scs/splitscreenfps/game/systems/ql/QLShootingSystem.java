package com.scs.splitscreenfps.game.systems.ql;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector3;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.Settings;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.components.PositionComponent;
import com.scs.splitscreenfps.game.components.ql.QLCanShoot;
import com.scs.splitscreenfps.game.entities.AbstractPlayersAvatar;
import com.scs.splitscreenfps.game.entities.ql.QuantumLeagueEntityFactory;
import com.scs.splitscreenfps.game.input.ControllerInputMethod;
import com.scs.splitscreenfps.game.input.IInputMethod;
import com.scs.splitscreenfps.game.input.MouseAndKeyboardInputMethod;
import com.scs.splitscreenfps.game.input.NoInputMethod;
import com.scs.splitscreenfps.game.levels.QuantumLeagueLevel;
import com.scs.splitscreenfps.game.systems.ql.recorddata.BulletFiredRecordData;

public class QLShootingSystem extends AbstractSystem {

	private Game game;
	private QuantumLeagueLevel level;
	
	public QLShootingSystem(BasicECS ecs, Game _game, QuantumLeagueLevel _level) {
		super(ecs, QLCanShoot.class);
		
		game = _game;
		level = _level;
	}


	@Override
	public void processEntity(AbstractEntity entity) {
		if (level.isGamePhase() == false) {
			return;
		}
		
		QLCanShoot cc = (QLCanShoot)entity.getComponent(QLCanShoot.class);
		if (cc.lastShotTime + 1000 > System.currentTimeMillis()) {
			return;
		}

		AbstractPlayersAvatar player = (AbstractPlayersAvatar)entity;

		if (isShootPressed(player.inputMethod)) {
			Settings.p("Shot!");
			
			cc.lastShotTime = System.currentTimeMillis();

			PositionComponent posData = (PositionComponent)entity.getComponent(PositionComponent.class);
			
			Vector3 startPos = new Vector3();
			startPos.set(posData.position);
			
			Vector3 tmpBulletOffset = new Vector3();
			tmpBulletOffset.set((float)Math.sin(Math.toRadians(posData.angle_degs-90)), 0, (float)Math.cos(Math.toRadians(posData.angle_degs-90)));
			tmpBulletOffset.nor();
			tmpBulletOffset.scl(-4);
			AbstractEntity bullet = QuantumLeagueEntityFactory.createBullet(ecs, player, startPos, tmpBulletOffset);
			game.ecs.addEntity(bullet);
			
			level.qlRecordAndPlaySystem.addEvent(new BulletFiredRecordData(this.level.getPhaseTime(), player, startPos, tmpBulletOffset));
		}
	}


	private boolean isShootPressed(IInputMethod input) {
		if (input instanceof MouseAndKeyboardInputMethod) { 
			return input.isKeyPressed(Keys.SPACE);
		} else if (input instanceof ControllerInputMethod) {
			return input.isCrossPressed();
		} else if (input instanceof NoInputMethod) {
			return false;
		} else {
			throw new RuntimeException("Unknown input type");
		}

	}

}
