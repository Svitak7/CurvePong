package com.svitakstudio.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.svitakstudio.game.utils.Assets;
import com.svitakstudio.game.utils.Constants;
import com.svitakstudio.game.utils.Enums;
import com.svitakstudio.game.utils.Enums.BallState;
import com.svitakstudio.game.utils.Utils;

import static com.svitakstudio.game.utils.Constants.BALL_ACCELERATION;
import static com.svitakstudio.game.utils.Constants.BALL_DECCELERATION_IN_TIME;
import static com.svitakstudio.game.utils.Constants.BALL_SPEED_ADD_AFTER_COLLISION;
import static com.svitakstudio.game.utils.Constants.BALL_SPEED_AFTER_WRONG_COLLISION_MULTIPLAYER;

/**
 * Created by Adam on 06.08.2017.
 */

public class Ball {

    Vector2 velocity;
    Vector2 position;
    Viewport viewport;
    Paddle paddle1P;
    Paddle paddle2P;

    boolean gameStart = false;

    float rotationAcceleration = 0;


    boolean hitPaddle1P = false;
    boolean hitPaddle2P = false;

    boolean hitPaddle = false;




    long animationStartTime;
    float rotationSpeedAnimation;

    BallState ballState = BallState.STATIC;

    TextureRegion region = Assets.instance.ballAssets.ballImage;


    public Ball(Viewport viewport, Paddle paddle1P, Paddle paddle2P) {

        this.viewport = viewport;
        this.velocity = new Vector2(0,0);
        this.paddle1P = paddle1P;
        this.paddle2P = paddle2P;
        init();

    }

    public void init() {
        position = new Vector2(viewport.getWorldWidth()/2,viewport.getWorldHeight()/2);
        rotationAcceleration = 0;
        ballState = BallState.STATIC;
        velocity.x = Constants.BALL_SPEED;
        velocity.y = 0;
        hitPaddle1P = false;
        hitPaddle2P = false;
    }

    public void render(SpriteBatch batch) {

        if(ballState == BallState.STATIC)
        {
            region = Assets.instance.ballAssets.ballImage;
        }

        if (ballState == BallState.DOWN)
        {
            rotationSpeedAnimation = rotationAcceleration;
            rotationSpeedAnimation = 25 / ((-1 * rotationSpeedAnimation) * 3);
            region = (TextureRegion) Assets.instance.ballAssets.ballLeftAnimation.getKeyFrame(Utils.secondsSince(animationStartTime));
            Assets.instance.ballAssets.ballLeftAnimation.setFrameDuration(rotationSpeedAnimation);
        }

        if (ballState == BallState.UP)
        {
            rotationSpeedAnimation = rotationAcceleration;
            rotationSpeedAnimation = 25 / ((rotationSpeedAnimation) * 3);
            region = (TextureRegion) Assets.instance.ballAssets.ballLeftAnimation.getKeyFrame(Utils.secondsSince(animationStartTime));
            Assets.instance.ballAssets.ballLeftAnimation.setFrameDuration(rotationSpeedAnimation);
        }


        Utils.drawTextureRegion(batch, region , position.x,position.y);


    }

    public void update(float delta) {

        //DOWN - minus rotation
        //UP   - plus rotation

        Gdx.app.log("ROTATION ACCELERATION"," "+ rotationAcceleration);



        if (rotationAcceleration > 0 && ballState == BallState.DOWN) {
            rotationAcceleration = 0;
        } else if (rotationAcceleration < 0 && ballState == BallState.DOWN) {
            rotationAcceleration += BALL_DECCELERATION_IN_TIME * BALL_DECCELERATION_IN_TIME;
        }

        if (rotationAcceleration < 0 && ballState == BallState.UP) {
            rotationAcceleration = 0;
        } else if (rotationAcceleration > 0 && ballState == BallState.UP) {
            rotationAcceleration -= BALL_DECCELERATION_IN_TIME * BALL_DECCELERATION_IN_TIME;
        }

        velocity.y += delta * rotationAcceleration;
        position.y += delta * velocity.y;
        position.x += delta * velocity.x;

        wallCollision();
        paddle1PCollision();
        paddle2PCollision();

        debugReset();



    }



