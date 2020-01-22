package com.scs.splitscreenfps.game.entities.aliens;

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

public class Alien extends AbstractEntity {

	public static int total_aliens = 0;
	
    public Alien(int x, int y) {
        super(Alien.class.getSimpleName());

        PositionData pos = new PositionData();
        pos.position = new Vector3(x*Game.UNIT+(Game.UNIT/2), 0, y*Game.UNIT+(Game.UNIT/2));
        this.addComponent(pos);
        
        TextureRegion[][] trs = Art.createSheet("aliens/alien.png", 6, 8);//6, 8);
        //TextureRegion[][] trs = Art.createSheet("aliens/alien2.png", 2, 1);//6, 8);

        HasDecal hasDecal = new HasDecal();
        hasDecal.decal = Decal.newDecal(trs[1][0], true);
        hasDecal.decal.setScale(Game.UNIT / trs[1][0].getRegionWidth()); // Scale to sq size by default
        hasDecal.faceCamera = true;
        hasDecal.faceCameraTilted = true;
        this.addComponent(hasDecal);

        HasDecalCycle hdc = new HasDecalCycle(.5f, 2);
        for (int i=0 ; i<2 ; i++) {
        	hdc.decals[i] = Decal.newDecal(trs[i+1][0], true);
        }
        this.addComponent(hdc);

        HasAI ai = new HasAI(Mode.GoForPlayerIfClose, 3.5f, Game.UNIT*7f);
        this.addComponent(ai);
        
        this.addComponent(new MovementData(.85f));

        this.addComponent(new HarmsPlayer(1));
        
        IsDamagableNasty damagable = new IsDamagableNasty(5);
        this.addComponent(damagable);

        total_aliens++;
    }
    

    @Override
	public void remove() {
		super.remove();
		
		total_aliens--;
	}


}
