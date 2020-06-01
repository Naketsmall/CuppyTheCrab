package com.mygdx.cuppycrab.tools;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.cuppycrab.sprites.Cuppy;

//Класс, отвечающий за вывод информации о статусе персонажа и игровых данных

public class Hud implements Disposable {
    private Viewport viewport;
    public Stage stage;
    private Cuppy cuppy;
    // Думал ввести какие-нибудь суперспособности у краба, но пока не успел
    // Так что пока единственной способностью моего краба будет двойной прыжок))
    //private Integer mana;

    private Integer time;
    private float rounder;
    Label hpLabel;
    Label bibaLabel;
    //Label manaLabel;
    //Label bobaLabel;
    Label timeLabel;
    Label bribaLabel;

    public Hud(SpriteBatch batch, Cuppy cuppy) {
        viewport = new ScreenViewport();
        stage = new Stage(viewport, batch);
        this.cuppy = cuppy;

        Table table = new Table();
        table.top().left();
        table.setFillParent(true);

        //mana = 100;
        time = 0;
        rounder = 0;

        bibaLabel = new Label("HP:  ",
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        hpLabel = new Label(String.format("%02d", cuppy.getHp()),
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        //bobaLabel = new Label("MANA:  ",
        //        new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        //manaLabel = new Label(String.format("%02d", mana),
        //        new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        bribaLabel = new Label("TIME:  ",
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label(String.format("%03d", time),
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(bibaLabel).pad(10, 10, 0, 0);
        table.add(hpLabel).padTop(10);
        table.row();
        //table.add(bobaLabel).pad(10, 10, 0, 0);
        //table.add(manaLabel).padTop(10);
        //table.row();
        table.add(bribaLabel).pad(10, 10, 0, 0);
        table.add(timeLabel).padTop(10);

        stage.addActor(table);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public void update(float delta) {
        rounder += delta;
        if (rounder > time + 1) {
            time++;
            timeLabel.setText(String.format("%03d", time));
        }
        hpLabel.setText(String.format("%02d", cuppy.getHp()));
    }

}
