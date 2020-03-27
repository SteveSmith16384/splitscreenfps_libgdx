package com.scs.splitscreenfps.game.systems.towerdefence;

import java.util.Iterator;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.Settings;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.components.PositionComponent;
import com.scs.splitscreenfps.game.components.towerdefence.IsTurretComponent;
import com.scs.splitscreenfps.game.components.towerdefence.TowerEnemyComponent;
import com.scs.splitscreenfps.game.entities.towerdefence.TowerDefenceEntityFactory;
import com.scs.splitscreenfps.game.levels.TowerDefenceLevel;

import ssmith.lang.NumberFunctions;

public class TurretSystem extends AbstractSystem {

	private static final float RANGE_SQ = (float)Math.pow(Float.parseFloat(TowerDefenceLevel.prop.getProperty("turret_range", "5")), 2);
	private static final float BULLET_SPEED = (float)Float.parseFloat(TowerDefenceLevel.prop.getProperty("bullet_speed", "20"));

	private Game game;
	private Vector2 tmp2 = new Vector2();
	private Vector3 tmpBulletOffset = new Vector3();
	private Vector3 startPos = new Vector3();

	public TurretSystem(BasicECS ecs, Game _game) {
		super(ecs, IsTurretComponent.class);

		game = _game;
	}


	@Override
	public void processEntity(AbstractEntity turret) {
		PositionComponent turretPos = (PositionComponent)turret.getComponent(PositionComponent.class);
		IsTurretComponent itc = (IsTurretComponent)turret.getComponent(IsTurretComponent.class);
		if (itc.current_target != null) {
			if (itc.current_target.isMarkedForRemoval()) {
				itc.current_target = null;
			}
		}

		if (itc.nextTargetCheck < System.currentTimeMillis()) {
			itc.nextTargetCheck = System.currentTimeMillis() + 2000;
			itc.current_target = this.getTarget(turretPos);//game.players[0];
			//itc.current_target = game.players[0];
		}

		if (itc.current_target != null) {
			PositionComponent targetPos = (PositionComponent)itc.current_target.getComponent(PositionComponent.class);
			tmp2.set(turretPos.position.x, turretPos.position.z);
			tmp2.x -= targetPos.position.x;
			tmp2.y -= targetPos.position.z;
			float target_angle = -tmp2.angle();
			turretPos.angle_degs += Math.signum(target_angle-turretPos.angle_degs) * .3f;// .15f; // Turn speed
			//Settings.p("Angle: " + turretPos.angle_degs);
			
			if (itc.nextShotTime < System.currentTimeMillis()) {
				Settings.p("Shot!");
				itc.nextShotTime = System.currentTimeMillis() + NumberFunctions.rnd(900,  1100);
				startPos.set(turretPos.position);
				
				tmpBulletOffset.set((float)Math.sin(Math.toRadians(turretPos.angle_degs-90)), 0, (float)Math.cos(Math.toRadians(turretPos.angle_degs-90)));
				tmpBulletOffset.nor();
				tmpBulletOffset.scl(4);//todo BULLET_SPEED);
				AbstractEntity bullet = TowerDefenceEntityFactory.createBullet(ecs, turret, startPos, tmpBulletOffset);
				game.ecs.addEntity(bullet);
			}
		}
	}


	private AbstractEntity getTarget(PositionComponent turretPos) {
		AbstractEntity target = null;
		float dist = 9999;

		Iterator<AbstractEntity> it = ecs.getEntityIterator();
		while (it.hasNext()) {
			AbstractEntity ent = it.next();
			if (ent.getComponent(TowerEnemyComponent.class) != null) {
				PositionComponent enemyPos = (PositionComponent)ent.getComponent(PositionComponent.class);
				float dst = enemyPos.position.dst2(turretPos.position);
				if (dst < RANGE_SQ) {
					target = ent;
					dist = dst;
				}
			}
		}
		return target;
	}

}
