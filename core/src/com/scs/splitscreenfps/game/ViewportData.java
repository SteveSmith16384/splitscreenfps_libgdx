package com.scs.splitscreenfps.game;

import java.awt.Rectangle;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
//import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.bitfire.postprocessing.PostProcessing;
import com.scs.splitscreenfps.Settings;
import com.scs.splitscreenfps.game.decals.ShadedGroupStrategy;

public class ViewportData {

	public PerspectiveCamera camera;
	public Rectangle viewPos;
	public FrameBuffer frameBuffer;
	public PostProcessing post;
	public DecalBatch decalBatch;

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
		camera.near = 0.1f;
		camera.far = 30f;
		camera.update();
		
		ShadedGroupStrategy groupStrategy = new ShadedGroupStrategy(camera);
		decalBatch = new DecalBatch(groupStrategy);

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
		
		frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, Settings.LOGICAL_WIDTH_PIXELS, Settings.LOGICAL_HEIGHT_PIXELS, true);
		//frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, w, h, true);
		frameBuffer.getColorBufferTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

		if (Gdx.app.getType() != ApplicationType.WebGL) {
			//post = new PostProcessing(w, h);
		}
	}
	
	
	public void dispose() {
		decalBatch.dispose();
		frameBuffer.dispose();
		if (this.post != null) {
			post.dispose();
		}
	}

}
