package com.mygdx.cuppycrab.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.cuppycrab.screens.PlayScreen;

//Класс, обеспечивающий игроку возможность управления персонажем с андроид устройства

public class Controller {
    Viewport viewport;
    Stage stage;
    boolean upPressed, rightPressed, leftPressed;
    OrthographicCamera camera;

    public boolean isUpPressed() {
        return upPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public Controller(PlayScreen screen){
        camera = screen.getCamera();
        viewport = screen.getViewport();
        stage = screen.getHud().stage;
        Gdx.input.setInputProcessor(stage);


        Image upBtn = new Image(new Texture("up_button.png"));
        upBtn.setSize(100, 100);
        upBtn.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                upPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                upPressed = false;
            }
        });

        Image rigtBtn = new Image(new Texture("right_button.png"));
        rigtBtn.setSize(100, 100);
        rigtBtn.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = false;
            }
        });



        Image leftBtn = new Image(new Texture("left_button.png"));
        leftBtn.setSize(100, 100);
        leftBtn.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = false;
            }
        });
        Table table = new Table();
        table.left().bottom();

        table.add(leftBtn).size(200, 200);
        table.add(rigtBtn).size(200, 200);
        table.row().pad(5, 5, 0, 0);

        stage.addActor(table);

        Table toble = new Table();
        toble.right().bottom();
        toble.add(upBtn).size(100, 100).expandX();

        stage.addActor(table);
    }

    public void draw(){
        stage.draw();
    }

}
