package com.scs.splitscreenfps.game.player.weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.player.CameraController;

public class PlayersSword implements IPlayersWeapon {

	private static final float defaultWeaponRotation = 30f;
	private static final float chargeWeaponRotation = -20f;
	private static final float attackWeaponRotation = 120f;

	private Sprite weaponSprite;
	private float weaponRotation;
	private Vector2 weaponPosition;
	private float attackAnimationTimer;
	private float weaponScaleY = 1f;
	private boolean didPlayAudio = false;
	private boolean didAttack = true;
	private boolean removeAfterAttack = false;

	public PlayersSword(boolean _removeAfterAttack) {
		removeAfterAttack = _removeAfterAttack;
		
		Texture weaponTex = new Texture(Gdx.files.internal("sword.png"));
		weaponSprite = new Sprite(weaponTex);
		weaponSprite.setOrigin(32, 0);
		weaponSprite.setScale(7.5f, 5f);
		weaponPosition = new Vector2(0,0);

		weaponRotation = defaultWeaponRotation;
		weaponSprite.setRotation(defaultWeaponRotation);

	}


	public void update(CameraController cameraController) {
		float weaponBob = (float)Math.cos(cameraController.bobbing * 15f + .15f) * 20f;
		weaponSprite.setPosition(Gdx.graphics.getWidth()-300 + weaponPosition.x, -20 + weaponBob+weaponPosition.y);
		weaponSprite.setScale(6f, Math.min(5f*weaponScaleY,8f));
		weaponSprite.setRotation(weaponRotation + (float)Math.cos(cameraController.bobbing*7.5f)*5f - 2.5f);

		if (attackAnimationTimer > 0f) {
			attackAnimationTimer -= Gdx.graphics.getDeltaTime()*4;
			if(attackAnimationTimer > 0.3f) {
				weaponRotation = MathUtils.lerp(weaponRotation, chargeWeaponRotation, Gdx.graphics.getDeltaTime() * 8f);
				weaponPosition.set(
						MathUtils.lerp(weaponPosition.x, 30, Gdx.graphics.getDeltaTime()*20f),
						MathUtils.lerp(weaponPosition.y, -80, Gdx.graphics.getDeltaTime()*20f)
						);

				if (!didPlayAudio && attackAnimationTimer<.8f) {
					didPlayAudio = true;
					Game.audio.play("weapon");
				}

			} else {
				weaponRotation = MathUtils.lerp(weaponRotation, attackWeaponRotation, Gdx.graphics.getDeltaTime() * 15f);
				weaponPosition.set(
						MathUtils.lerp(weaponPosition.x, -150, Gdx.graphics.getDeltaTime()*20f),
						MathUtils.lerp(weaponPosition.y, 150, Gdx.graphics.getDeltaTime()*20f)
						);
				weaponScaleY = MathUtils.lerp(weaponScaleY, 2f, Gdx.graphics.getDeltaTime()*3);

				//In case of low framerate skip, unlikely
				if (!didPlayAudio) {
					didPlayAudio = true;
					Game.audio.play("weapon");
				}
			}
		} else {
			weaponRotation = MathUtils.lerp(weaponRotation, defaultWeaponRotation, Gdx.graphics.getDeltaTime()*5f);
			weaponPosition.set(
					MathUtils.lerp(weaponPosition.x, 0, Gdx.graphics.getDeltaTime()*10f),
					MathUtils.lerp(weaponPosition.y, 0, Gdx.graphics.getDeltaTime()*10f)
					);
			weaponScaleY = MathUtils.lerp(weaponScaleY, 1f, Gdx.graphics.getDeltaTime()*10);
		}
	}


	public void render(SpriteBatch batch) {
		weaponSprite.draw(batch);
	}


	public void attackPressed(Vector3 position, Vector3 direction) {
		if (attackAnimationTimer <= 0f) {
			attackAnimationTimer = 1.0f;
			didAttack = false;
			didPlayAudio = false;
		}

		boolean res = (attackAnimationTimer < 0.3f && !didAttack);
		if (res) {
			didAttack = true;

			/*if (Settings.PLAYER_SHOOTING) {
				Entity b = new ChaosBolt(this, position, direction);
				Game.entityManager.add(b);
			} else {*/
				checkAttackHit(position, direction);
			//}
		}
	}


	private void checkAttackHit(Vector3 position, Vector3 direction) {
/*		IDamagable closest = null;
		float dist = 0f;

		Vector3 tmp = new Vector3();
/*
		for (Entity ent : Game.entityManager.getEntities()) {
			if(ent instanceof IAttackable == false) {
				continue;
			}
			if(ent instanceof IDamagable == false) {
				continue;
			}

			tmp.set(position).mulAdd(direction, Game.UNIT*.75f);

			if(Game.collision.hitCircle(ent.getPosition(), tmp, Game.UNIT*.75f)){
				float d = position.dst2(ent.getPosition());
				if(closest == null || d<dist) {
					dist = d;
					closest = (IDamagable)ent;
				}
			}

		}
*/
/*		if (closest != null) {
			closest.damaged(1, direction);
			if (removeAfterAttack) {
				Game.player.setWeapon(null);
			}
		}*/
	}


}
