package com.scs.splitscreenfps.game.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.entities.PlayersAvatar_Person;

import ssmith.libgdx.GridPoint2Static;

public abstract class AbstractLevel {

	protected Game game;
	protected int map_width;
	protected int map_height;
	protected GridPoint2Static startPositions[] = new GridPoint2Static[4];
	
	public AbstractLevel(Game _game) {
		game = _game;
	}
	
	
	public void loadAvatars() {
		for (int i=0 ; i<game.players.length ; i++) {
			game.players[i] = new PlayersAvatar_Person(game, i, game.viewports[i], game.inputs.get(i));
			game.ecs.addEntity(game.players[i]);
		}	
	}
	
	
	public void setBackgroundColour() {
		Gdx.gl.glClearColor(1f, 1f, 1f, 1);
	}
	
	
	/**
	 * This will get called at the start and also every time the screen is resized.
	 */
	public void loadAssets() {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/SHOWG.TTF"));
		
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = Gdx.graphics.getBackBufferHeight()/30;
		//Settings.p("Font size=" + parameter.size);
		game.font_small = generator.generateFont(parameter);
		
		parameter = new FreeTypeFontParameter();
		parameter.size = Gdx.graphics.getBackBufferHeight()/20;
		//Settings.p("Font size=" + parameter.size);
		game.font_med = generator.generateFont(parameter);
		
		parameter = new FreeTypeFontParameter();
		parameter.size = Gdx.graphics.getBackBufferHeight()/10;
		//Settings.p("Font size=" + parameter.size);
		game.font_large = generator.generateFont(parameter);
		
		generator.dispose(); // don't forget to dispose to avoid memory leaks!
	}

	public abstract void load();
	
	public void setupAvatars(AbstractEntity player, int playerIdx) {}
	
	public abstract void addSystems(BasicECS ecs);

	public abstract void update();
	
	public void renderUI(SpriteBatch batch, int viewIndex) {}

	public GridPoint2Static getPlayerStartMap(int idx) {
		return this.startPositions[idx];
	}
	
	public String getMusicFilename() {
		// Override if required
		return null;
	}
}
