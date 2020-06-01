package com.mygdx.cuppycrab.screens;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.cuppycrab.CuppyCrab;

//Задумывался, как экран перехода между уровнями. Пока не успел реализовать

public class EndGameScreen extends ApplicationAdapter implements Screen {
    private Viewport viewport;
    private Stage stage;

    private Game game;

    public EndGameScreen(PlayScreen screen){
        this.game = screen.getGame();
        viewport = screen.getViewport();
        stage = new Stage(viewport, ((CuppyCrab) game).batch);

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label gameOverLabel = new Label("Thanks for the playing", font);
        table.add(gameOverLabel).expandX();
        stage.addActor(table);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 1, 2, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void hide() {

    }
}
