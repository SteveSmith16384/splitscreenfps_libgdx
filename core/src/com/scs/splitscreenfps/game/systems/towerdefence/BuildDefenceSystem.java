package com.scs.splitscreenfps.game.systems.towerdefence;

import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.components.towerdefence.CanBuildComponent;
import com.scs.splitscreenfps.game.components.towerdefence.CanBuildOnComponent;
import com.scs.splitscreenfps.game.components.towerdefence.ShowFloorSelectorComponent;
import com.scs.splitscreenfps.game.entities.AbstractPlayersAvatar;

public class BuildDefenceSystem extends AbstractSystem {

	private Game game;
	
	public BuildDefenceSystem(BasicECS ecs, Game _game) {
		super(ecs, CanBuildComponent.class);
		
		game = _game;
	}


	@Override
	public void processEntity(AbstractEntity carrier) {
		CanBuildComponent cc = (CanBuildComponent)carrier.getComponent(CanBuildComponent.class);
		if (cc.lastPickupDropTime + 1000 > System.currentTimeMillis()) {
			return;
		}
		
		AbstractPlayersAvatar player = (AbstractPlayersAvatar)carrier;
		ShowFloorSelectorComponent sfsc = (ShowFloorSelectorComponent)carrier.getComponent(ShowFloorSelectorComponent.class);
		
		if (player.inputMethod.isCirclePressed()) {
			// todo - check coins
			
			// Check map is empty
			CanBuildOnComponent cbboc = (CanBuildOnComponent)game.mapData.map[sfsc.pos.x][sfsc.pos.y].entity.getComponent(CanBuildOnComponent.class);
			if (cbboc != null) {
				game.mapData.map[sfsc.pos.x][sfsc.pos.y].entity.hideComponent(CanBuildOnComponent.class);
				
				
			}
			
		}
		
	}

}
