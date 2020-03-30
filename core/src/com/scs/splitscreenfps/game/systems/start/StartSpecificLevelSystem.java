package com.scs.splitscreenfps.game.systems.start;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractEvent;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.BillBoardFPS_Main;
import com.scs.splitscreenfps.Settings;
import com.scs.splitscreenfps.game.EventCollision;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.components.CanCarryComponent;
import com.scs.splitscreenfps.game.components.monstermaze.MonsterMazeKeyComponent;
import com.scs.splitscreenfps.game.components.start.CanStartNewLevelComponent;
import com.scs.splitscreenfps.game.components.start.StartLevelExitComponent;
import com.scs.splitscreenfps.game.entities.TextEntity;
import com.scs.splitscreenfps.game.systems.monstermaze.MonsterMazeExitSystem;
import com.scs.splitscreenfps.game.systems.monstermaze.TRexAISystem;
import com.scs.splitscreenfps.game.systems.monstermaze.TRexHarmsPlayerSystem;

public class StartSpecificLevelSystem extends AbstractSystem {

	private Game game;

	public StartSpecificLevelSystem(BasicECS ecs, Game _game) {
		super(ecs, StartLevelExitComponent.class);

		game = _game;
	}


	@Override
	public void processEntity(AbstractEntity exit) {
		List<AbstractEvent> it = ecs.getEventsForEntity(EventCollision.class, exit);
		for (AbstractEvent e : it) {
			EventCollision evt = (EventCollision)e;
			AbstractEntity player = evt.movingEntity;
			CanStartNewLevelComponent cumme = (CanStartNewLevelComponent)player.getComponent(CanStartNewLevelComponent.class);
			if (cumme != null) {
				StartLevelExitComponent slec = (StartLevelExitComponent)exit.getComponent(StartLevelExitComponent.class);
				Settings.CURRENT_MODE = slec.level;
				game.playerHasWon(player);
				return;

			}
		}
	}

}