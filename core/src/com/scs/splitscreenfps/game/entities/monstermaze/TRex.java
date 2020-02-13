package com.scs.splitscreenfps.game.entities.monstermaze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Vector3;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.BasicECS;
import com.scs.splitscreenfps.game.components.CollidesComponent;
import com.scs.splitscreenfps.game.components.TRexHarmsPlayerComponent;
import com.scs.splitscreenfps.game.components.HasDecal;
import com.scs.splitscreenfps.game.components.HasDecalCycle;
import com.scs.splitscreenfps.game.components.MoveAStarComponent;
import com.scs.splitscreenfps.game.components.MovementData;
import com.scs.splitscreenfps.game.components.PositionComponent;

import ssmith.libgdx.GraphicsHelper;

public class TRex extends AbstractEntity {

    public TRex(BasicECS ecs, int x, int y) {
        super(ecs, TRex.class.getSimpleName());

        PositionComponent pos = new PositionComponent();
        pos.position = new Vector3(x+0.5f, 0, y+0.5f);
        this.addComponent(pos);
        
		HasDecal hasDecal = new HasDecal();
		Texture tex = new Texture(Gdx.files.internal("monstermaze/trex1.png"));
		TextureRegion tr = new TextureRegion(tex, 0, 0, tex.getWidth(), tex.getHeight());
        hasDecal.decal = Decal.newDecal(tr, true);
        hasDecal.decal.setScale(1f / tr.getRegionWidth()); // Scale to sq size by default
        hasDecal.faceCamera = true;
        hasDecal.dontLockYAxis = true;        
        this.addComponent(hasDecal);
        
        HasDecalCycle cycle = new HasDecalCycle(.8f, 2);
        cycle.decals[0] = hasDecal.decal;
        cycle.decals[1] = GraphicsHelper.DecalHelper("monstermaze/trex2.png", 1f);
        this.addComponent(cycle);
        
        //HasAI ai = new HasAI(Mode.GoForPlayerIfClose, 1.5f, 7f, target);
        //this.addComponent(ai);
        MoveAStarComponent astar = new MoveAStarComponent(.002f, false);
        this.addComponent(astar);       
        
        this.addComponent(new MovementData(.85f));

        this.addComponent(new TRexHarmsPlayerComponent());
        
        this.addComponent(new CollidesComponent(true, .3f, .3f, .3f));

    }
    
}
