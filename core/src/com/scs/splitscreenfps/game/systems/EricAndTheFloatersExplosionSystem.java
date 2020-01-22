package com.scs.splitscreenfps.game.systems;

import com.badlogic.gdx.Gdx;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.components.EricAndTheFloatersBombData;

public class EricAndTheFloatersExplosionSystem extends AbstractSystem {

	public EricAndTheFloatersExplosionSystem(BasicECS ecs) {
		super(ecs);
	}


	@Override
	public Class<?> getComponentClass() {
		return EricAndTheFloatersBombData.class;
	}


	@Override
	public void processEntity(AbstractEntity entity) {
		EricAndTheFloatersBombData hdc = (EricAndTheFloatersBombData)entity.getComponent(EricAndTheFloatersBombData.class);

		float dt = Gdx.graphics.getDeltaTime();

		hdc.timeRemaining -= dt;
		if(hdc.timeRemaining <= 0) {
			entity.remove();
			Game.audio.play("beepfx_samples/23_boom_4.wav");
			//todo - explosion();
		}
	}


}
