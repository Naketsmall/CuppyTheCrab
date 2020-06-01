package com.mygdx.cuppycrab.sprites;

import com.badlogic.gdx.maps.MapObject;
import com.mygdx.cuppycrab.screens.PlayScreen;
import com.mygdx.cuppycrab.tools.Variables;

//Класс, обрабатывающий определенный вид блоков (ракушки)
// (в настоящий момент они не особо используются, но планируется использовать для спавна аптечек и левелапа персонажа)

public class Shell extends Block {
    public Shell(PlayScreen screen, MapObject object) {
        super(screen, object);
        fixture.setUserData(this);
        setCategoryFilter(Variables.SHELL_BIT);
    }
}
