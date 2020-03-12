package com.scs.splitscreenfps.game.systems.towerdefence;

import java.util.ArrayList;
import java.util.List;

import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.BasicECS;
import com.scs.basicecs.ISystem;
import com.scs.splitscreenfps.game.entities.towerdefence.TowerDefenceEntityFactory;
import com.scs.splitscreenfps.game.levels.TowerDefenceLevel;

import ssmith.lang.NumberFunctions;
import ssmith.libgdx.GridPoint2Static;

public class SpawnEnemiesSystem implements ISystem {

	private BasicECS ecs;
	public List<GridPoint2Static> enemy_spawn_points = new ArrayList<GridPoint2Static>();
	private long nextSpawnTime;
	private TowerDefenceLevel towerDefenceLevel;
	
	public SpawnEnemiesSystem(BasicECS _ecs, TowerDefenceLevel _level) {
		ecs = _ecs;
		towerDefenceLevel = _level;
	}

	
	@Override
	public void process() {
		if (nextSpawnTime < System.currentTimeMillis()) {
			nextSpawnTime = System.currentTimeMillis() + 3000;//TowerDefenceLevel.prop.getProperty("spawn_interval", 3000);
			
			int max_spawn = Math.min(this.towerDefenceLevel.levelNum, this.enemy_spawn_points.size());
			GridPoint2Static spawn_point = this.enemy_spawn_points.get(NumberFunctions.rnd(0, max_spawn)-1);
			AbstractEntity alien = TowerDefenceEntityFactory.createAlien(ecs, spawn_point.x, spawn_point.y);
			ecs.addEntity(alien);
		}
		
	}

}