    public void wallCollision() {

        if (position.y < Constants.WORLD_WALL_THICKNESS) //odbicie z lewej
        {
            position.y = Constants.WORLD_WALL_THICKNESS;
            velocity.y = -1 * velocity.y;
        }

        if (position.y > viewport.getWorldHeight() -  Constants.BALL_DIAMETER- Constants.WORLD_WALL_THICKNESS) //odbicie z prawej
        {
            position.y = viewport.getWorldHeight() -  Constants.BALL_DIAMETER - Constants.WORLD_WALL_THICKNESS;
            velocity.y = -1 * velocity.y;
        }
    }


    //TODO sklaibrowac jescze odbicia zeby latwiej sie odbilao potem
    public void paddle1PCollision() {

        /** paddle typical bounce collision */

        if (       position.x <     Constants.BALL_PADDLE_COLLISION
                && position.y >     paddle1P.position.y - (region.getRegionHeight()/2 *3F/4F)
                && position.y <     paddle1P.position.y + paddle1P.getPaddleHeight() - (region.getRegionHeight()/2 *1F/4F)
                && hitPaddle1P ==   false) {
            Gdx.app.log("Paddle1", "Odbicie normalne");
            hitPaddle1P = true;
            hitPaddle2P = false;
            animationStartTime = TimeUtils.nanoTime();
            position.x = Constants.BALL_PADDLE_COLLISION;
            velocity.x -= BALL_SPEED_ADD_AFTER_COLLISION;
            velocity.x = -1 * velocity.x;


            if (paddle1P.direction == Enums.Direction.DOWN) {
                rotationAcceleration += -1*(paddle1P.paddleVelocity - paddle1P.paddleSpeed) * BALL_ACCELERATION;
            }
            else if (paddle1P.direction == Enums.Direction.UP)
            {
                rotationAcceleration +=  (paddle1P.paddleVelocity - paddle1P.paddleSpeed) * BALL_ACCELERATION;
            }

            if(rotationAcceleration > 0)
            {
                ballState = BallState.UP;
            }
            else
            {
                ballState = BallState.DOWN;
            }





        }


        /** Paddle top bounce collision check*/

        if (        position.x < Constants.BALL_PADDLE_COLLISION
                &&  position.x > Constants.BALL_PADDLE_COLLISION - region.getRegionWidth()/2 - paddle1P.getPaddleWidth()
                &&  position.y > paddle1P.position.y + paddle1P.getPaddleHeight() -  region.getRegionHeight()
                &&  position.y < paddle1P.position.y + paddle1P.getPaddleHeight())
        {
            Gdx.app.log("Paddle1","Odbicie gora");
            if(velocity.y<0)
            {
                velocity.y = -1 * (velocity.y + paddle1P.paddleVelocity*BALL_SPEED_AFTER_WRONG_COLLISION_MULTIPLAYER);
            }
            else
            {
                velocity.y = velocity.y + paddle1P.paddleVelocity*BALL_SPEED_AFTER_WRONG_COLLISION_MULTIPLAYER;
            }

            hitPaddle1P = true;
        }

        /** Paddle bottom bounce collision check*/

        if (        position.x < Constants.BALL_PADDLE_COLLISION
                &&  position.x > Constants.BALL_PADDLE_COLLISION - region.getRegionWidth()/2 - paddle1P.getPaddleWidth()
                &&  position.y < paddle1P.position.y
                &&  position.y > paddle1P.position.y - region.getRegionHeight())
        {
            Gdx.app.log("Paddle1","Odbicie dolem");
            if(velocity.y>0)
            {
                velocity.y = -1 * (velocity.y  +paddle1P.paddleVelocity * BALL_SPEED_AFTER_WRONG_COLLISION_MULTIPLAYER);
            }
            else
            {
                velocity.y = velocity.y + (paddle1P.paddleVelocity * BALL_SPEED_AFTER_WRONG_COLLISION_MULTIPLAYER);
            }
            hitPaddle1P = true;
        }

        /** if ball went behind the paddle dont check the collision */

        if (position.x < Constants.BALL_PADDLE_COLLISION - paddle1P.getPaddleWidth())
        {
            hitPaddle1P = true;
        }

    }




