package com.scs.splitscreenfps.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.game.components.DrawTextData;

public class TextEntity extends AbstractEntity {

	public TextEntity(BasicECS ecs, String text, float _x, float _y, float _duration, Color col) {
		super(ecs, "Text");

		DrawTextData dtd = new DrawTextData();
		dtd.text = text;
		dtd.x = _x;
		dtd.y = _y;
		dtd.drawUntil = _duration;
		dtd.colour = col;

		this.addComponent(dtd);
	}


	public TextEntity(BasicECS ecs, String text, float _y, float _duration, Color col) {
		super(ecs, "Text");

		DrawTextData dtd = new DrawTextData();
		dtd.text = text;
		dtd.centre_x = true;
		dtd.x = -1;
		dtd.y = _y;
		dtd.drawUntil = _duration;
		dtd.colour = col;

		this.addComponent(dtd);
	}

}
