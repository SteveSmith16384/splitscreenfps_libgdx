package com.scs.splitscreenfps.game.systems;

import com.badlogic.gdx.math.GridPoint2;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.Settings;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.components.PositionData;
import com.scs.splitscreenfps.game.components.WarnIfAdjacentData;
import com.scs.splitscreenfps.game.player.Player;

public class CountMinesSystem extends AbstractSystem {
	
	private int num_mines;
	private Player player;

	public CountMinesSystem(BasicECS ecs, Player _player) {
		super(ecs);
		
		player = _player;
	}


	@Override
	public Class<?> getComponentClass() {
		return WarnIfAdjacentData.class;
	}


	@Override
	public void process() {
		num_mines = 0;
		super.process();
	}
	

	@Override
	public void processEntity(AbstractEntity entity) {
		PositionData playerPosData = (PositionData)player.getComponent(PositionData.class);
		GridPoint2 playerPos = new GridPoint2((int)(playerPosData.position.x/Game.UNIT), (int)(playerPosData.position.z/Game.UNIT));
		//Settings.p("playerPos=" + playerPos);
		PositionData minePosData = (PositionData)entity.getComponent(PositionData.class);
		GridPoint2 minePos = new GridPoint2((int)(minePosData.position.x/Game.UNIT), (int)(minePosData.position.z/Game.UNIT));
		float dist = minePos.dst(playerPos);
		//Settings.p("pos: " + playerPos.x + "," + playerPos.y + " = " + dist);
		if (dist == 0) {
			Settings.p("Player walked on mine!");
			player.damaged(1, null);
			Game.audio.play("beepfx_samples/24_boom_5.wav");
		} else if (dist <= 1.5f) {
			num_mines++;
		}
	}
	
	
	public int getNumMines() {
		return this.num_mines;
	}

}
