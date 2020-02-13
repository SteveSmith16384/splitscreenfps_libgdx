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
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.utils.Array;
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
	private BitmapFont font_white;
	private ControllerManager controllerManager = new ControllerManager(null);
	private List<String> log = new LinkedList<String>();
	private FrameBuffer frameBuffer;
	private BillBoardFPS_Main main;

	public PreGameScreen(BillBoardFPS_Main _main) {
		super();

		main = _main;

		batch2d = new SpriteBatch();
		font_white = new BitmapFont(Gdx.files.internal("font/spectrum1white.fnt"));

		//frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
		frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, 512, 512, true);
		frameBuffer.getColorBufferTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

		loadFonts();
		
		this.appendToLog("Welcome to " + Settings.TITLE);
		this.appendToLog("Looking for controllers...");

	}


	private void loadFonts() {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/SHOWG.TTF"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = Gdx.graphics.getBackBufferWidth()/23;
		font_white = generator.generateFont(parameter); // font size 12 pixels
		generator.dispose(); // don't forget to dispose to avoid memory leaks!
	}
	

	@Override
	public void render() {
		controllerManager.checkForControllers();

		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		frameBuffer.begin();

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		batch2d.begin();
		int y = Gdx.graphics.getHeight() - 20;
		Array<Controller> allControllers = this.controllerManager.getAllControllers();
		for (Controller c : allControllers) {
			//StringBuilder str = new StringBuilder("Controller " + c.getName());
			font_white.draw(batch2d, "Controller " + c.getName(), 10, y);

			if (this.controllerManager.isControllerInGame(c)) {
				font_white.draw(batch2d, "IN GAME!", 10, y-10);
			} else {
				font_white.draw(batch2d, "Not in game", 10, y-10);
			}
			//font_white.draw(batch2d, str.toString(), 10, y);
			y -= 20;
		}
		if (allControllers.size == 0) {
			font_white.draw(batch2d, "No Controllers Found", 10, y);
		}
		
		y = Gdx.graphics.getHeight() - 220;
		for (String s :this.log) {
			font_white.draw(batch2d, s, 10, y);
			y -= 20;
		}

		if (this.controllerManager.getInGameControllers().size() >= 1) {
			font_white.draw(batch2d, "PRESS SPACE TO START!", 10, y);
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
			List<IInputMethod> inputs = new ArrayList<IInputMethod>();
			inputs.add(new MouseAndKeyboardInputMethod());
				for (Controller c : controllerManager.getInGameControllers()) {
					inputs.add(new ControllerInputMethod(c));
				}

			main.next_module = new Game(main, inputs);
		}
	}


	@Override
	public void dispose() {
		this.batch2d.dispose();
		this.frameBuffer.dispose();
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
