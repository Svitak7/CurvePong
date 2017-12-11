package com.svitakstudio.game.overlays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.svitakstudio.game.objects.Paddle;
import com.svitakstudio.game.utils.Assets;
import com.svitakstudio.game.utils.Constants;
import com.svitakstudio.game.utils.Utils;

/**
 * Created by Adam on 27.07.2017.
 */

public class OnScreenControls extends InputAdapter{


    private Vector2 middleJoyState1P;
    private Vector2 upJoyState1P;
    private Vector2 downJoyState1P;

    private Vector2 middleJoyState2P;
    private Vector2 upJoyState2P;
    private Vector2 downJoyState2P;

    TextureRegion regionUIP1 = Assets.instance.uiAssets.playerOneUiImage;
    TextureRegion regionUIP2 = Assets.instance.uiAssets.playerTwoUiImage;


    public Paddle paddle1P;
    public Paddle paddle2P;

    private Viewport viewport;

    private int middlePointer1P;
    private int downPointer1P;
    private int upPointer1P;

    private int middlePointer2P;
    private int downPointer2P;
    private int upPointer2P;



    private boolean onePlayer = false;

    public TextureRegion    region1P = Assets.instance.onScreenControlsAssets.joystickImage;
    public TextureRegion    region2P = Assets.instance.onScreenControlsAssets.joystickImage;


    public OnScreenControls(Viewport viewport,Paddle paddle1P, Paddle paddle2P)
    {
        this.paddle1P = paddle1P;
        this.paddle2P = paddle2P;
        this.viewport = viewport;

        middleJoyState1P = new Vector2();
        upJoyState1P = new Vector2();
        downJoyState1P = new Vector2();

        middleJoyState2P = new Vector2();
        upJoyState2P = new Vector2();
        downJoyState2P = new Vector2();

        onePlayer = true;
        recalculateButtonPositions();
    }




    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        Vector2 viewportPosition = viewport.unproject(new Vector2(screenX, screenY));

        //1P
        if (viewportPosition.dst(middleJoyState1P) < Constants.BUTTON_RADIUS) {
            middlePointer1P = pointer;
            paddle1P.joyStickDraggedUp = false;
            paddle1P.joyStickDraggedDown = false;
            region1P = Assets.instance.onScreenControlsAssets.joystickImage;
        }
        if (viewportPosition.dst(upJoyState1P) < Constants.BUTTON_RADIUS+5) {
            upPointer1P = pointer;
            paddle1P.joyStickDraggedDown = false;
            paddle1P.joyStickDraggedUp = true;
            region1P = Assets.instance.onScreenControlsAssets.joystickDraggedUpImage;
        }
        if (viewportPosition.dst(downJoyState1P) < Constants.BUTTON_RADIUS+5) {
            downPointer1P = pointer;
            paddle1P.joyStickDraggedUp = false;
            paddle1P.joyStickDraggedDown = true;
            region1P = Assets.instance.onScreenControlsAssets.joystickDraggedDownImage;
        }

        //2P

        if (viewportPosition.dst(middleJoyState2P) < Constants.BUTTON_RADIUS) {
            middlePointer2P = pointer;
            paddle2P.joyStickDraggedUp = false;
            paddle2P.joyStickDraggedDown = false;
            region2P = Assets.instance.onScreenControlsAssets.joystickImage;
        }
        if (viewportPosition.dst(upJoyState2P) < Constants.BUTTON_RADIUS+5) {
            upPointer2P = pointer;
            paddle2P.joyStickDraggedDown = false;
            paddle2P.joyStickDraggedUp = true;
            region2P = Assets.instance.onScreenControlsAssets.joystickDraggedUpImage;
        }
        if (viewportPosition.dst(downJoyState2P) < Constants.BUTTON_RADIUS+5) {
            downPointer2P = pointer;
            paddle2P.joyStickDraggedUp = false;
            paddle2P.joyStickDraggedDown = true;
            region2P = Assets.instance.onScreenControlsAssets.joystickDraggedDownImage;
        }

        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        Vector2 viewportPosition = viewport.unproject(new Vector2(screenX, screenY));

        if (pointer == middlePointer1P && viewportPosition.dst(upJoyState1P) < Constants.BUTTON_RADIUS+5) {
            paddle1P.joyStickDraggedDown = false;
            paddle1P.joyStickDraggedUp = true;
            region1P = Assets.instance.onScreenControlsAssets.joystickDraggedUpImage;
            middlePointer1P = 0;
            upPointer1P = pointer;
        }

        if (pointer == upPointer1P && viewportPosition.dst(middleJoyState1P) < Constants.BUTTON_RADIUS) {
            paddle1P.joyStickDraggedDown = false;
            paddle1P.joyStickDraggedUp = false;
            region1P = Assets.instance.onScreenControlsAssets.joystickImage;
            upPointer1P = 0;
            middlePointer1P = pointer;
        }

        if (pointer == middlePointer1P && viewportPosition.dst(downJoyState1P)< Constants.BUTTON_RADIUS+5) {

            paddle1P.joyStickDraggedDown = true;
            paddle1P.joyStickDraggedUp = false;
            region1P = Assets.instance.onScreenControlsAssets.joystickDraggedDownImage;
            middlePointer1P = 0;
            downPointer1P = pointer;
        }

