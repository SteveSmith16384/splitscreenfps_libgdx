package com.scs.splitscreenfps.game.systems;

import com.badlogic.gdx.Gdx;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.components.CollidesComponent;
import com.scs.splitscreenfps.game.components.TagableComponent;
import com.scs.splitscreenfps.game.data.CollisionResult;
import com.scs.splitscreenfps.game.entities.TextEntity;

import ssmith.lang.NumberFunctions;

public class TagSystem extends AbstractSystem {

	public static final long TAG_INTERVAL = 4000;
	
	private Game game;
	
	public AbstractEntity currentIt;
	public long lastTagTime = 0;

	public TagSystem(BasicECS ecs, Game _game) {
		super(ecs);
		
		game = _game;
	}


	@Override
	public Class<?> getComponentClass() {
		return TagableComponent.class;
	}


	@Override
	public void processEntity(AbstractEntity entity) {
		if (currentIt == null) {
			// Choose random player
			this.currentIt = game.players[NumberFunctions.rnd(0, 3)]; // todo - check num players
		}

		if (System.currentTimeMillis() < this.lastTagTime + TAG_INTERVAL) {
			return;
		}
		
		if (entity != this.currentIt) {
			return;
		}
		
		TagableComponent ctc = (TagableComponent)entity.getComponent(TagableComponent.class);
		ctc.timeAsIt += Gdx.graphics.getDeltaTime();
		
		CollidesComponent cc = (CollidesComponent)entity.getComponent(CollidesComponent.class);
		for (CollisionResult cr : cc.results) {
			AbstractEntity e = cr.collidedWith;
			TagableComponent ic = (TagableComponent)e.getComponent(TagableComponent.class);
			if (ic != null) {
				this.currentIt = cr.collidedWith;
				lastTagTime = System.currentTimeMillis();
				// todo - change model
				
				game.ecs.addEntity(new TextEntity(ecs, "PLAYER TAGGED!", 50, 2));
			}
		}

	}

}
