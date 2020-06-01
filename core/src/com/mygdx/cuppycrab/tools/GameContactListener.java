package com.mygdx.cuppycrab.tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.cuppycrab.sprites.Cuppy;
import com.mygdx.cuppycrab.sprites.Enemy;

//Класс, отвечающий за "физическое" взаимодействие между объектами

public class GameContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        int cDef = fixtureA.getFilterData().categoryBits | fixtureB.getFilterData().categoryBits;


        switch (cDef){
            case Variables.ENEMY_HEAD_BIT | Variables.CUPPY_BIT:
                if (fixtureA.getFilterData().categoryBits == Variables.ENEMY_HEAD_BIT) {
                    ((Enemy) fixtureA.getUserData()).hitOnHead();
                }
                else
                    ((Enemy)fixtureB.getUserData()).hitOnHead();
                break;
            case Variables.ENEMY_BIT | Variables.SHELL_BIT:
                if (fixtureA.getFilterData().categoryBits == Variables.ENEMY_BIT)
                    ((Enemy)fixtureA.getUserData()).reverseVelocity(true, false);
                else
                    ((Enemy)fixtureB.getUserData()).reverseVelocity(true, false);
                break;
            case Variables.CUPPY_BIT | Variables.ENEMY_BIT:
                if (fixtureA.getFilterData().categoryBits == Variables.CUPPY_BIT) {
                    ((Cuppy) fixtureA.getUserData()).onHit();
                    ((Enemy) fixtureB.getUserData()).reverseVelocity(true, false);
                }
                else {
                    ((Cuppy) fixtureB.getUserData()).onHit();
                    ((Enemy) fixtureA.getUserData()).reverseVelocity(true, false);
                }
                break;
            case Variables.ENEMY_BIT | Variables.ENEMY_BIT:
                ((Enemy)fixtureA.getUserData()).reverseVelocity(true, false);
                ((Enemy)fixtureB.getUserData()).reverseVelocity(true, false);
                break;
            case Variables.ENEMY_BIT | Variables.ENEMY_COLLISION_OBJECT:
                if (fixtureA.getFilterData().categoryBits == Variables.ENEMY_BIT)
                    ((Enemy)fixtureA.getUserData()).reverseVelocity(true, false);
                else
                    ((Enemy)fixtureB.getUserData()).reverseVelocity(true, false);

        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
