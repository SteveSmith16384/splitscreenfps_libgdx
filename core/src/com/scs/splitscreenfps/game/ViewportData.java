package com.scs.splitscreenfps.game;

import java.awt.Rectangle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;

public class ViewportData {

	public PerspectiveCamera camera;
	public Rectangle viewPos;
	public FrameBuffer frameBuffer = null;
	
	public ViewportData(boolean full_screen, int idx) {
		int w = Gdx.graphics.getWidth()/2;
		int h = Gdx.graphics.getHeight()/2;
		if (full_screen) {
			w = Gdx.graphics.getBackBufferWidth()/2;
			h = Gdx.graphics.getBackBufferHeight()/2;
		}
		
		// Goes clockwise starting top-left
		/*switch (idx) {
		case 0:
			this.viewPos = new Rectangle(0, 0, w, h);
			break;
		case 1:
			this.viewPos = new Rectangle(w, 0, w, h);
			break;
		case 2:
			this.viewPos = new Rectangle(w, h, w, h);
			break;
		case 3:
			this.viewPos = new Rectangle(0, h, w, h);
			break;
		}*/
		
		camera = new PerspectiveCamera(65, w, h);
		camera.position.set(10f, 0, 10f);
		camera.lookAt(11f, 0, 10f);
		camera.near = .5f;
		camera.far = 30f * Game.UNIT;
		camera.update();
		
		//frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, w, h, true);
		//frameBuffer.getColorBufferTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

		this.resize(full_screen, idx);
	}
	
	
	public void resize(boolean full_screen, int idx) {
		int w = Gdx.graphics.getWidth()/2;
		int h = Gdx.graphics.getHeight()/2;
		if (full_screen) {
			w = Gdx.graphics.getBackBufferWidth()/2;
			h = Gdx.graphics.getBackBufferHeight()/2;
		}
		
		// Goes clockwise starting top-left
		switch (idx) {
		case 0:
			this.viewPos = new Rectangle(0, 0, w, h);
			break;
		case 1:
			this.viewPos = new Rectangle(w, 0, w, h);
			break;
		case 2:
			this.viewPos = new Rectangle(w, h, w, h);
			break;
		case 3:
			this.viewPos = new Rectangle(0, h, w, h);
			break;
		}
		
		camera.viewportWidth = w;
		camera.viewportHeight = h;
		camera.update();
		
		frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, w, h, true);
		frameBuffer.getColorBufferTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

	}

}
