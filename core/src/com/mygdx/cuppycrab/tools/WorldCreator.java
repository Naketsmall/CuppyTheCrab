package com.mygdx.cuppycrab.tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.cuppycrab.screens.PlayScreen;
import com.mygdx.cuppycrab.sprites.EnemyCollisionBlock;
import com.mygdx.cuppycrab.sprites.Frog;
import com.mygdx.cuppycrab.sprites.Ground;
import com.mygdx.cuppycrab.sprites.Shell;

//Класс, используемый для создания мира и определения объектов
public class WorldCreator {
    private Array<Frog> frogs;

    public Array<Frog> getFrogs() {
        return frogs;
    }

    public WorldCreator(PlayScreen screen){
        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        BodyDef bodyDef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();
        Body body;



        for (MapObject object: map.getLayers()
                .get(4).getObjects().
                        getByType(RectangleMapObject.class)){

            new Ground(screen, object);
        }

        for (MapObject object: map.getLayers()
                .get(5).getObjects().
                        getByType(RectangleMapObject.class)){

            new Shell(screen, object);
        }

        frogs = new Array<>();
        for (MapObject object : map.getLayers()
                .get(6).getObjects()
                    .getByType(RectangleMapObject.class)) {
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
            frogs.add(new Frog(screen, rectangle.getX() / Variables.PPM,
                    rectangle.getY() / Variables.PPM));

        }

        for (MapObject object: map.getLayers()
                .get(7).getObjects().
                        getByType(RectangleMapObject.class)){

            new EnemyCollisionBlock(screen, object);
        }
    }
}
