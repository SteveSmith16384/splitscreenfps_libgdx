package com.scs.splitscreenfps.pregame;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.scs.splitscreenfps.BillBoardFPS_Main;
import com.scs.splitscreenfps.ControllerManager;
import com.scs.splitscreenfps.IModule;
import com.scs.splitscreenfps.Settings;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.input.ControllerInputMethod;
import com.scs.splitscreenfps.game.input.IInputMethod;
import com.scs.splitscreenfps.game.input.MouseAndKeyboardInputMethod;

public class PreGameScreen implements IModule {

	private SpriteBatch batch2d;
	private final BitmapFont font_white, font_black;
	private ControllerManager controllerManager = new ControllerManager();
	private List<String> log = new LinkedList<String>();
	private FrameBuffer frameBuffer;
	private BillBoardFPS_Main main;
	private List<IInputMethod> inputs = new ArrayList<IInputMethod>();
	
	public PreGameScreen(BillBoardFPS_Main _main) {
		super();
		
		main = _main;
		
		batch2d = new SpriteBatch();
		font_white = new BitmapFont(Gdx.files.internal("font/spectrum1white.fnt"));
		font_black = new BitmapFont(Gdx.files.internal("font/spectrum1black.fnt"));
		
		frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
		frameBuffer.getColorBufferTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
		
		this.appendToLog("Welcome to " + Settings.TITLE);
		this.appendToLog("Looking for controllers...");
		
		this.inputs.add(new MouseAndKeyboardInputMethod());
	}

	
	@Override
	public void render() {
		controllerManager.checkForControllers();
		
		if (controllerManager.controllersAdded.size() > 0) {
			this.appendToLog("Found " + controllerManager.controllersAdded.size() + " controllers");
			for (Controller c : controllerManager.controllersAdded) {
				// todo -check if players actually want to join 
				this.inputs.add(new ControllerInputMethod(c));
			}
		}
		
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		frameBuffer.begin();

		Gdx.gl.glClearColor(.9f, .9f, .9f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		batch2d.begin();
		int y = Gdx.graphics.getHeight() - 20;
		for (String s :this.log) {
			font_white.draw(batch2d, s, 10, y);
			y -= 30;
		}
		batch2d.end();

		frameBuffer.end();

		//Draw buffer and FPS
		batch2d.begin();
		batch2d.draw(frameBuffer.getColorBufferTexture(), 0, Gdx.graphics.getHeight(), Gdx.graphics.getWidth(), -Gdx.graphics.getHeight());

		if (Settings.SHOW_FPS) {
			font_white.draw(batch2d, "FPS: "+Gdx.graphics.getFramesPerSecond(), 10, 20);
		}

		batch2d.end();
		
		if (Gdx.input.isKeyPressed(Keys.SPACE)) {
			main.next_module = new Game(main, inputs);
		}
	}

	
	@Override
	public void dispose() {
		this.batch2d.dispose();
		this.frameBuffer.dispose();
		this.font_black.dispose();
		this.font_white.dispose();
	}
	

	@Override
	public void setFullScreen(boolean fullscreen) {

	}

	
	@Override
	public void resize(int w, int h) {

	}
	
	
	private void appendToLog(String s) {
		this.log.add(s);
		while (log.size() > 20) {
			log.remove(0);
		}
	}

}
