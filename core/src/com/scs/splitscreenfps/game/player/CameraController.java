package com.scs.splitscreenfps.game.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.input.IInputMethod;

public class CameraController {

	private Camera camera;
	private Vector3 tmp;

	private float rotSpeedX = 220f;
	private float rotSpeedY = 140f;

	private float cursorSpeed = .15f;

	public float bobbing = 0f;
	private float bobHeight = .03f;
	public float bobSpeed = 20f;

	float[] sensitivity = new float[]{
			0.5f, 1f, 1.75f
	};
	float[] sensitivity2 = new float[]{
			0.25f, 1f, 1.75f
	};

	//private static final float maxLookY = 80f;
	//private float lookY = 0f;

	private IInputMethod input;

	public CameraController(Camera cam, IInputMethod _input) {
		camera = cam;
		input = _input;

		tmp = new Vector3();

		int sens = 1;
		rotSpeedX *= sensitivity[sens];
		rotSpeedY *= sensitivity[sens];
		cursorSpeed *= sensitivity2[sens];
	}

	public void update() {
		float dt = Gdx.graphics.getDeltaTime();

		if (this.input.isMouse()) {
			if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
				Gdx.input.setCursorCatched(true);
			}
			if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
				Gdx.input.setCursorCatched(false);
			}

			if (Gdx.input.isCursorCatched()) {
				float rx = Gdx.input.getDeltaX();
				float ry = Gdx.input.getDeltaY();

				tmp.set(camera.direction).crs(camera.up).nor();
				if ((ry>0 && camera.direction.y>-0.95) || (ry<0 && camera.direction.y < 0.95)) {
					camera.rotate(tmp, -cursorSpeed * ry);
				}
				camera.rotate(Vector3.Y, -cursorSpeed  * rx);
			}
		} else {
			//Rotation
			if (input.getLookUp() > 0) { //if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
				if(camera.direction.y < 0.95) {
					tmp.set(camera.direction).crs(camera.up).nor();
					camera.rotate(tmp, rotSpeedY * input.getLookUp() * dt);
				}

			} else if (input.getLookDown() > 0) { // Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
				if(camera.direction.y>-0.95) {
					tmp.set(camera.direction).crs(camera.up).nor();
					camera.rotate(tmp, -rotSpeedY * input.getLookDown() * dt);
				}
			}
			if (input.getLookLeft() > 0) {//Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
				camera.rotate(Vector3.Y, rotSpeedX * input.getLookLeft() * dt);
			} else if (input.getLookRight() > 0) {
				camera.rotate(Vector3.Y, -rotSpeedX * input.getLookRight() * dt);
			}
		}

		// todo - move this:-
		if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.D)){
			bobbing += dt;
		}

		tmp.set(0f, (float) (Math.sin(bobbing * bobSpeed) * bobHeight * Game.UNIT), 0f);
		camera.position.add(tmp);

	}

}
