package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.PlayScreen;
import com.mygdx.game.helper.Const;
import com.mygdx.game.helper.ContactType;

public abstract class Car {
    public static class CarUserData extends ObjectUserData {
        final int fullHealth;
        int currentHealth;
        CarUserData(ContactType contactType, int fullHealth) {
            super(contactType);
            this.currentHealth = fullHealth;
            this.fullHealth = fullHealth;
        }

        public int getCurrentHealth(){
            return currentHealth;
        }

        public void setCurrentHealth(int currentHealth) {
            this.currentHealth = currentHealth;
        }

        public int getFullHealth() { return this.fullHealth; }
    }

    protected Body body;
    protected float x, y, speedY, speedX, velY, velX, updatePerSecond;
    protected int width, height;
    protected Texture texture;
    protected PlayScreen playScreen;
    protected CarUserData userData;

    Car(float x, float y, PlayScreen playScreen){
        this.x = x;
        this.y = y;
        this.playScreen = playScreen;

        this.width = 32;
        this.height = 64;
        this.speedY = 12;
        this.speedX = 8;
        this.updatePerSecond = 75;
    }

    public void update(float delta) {
        x = body.getPosition().x * Const.PPM - (width /2);
        y = body.getPosition().y * Const.PPM - (height /2);
    }

    public void render(SpriteBatch batch) { batch.draw(texture, x, y, width, height); }

    public float getUpdateCount(float delta) {
        return delta * updatePerSecond;
    }

    public CarUserData getUserData() {
        return userData;
    }
}
