package com.mygdx.cuppycrab.tools;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

//Класс, в котором хранятся глобальные перемены  (ные)
public class Variables {
    public static final int V_WIDTH = 390;
    public static final int V_HEIGHT = 190;
    public static final float PPM = 100;

    //Bit masks
    public static final short GROUND_BIT = 1;
    public static final short DESTROYED_BIT = 2;
    public static final short CUPPY_BIT = 4;
    public static final short SHELL_BIT = 8;
    public static final short ENEMY_BIT = 16;
    public static final short ENEMY_HEAD_BIT = 32;
    public static final short ENEMY_COLLISION_OBJECT = 64;


    public static final TextureAtlas atlas = new TextureAtlas("atlass.atlas");


}
