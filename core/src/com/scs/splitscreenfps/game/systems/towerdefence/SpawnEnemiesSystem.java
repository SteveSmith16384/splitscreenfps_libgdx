package com.scs.splitscreenfps.game.systems.towerdefence;

import java.util.ArrayList;
import java.util.List;

import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.BasicECS;
import com.scs.basicecs.ISystem;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.entities.towerdefence.TowerDefenceEntityFactory;
import com.scs.splitscreenfps.game.levels.TowerDefenceLevel;

import ssmith.lang.NumberFunctions;
import ssmith.libgdx.GridPoint2Static;

public class SpawnEnemiesSystem implements ISystem {

	private BasicECS ecs;
	private Game game;
	public List<GridPoint2Static> enemy_spawn_points = new ArrayList<GridPoint2Static>();
	private long nextSpawnTime;
	private TowerDefenceLevel towerDefenceLevel;
	private AbstractEntity next_alien;
	
	public SpawnEnemiesSystem(BasicECS _ecs, Game _game, TowerDefenceLevel _level) {
		ecs = _ecs;
		game = _game;
		towerDefenceLevel = _level;
	}

	
	@Override
	public void process() {
		if (nextSpawnTime < System.currentTimeMillis()) {
			
			int max_spawn = Math.min(this.towerDefenceLevel.levelNum, this.enemy_spawn_points.size());
			GridPoint2Static spawn_point = this.enemy_spawn_points.get(NumberFunctions.rnd(0, max_spawn-1));
			if (next_alien == null) {
				next_alien = TowerDefenceEntityFactory.createAlien(ecs, spawn_point.x, spawn_point.y);
			}
			if (game.isAreaEmpty(next_alien)) {
				nextSpawnTime = System.currentTimeMillis() + 3000;//TowerDefenceLevel.prop.getProperty("spawn_interval", 3000);
				ecs.addEntity(next_alien);
				next_alien = null;
			}
		}
		
	}

}
