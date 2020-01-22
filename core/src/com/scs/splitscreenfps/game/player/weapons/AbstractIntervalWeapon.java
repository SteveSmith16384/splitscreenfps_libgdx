package com.scs.splitscreenfps.game.player.weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.scs.splitscreenfps.Settings;
import com.scs.splitscreenfps.game.player.CameraController;

public abstract class AbstractIntervalWeapon implements IPlayersWeapon {

	private Sprite weaponSprite;
	private float timeSinceLastFire = 0;
	private final float shot_interval;

	public AbstractIntervalWeapon(String tex, float interval) {
		Texture weaponTex = new Texture(Gdx.files.internal(tex));
		this.shot_interval = interval;
		
		weaponSprite = new Sprite(weaponTex);
		//weaponSprite.setOrigin(32, 20);
		weaponSprite.setOrigin(weaponSprite.getWidth()/2f, 0);
		//weaponSprite.setScale(7.5f, 5f);
		float scale = (float)Settings.WINDOW_WIDTH_PIXELS / (float)weaponTex.getWidth() / 3f;
		weaponSprite.setScale(scale);
		//weaponSprite.setPosition(Gdx.graphics.getWidth()-300, -20);
		weaponSprite.setPosition((Gdx.graphics.getWidth()-weaponSprite.getWidth())/2, 0);
	}
	
	
	public void update(CameraController cameraController) {
		this.timeSinceLastFire += Gdx.graphics.getDeltaTime();
	}


	public void render(SpriteBatch batch) {
		weaponSprite.draw(batch);
	}


	public void attackPressed(Vector3 position, Vector3 direction) {
		if (timeSinceLastFire >= shot_interval) {
			timeSinceLastFire = 0;
			this.weaponFired(position, direction);
		}
	}
	
	
	protected abstract void weaponFired(Vector3 position, Vector3 direction);

}
