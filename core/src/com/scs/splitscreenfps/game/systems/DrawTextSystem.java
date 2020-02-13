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
	private BitmapFont font;

	public DrawTextSystem(BasicECS ecs, SpriteBatch _batch2d, BitmapFont _font) {
		super(ecs, DrawTextData.class);

		batch2d = _batch2d;
		font = _font;
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

			font.setColor(dtd.colour);
			font.draw(batch2d, dtd.text, dtd.x, dtd.y);
		}

	}

}
