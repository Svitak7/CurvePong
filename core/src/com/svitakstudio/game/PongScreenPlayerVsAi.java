package com.svitakstudio.game;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.svitakstudio.game.objects.Background;
import com.svitakstudio.game.objects.Ball;
import com.svitakstudio.game.objects.Paddle;
import com.svitakstudio.game.overlays.OnScreenControls;
import com.svitakstudio.game.utils.Assets;
import com.svitakstudio.game.utils.Enums;

import static com.badlogic.gdx.Application.LOG_DEBUG;
import static com.svitakstudio.game.utils.Constants.WORLD_HEIGHT;
import static com.svitakstudio.game.utils.Constants.WORLD_WIDTH;

/**
 * Created by Adam on 22.07.2017.
 */

public class PongScreenPlayerVsAi extends InputAdapter implements Screen {


    PongGame game;

    SpriteBatch batch;

    Viewport pongViewport;

    Background background;

    Paddle paddle1p;

    Paddle paddle2p;

    OnScreenControls onScreenControls;

    Ball ball;

    public PongScreenPlayerVsAi(PongGame game) {this.game = game;}

    @Override
    public void show() {
        AssetManager assetManager = new AssetManager();
        Assets.instance.init(assetManager);
        Gdx.app.setLogLevel(LOG_DEBUG);
        pongViewport = new FitViewport(WORLD_WIDTH,WORLD_HEIGHT);
        batch = new SpriteBatch();

        background = new Background(pongViewport,batch);
        paddle1p = new Paddle(pongViewport,batch, Enums.Players.ONEP);
        paddle2p = new Paddle(pongViewport,batch, Enums.Players.TWOP);
        ball = new Ball(pongViewport,paddle1p,paddle2p);

        paddle1p.init();
        paddle2p.init();
        ball.init();
        onScreenControls = new OnScreenControls(pongViewport,paddle1p,paddle2p);


        Gdx.input.setInputProcessor(this);



        if (onMobile()) {
            Gdx.input.setInputProcessor(onScreenControls);
        } else  {
            Gdx.input.setInputProcessor(this);
        }
    }

    private boolean onMobile() {
        return Gdx.app.getType() == ApplicationType.Android || Gdx.app.getType() == ApplicationType.iOS;
    }

    @Override
    public void render(float delta) {

        updateObjects(delta);

        pongViewport.apply(true);
        clearScreen();

        batch.setProjectionMatrix(pongViewport.getCamera().combined);
        batch.begin();
        background.render(batch);
        paddle1p.render(batch);
        paddle2p.render(batch);
        ball.render(batch);

        onScreenControls.render(batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        pongViewport.update(width, height, true);
        onScreenControls.recalculateButtonPositions();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        batch.dispose();
    }

    @Override
    public void dispose() {
        Assets.instance.dispose();
    }


    private void clearScreen()
    {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void updateObjects(float delta)
    {
        paddle2p.update(delta);
        paddle1p.update(delta);
        ball.update(delta);
    }
}
