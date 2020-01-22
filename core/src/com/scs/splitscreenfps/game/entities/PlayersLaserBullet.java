package com.scs.splitscreenfps.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.scs.basicecs.AbstractEntity;
import com.scs.splitscreenfps.game.Game;
import com.scs.splitscreenfps.game.components.AutoMove;
import com.scs.splitscreenfps.game.components.HarmsNasties;
import com.scs.splitscreenfps.game.components.HasDecal;
import com.scs.splitscreenfps.game.components.MovementData;
import com.scs.splitscreenfps.game.components.PositionData;

public class PlayersLaserBullet extends AbstractEntity {

	public PlayersLaserBullet(Vector3 _position, Vector3 _dir) {
		super(PlayersLaserBullet.class.getSimpleName());
		
        PositionData posData = new PositionData();
        posData.position = _position.cpy().add(_dir.cpy().scl(3));
        //posData.position.y = -Game.UNIT/2;
        this.addComponent(posData);
        
		HasDecal hasDecal = new HasDecal();
		Texture tex = new Texture(Gdx.files.internal("players_bullet.png"));
		TextureRegion tr = new TextureRegion(tex, 0, 0, tex.getWidth(), tex.getHeight());
        hasDecal.decal = Decal.newDecal(tr, true);
        hasDecal.decal.setScale(Game.UNIT / tr.getRegionWidth() / 10, Game.UNIT / tr.getRegionWidth() / 10);
        hasDecal.decal.transformationOffset = new Vector2(0, -2);//hasDecal.decal.getHeight()/2);
        hasDecal.faceCamera = true;
        hasDecal.faceCameraTilted = true;        
        this.addComponent(hasDecal);
        
        MovementData mov = new MovementData(.2f);
        mov.removeIfHitWall = true;
		this.addComponent(mov);

		this.addComponent(new HarmsNasties());
		
		this.addComponent(new AutoMove(_dir.cpy().scl(200)));
		
	}

}
