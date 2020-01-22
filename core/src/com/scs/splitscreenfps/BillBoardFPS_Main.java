package com.scs.splitscreenfps;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.Input.Keys;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.modules.IModule;

public class BillBoardFPS_Main extends ApplicationAdapter {

	private IModule current_module;
	private boolean toggleFullscreen = false, fullscreen = false;

	@Override
	public void create () {
		current_module = new Game();
	}


	@Override
	public void render() {
		if (current_module != null) {
			current_module.update();
			current_module.render();

			if(current_module.isFinished()) {
				Game.audio.stopMusic();
			}
		}

		Game.audio.update();

		if (Gdx.input.isKeyPressed(Keys.F1)) {
			if (Gdx.app.getType() == ApplicationType.WebGL) {
				if (!Gdx.graphics.isFullscreen()) {
					Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayModes()[0]);
				}
			} else {
				toggleFullscreen = true;
			}
		}

		if (this.toggleFullscreen) {
			this.toggleFullscreen = false;
			if (fullscreen) {
				Gdx.graphics.setWindowedMode(Settings.WINDOW_WIDTH_PIXELS, Settings.WINDOW_HEIGHT_PIXELS);
				//scs new Gdx.gl.glViewport(0, 0, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight());
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
				//scs new Gdx.gl.glViewport(0, 0, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight());
				fullscreen = true;
			}
			this.current_module.setFullScreen(fullscreen);
		}
	}


	@Override
	public void resize(int width, int height) {
		if(this.current_module != null) {
			this.current_module.resize(width, height);
		}
	}


	@Override
	public void dispose () {
		if (current_module != null) {
			current_module.destroy();
		}
	}
	
}

