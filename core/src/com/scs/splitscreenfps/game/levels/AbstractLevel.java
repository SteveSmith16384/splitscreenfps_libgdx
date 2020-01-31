package com.scs.splitscreenfps.game.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.GridPoint2;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.MapData;

public abstract class AbstractLevel {

	protected int map_width;
	protected int map_height;
	protected GridPoint2 startPositions[] = new GridPoint2[4];
	
	public AbstractLevel() {
	}
	
	public void setBackgroundColour() {
		Gdx.gl.glClearColor(1f, 1f, 1f, 1);
	}

	public abstract void load();
	
	public void update(Game game, MapData world) {};
	
	public void renderUI(SpriteBatch batch, BitmapFont font_white, BitmapFont font_black) {}

	public GridPoint2 getPlayerStartMap(int idx) {
		return this.startPositions[idx];
	}
	
	public String getMusicFilename() {
		// Override if required
		return null;
	}
}
