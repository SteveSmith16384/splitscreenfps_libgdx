package com.scs.splitscreenfps.pregame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.scs.splitscreenfps.ControllerManager;
import com.scs.splitscreenfps.IModule;
import com.scs.splitscreenfps.Settings;
import com.scs.splitscreenfps.game.ViewportData;

public class PreGameScreen implements IModule {

	private SpriteBatch batch2d;
	private final BitmapFont font_white, font_black;
	private ViewportData viewportData;
	private ControllerManager controllerManager = new ControllerManager();
	
	public PreGameScreen() {
		batch2d = new SpriteBatch();
		font_white = new BitmapFont(Gdx.files.internal("font/spectrum1white.fnt"));
		font_black = new BitmapFont(Gdx.files.internal("font/spectrum1black.fnt"));
		
		viewportData = new ViewportData(false, 0);
	}

	
	@Override
	public void render() {
		controllerManager.checkForControllers();
		
		Gdx.gl.glViewport(viewportData.viewPos.x, viewportData.viewPos.y, viewportData.viewPos.width, viewportData.viewPos.height);

		viewportData.frameBuffer.begin();

		Gdx.gl.glClearColor(.9f, .9f, .9f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		batch2d.begin();
		font_white.draw(batch2d, "hello!", 10, 10);
		batch2d.end();

		viewportData.frameBuffer.end();

		//Draw buffer and FPS
		batch2d.begin();
		batch2d.draw(viewportData.frameBuffer.getColorBufferTexture(), viewportData.viewPos.x, viewportData.viewPos.y+viewportData.viewPos.height, viewportData.viewPos.width, -viewportData.viewPos.height);

		if (Settings.SHOW_FPS) {
			font_white.draw(batch2d, "FPS: "+Gdx.graphics.getFramesPerSecond(), 10, 20);
		}

		batch2d.end();
	}

	
	@Override
	public void dispose() {
		this.batch2d.dispose();
		this.viewportData.dispose();
		this.font_black.dispose();
		this.font_white.dispose();
	}
	

	@Override
	public void setFullScreen(boolean fullscreen) {

	}

	
	@Override
	public void resize(int w, int h) {

	}

}
