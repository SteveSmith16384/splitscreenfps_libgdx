package com.scs.splitscreenfps.game.systems;

import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.components.CompletesLevelData;
import com.scs.splitscreenfps.game.components.PositionData;
import com.scs.splitscreenfps.game.player.Player;

public class GotToExitSystem extends AbstractSystem {

	//private Player player;
	
	public GotToExitSystem(BasicECS ecs) {//, Player _player) {
		super(ecs);
		
		//player = _player;
	}


	@Override
	public Class<?> getComponentClass() {
		return CompletesLevelData.class;
	}


	@Override
	public void processEntity(AbstractEntity entity) {
		PositionData ourPos = (PositionData)entity.getComponent(PositionData.class);
		/* todo - loop through players
		 PositionData playerPos = (PositionData)player.getComponent(PositionData.class);
		float dist = ourPos.getMapPos().dst(playerPos.getMapPos());
		if (dist < 1) {//Game.UNIT / 2) {
			Game.audio.play("beepfx_samples/19_jet_burst.wav");
			Game.levelComplete = true;
		}*/
	}

}
