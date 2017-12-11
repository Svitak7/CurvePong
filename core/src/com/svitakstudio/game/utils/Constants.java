package com.svitakstudio.game.utils;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Adam on 23.07.2017.
 */

public class Constants {

    //WORLD CONSTANTS
    public static final  float WORLD_WIDTH = 256;
    public static final  float WORLD_HEIGHT = 144;
    public static final  float WORLD_WALL_THICKNESS = 2;
    public static final  float WORLD_UI_THICKNESS = 24;
    public static final  float BALL_PADDLE_COLLISION = 49;

    //SPRITES CONSTANTS
    public static final String TEXTURE_ATLAS = "images/sprites.pack.atlas";
    public static final String BACKGROUND = "background1";
    public static final String PADDLE_LEFT_1 = "paddleleft1";
    public static final String PADDLE_LEFT_2 = "paddleleft2";
    public static final String PADDLE_RIGHT_1 = "paddleright1";
    public static final String PADDLE_RIGHT_2 = "paddleright2";
    public static final String DIRECTION_BUTTON = "button1";
    public static final String DIRECTION_BUTTON_PUSHED = "button2";
    public static final String JOYSTICK_UP_1 = "joystickUp1";
    public static final String JOYSTICK_UP_2 = "joystickUp2";
    public static final String JOYSTICK_DOWN_1 = "joystickdown1";
    public static final String JOYSTICK_DOWN_2 = "joystickdown2";
    public static final String BALL_1 = "ball1";
    public static final String BALL_2 = "ball2";
    public static final String BALL_3 = "ball3";
    public static final String BALL_4 = "ball4";
    public static final String BALL_5 = "ball5";
    public static final String BALL_6 = "ball6";
    public static final String BALL_7 = "ball7";
    public static final String BALL_8 = "ball8";
    public static final String PLAYER_ONE_UI = "PlayerOneUI";
    public static final String PLAYER_TWO_UI = "PlayerTwoUI";

    //PADDLE CONSTANTS
    public static final float ACCELERATION_CONSTANT = 0.1f;
    public static final float PADDLE_SPEED  = 10.f;
    public static final float MOVEMENT_LOOP_DURATION = 0.1f;
    public static final float PADDLE_INIT_X_DISTANCE = 35f;
    public static final float PADDLE_INIT_Y_DISTANCE = 0f;
    public static final float PADDLE_WIDTH = 14f;
    public static final float PADDLE_HEIGHT = 28f;
    public static final float BREAKING_DECELERATION_MULTIPLAYER = 3f;
    public static final float DECELERATION_SLIDE_MULTIPLAYER = 3f;

    //BUTTON CONSTANTS
    public static final Vector2 BUTTON_CENTER_1P = new Vector2(12,14);
    public static final Vector2 BUTTON_CENTER_2P = new Vector2(8,16);
    public static final float   BUTTON_RADIUS = 14;
    public static final float   BUTTON_X = 18;
    public static final float   BUTTON_Y = 71;

    //BALL CONSTANTS
    public static final float BALL_SPEED = 40f;
    public static final float BALL_DIAMETER = 9f;
    public static final float BALL_RADIUS = 4.5f;
    public static final float BALL_ACCELERATION = 3f;
    public static final float BALL_DECCELERATION_IN_TIME = 0.3f;
    public static final float BALL_SPEED_ADD_AFTER_COLLISION = 7f;
    public static final float BALL_SPEED_AFTER_WRONG_COLLISION_MULTIPLAYER = 40f;





}
