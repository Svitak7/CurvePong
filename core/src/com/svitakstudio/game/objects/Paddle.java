package com.svitakstudio.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.svitakstudio.game.utils.Assets;
import com.svitakstudio.game.utils.Constants;
import com.svitakstudio.game.utils.Enums;

import static com.svitakstudio.game.utils.Constants.PADDLE_INIT_X_DISTANCE;
import static com.svitakstudio.game.utils.Constants.PADDLE_SPEED;
import static com.svitakstudio.game.utils.Utils.drawTextureRegion;
import static com.svitakstudio.game.utils.Utils.secondsSince;

/**
 * Created by Adam on 23.07.2017.
 */

public class Paddle {

    private Viewport        pongViewport;
    public SpriteBatch      batch;
    private TextureRegion   region;
    public  Enums.Direction direction;
    private Enums.Players   players;

    private float           paddleSizeH;
    private float           paddleSizeW;
    public float           paddleSpeed          = PADDLE_SPEED;
    public  float           paddleVelocity          =0;
    private float           paddleAcceleration      = 0;

    public boolean          joyStickDraggedUp        = false; //used for touch listener
    public boolean          joyStickDraggedDown        = false; //used for touch listener


    private boolean         up                      = false; //movement condition combo
    private boolean         down                    = false; //movement condition combo

    private long            animationTime           = 0;
    public  Vector2         position;


    public Paddle(Viewport pongViewport, SpriteBatch batch, Enums.Players players) {
        this.pongViewport = pongViewport;
        this.batch = batch;
        this.players = players;
    }

    public void render(SpriteBatch batch) {


        if(up ^ down) {

            if (players == Enums.Players.ONEP)
            {
                Assets.instance.paddleAssets.paddleMovementAnimationLeft.setPlayMode(Animation.PlayMode.LOOP);
                region = (TextureRegion) Assets.instance.paddleAssets.paddleMovementAnimationLeft
                        .getKeyFrame(secondsSince(animationTime));
            }
            else if (players == Enums.Players.TWOP)
            {
                Assets.instance.paddleAssets.paddleMovementAnimationRight.setPlayMode(Animation.PlayMode.LOOP);
                region = (TextureRegion) Assets.instance.paddleAssets.paddleMovementAnimationRight
                        .getKeyFrame(secondsSince(animationTime));
            }



        }

        drawTextureRegion(batch, region, position.x,position.y,0,0,0);

    }

    public void init()
    {
        direction = Enums.Direction.NO_DIRECTION;

        if (players == Enums.Players.ONEP)
        {
            region = Assets.instance.paddleAssets.paddleImageLeft;
            paddleSizeH = region.getRegionHeight();
            paddleSizeW = region.getRegionWidth();
            position = new Vector2(PADDLE_INIT_X_DISTANCE, pongViewport.getWorldHeight()/2-paddleSizeH/2);
        }
        else if (players == Enums.Players.TWOP)
        {
            region = Assets.instance.paddleAssets.paddleImageRight;
            paddleSizeH = region.getRegionHeight();
            paddleSizeW = region.getRegionWidth();
            position = new Vector2(pongViewport.getWorldWidth() - PADDLE_INIT_X_DISTANCE - paddleSizeW, pongViewport.getWorldHeight()/2-paddleSizeH/2);

        }
    }

    public void update(float delta)
    {
        ensureInBounds();

        if (players == Enums.Players.ONEP)
        {
            up = Gdx.input.isKeyPressed(Input.Keys.UP) || joyStickDraggedUp;
            down = Gdx.input.isKeyPressed(Input.Keys.DOWN) || joyStickDraggedDown;
        }

        if (players == Enums.Players.TWOP)
        {
            up = Gdx.input.isKeyPressed(Input.Keys.W) || joyStickDraggedUp;
            down = Gdx.input.isKeyPressed(Input.Keys.S) || joyStickDraggedDown;
        }


        if(up && !down) //up movement
        {
            moveUp(delta);
        } else

        if(down && !up) //down movement
        {
            moveDown(delta);
        }
        else
        {
            if (paddleAcceleration > 0) //if you release the up movement button break the paddle
            {
                breakFromUp(delta);
            } else if (paddleAcceleration < 0) //if you release the down movement button break the paddle
            {
                breakFromDown(delta);
            }
        }

    }


    public void ensureInBounds()
    {
        if(position.y < 0)
        {
            position.y = 0;
            paddleAcceleration = -1*paddleAcceleration/2; //bounce of the down wall
        }
        else if(position.y > pongViewport.getWorldHeight() - paddleSizeH)
        {
            position.y = pongViewport.getWorldHeight() - paddleSizeH;
            paddleAcceleration = -1*paddleAcceleration/2; //bounce of the up wall
        }
    }


    public void moveUp(float delta)
    {
        if(direction != Enums.Direction.UP) {
            animationTime = TimeUtils.nanoTime();
        }
        direction = Enums.Direction.UP;
        if(paddleAcceleration<0)  //if change direction break faster
        {
            paddleAcceleration += Constants.ACCELERATION_CONSTANT * Constants.DECELERATION_SLIDE_MULTIPLAYER;
        }
        else
        {
            paddleAcceleration += Constants.ACCELERATION_CONSTANT;
        }
        paddleVelocity = (paddleSpeed * delta) + paddleAcceleration;
        position.y = position.y + paddleVelocity;
    }

    public void moveDown(float delta)
    {
        if(direction != Enums.Direction.DOWN) {
            animationTime = TimeUtils.nanoTime();
        }
        direction = Enums.Direction.DOWN;
        if(paddleAcceleration>0) ///if change direction break faster
        {
            paddleAcceleration -=  Constants.ACCELERATION_CONSTANT * Constants.DECELERATION_SLIDE_MULTIPLAYER;
        }
        else
        {
            paddleAcceleration -=  Constants.ACCELERATION_CONSTANT;
        }
        paddleVelocity = (-1 * (paddleSpeed * delta) + paddleAcceleration);
        position.y = position.y + paddleVelocity;
    }

    private void breakFromUp(float delta) {
        direction = Enums.Direction.NO_DIRECTION;
        paddleAcceleration -= Constants.ACCELERATION_CONSTANT * Constants.BREAKING_DECELERATION_MULTIPLAYER;
        position.y = position.y + (paddleSpeed * delta) + paddleAcceleration;
        if (paddleAcceleration < 0) {
            paddleAcceleration = 0;
            paddleVelocity = 0;
        }
    }

    private void breakFromDown(float delta) {
        direction = Enums.Direction.NO_DIRECTION;
        paddleAcceleration += Constants.ACCELERATION_CONSTANT * Constants.BREAKING_DECELERATION_MULTIPLAYER;
        position.y = position.y - (paddleSpeed * delta) + paddleAcceleration;
        if(paddleAcceleration>0)   {
            paddleAcceleration = 0;
            paddleVelocity = 0;
        }
    }

    public float getPaddleHeight() {return paddleSizeH;}
    public float getPaddleWidth() {return paddleSizeW;}

    public Enums.Players getPlayerInfo()
    {
        return players;
    }
}
