package com.scs.splitscreenfps.game.components.towerdefence;

import com.scs.basicecs.AbstractEntity;

public class IsTurretComponent {
	
	public long nextTargetCheck = 0;
	public long nextShotTime = 0;
	public AbstractEntity current_target;

	public IsTurretComponent() {
	}

}
