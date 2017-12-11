package com.svitakstudio.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

import static com.svitakstudio.game.utils.Constants.BACKGROUND;
import static com.svitakstudio.game.utils.Constants.BALL_1;
import static com.svitakstudio.game.utils.Constants.BALL_2;
import static com.svitakstudio.game.utils.Constants.BALL_3;
import static com.svitakstudio.game.utils.Constants.BALL_4;
import static com.svitakstudio.game.utils.Constants.BALL_5;
import static com.svitakstudio.game.utils.Constants.BALL_6;
import static com.svitakstudio.game.utils.Constants.BALL_7;
import static com.svitakstudio.game.utils.Constants.BALL_8;
import static com.svitakstudio.game.utils.Constants.JOYSTICK_DOWN_1;
import static com.svitakstudio.game.utils.Constants.JOYSTICK_DOWN_2;
import static com.svitakstudio.game.utils.Constants.JOYSTICK_UP_2;
import static com.svitakstudio.game.utils.Constants.MOVEMENT_LOOP_DURATION;
import static com.svitakstudio.game.utils.Constants.PADDLE_LEFT_1;
import static com.svitakstudio.game.utils.Constants.PADDLE_LEFT_2;
import static com.svitakstudio.game.utils.Constants.PADDLE_RIGHT_1;
import static com.svitakstudio.game.utils.Constants.PADDLE_RIGHT_2;
import static com.svitakstudio.game.utils.Constants.TEXTURE_ATLAS;

/**
 * Created by Adam on 23.07.2017.
 */

public class Assets implements Disposable, AssetErrorListener {

    public static final String TAG = Assets.class.getName();
    public static final Assets instance = new Assets();
    private AssetManager assetManager;
    public BackgroundAssets backgroundAssets;
    public PaddleAssets paddleAssets;
    public OnScreenControlsAssets onScreenControlsAssets;
    public BallAssets ballAssets;
    public UIAssets uiAssets;

    private Assets(){}

    public void init(AssetManager assetManager) {
        this.assetManager = assetManager;
        assetManager.setErrorListener(this);
        assetManager.load(TEXTURE_ATLAS, TextureAtlas.class);
        assetManager.finishLoading();

        TextureAtlas atlas      = assetManager.get(TEXTURE_ATLAS);
        backgroundAssets        = new BackgroundAssets(atlas);
        paddleAssets            = new PaddleAssets(atlas);
        onScreenControlsAssets  = new OnScreenControlsAssets(atlas);
        ballAssets              = new BallAssets(atlas);
        uiAssets                = new UIAssets(atlas);

    }

    @Override
    public void error (AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "Nie można załadować assetów: "+asset.fileName,throwable);
    }

    @Override
    public void dispose() {assetManager.dispose();}

    public class BackgroundAssets {

        public final AtlasRegion backgroundImage;

        public BackgroundAssets(TextureAtlas atlas) {

            backgroundImage = atlas.findRegion(BACKGROUND);
        }
    }

    public class PaddleAssets {

        public final AtlasRegion paddleImageLeft;
        public final AtlasRegion paddleImageRight;
        public final Animation paddleMovementAnimationLeft;
        public final Animation paddleMovementAnimationRight;


        public PaddleAssets(TextureAtlas atlas) {

            paddleImageRight = atlas.findRegion(PADDLE_RIGHT_2);
            paddleImageLeft = atlas.findRegion(PADDLE_LEFT_1);

            Array<AtlasRegion> paddleMovementFramesLeft = new Array();
            paddleMovementFramesLeft.add(atlas.findRegion(PADDLE_LEFT_1));
            paddleMovementFramesLeft.add(atlas.findRegion(PADDLE_LEFT_2));
            paddleMovementAnimationLeft = new Animation(MOVEMENT_LOOP_DURATION,paddleMovementFramesLeft, PlayMode.LOOP);

            Array<AtlasRegion> paddleMovementFramesRight = new Array();
            paddleMovementFramesRight.add(atlas.findRegion(PADDLE_RIGHT_1));
            paddleMovementFramesRight.add(atlas.findRegion(PADDLE_RIGHT_2));
            paddleMovementAnimationRight = new Animation(MOVEMENT_LOOP_DURATION,paddleMovementFramesRight, PlayMode.LOOP);

        }
    }

    public class OnScreenControlsAssets {

        public final AtlasRegion joystickImage;
        public final AtlasRegion joystickDraggedUpImage;
        public final AtlasRegion joystickDraggedDownImage;



        public OnScreenControlsAssets(TextureAtlas atlas) {

            joystickImage            = atlas.findRegion(JOYSTICK_DOWN_1);
            joystickDraggedUpImage   = atlas.findRegion(JOYSTICK_UP_2);
            joystickDraggedDownImage = atlas.findRegion(JOYSTICK_DOWN_2);
        }


    }


    public class BallAssets {

        public final AtlasRegion ballImage;
        public final Animation   ballLeftAnimation;
        public final Animation   ballRightAnimation;

        public BallAssets(TextureAtlas atlas) {

            ballImage = atlas.findRegion(BALL_1);

            Array<AtlasRegion> ballLeftFrames = new Array();
            {
                ballLeftFrames.add(atlas.findRegion(BALL_1));
                ballLeftFrames.add(atlas.findRegion(BALL_2));
                ballLeftFrames.add(atlas.findRegion(BALL_3));
                ballLeftFrames.add(atlas.findRegion(BALL_4));
                ballLeftFrames.add(atlas.findRegion(BALL_5));
                ballLeftFrames.add(atlas.findRegion(BALL_6));
                ballLeftFrames.add(atlas.findRegion(BALL_7));
                ballLeftFrames.add(atlas.findRegion(BALL_8));
            }

            ballLeftAnimation   = new Animation(0,ballLeftFrames,PlayMode.LOOP);
            ballRightAnimation  = new Animation(0,ballLeftFrames,PlayMode.LOOP_REVERSED);

        }
    }

    public class UIAssets {

        public final AtlasRegion playerOneUiImage;
        public final AtlasRegion playerTwoUiImage;

        public UIAssets (TextureAtlas atlas) {

            playerOneUiImage = atlas.findRegion(Constants.PLAYER_ONE_UI);
            playerTwoUiImage = atlas.findRegion(Constants.PLAYER_TWO_UI);
        }





    }





}
