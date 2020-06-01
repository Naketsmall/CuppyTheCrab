package com.mygdx.cuppycrab.sprites;

import com.badlogic.gdx.maps.MapObject;
import com.mygdx.cuppycrab.screens.PlayScreen;
import com.mygdx.cuppycrab.tools.Variables;

//Блок, с которым взаимодействуют противники Каппи

public class EnemyCollisionBlock extends Block {

    public EnemyCollisionBlock(PlayScreen screen, MapObject object) {
        super(screen, object);
        fixture.setUserData(this);
        setCategoryFilter(Variables.ENEMY_COLLISION_OBJECT);
    }

}
