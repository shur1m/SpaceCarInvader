package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.PlayScreen;
import com.mygdx.game.helper.AudioManager;
import com.mygdx.game.helper.BodyHelper;
import com.mygdx.game.helper.ContactType;

/**
 * Represents an enemy car in the game. Has three different colors, and the
 * speed of the cars can be changed.
 */

public class EnemyCar extends Car {
    private static Texture[] textures = {
            new Texture("redCar.png"),
            new Texture("greenCar.png"),
            new Texture("blueCar.png"),
    };
    private TextureRegion[] healthSpriteSheet;
    private float descentSpeed;

    /**
     * Constructor of EnemyCar
     * @param x x-position, in pixels, of the enemy car.
     * @param y y-position, in pixels, of the enemy car.
     * @param descentSpeed Speed of the car. The higher the value, the faster the car moves towards the bottom of the screen.
     * @param playScreen Screen to render the enemy car on.
     */
    public EnemyCar(float x, float y, float descentSpeed, PlayScreen playScreen) {
        super(x, y, playScreen);
        this.texture = this.textures[(int)(Math.random() * 3)];

        float ratio = 0.075f;
        this.width = (int)(texture.getWidth() * ratio);
        this.height = (int)(texture.getHeight() * ratio);
        this.userData = new CarUserData(ContactType.ENEMY, 3);
        this.body = BodyHelper.createBody(x, y, width, height, false, 100, playScreen.getWorld(), userData);
        this.descentSpeed = descentSpeed;

        Texture healthBarTexture = new Texture("healthbar.png");
        this.healthSpriteSheet = TextureRegion.split(healthBarTexture, healthBarTexture.getWidth()/3, healthBarTexture.getHeight())[0];
    }

    /**
     * Update the position of the car to match the position of the Box2D body.
     * Deletes the car if the car has no more health or has exited the viewport.
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void update(float delta) {
        super.update(delta);

        velY = -playScreen.getPlayerCar().getInGameVelocity() - descentSpeed;
        body.setLinearVelocity(velX * speedX, velY * speedY);

        if (y < -300 || userData.getCurrentHealth() <= 0) {
            dispose();
        }
    }

    /**
     * Render the EnemyCar onto the screen. Renders a health bar if the car is not at full health.
     * @param batch The SpriteBatch to render on.
     */
    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
        if (userData.getCurrentHealth() < userData.getFullHealth()){
            int healthDifference = userData.getFullHealth() - userData.getCurrentHealth();
            batch.draw(healthSpriteSheet[healthDifference],
                    x + (float) width/2 - (float) healthSpriteSheet[healthDifference].getRegionWidth()/2, y + height + 15);
        }
    }

    /**
     * Perform cleanup tasks before deallocation.
     */
    public void dispose() {
        playScreen.getWorld().destroyBody(body);
        playScreen.getEnemyCarList().removeValue(this, true);
    }
}
