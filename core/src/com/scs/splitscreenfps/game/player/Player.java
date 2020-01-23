package com.scs.splitscreenfps.game.player;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.scs.basicecs.AbstractEntity;
import com.scs.splitscreenfps.Settings;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.ViewportData;
import com.scs.splitscreenfps.game.components.CanCollect;
import com.scs.splitscreenfps.game.components.HarmsPlayer;
import com.scs.splitscreenfps.game.components.MovementData;
import com.scs.splitscreenfps.game.components.PositionData;
import com.scs.splitscreenfps.game.player.weapons.IPlayersWeapon;

public class Player extends AbstractEntity {

	private static final float moveSpeed = 2f * Game.UNIT;
	public static final float playerHeight = Game.UNIT * 0.4f;
	//private static final float colliderSize = .2f * Game.UNIT;
	//private static final float hurtDistanceSquared = Game.UNIT * .5f * Game.UNIT * .5f;

	private Camera camera;
	private ViewportData viewportData;
	//public IInventory inventory;
	public CameraController cameraController;
	private Vector3 tmpVector;
	private boolean mouseReleased = false;
	private float footstepTimer;
	private int lives;
	private float hurtTimer = 0f;
	private Texture hurtTexture; // Screen goes red when hit
	private Texture heart;

	private MovementData movementData;
	private PositionData positionData;

	private IPlayersWeapon weapon;

	public Player(ViewportData _viewportData, IInventory inv, int _lives) {
		super(Player.class.getSimpleName());

		this.movementData = new MovementData(0.5f);
		this.addComponent(movementData);
		this.positionData = new PositionData();
		this.addComponent(positionData);
		this.addComponent(new CanCollect());

		//inventory = inv;
		camera = _viewportData.camera;
		viewportData = _viewportData;
		this.lives = _lives;

		cameraController = new CameraController(camera);

		tmpVector = new Vector3();

		hurtTexture = new Texture(Gdx.files.internal("red.png"));
		heart = new Texture(Gdx.files.internal("heart.png"));
	}


	public Vector3 getPosition() {
		return this.positionData.position;//position;
	}


	public void update() {
		move();
		if (weapon != null) {
			checkForAttack();
		}

		cameraController.update();

		if (weapon != null) {
			weapon.update(cameraController);
		}

		if (hurtTimer > 0) {
			hurtTimer -= Gdx.graphics.getDeltaTime();
		} else {
			// Check if any enemies are harming us
			Iterator<AbstractEntity> it = Game.ecs.getIterator();
			while (it.hasNext()) {
				AbstractEntity entity = it.next();
				HarmsPlayer hp = (HarmsPlayer)entity.getComponent(HarmsPlayer.class);
				if (hp != null) {
					PositionData posData = (PositionData)entity.getComponent(PositionData.class);
					Vector3 enemyPos = posData.position;
					float dist = enemyPos.dst(camera.position);
					if (dist < Game.UNIT * .5f) {
						this.damaged(hp.damageCaused, new Vector3()); // todo - direction
						entity.remove(); // Prevent further collisions
					}
				}
			}
		}
	}


	private void checkForAttack() {
		/* if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && attackAnimation <= 0){
			attackAnimation = 1.0f;
			didAttack = false;
			didPlayAudio = false;
		}
		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
			if (mouseReleased && Gdx.input.isCursorCatched() && attackAnimation <= 0) {
				attackAnimation = 1.0f;
				didAttack = false;
				didPlayAudio = false;
			}
			mouseReleased = false;
		} else {
			mouseReleased = true;
		}
		 */
		if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
			weapon.attackPressed(this.getPosition(), this.camera.direction);
		} else if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			mouseReleased = true;
			if (mouseReleased && Gdx.input.isCursorCatched()) {
				mouseReleased = false;
				weapon.attackPressed(this.getPosition(), this.camera.direction);
			}
		}

		//weapon.checkForAttack();

		/*if (weapon.IsAttackMade(this)) {// attackAnimation < 0.3f && !didAttack) {
			//didAttack = true;
			checkAttackHit();
		}*/
	}

	private void move() {
		float dt = Gdx.graphics.getDeltaTime();

		this.movementData.offset.setZero();

		//Movement
		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			tmpVector.set(camera.direction);
			tmpVector.y = 0;
			this.movementData.offset.add(tmpVector.nor().scl(dt * moveSpeed));
		} else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			tmpVector.set(camera.direction);
			tmpVector.y = 0;
			this.movementData.offset.add(tmpVector.nor().scl(dt * -moveSpeed));
		}
		if (Gdx.input.isKeyPressed(Input.Keys.A)){
			tmpVector.set(camera.direction).crs(camera.up);
			tmpVector.y = 0;
			this.movementData.offset.add(tmpVector.nor().scl(dt * -moveSpeed));
		} else if (Gdx.input.isKeyPressed(Input.Keys.D)){
			tmpVector.set(camera.direction).crs(camera.up);
			tmpVector.y = 0;
			this.movementData.offset.add(tmpVector.nor().scl(dt * moveSpeed));
		}

		camera.position.set(getPosition().x, getPosition().y + playerHeight, getPosition().z);

		if (this.movementData.offset.len2() > 0) {
			footstepTimer += Gdx.graphics.getDeltaTime();
			if (footstepTimer > 0.45f) {
				footstepTimer -= 0.45f;
				Game.audio.play("step");
			}
		}
	}


	public void render(SpriteBatch batch) {
		if (weapon != null) {
			weapon.render(batch);
		}
	}


	public void renderUI(SpriteBatch batch, BitmapFont font) {
		/*if (interactTarget != null) {
			String str = interactTarget.getInteractText(this);
			int w2 = str.length() * 8;
			font.setColor(1,1,1,1);
			font.draw(batch, str, Gdx.graphics.getWidth() / 2 - w2, Gdx.graphics.getHeight() / 2 + 50/8);
		}*/

		/*for (int i = 0; i < inventory.keys; i++) {
			batch.draw(Game.art.items[0][0], 10 + i*50, Gdx.graphics.getHeight()-40, 48, 48);
		}*/

		//int sx = Gdx.graphics.getWidth()/2 - lives*18;
		int sx = (int)this.viewportData.viewPos.x + (this.viewportData.viewPos.width/2) - (lives*18);
		for (int i = 0; i < lives; i++) {
			//batch.draw(heart, sx + i*36, Gdx.graphics.getHeight()-40, 32, 32);
			batch.draw(heart, sx + i*36, this.viewportData.viewPos.y-40, 32, 32);
		}

		if (hurtTimer > 0 && (int)(hurtTimer*5)%2 == 0) {
			batch.setColor(1,1,1,.25f);
			//batch.draw(hurtTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			batch.draw(hurtTexture, this.viewportData.viewPos.x, this.viewportData.viewPos.y, this.viewportData.viewPos.width, this.viewportData.viewPos.height);
			batch.setColor(1,1,1,1);
		}

	}


	public int getHealth() {
		return lives;
	}


	public void damaged(int amt, Vector3 dir) {
		if (Settings.PLAYER_INVINCIBLE) {
			Settings.p("Player Invincible!");
			return;
		}
		//health -= amt;
		lives--;
		hurtTimer = 1.5f;
		if (lives >= 0) {
			Game.audio.play("player_hurt");
		}
		Game.restartLevel = true;
	}


	public void setWeapon(IPlayersWeapon w) {
		this.weapon = w;
	}


	public int getLives() {
		return this.lives;
	}

}

