package com.scs.splitscreenfps.game.systems.towerdefence;

import com.badlogic.gdx.math.Vector2;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.Settings;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.components.PositionComponent;
import com.scs.splitscreenfps.game.components.towerdefence.IsTurretComponent;

public class TurretSystem extends AbstractSystem {
	
	private Game game;
	private Vector2 tmp = new Vector2();
	
	public TurretSystem(BasicECS ecs, Game _game) {
		super(ecs, IsTurretComponent.class);
		
		game = _game;
	}


	@Override
	public void processEntity(AbstractEntity turret) {
		IsTurretComponent itc = (IsTurretComponent)turret.getComponent(IsTurretComponent.class);
		if (itc.nextTargetCheck < System.currentTimeMillis()) {
			itc.nextTargetCheck = System.currentTimeMillis() + 2000;
			itc.current_target = game.players[0];
		}
		
		PositionComponent turretPos = (PositionComponent)turret.getComponent(PositionComponent.class);
		PositionComponent targetPos = (PositionComponent)itc.current_target.getComponent(PositionComponent.class);
		tmp.set(turretPos.position.x, turretPos.position.z);
		tmp.x -= targetPos.position.x;
		tmp.y -= targetPos.position.z;
		turretPos.angle_degs = -tmp.angle();
		//Settings.p("Angle: " + turretPos.angle_degs);
	}

	
}
