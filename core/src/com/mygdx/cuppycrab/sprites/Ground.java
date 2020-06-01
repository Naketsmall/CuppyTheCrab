package com.mygdx.cuppycrab.sprites;

import com.badlogic.gdx.maps.MapObject;
import com.mygdx.cuppycrab.screens.PlayScreen;
import com.mygdx.cuppycrab.tools.Variables;

//Класс, обрабатывающий определенный вид блоков (Земля)
// Что интересно, лягушки, осьминоги и другие мобы с ней не коллайдир.. коллиз..
//      Не соприкасаются, в общем) А взаимодействуют они со специальными EnemyCollisionBlock

public class Ground extends Block {
    public Ground(PlayScreen screen, MapObject object) {
        super(screen, object);
        fixture.setUserData(this);
        setCategoryFilter(Variables.GROUND_BIT);

    }


}
