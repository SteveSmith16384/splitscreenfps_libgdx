package com.scs.splitscreenfps.game.entities.start;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.game.components.CollidesComponent;
import com.scs.splitscreenfps.game.components.HasDecal;
import com.scs.splitscreenfps.game.components.HasDecalCycle;
import com.scs.splitscreenfps.game.components.PositionComponent;
import com.scs.splitscreenfps.game.components.start.StartLevelExitComponent;

import ssmith.libgdx.GraphicsHelper;

public class StartLevelEntityFactory {

	/*
	public static AbstractEntity createStartLevelEntity(BasicECS ecs, int map_x, int map_y, int level) {
		AbstractEntity entity = new AbstractEntity(ecs, "MMStartLevel");
		PositionComponent posData = new PositionComponent((map_x)+(0.5f), (map_y)+(0.5f));
		entity.addComponent(posData);

		HasDecal hasDecal = new HasDecal();
		Texture tex = new Texture(Gdx.files.internal("monstermaze/exit1.png"));
		TextureRegion tr = new TextureRegion(tex, 0, 0, tex.getWidth(), tex.getHeight());
        hasDecal.decal = Decal.newDecal(tr, true);
        hasDecal.decal.setScale(1f / tr.getRegionWidth());
        hasDecal.decal.setPosition(posData.position);
        hasDecal.faceCamera = true;
        hasDecal.dontLockYAxis = true;        
        entity.addComponent(hasDecal);

        HasDecalCycle cycle = new HasDecalCycle(.5f, 2);
        cycle.decals[0] = hasDecal.decal;
        cycle.decals[1] = GraphicsHelper.DecalHelper("monstermaze/exit2.png", 1f);
        entity.addComponent(cycle);
        
        entity.addComponent(new CollidesComponent(false, .3f));

        entity.addComponent(new StartLevelExitComponent(level));

        return entity;
	}
	*/
}
