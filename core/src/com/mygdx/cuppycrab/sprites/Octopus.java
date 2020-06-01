package com.mygdx.cuppycrab.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.mygdx.cuppycrab.screens.PlayScreen;
import com.mygdx.cuppycrab.tools.Variables;

//Главный босс первого уровня, после победы над которым уровень будет переключаться на следующий
//          (пока не реализованно, но технической сложности никакой не составляет)
//Их (как уровней, так и боссов) планируется ввести несколько, лишь вопрос времени

public class Octopus extends Enemy {
    private int hp;
    private int attackedTimer;
    private float timer;
    private Animation<TextureRegion> existing; //4real
    private Animation<TextureRegion> slowlyNicelyDying;
    private TextureRegion attacked; //White men don't know, how it is to be attacked.. But purple octopuses do))

    public boolean isDestroyed() {
        return destroyed;
    }

    private boolean toDestroy;
    private boolean destroyed;
    private boolean oneMoreDestroyed; //На часах 2:52, мне лень придумывать корректные имена для переменных, простите)
    private float flashingTimer;

    public Octopus(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        Array<TextureRegion> array = new Array<>();
        for (int i = 0; i < 4; i++) {
            array.add(new TextureRegion(Variables.atlas.findRegion("octopus"),
                    i*64, 0, 64, 48));
        }
        existing = new Animation<>(0.4f, array);
        array.clear();
        for (int i = 0; i < 4; i++) {
            array.add(new TextureRegion(Variables.atlas.findRegion("octopus_flashing"),
                    i*64, 0, 64, 48));
        }
        slowlyNicelyDying = new Animation<>(0.3f, array);
        attacked = new TextureRegion(Variables.atlas.findRegion("octopus_damaged"));
        timer = 0;
        flashingTimer = 5;
        hp = 300;
        setBounds(getX(), getY(), 128 / Variables.PPM, 64 / Variables.PPM);
        toDestroy = false;
        destroyed = false;
        oneMoreDestroyed = false;

    }

    @Override
    protected void defineEnemy() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(getX(), getY());
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        Vector2[] bodyVectors = new Vector2[4];
        bodyVectors[0] = new Vector2(-30, 4).scl(2/Variables.PPM);
        bodyVectors[1] = new Vector2(30, 4).scl(2/Variables.PPM);
        bodyVectors[2] = new Vector2(-32, -14).scl(2/Variables.PPM);
        bodyVectors[3] = new Vector2(32, -14).scl(2/Variables.PPM);
        shape.set(bodyVectors);
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = Variables.ENEMY_BIT;
        fixtureDef.filter.maskBits =
                        Variables.ENEMY_BIT |
                        Variables.ENEMY_COLLISION_OBJECT |
                        Variables.CUPPY_BIT |
                        Variables.SHELL_BIT;
        body.createFixture(fixtureDef).setUserData(this);

        PolygonShape head = new PolygonShape();
        Vector2[] headVectors = new Vector2[4];
        headVectors[0] = new Vector2(-11, 17).scl(2/Variables.PPM);
        headVectors[1] = new Vector2(11, 17).scl(2/Variables.PPM);
        headVectors[2] = new Vector2(-13, 6).scl(2/Variables.PPM);
        headVectors[3] = new Vector2(13, 6).scl(2/Variables.PPM);
        head.set(headVectors);

        fixtureDef.shape = head;
        fixtureDef.restitution = 0.5f;
        fixtureDef.filter.categoryBits = Variables.ENEMY_HEAD_BIT;
        body.createFixture(fixtureDef).setUserData(this);
    }

    @Override
    public void hitOnHead() {
        if (hp > 0) {
            if (hp == 200)
                this.toRage();
            if (hp == 100)
                this.toRage();
            hp -= 50;
            attackedTimer = 5;
            setRegion(attacked);
        }
        else
            toDestroy = true;
    }

    @Override
    public void update(float delta) {
        timer+=delta;
        attackedTimer-=delta;
        if (toDestroy && !destroyed) {
            flashingTimer-=delta;
            if (!oneMoreDestroyed) {
                world.destroyBody(body);
                oneMoreDestroyed = true;
            }
            if (flashingTimer > 0) {
                setRegion(slowlyNicelyDying.getKeyFrame(timer, true));
            }
            else {
                destroyed = true;
                setRegion(new TextureRegion(Variables.atlas.findRegion("octopus_killed")));
                timer = 0;
            }
        }

        else if (!destroyed){
            body.setLinearVelocity(velocity);
            setPosition(body.getPosition().x - getWidth()/2,
                    body.getPosition().y - getHeight()/2);
            if (attackedTimer > 0)
                setRegion(attacked);
            else
                setRegion(existing.getKeyFrame(timer, true));
        }

    }

    private void toRage(){
        this.velocity = velocity.scl(2);
    }
}
