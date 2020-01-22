package com.scs.splitscreenfps.game.entities.chaos;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Vector3;
import com.scs.basicecs.AbstractEntity;
import com.scs.splitscreenfps.game.Art;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.components.HarmsPlayer;
import com.scs.splitscreenfps.game.components.HasAI;
import com.scs.splitscreenfps.game.components.HasDecal;
import com.scs.splitscreenfps.game.components.HasDecalCycle;
import com.scs.splitscreenfps.game.components.IsDamagableNasty;
import com.scs.splitscreenfps.game.components.MovementData;
import com.scs.splitscreenfps.game.components.PositionData;
import com.scs.splitscreenfps.game.systems.MobAISystem.Mode;

public class Giant extends AbstractEntity {

    public Giant(int x, int y) {
        super(Giant.class.getSimpleName());

        PositionData pos = new PositionData();
        pos.position = new Vector3(x*Game.UNIT+(Game.UNIT/2), 0, y*Game.UNIT+(Game.UNIT/2));
        this.addComponent(pos);
        
        TextureRegion[][] trs = Art.createSheet("chaos/giant.png", 4, 1);
        
		HasDecal hasDecal = new HasDecal();
		//Texture tex = new Texture(Gdx.files.internal("chaos/trex1.png"));
		//TextureRegion tr = new TextureRegion(tex, 0, 0, tex.getWidth(), tex.getHeight());
        hasDecal.decal = Decal.newDecal(trs[0][0], true);
        hasDecal.decal.setScale(Game.UNIT / trs[0][0].getRegionWidth()); // Scale to sq size by default
        hasDecal.faceCamera = true;
        hasDecal.faceCameraTilted = true;        
        this.addComponent(hasDecal);
        
        HasDecalCycle cycle = new HasDecalCycle(.5f, 4);
        cycle.decals[0] = hasDecal.decal;
        cycle.decals[1] = Art.DecalHelper(trs[1][0], 1f);
        cycle.decals[2] = Art.DecalHelper(trs[2][0], 1f);
        cycle.decals[3] = Art.DecalHelper(trs[3][0], 1f);
        this.addComponent(cycle);
        
        HasAI ai = new HasAI(Mode.GoForPlayerIfClose, 1f, Game.UNIT*7f);
        this.addComponent(ai);
        
        this.addComponent(new MovementData(.85f));

        this.addComponent(new HarmsPlayer(1));

        IsDamagableNasty damagable = new IsDamagableNasty(3);
        this.addComponent(damagable);
}
    
}
