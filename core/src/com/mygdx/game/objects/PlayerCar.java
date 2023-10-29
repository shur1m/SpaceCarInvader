package com.mygdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.PlayScreen;
import com.mygdx.game.helper.BodyHelper;
import com.mygdx.game.helper.ContactType;

public class PlayerCar extends Car {
    private final float acceleration = 0.3f;
    private final int upperLimit = playScreen.getGame().getScreenHeight() / 4;
    private final int lowerLimit = 40;
    private float inGameVelocity = 0;
    private static final float maxInGameVelocity = 1.5f;

    public PlayerCar(float x, float y, PlayScreen playScreen) {
        super(x, y, playScreen);
        this.texture = new Texture("white.png");
        this.userData = new ObjectUserData(ContactType.PLAYER);
        this.body = BodyHelper.createBody(x, y, width, height, false, 100, playScreen.getWorld(), userData);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        velX = 0;

        // controls
        if (Gdx.input.isKeyPressed(Input.Keys.W)){
            velY += acceleration * getUpdateCount(delta);
            inGameVelocity = (float) Math.pow((0.1f + 1.1f * inGameVelocity), getUpdateCount(delta));
        } else {
            velY -= 0.01f * getUpdateCount(delta);
            inGameVelocity *= (float) Math.pow(0.9, getUpdateCount(delta));
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A))
            velX = -1;
        if (Gdx.input.isKeyPressed(Input.Keys.D))
            velX = 1;

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

    public void shootBullet(){
        playScreen.getBulletList().add(new Bullet(x + width/2, y + height + Bullet.height, playScreen));
    }

    public float getInGameVelocity() {
        return inGameVelocity;
    }
}
