package com.mygdx.game.helper;

import com.badlogic.gdx.physics.box2d.*;

/**
 * Helps creates Box2D bodies, which are needed to create the physics simulations underneath the game.
 * Has a single static function, createBody, which returns a Box2D according to parameters specified.
 */

public class BodyHelper {

    /**
     * Creates and returns a Box2D body according to the parameters specified.
     *
     * @param x The x coordinate, in pixels, of the body to be created.
     * @param y The y coordinate, in pixels, of the body to be created.
     * @param width The width, in pixels, of the body to be created.
     * @param height The height, in pixels, of the body to be created.
     * @param isStatic Whether the body should be static. The body cannot move if this is set to true.
     * @param density The density of the body. Used in underlying physics.
     * @param world The Box2D world to add this body to.
     * @param userdata The data attached to this body, which can be retrieved in the GameContactListener.
     * @return a Box2D body created using the parameters passed in.
     */
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
