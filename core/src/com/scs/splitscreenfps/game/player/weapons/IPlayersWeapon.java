package com.scs.splitscreenfps.game.player.weapons;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.scs.splitscreenfps.game.player.CameraController;

public interface IPlayersWeapon {

	void update(CameraController cameraController);
	
	void render(SpriteBatch batch);
	
	void attackPressed(Vector3 position, Vector3 direction);
	
}
