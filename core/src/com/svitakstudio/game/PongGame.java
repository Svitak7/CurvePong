package com.svitakstudio.game;

import com.badlogic.gdx.Game;

public class PongGame extends Game {
	@Override
	public void create()
	{
		showScreenPong();
	}

	private void showScreenPong(){setScreen(new PongScreenPlayerVsAi(this));}

}
