package com.scs.splitscreenfps.game.systems.towerdefence;

import java.util.Iterator;

import com.badlogic.gdx.math.Vector2;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.components.PositionComponent;
import com.scs.splitscreenfps.game.components.towerdefence.IsTurretComponent;
import com.scs.splitscreenfps.game.components.towerdefence.TowerEnemyComponent;

public class TurretSystem extends AbstractSystem {

	private static final float RANGE_SQ = 5*5;

	private Game game;
	private Vector2 tmp = new Vector2();

	public TurretSystem(BasicECS ecs, Game _game) {
		super(ecs, IsTurretComponent.class);

		game = _game;
	}


	@Override
	public void processEntity(AbstractEntity turret) {
		PositionComponent turretPos = (PositionComponent)turret.getComponent(PositionComponent.class);
		IsTurretComponent itc = (IsTurretComponent)turret.getComponent(IsTurretComponent.class);
		if (itc.nextTargetCheck < System.currentTimeMillis()) {
			itc.nextTargetCheck = System.currentTimeMillis() + 2000;
			itc.current_target = this.getTarget(turretPos);//game.players[0];
		}

		if (itc.current_target != null) {
			PositionComponent targetPos = (PositionComponent)itc.current_target.getComponent(PositionComponent.class);
			tmp.set(turretPos.position.x, turretPos.position.z);
			tmp.x -= targetPos.position.x;
			tmp.y -= targetPos.position.z;
			turretPos.angle_degs = -tmp.angle();
			//Settings.p("Angle: " + turretPos.angle_degs);
		}
	}


	private AbstractEntity getTarget(PositionComponent turretPos) {
		//AbstractEntity target = null;
		//float dist = 9999;

		Iterator<AbstractEntity> it = ecs.getEntityIterator();
		while (it.hasNext()) {
			AbstractEntity ent = it.next();
			if (ent.getComponent(TowerEnemyComponent.class) != null) {
				PositionComponent enemyPos = (PositionComponent)ent.getComponent(PositionComponent.class);
				float dst = enemyPos.position.dst2(turretPos.position);
				if (dst < RANGE_SQ) {
					return ent;
				}

			}
		}
		return null;
	}

}
