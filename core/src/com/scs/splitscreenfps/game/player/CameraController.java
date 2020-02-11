package com.scs.splitscreenfps.game.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.input.IInputMethod;
import com.scs.splitscreenfps.game.input.NoInputMethod;

public class CameraController {

	private Game game;
	private Camera camera;
	private Vector3 tmp;

	private float rotSpeedX = 220f;
	private float rotSpeedY = 140f;

	private float cursorSpeed = .15f;

	float[] sensitivity = new float[]{
			0.5f, 1f, 1.75f
	};
	float[] sensitivity2 = new float[]{
			0.25f, 1f, 1.75f
	};

	private IInputMethod input;
	//public float camAngleChange = 0;

	public CameraController(Game _game, Camera cam, IInputMethod _input) {
		game = _game;
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

		//Vector2 v2 = new Vector2(camera.direction.x, camera.direction.z);
		//float cam_ang = v2.angle();

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
		} else if (input instanceof NoInputMethod) {
			// Look at player 1
			//PositionComponent posData = (PositionComponent)game.players[0].getComponent(PositionComponent.class);
			//todo camera.lookAt(posData.position);
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
		camera.update();
	
		/*v2 = new Vector2(camera.direction.x, camera.direction.z);
		float cam_ang2 = v2.angle();

		this.camAngleChange = cam_ang - cam_ang2;*/

	}

}
