package com.mygdx.cuppycrab.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.mygdx.cuppycrab.screens.PlayScreen;
import com.mygdx.cuppycrab.tools.Variables;

//Массовый противник нашего персонажа. Возможно, в будующем будут прыгать

public class Frog extends Enemy{
    private float timer;
    private Animation<TextureRegion> walking;
    private TextureRegion jumping;
    private boolean toDestroy;
    private boolean destroyed;

    public Frog(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        Array<TextureRegion> array = new Array<>();
        for (int i = 0; i < 3; i++) {
            array.add(new TextureRegion(Variables.atlas.findRegion("frog_front"),
                    i*16, 0, 16, 16));
        }
        jumping = new TextureRegion(Variables.atlas.findRegion("frog_jump"));
        walking = new Animation<>(0.4f, array);
        timer = 0;
        setBounds(getX(), getY(), 16 / Variables.PPM, 16 / Variables.PPM);
        toDestroy = false;
        destroyed = false;
    }

    @Override
    protected void defineEnemy() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(getX(), getY());
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5 / Variables.PPM);
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = Variables.ENEMY_BIT;
        fixtureDef.filter.maskBits =
                        Variables.ENEMY_COLLISION_OBJECT |
                        Variables.ENEMY_BIT |
                        Variables.SHELL_BIT |
                        Variables.CUPPY_BIT;

        body.createFixture(fixtureDef).setUserData(this);


        PolygonShape head = new PolygonShape();
        Vector2[] headVectors = new Vector2[4];
        headVectors[0] = new Vector2(-3, 8).scl(1/Variables.PPM);
        headVectors[1] = new Vector2(3, 8).scl(1/Variables.PPM);
        headVectors[2] = new Vector2(-3, 3).scl(1/Variables.PPM);
        headVectors[3] = new Vector2(3, 3).scl(1/Variables.PPM);
        head.set(headVectors);

        fixtureDef.shape = head;
        fixtureDef.restitution = 0.5f;
        fixtureDef.filter.categoryBits = Variables.ENEMY_HEAD_BIT;
        body.createFixture(fixtureDef).setUserData(this);
    }

    @Override
    public void hitOnHead() {
        toDestroy = true;
    }

    @Override
    public void update(float delta) {
        timer += delta;
        if (toDestroy && !destroyed) {
            world.destroyBody(body);
            destroyed = true;
            setRegion(new TextureRegion(Variables.atlas.findRegion("frog_killed")));
            timer = 0;
        }
        else if (!destroyed) {
            body.setLinearVelocity(velocity);
            setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
            setRegion(walking.getKeyFrame(timer, true));
        }
    }

    @Override
    public void draw(Batch batch){
        if (!destroyed || timer < 1){
            super.draw(batch);
        }
    }
}