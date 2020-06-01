package com.mygdx.cuppycrab.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.cuppycrab.screens.PlayScreen;
import com.mygdx.cuppycrab.tools.Variables;

public class Cuppy extends Sprite {
    private enum State {FALLING, JUMPING, STANDING, RUNNING}
    private State currentState;
    private State previousState;
    private World world;
    public Body body;

    private Animation<TextureRegion> standing;
    private Animation<TextureRegion> running;
    private TextureRegion jumping;

    private float timer;
    private boolean runningRight;
    private Integer hp;

    public Integer getHp() {
        return hp;
    }

    public Cuppy(PlayScreen screen){
        this.world = screen.getWorld();
        currentState = State.STANDING;
        previousState = State.STANDING;
        timer = 0;
        runningRight = true;
        this.hp = 100;

        Array<TextureRegion> array = new Array<>();
        for (int i = 0; i < 3; i++){
            array.add(new TextureRegion(Variables.atlas.findRegion("crab2"),
                    i*16, 0, 16, 16));
        }

        standing = new Animation<>(0.4f, array);
        array.clear();

        for (int i = 0; i < 3; i++){
            array.add(new TextureRegion(Variables.atlas.findRegion("crab_side2"),
                    i*16, 0, 16, 16));
        }

        running = new Animation<>(0.1f, array);

        jumping = new TextureRegion(Variables.atlas.findRegion("crab_jump"), 0, 0, 16, 16);

        defineCuppy();
        setBounds(0, 0, 16 / Variables.PPM, 16 / Variables.PPM);
        setRegion(standing.getKeyFrame(timer));
    }

    public void defineCuppy(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(64 / Variables.PPM, (68 - 32)*16 / Variables.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5 / Variables.PPM);

        fixtureDef.filter.categoryBits = Variables.CUPPY_BIT;
        fixtureDef.filter.maskBits = Variables.GROUND_BIT |
                        Variables.ENEMY_BIT |
                        Variables.SHELL_BIT |
                        Variables.ENEMY_HEAD_BIT;

        fixtureDef.shape = shape;
        body.createFixture(fixtureDef).setUserData(this);

    }

    public  State getState(){
        if (body.getLinearVelocity().y > 0 || body.getLinearVelocity().y < 0 && previousState == State.JUMPING)
            return State.JUMPING;
        else if (body.getLinearVelocity().y < 0)
            return State.FALLING;
        else if (body.getLinearVelocity().x != 0)
            return State.RUNNING;
        else
            return State.STANDING;
    }

    public TextureRegion getFrame(float delta){

        currentState = getState();

        TextureRegion region;
        switch (currentState){
            case JUMPING:
                region = jumping;
                break;
            case RUNNING:
                region = running.getKeyFrame(timer, true);
                break;
            case FALLING:
            case STANDING:
            default:
                region = standing.getKeyFrame(timer, true);
                break;
        }

        if ((body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()){
            region.flip(true, false);
            runningRight = false;
        }
        else if ((body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()){
            region.flip(true, false);
            runningRight = true;
        }

        timer = currentState == previousState ?  timer + delta : 0;
        previousState = currentState;
        return region;

    }

    public void update(float delta){
        setPosition(body.getPosition().x - getWidth()/2,
                body.getPosition().y - getHeight()/2);
        setRegion(getFrame(delta));
    }

    public void onHit(){
        this.hp -= 10;
    }
}
