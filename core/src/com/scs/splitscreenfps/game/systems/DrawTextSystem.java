package com.scs.splitscreenfps.game.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.game.components.DrawTextData;

public class DrawTextSystem extends AbstractSystem {

	private SpriteBatch batch2d;
	private BitmapFont font_white;

	public DrawTextSystem(BasicECS ecs, SpriteBatch _batch2d, BitmapFont _font_white) {
		super(ecs);

		batch2d = _batch2d;
		font_white = _font_white;
	}


	@Override
	public Class<?> getComponentClass() {
		return DrawTextData.class;
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

			font_white.draw(batch2d, dtd.text, dtd.x, dtd.y);
		}

	}

}
