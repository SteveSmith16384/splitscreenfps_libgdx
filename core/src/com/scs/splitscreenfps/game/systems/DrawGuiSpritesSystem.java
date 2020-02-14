package com.scs.splitscreenfps.game.systems;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.components.HasGuiSpriteComponent;

public class DrawGuiSpritesSystem extends AbstractSystem implements Comparator<AbstractEntity> {

	private Game game;
	private SpriteBatch batch2d;

	public DrawGuiSpritesSystem(BasicECS ecs, Game _game, SpriteBatch _batch2d) {
		super(ecs, HasGuiSpriteComponent.class);
		game = _game;
		batch2d = _batch2d;
	}


	@Override
	public void process() {
		Collections.sort(this.entities, this);
		Iterator<AbstractEntity> it = this.entities.iterator();
		while (it.hasNext()) {
			AbstractEntity entity = it.next();
			this.processEntity(entity);
			//return;
		}
	}


	@Override
	public void processEntity(AbstractEntity entity) {
		HasGuiSpriteComponent hgs = (HasGuiSpriteComponent)entity.getComponent(HasGuiSpriteComponent.class);
		if (hgs.onlyViewId >= 0) {
			if (hgs.onlyViewId != game.currentViewId) {
				return;
			}
		}
		hgs.sprite.draw(batch2d);
	}


	@Override
	public int compare(AbstractEntity arg0, AbstractEntity arg1) {
		HasGuiSpriteComponent im0 = (HasGuiSpriteComponent)arg0.getComponent(HasGuiSpriteComponent.class);
		HasGuiSpriteComponent im1 = (HasGuiSpriteComponent)arg1.getComponent(HasGuiSpriteComponent.class);
		return im0.zOrder - im1.zOrder;
	}

}
