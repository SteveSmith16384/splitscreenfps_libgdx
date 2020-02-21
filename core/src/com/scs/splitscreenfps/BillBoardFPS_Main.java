package com.scs.splitscreenfps;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.Input.Keys;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.input.IInputMethod;
import com.scs.splitscreenfps.game.input.MouseAndKeyboardInputMethod;
import com.scs.splitscreenfps.game.input.NoInputMethod;
import com.scs.splitscreenfps.game.systems.AudioSystem;
import com.scs.splitscreenfps.pregame.PreGameScreen;

import ssmith.libgdx.GraphicsHelper;

public class BillBoardFPS_Main extends ApplicationAdapter {

	public static final GraphicsHelper art = new GraphicsHelper();
	public static final AudioSystem audio = new AudioSystem();

	private IModule current_module;
	public  IModule next_module;
	private boolean fullscreen = false;

	@Override
	public void create() {
		Settings.init();
		
		if (Settings.DEFAULT_4_PLAYERS) {
			List<IInputMethod> inputs = new ArrayList<IInputMethod>();
			inputs.add(new MouseAndKeyboardInputMethod());
			inputs.add(new NoInputMethod());
			inputs.add(new NoInputMethod());
			inputs.add(new NoInputMethod());
			current_module = new Game(this, inputs);
		} else {
			current_module = new PreGameScreen(this);//Game();
		}
	}


	@Override
	public void render() {
		if (next_module != null) {
			this.current_module.dispose();
			this.current_module = this.next_module;
			this.next_module = null;
		}


		if (current_module != null) {
			current_module.render();
		}

		audio.update();

		if (Gdx.input.isKeyJustPressed(Keys.F1)) {
			if (fullscreen) {
				Gdx.graphics.setWindowedMode(Settings.WINDOW_WIDTH_PIXELS, Settings.WINDOW_HEIGHT_PIXELS);
				fullscreen = false;
			} else {
				DisplayMode m = null;
				for(DisplayMode mode: Gdx.graphics.getDisplayModes()) {
					if (m == null) {
						m = mode;
					} else {
						if (m.width < mode.width) {
							m = mode;
						}
					}
				}

				Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
				fullscreen = true;
			}
			this.current_module.setFullScreen(fullscreen);
		} else if (Gdx.input.isKeyJustPressed(Keys.F2)) {
			if (fullscreen) {
				Gdx.graphics.setWindowedMode(Settings.WINDOW_WIDTH_PIXELS, Settings.WINDOW_HEIGHT_PIXELS);
				fullscreen = false;
			} else {
				int w = Gdx.graphics.getDisplayMode().width;
				int h = Gdx.graphics.getDisplayMode().height;
				Gdx.graphics.setWindowedMode(w, h);
				fullscreen = true;
			}
		} else if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			if (this.current_module instanceof PreGameScreen) {
				System.exit(0);
			} else {
				this.next_module = new PreGameScreen(this);
			}
		}


	}


	@Override
	public void resize(int width, int height) {
		if(this.current_module != null) {
			this.current_module.resize(width, height);
		}
	}


	@Override
	public void dispose() {
		if (current_module != null) {
			current_module.dispose();
		}
		audio.dipose();
	}

}

