package com.scs.splitscreenfps.game.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.components.DrawTextData;

public class DrawTextSystem extends AbstractSystem {

	private Game game;
	private SpriteBatch batch2d;

	public DrawTextSystem(BasicECS ecs, Game _game, SpriteBatch _batch2d) {
		super(ecs, DrawTextData.class);

		game = _game;
		batch2d = _batch2d;
	}


	@Override
	public void processEntity(AbstractEntity entity) {
		DrawTextData dtd = (DrawTextData)entity.getComponent(DrawTextData.class);

		dtd.drawUntil -= Gdx.graphics.getDeltaTime();
		if (dtd.drawUntil <= 0) {
			entity.remove();
		} else {
			if (dtd.centre_x && dtd.x < 0) {
				int len = dtd.text.length() * 8;
				dtd.x = Gdx.graphics.getWidth() / 2 - len;
			}

			game.font.setColor(dtd.colour);
			game.font.draw(batch2d, dtd.text, dtd.x, dtd.y);
		}

	}

}
