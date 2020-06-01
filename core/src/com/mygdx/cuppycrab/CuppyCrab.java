package com.mygdx.cuppycrab;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.cuppycrab.screens.PlayScreen;

//То, что находится в папке guitar_recordings записывал сам)
//		Так что если соберетесь послушать, не забудьте выключить наушники))

public class CuppyCrab extends Game {
	public SpriteBatch batch;
	public static AssetManager manager;


	
	@Override
	public void create () {
		manager = new AssetManager();
		manager.load("audio/first_level.mp3", Music.class);
		manager.load("audio/first_boss2.mp3", Music.class);
        manager.load("audio/first_end2.mp3", Music.class);
		manager.load("audio/guitar_recordings/fail.wav", Sound.class);
		manager.load("audio/guitar_recordings/victory.wav", Sound.class);
		manager.load("audio/guitar_recordings/temper.wav", Sound.class);

		manager.finishLoading();
		batch = new SpriteBatch();
		setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
