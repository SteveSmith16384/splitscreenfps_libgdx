package com.scs.splitscreenfps.game.systems.monstermaze;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractEvent;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.game.EventCollision;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.components.CanCarryComponent;
import com.scs.splitscreenfps.game.components.monstermaze.CanUseMonsterMazeExitComponent;
import com.scs.splitscreenfps.game.components.monstermaze.MonsterMazeExitComponent;
import com.scs.splitscreenfps.game.components.monstermaze.MonsterMazeKeyComponent;
import com.scs.splitscreenfps.game.entities.TextEntity;

public class MonsterMazeExitSystem extends AbstractSystem {

	private Game game;
	
	public MonsterMazeExitSystem(BasicECS ecs, Game _game) {
		super(ecs, MonsterMazeExitComponent.class);

		game = _game;
	}


	@Override
	public void processEntity(AbstractEntity exit) {
		List<AbstractEvent> it = ecs.getEventsForEntity(EventCollision.class, exit);
		for (AbstractEvent e : it) {
			EventCollision evt = (EventCollision)e;
			AbstractEntity player = evt.movingEntity;
			CanUseMonsterMazeExitComponent cumme = (CanUseMonsterMazeExitComponent)player.getComponent(CanUseMonsterMazeExitComponent.class);
			if (cumme != null) {
				CanCarryComponent cc = (CanCarryComponent)player.getComponent(CanCarryComponent.class);
				if (cc != null) {
					if (cc.carrying != null) {
						MonsterMazeKeyComponent key = (MonsterMazeKeyComponent)cc.carrying.getComponent(MonsterMazeKeyComponent.class);
						if (key != null) {
							game.ecs.removeSystem(MonsterMazeExitSystem.class);
							game.ecs.removeSystem(MonsterMazeExitSystem.class);
							game.ecs.removeSystem(TRexHarmsPlayerSystem.class);
							game.ecs.removeSystem(TRexAISystem.class);
							game.playerHasWon(player);
							return;
						}
					}
				}
				TextEntity te = new TextEntity(ecs, "YOU NEED THE KEY!", Gdx.graphics.getBackBufferHeight()/2, 4, new Color(1, 1, 0, 1), cumme.playerIdx, 2);
				ecs.addEntity(te);
				
			}
		}
	}

}