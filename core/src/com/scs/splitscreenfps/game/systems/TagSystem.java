package com.scs.splitscreenfps.game.systems;

import com.badlogic.gdx.Gdx;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.Settings;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.components.AnimatedComponent;
import com.scs.splitscreenfps.game.components.AnimatedForAvatarComponent;
import com.scs.splitscreenfps.game.components.CollidesComponent;
import com.scs.splitscreenfps.game.components.HasModel;
import com.scs.splitscreenfps.game.components.TagableComponent;
import com.scs.splitscreenfps.game.data.CollisionResult;
import com.scs.splitscreenfps.game.entities.TextEntity;

/**
 * System for checking if one player has "tagged" another player.
 * @author StephenCS
 *
 */
public class TagSystem extends AbstractSystem {

	public static final long TAG_INTERVAL = 4000;

	private Game game;

	public AbstractEntity currentIt;
	public long lastTagTime = System.currentTimeMillis(); // Can't move at start!

	public TagSystem(BasicECS ecs, Game _game) {
		super(ecs);

		game = _game;
	}


	@Override
	public Class<?> getComponentClass() {
		return TagableComponent.class;
	}


	@Override
	public void processEntity(AbstractEntity it_entity) {
		if (currentIt == null) {
			// Choose random player
			this.currentIt = game.players[0];//NumberFunctions.rnd(0, 3)]; // todo - check num players

			TagableComponent it2 = (TagableComponent)it_entity.getComponent(TagableComponent.class);
			this.swapModel(it_entity, it2);
		}

		if (it_entity != this.currentIt) {
			return;
		}

		if (System.currentTimeMillis() < this.lastTagTime + TAG_INTERVAL) {
			return;
		}

		// Entity must be the current "it" by this point
		TagableComponent it_tagable = (TagableComponent)it_entity.getComponent(TagableComponent.class);
		it_tagable.timeLeftAsIt -= Gdx.graphics.getDeltaTime();
		
		// Check for winner
		if (it_tagable.timeLeftAsIt <= 0) {
			game.playerHasLost(it_tagable.playerId);
		}

		CollidesComponent cc = (CollidesComponent)it_entity.getComponent(CollidesComponent.class);
		for (CollisionResult cr : cc.results) {
			AbstractEntity e = cr.collidedWith;
			TagableComponent clean_tagable = (TagableComponent)e.getComponent(TagableComponent.class);
			if (clean_tagable != null) {
				Settings.p("Player " + clean_tagable.playerId + " tagged");
				
				// Change "it" player to normal
				this.swapModel(it_entity, it_tagable);
				// Change normal player to "it"
				this.swapModel(e, clean_tagable);

				this.currentIt = cr.collidedWith;
				lastTagTime = System.currentTimeMillis();

				game.ecs.addEntity(new TextEntity(ecs, "PLAYER TAGGED!", 50, 2));
			}
		}
	}

	
	private void swapModel(AbstractEntity it_entity, TagableComponent it_tagable) {
		HasModel hasModel = (HasModel)it_entity.getComponent(HasModel.class);
		AnimatedForAvatarComponent animatedForAvatarComponent = (AnimatedForAvatarComponent)it_entity.getComponent(AnimatedForAvatarComponent.class);
		AnimatedComponent animatedComponent = (AnimatedComponent)it_entity.getComponent(AnimatedComponent.class);

		// Store current
		TagableComponent tmp = new TagableComponent(null); // temp for swapping vars
		tmp.animated = animatedComponent;
		tmp.avatarAnim = animatedForAvatarComponent;
		tmp.hasModel = hasModel;

		// Remove current
		it_entity.removeComponent(HasModel.class);
		it_entity.removeComponent(AnimatedForAvatarComponent.class);
		it_entity.removeComponent(AnimatedComponent.class);

		// Put other ones in
		it_entity.addComponent(it_tagable.animated);
		it_entity.addComponent(it_tagable.avatarAnim);
		it_entity.addComponent(it_tagable.hasModel);
		
		
		it_tagable.animated = tmp.animated;
		it_tagable.avatarAnim = tmp.avatarAnim;
		it_tagable.hasModel = tmp.hasModel;

	}

}
