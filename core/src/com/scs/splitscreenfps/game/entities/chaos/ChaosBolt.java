package com.scs.splitscreenfps.game.entities.chaos;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.scs.basicecs.AbstractEntity;
import com.scs.splitscreenfps.game.Art;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.components.AutoMove;
import com.scs.splitscreenfps.game.components.HarmsNasties;
import com.scs.splitscreenfps.game.components.HasDecal;
import com.scs.splitscreenfps.game.components.HasDecalCycle;
import com.scs.splitscreenfps.game.components.MovementData;
import com.scs.splitscreenfps.game.components.PositionData;

public class ChaosBolt extends AbstractEntity {

	public ChaosBolt(Vector3 _position, Vector3 _dir) {
		super(ChaosBolt.class.getSimpleName());
		
        PositionData posData = new PositionData();
        posData.position = _position.cpy().add(_dir.cpy().scl(3));
        //posData.position.y = -Game.UNIT/2;
        this.addComponent(posData);
        
        TextureRegion[][] trs = Art.createSheet("chaos/chaosbolt.png", 4, 1);
        
		HasDecal hasDecal = new HasDecal();
		//Texture tex = new Texture(Gdx.files.internal("players_bullet.png"));
		//TextureRegion tr = new TextureRegion(tex, 0, 0, tex.getWidth(), tex.getHeight());
        hasDecal.decal = Decal.newDecal(trs[0][0], true);
        hasDecal.decal.setScale(Game.UNIT / trs[0][0].getRegionWidth() / 10, Game.UNIT / trs[0][0].getRegionWidth() / 10);
        hasDecal.decal.transformationOffset = new Vector2(0, -2);//hasDecal.decal.getHeight()/2);
        hasDecal.faceCamera = true;
        hasDecal.faceCameraTilted = true;        
        this.addComponent(hasDecal);
        
        HasDecalCycle hdc = new HasDecalCycle(.05f, 4);
        for (int i=0 ; i<4 ; i++) {
        	hdc.decals[i] = Decal.newDecal(trs[i][0], true);
        }
        this.addComponent(hdc);
        
        MovementData mov = new MovementData(.2f);
        mov.removeIfHitWall = true;
		this.addComponent(mov);

		this.addComponent(new HarmsNasties());
		
		this.addComponent(new AutoMove(_dir.cpy().scl(200)));
		
	}

}
