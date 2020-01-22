package com.scs.splitscreenfps.game.entities.ericandthefloaters;

import com.badlogic.gdx.math.Vector3;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.player.weapons.AbstractIntervalWeapon;
import com.scs.splitscreenfps.game.player.weapons.IPlayersWeapon;

public class EricBombDropper extends AbstractIntervalWeapon implements IPlayersWeapon {

	public EricBombDropper() {
		super("ericandthefloaters/bomb.png", 3f);
	}

	
	@Override
	protected void weaponFired(Vector3 position, Vector3 direction) {
		EricDroppedBomb bomb = new EricDroppedBomb(position.x, position.z);
		Game.ecs.addEntity(bomb);
	}

}
