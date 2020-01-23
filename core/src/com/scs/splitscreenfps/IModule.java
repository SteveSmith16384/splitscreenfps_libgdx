package com.scs.splitscreenfps;

public interface IModule {

	void update();
	
	void render();
	
	//boolean isFinished();

	void dispose();
	
	void setFullScreen(boolean fullscreen);
	
	void resize(int w, int h);
}
