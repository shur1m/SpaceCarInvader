package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.PlayScreen;
import com.mygdx.game.helper.Const;

public abstract class Car {
    protected Body body;
    protected float x, y, speedY, speedX, velY, velX;
    protected int width, height, score;
    protected Texture texture;
    protected PlayScreen playScreen;

    Car(float x, float y, PlayScreen playScreen){
        this.x = x;
        this.y = y;
        this.playScreen = playScreen;

        // TODO set up speed, width, height, texture, and body
    }

    public void update() {
        x = body.getPosition().x * Const.PPM - (width/2);
        y = body.getPosition().y * Const.PPM - (height/2);
    }

    public void render(SpriteBatch batch) { batch.draw(texture, x, y, width, height); }
}
