package com.mygdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.PlayScreen;
import com.mygdx.game.helper.AudioManager;
import com.mygdx.game.helper.BodyHelper;
import com.mygdx.game.helper.ContactType;

public class PlayerCar extends Car {
    private final float acceleration = 0.3f;
    private final int upperLimit = playScreen.getGame().getScreenHeight() / 4;
    private final int lowerLimit = 40;
    private float inGameVelocity = 0;
    private static final float maxInGameVelocity = 1.5f;

    /**
     * The constructor of PlayerCar.
     * @param x x-coordinate, measured in pixels, of the PlayerCar.
     * @param y y-coordinate, measured in pixels, of the PlayerCar.
     * @param playScreen The screen to render the PlayerCar on.
     */
    public PlayerCar(float x, float y, PlayScreen playScreen) {
        super(x, y, playScreen);
        float ratio = 0.35f;
        this.texture = new Texture("tank.png");
        this.width = (int) (texture.getWidth() * ratio);
        this.height = (int) (texture.getHeight() * ratio);
        this.userData = new CarUserData(ContactType.PLAYER, 3);
        this.body = BodyHelper.createBody(x, y, width-10, height-40, false, 100, playScreen.getWorld(), userData);
    }

    /**
     * Update the position of the player car to match that of the Box2D body and also respond to user controls.
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void update(float delta) {
        super.update(delta);

        // gameover
        if (userData.getCurrentHealth() <= 0){
            playScreen.getScoreKeeper().saveScore();
            playScreen.getGame().setToGameOver();
            AudioManager.playGameOver();
        }

        // controls
        if (Gdx.input.isKeyPressed(Input.Keys.W)){
            velY += acceleration * getUpdateCount(delta);
            inGameVelocity = (float) Math.pow((0.1f + 1.1f * inGameVelocity), getUpdateCount(delta));
        } else {
            velY -= 0.01f * getUpdateCount(delta);
            inGameVelocity *= (float) Math.pow(0.9, getUpdateCount(delta));
        }

        velX = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.A))
            velX = -1;
        if (Gdx.input.isKeyPressed(Input.Keys.D))
            velX = 1;

        // shooting
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            shootBullet();
        }

        // upper limit on in game velocity
        if (inGameVelocity > maxInGameVelocity)
            inGameVelocity = maxInGameVelocity;

        // upper limits on speed
        if (velY > 1)
            velY = 1;

        if (velY < -1)
            velY = -1;

        // slowdown of car when approaching either limit
        float range = upperLimit - lowerLimit;
        float distanceUpper = upperLimit - y;
        float distanceLower = y - lowerLimit;

        if (distanceLower < range / 3 && velY < 0)
            velY -= velY * (distanceUpper)/range * 0.2f;
        else if (distanceUpper < range / 2 && velY > 0)
            velY -= velY * (distanceLower)/range;

        // lower bound of position
        if (y <= lowerLimit && velY <= 0) velY = 0;
        body.setLinearVelocity(velX * speedX, velY * speedY);
    }

    /**
     * Shoot a bullet from the top of the PlayerCar. Creates a Bullet instance and plays the corresponding sound.
     */
    public void shootBullet(){
        AudioManager.playShoot();
        playScreen.getBulletList().add(new Bullet(x + width/2, y + height + Bullet.height, playScreen));
    }

    /**
     * Returns the in-game velocity, which represents how fast the player car is going.
     * @return the in-game velocity.
     */
    public float getInGameVelocity() {
        return inGameVelocity;
    }
}
