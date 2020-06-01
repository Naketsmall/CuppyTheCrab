package com.mygdx.cuppycrab.screens;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.cuppycrab.CuppyCrab;
import com.mygdx.cuppycrab.sprites.Cuppy;
import com.mygdx.cuppycrab.sprites.Enemy;
import com.mygdx.cuppycrab.sprites.Frog;
import com.mygdx.cuppycrab.sprites.Octopus;
import com.mygdx.cuppycrab.tools.Controller;
import com.mygdx.cuppycrab.tools.GameContactListener;
import com.mygdx.cuppycrab.tools.Hud;
import com.mygdx.cuppycrab.tools.Variables;
import com.mygdx.cuppycrab.tools.WorldCreator;

//Экран игры

public class PlayScreen extends ApplicationAdapter implements Screen {
    private OrthographicCamera camera;
    private Viewport viewport;

    private CuppyCrab game;
    private Hud hud;

    private TiledMap map;
    private TmxMapLoader loader;
    private OrthogonalTiledMapRenderer renderer;

    private WorldCreator creator;
    private World world;
    //private Box2DDebugRenderer b2dr;
    private Cuppy player;
    private Array<Frog> frogs;
    Octopus octopus;

    private Controller controller;

    private Music main_theme;
    private Music boss_theme;
    private Music end_theme;
    private Sound victory;
    private float victoryLength;
    private Sound fail;
    private Sound temper;


    public PlayScreen(CuppyCrab game){
        this.game = game;
        world = new World(new Vector2(0, -10), true);
        camera = new OrthographicCamera();
        viewport = new FitViewport(Variables.V_WIDTH / Variables.PPM,
                Variables.V_HEIGHT / Variables.PPM, camera);

        loader = new TmxMapLoader();
        map = loader.load("level_third.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / Variables.PPM);

        player = new Cuppy(this);
        octopus = new Octopus(this, 137*16 / Variables.PPM, (68 - 46)*16 / Variables.PPM);


        hud = new Hud(game.batch, player);

        world.setContactListener(new GameContactListener());
        camera.position.set(player.body.getPosition().x, player.body.getPosition().y, 0);
        controller = new Controller(this);

        //b2dr = new Box2DDebugRenderer();
        creator = new WorldCreator(this);
        main_theme = CuppyCrab.manager.get("audio/first_level.mp3", Music.class);
        main_theme.setLooping(true);
        main_theme.setVolume(0.1f);
        main_theme.play();
        boss_theme = CuppyCrab.manager.get("audio/first_boss2.mp3", Music.class);
        boss_theme.setLooping(true);
        boss_theme.setVolume(0.1f);
        end_theme = CuppyCrab.manager.get("audio/first_end2.mp3", Music.class);
        end_theme.setLooping(true);
        end_theme.setVolume(0.1f);
        victory = CuppyCrab.manager.get("audio/guitar_recordings/victory.wav", Sound.class);

        victoryLength = 8;
        fail = CuppyCrab.manager.get("audio/guitar_recordings/fail.wav", Sound.class);
        temper = CuppyCrab.manager.get("audio/guitar_recordings/temper.wav", Sound.class);

    }

    @Override
    public void show() {

    }

    public void handleInput(float delta){
        if ((Gdx.input.isKeyPressed(Input.Keys.RIGHT) || controller.isRightPressed()) && player.body.getLinearVelocity().x <= 2) {
                player.body.applyLinearImpulse(new Vector2(0.05f, 0),
                        player.body.getWorldCenter(), true);
        }
        if ((Gdx.input.isKeyPressed(Input.Keys.LEFT) || controller.isLeftPressed()) && player.body.getLinearVelocity().x >= -2) {
            player.body.applyLinearImpulse(new Vector2(-0.05f, 0),
                    player.body.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) ||
                controller.isUpPressed() ||
        Gdx.input.justTouched() && !(controller.isRightPressed() || controller.isLeftPressed())) {
            player.body.applyLinearImpulse(new Vector2(0, 3),
                    player.body.getWorldCenter(), true);
        }
    }

    public CuppyCrab getGame() {
        return game;
    }

    public void update(float delta){
        handleInput(delta);
        world.step(1/60f, 6, 2);
        player.update(delta);
        for (Enemy enemy : creator.getFrogs()) {
            enemy.update(delta);
            if (enemy.getX()  < player.getX() + 224 / Variables.PPM)
                enemy.body.setActive(true);
        }

        if ((octopus.body.getPosition().x - player.body.getPosition().x) / Variables.PPM < 2 / Variables.PPM &&
                !octopus.isDestroyed()) {
            main_theme.stop();
            boss_theme.play();
            octopus.body.setActive(true);
        }

        if (octopus.isDestroyed()) {
            if (victoryLength == 8) {
                boss_theme.stop();
                victory.play();
            }
            else if (victoryLength <= 0)
                end_theme.play();
            victoryLength-=delta;
            //game.setScreen(new EndGameScreen(this));
            //dispose();
        }

        octopus.update(delta);
        hud.update(delta);
        camera.position.set(player.body.getPosition().x, player.body.getPosition().y, 0);
        camera.update();
        renderer.setView(camera);
    }

    public Hud getHud() {
        return hud;
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0, 1, 2, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();
        //b2dr.render(world, camera.combined);

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        player.draw(game.batch);
        for (Enemy enemy : creator.getFrogs())
            enemy.draw(game.batch);
        octopus.draw(game.batch);
        game.batch.end();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }



    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    public Viewport getViewport() {
        return viewport;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        //b2dr.dispose();
        hud.dispose();
    }

    public World getWorld(){
        return this.world;
    }

    public TiledMap getMap(){
        return this.map;
    }
    public Batch getBatch() { return game.batch; }

}
