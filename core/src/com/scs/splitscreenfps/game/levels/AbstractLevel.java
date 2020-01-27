package com.scs.splitscreenfps.game.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.MapData;

public abstract class AbstractLevel {

	protected int map_width;
	protected int map_height;
	protected int playerStartMapX = -1, playerStartMapZ = -1;
	
	public AbstractLevel() {
	}
	
	public void setBackgroundColour() {
		Gdx.gl.glClearColor(0.1f, 1f, 0, 1);
	}

	public abstract void load();
	
	public void update(Game game, MapData world) {};
	
	public abstract void renderUI(SpriteBatch batch, BitmapFont font_white, BitmapFont font_black);

	public int getPlayerStartMapX() {
		return this.playerStartMapX;
	}
	
	public int getPlayerStartMapY() {
		return this.playerStartMapZ;
	}

	
	public abstract String getMusicFilename();
}
