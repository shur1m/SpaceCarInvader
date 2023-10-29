package com.mygdx.game.helper;

import com.badlogic.gdx.physics.box2d.*;

public class BodyHelper {
    public static Body createBody(float x,
                                  float y,
                                  float width,
                                  float height,
                                  boolean isStatic,
                                  float density,
                                  World world,
                                  Object userdata
    ){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = isStatic ? BodyDef.BodyType.StaticBody : BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x/Const.PPM, y/Const.PPM);
        bodyDef.fixedRotation = true;
        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width/2/Const.PPM, height/2/Const.PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = density;
        body.createFixture(fixtureDef).setUserData(userdata);

        shape.dispose();
        return body;
    }
}
