package com.scs.splitscreenfps.game.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.scs.splitscreenfps.Settings;
import com.scs.splitscreenfps.game.Game;

public class CarriedItem {

    public float rotation;
    public Sprite sprite;
    public Vector2 position;

    public CarriedItem(Texture src, int tx, int ty){
        int unit = (int)Game.UNIT;

        sprite = new Sprite(src, tx*unit, ty*unit, unit, unit);

        rotation = Settings.random.nextFloat()*40 - 20;
    }

}
