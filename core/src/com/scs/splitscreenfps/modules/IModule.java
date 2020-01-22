package com.scs.splitscreenfps.modules;

public interface IModule {

	void update();
	
	void render();
	
	boolean isFinished();

	void destroy();
	
	void setFullScreen(boolean fullscreen);
	
	void resize(int w, int h);
}
