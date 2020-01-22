package com.scs.splitscreenfps.game;

import com.scs.splitscreenfps.Settings;
import com.scs.splitscreenfps.game.levels.AbstractLevel;
import com.scs.splitscreenfps.game.levels.MonsterMazeLevel;
import com.scs.splitscreenfps.game.levels.StartLevel;

public class Levels {
	
	private int currentLevelNum = 1;//Settings.RELEASE_MODE ? 0 : Settings.START_LEVEL-1; 
	public int numberTimesLoopAround = 0;

	public Levels() {
	}
	
	public AbstractLevel getLevel() {
		switch (currentLevelNum) {
		case 0:
			return new StartLevel();
		case 1:
			return new MonsterMazeLevel(numberTimesLoopAround);
		//case 6:
			//return new EricAndTheFloatersLevel(entityManager, decalManager);
			//return new MaziacsLevel(entityManager, decalManager);
			//gameLevel = new LaserSquadLevel(this.entityManager, this.decalManager);
			//return new AndroidsLevel(entityManager, decalManager, numberTimesLoopAround);

		default:
			//throw new RuntimeException("Unknown level: " + currentLevelNum);
			// Loop around
			currentLevelNum -= 1;
			numberTimesLoopAround++;
			return getLevel();
		}
	}
	
	
	public void nextLevel() {
		this.currentLevelNum++;
	}
	
	
	public void restart() {
		this.currentLevelNum = 0;
	}

}