    public void paddle2PCollision() {

        /** paddle typical bounce collision */

        if (       position.x >     viewport.getWorldWidth() - Constants.BALL_PADDLE_COLLISION - paddle2P.getPaddleWidth() + region.getRegionWidth()/2
                && position.y >     paddle2P.position.y - (region.getRegionHeight()/2 *3F/4F)
                && position.y <     paddle2P.position.y + paddle2P.getPaddleHeight() - (region.getRegionHeight()/2 *1F/4F)
                && hitPaddle2P ==   false)
        {
            animationStartTime = TimeUtils.nanoTime();
            Gdx.app.log("Paddle1", "Odbicie normalne");
            hitPaddle2P = true;
            hitPaddle1P = false;

            position.x = viewport.getWorldWidth() - Constants.BALL_PADDLE_COLLISION - paddle2P.getPaddleWidth() + region.getRegionWidth()/2;
            velocity.x += BALL_SPEED_ADD_AFTER_COLLISION;
            velocity.x = -1 * velocity.x;


            if (paddle2P.direction == Enums.Direction.DOWN) {
                rotationAcceleration += -1*(paddle2P.paddleVelocity - paddle2P.paddleSpeed) * BALL_ACCELERATION;
            }
            else if (paddle2P.direction == Enums.Direction.UP)
            {
                rotationAcceleration +=  (paddle2P.paddleVelocity - paddle2P.paddleSpeed) * BALL_ACCELERATION;
            }

            if(rotationAcceleration > 0)
            {
                ballState = BallState.UP;
            }
            else
            {
                ballState = BallState.DOWN;
            }
        }


        /** Paddle top bounce collision check*/

        if (        position.x > viewport.getWorldWidth() - Constants.BALL_PADDLE_COLLISION -  paddle2P.getPaddleWidth() +region.getRegionWidth()/2
                &&  position.x < viewport.getWorldWidth() - Constants.BALL_PADDLE_COLLISION + region.getRegionWidth()
                &&  position.y > paddle2P.position.y + paddle2P.getPaddleHeight() -  region.getRegionHeight()
                &&  position.y < paddle2P.position.y + paddle2P.getPaddleHeight())
        {
            Gdx.app.log("Paddle1", "Odbicie gora");
            if(velocity.y<0)
            {
                velocity.y = -1 * velocity.y    + paddle2P.paddleVelocity*BALL_SPEED_AFTER_WRONG_COLLISION_MULTIPLAYER;
            }
            else
            {
                velocity.y = velocity.y      + paddle2P.paddleVelocity*BALL_SPEED_AFTER_WRONG_COLLISION_MULTIPLAYER;
            }


            hitPaddle2P = true;
        }

        /** Paddle bottom bounce collision check*/

        if(         position.x > viewport.getWorldWidth() - Constants.BALL_PADDLE_COLLISION - paddle2P.getPaddleWidth() + region.getRegionWidth()/2
                &&  position.x < viewport.getWorldWidth() - Constants.BALL_PADDLE_COLLISION + region.getRegionWidth()
                &&  position.y < paddle2P.position.y
                &&  position.y > paddle2P.position.y - region.getRegionHeight())
        {
            Gdx.app.log("Paddle1", "Odbicie dolem");
            if(velocity.y>0)
            {
                velocity.y = -1 * velocity.y  +paddle2P.paddleVelocity * BALL_SPEED_AFTER_WRONG_COLLISION_MULTIPLAYER;
            }
            else
            {
                velocity.y = velocity.y + paddle2P.paddleVelocity * BALL_SPEED_AFTER_WRONG_COLLISION_MULTIPLAYER;
            }
            hitPaddle2P = true;
        }

        /** if ball went behind the paddle dont check the collision */

        if (position.x > viewport.getWorldWidth() - Constants.BALL_PADDLE_COLLISION - paddle2P.getPaddleWidth() + region.getRegionWidth()/2)
        {
            hitPaddle2P = true;
        }

    }









    public void debugReset()
    {
        if(position.x > viewport.getWorldWidth() || position.x < 0)
        {
            init();
        }

    }



}