        if (pointer == downPointer1P && viewportPosition.dst(middleJoyState1P) < Constants.BUTTON_RADIUS) {
            paddle1P.joyStickDraggedDown = false;
            paddle1P.joyStickDraggedUp = false;
            region1P = Assets.instance.onScreenControlsAssets.joystickImage;
            downPointer1P = 0;
            middlePointer1P = pointer;
        }




        if (pointer == middlePointer2P && viewportPosition.dst(upJoyState2P) < Constants.BUTTON_RADIUS+5) {
            paddle2P.joyStickDraggedDown = false;
            paddle2P.joyStickDraggedUp = true;
            region2P = Assets.instance.onScreenControlsAssets.joystickDraggedUpImage;
            middlePointer2P = 0;
            upPointer2P = pointer;
        }

        if (pointer == upPointer2P && viewportPosition.dst(middleJoyState2P) < Constants.BUTTON_RADIUS) {
            paddle2P.joyStickDraggedDown = false;
            paddle2P.joyStickDraggedUp = false;
            region2P = Assets.instance.onScreenControlsAssets.joystickImage;
            upPointer2P = 0;
            middlePointer2P = pointer;
        }

        if (pointer == middlePointer2P && viewportPosition.dst(downJoyState2P)< Constants.BUTTON_RADIUS+5) {

            paddle2P.joyStickDraggedDown = true;
            paddle2P.joyStickDraggedUp = false;
            region2P = Assets.instance.onScreenControlsAssets.joystickDraggedDownImage;
            middlePointer2P = 0;
            downPointer2P = pointer;
        }

        if (pointer == downPointer2P && viewportPosition.dst(middleJoyState2P) < Constants.BUTTON_RADIUS) {
            paddle2P.joyStickDraggedDown = false;
            paddle2P.joyStickDraggedUp = false;
            region2P = Assets.instance.onScreenControlsAssets.joystickImage;
            downPointer2P = 0;
            middlePointer2P = pointer;
        }


        return super.touchDragged(screenX, screenY, pointer);
    }



    public void render(SpriteBatch batch) {



        if (!Gdx.input.isTouched(middlePointer1P)) {
            middlePointer1P = 0;
            paddle1P.joyStickDraggedDown = false;
            paddle1P.joyStickDraggedUp = false;
            region1P = Assets.instance.onScreenControlsAssets.joystickImage;
        }

        if (!Gdx.input.isTouched(upPointer1P)) {
            upPointer1P = 0;
            paddle1P.joyStickDraggedUp = false;
            region1P = Assets.instance.onScreenControlsAssets.joystickImage;
        }

        if (!Gdx.input.isTouched(downPointer1P)) {
            downPointer1P = 0;
            paddle1P.joyStickDraggedDown = false;
            region1P = Assets.instance.onScreenControlsAssets.joystickImage;
        }

        if (!Gdx.input.isTouched(middlePointer2P)) {
            middlePointer2P = 0;
            paddle2P.joyStickDraggedDown = false;
            paddle2P.joyStickDraggedUp = false;
            region2P = Assets.instance.onScreenControlsAssets.joystickImage;
        }

        if (!Gdx.input.isTouched(upPointer2P)) {
            upPointer2P = 0;
            paddle2P.joyStickDraggedUp = false;
            region2P = Assets.instance.onScreenControlsAssets.joystickImage;
        }

        if (!Gdx.input.isTouched(downPointer2P)) {
            downPointer2P = 0;
            paddle2P.joyStickDraggedDown = false;
            region2P = Assets.instance.onScreenControlsAssets.joystickImage;
        }




        batch.draw(regionUIP1,0,0,regionUIP1.getRegionWidth(),regionUIP1.getRegionHeight());
        batch.draw(regionUIP2,viewport.getWorldWidth()-regionUIP2.getRegionWidth(),0,regionUIP2.getRegionWidth(),regionUIP2.getRegionHeight());
        Utils.drawTextureRegion(batch, region1P,middleJoyState1P, Constants.BUTTON_CENTER_1P);
        Utils.drawTextureRegion(batch, region2P,middleJoyState2P, Constants.BUTTON_CENTER_2P);





    }


    public void recalculateButtonPositions() {


            middleJoyState1P.set(Constants.BUTTON_X, Constants.BUTTON_Y);
            upJoyState1P.set(Constants.BUTTON_X, Constants.BUTTON_Y + Constants.BUTTON_CENTER_1P.y + 20);
            downJoyState1P.set(Constants.BUTTON_X, Constants.BUTTON_Y - Constants.BUTTON_CENTER_1P.y - 20);


            middleJoyState2P.set(viewport.getWorldWidth() - Constants.BUTTON_X,viewport.getWorldHeight() - Constants.BUTTON_Y);
            upJoyState2P.set(viewport.getWorldWidth() - Constants.BUTTON_X,viewport.getWorldHeight() - Constants.BUTTON_Y+20);
            downJoyState2P.set(viewport.getWorldWidth() - Constants.BUTTON_X,viewport.getWorldHeight() - Constants.BUTTON_Y-20);
    }

}
