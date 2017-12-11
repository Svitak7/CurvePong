package com.svitakstudio.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.svitakstudio.game.utils.Assets;

/**
 * Created by Adam on 23.07.2017.
 */

public class Background {

    Viewport pongViewport;
    SpriteBatch batch;
    TextureRegion regionBG = Assets.instance.backgroundAssets.backgroundImage;


    public Background (Viewport pongViewport, SpriteBatch batch)
    {
        this.pongViewport = pongViewport;
        this.batch = batch;
    }


    public void render (SpriteBatch batch)
    {
        batch.draw(regionBG,0,0,pongViewport.getWorldWidth(),pongViewport.getWorldHeight());
    }





}
