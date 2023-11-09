package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.PlayScreen;
import com.mygdx.game.helper.Const;
import com.mygdx.game.helper.ContactType;

/**
 * Represents a Car in the game.
 */

public abstract class Car {

    /**
     * The user data of a Car. Holds the information of a Car so that
     * it can be accessible in the GameContactListener.
     */
    public static class CarUserData extends ObjectUserData {
        private final int fullHealth;
        private int currentHealth;

        /**
         * The constructor of CarUserData.
         * @param contactType ContactType of this Car.
         * @param fullHealth Amount of health this car should start with.
         */
        CarUserData(ContactType contactType, int fullHealth) {
            super(contactType);
            this.currentHealth = fullHealth;
            this.fullHealth = fullHealth;
        }

        /**
         * Returns the current amount of health the car has.
         * @return Integer value equal to the current amount of health the car has.
         */
        public int getCurrentHealth(){
            return currentHealth;
        }

        /**
         * Sets the current health of the car.
         * @param currentHealth The value to set the current health to.
         */
        public void setCurrentHealth(int currentHealth) {
            this.currentHealth = currentHealth;
        }

        /**
         * Returns the amount of health the car would have if it was at full health.
         * @return Integer value equal to the full health.
         */
        public int getFullHealth() { return this.fullHealth; }
    }

    /**
     * Box2D body of the car.
     */
    protected Body body;

    /**
     * The x-position, measured in pixels, of this car.
     */
    protected float x;

    /**
     * The y-position, measured in pixels, of this car.
     */
    protected float y;

    /**
     * Speed of the car along the y-axis.
     */
    protected float speedY;

    /**
     * Speed of the car along the x-axis.
     */
    protected float speedX;

    /**
     * Velocity of the car along the y-axis.
     */
    protected float velY;

    /**
     * Velocity of the car along the x-axis.
     */
    protected float velX;
    protected float updatePerSecond;

    /**
     * Width of the car in pixels.
     */
    protected int width;

    /**
     * Height of the car in pixels.
     */
    protected int height;

    /**
     * Texture rendered onto the screen.
     */
    protected Texture texture;

    /**
     * Screen to render the Car on.
     */
    protected PlayScreen playScreen;

    /**
     * The user data of the car, accessible to the GameContactListener.
     */
    protected CarUserData userData;

    /**
     * Constructor of Car.
     * @param x The x-position, measured in pixels, of this car.
     * @param y The y-position, measured in pixels, of this car.
     * @param playScreen Screen to render the Car on.
     */
    Car(float x, float y, PlayScreen playScreen){
        this.x = x;
        this.y = y;
        this.playScreen = playScreen;

        this.speedY = 12;
        this.speedX = 8;
        this.updatePerSecond = 75;
    }

    /**
     * Update the position of the car to match the position of the Box2D body.
     * @param delta The time in seconds since the last render.
     */
    public void update(float delta) {
        x = body.getPosition().x * Const.PPM - (width /2);
        y = body.getPosition().y * Const.PPM - (height /2);
    }

    /**
     * Render the Car onto the screen.
     * @param batch The SpriteBatch to render on.
     */
    public void render(SpriteBatch batch) { batch.draw(texture, x, y, width, height); }

    /**
     * Returns the number of updates that should be made given the time passed in seconds.
     * @param delta The time in seconds since the last render.
     * @return The number of updates that should be made.
     */
    public float getUpdateCount(float delta) {
        return delta * updatePerSecond;
    }

    /**
     * Returns the user data of the Car.
     * @return The user data of the Car.
     */
    public CarUserData getUserData() {
        return userData;
    }
}
