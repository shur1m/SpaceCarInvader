package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.PlayScreen;
import com.mygdx.game.helper.BodyHelper;
import com.mygdx.game.helper.ContactType;

public class EnemyCar extends Car {
    private float descentSpeed;

    public EnemyCar(float x, float y, PlayScreen playScreen) {
        this(x, y, 0.2f, playScreen);
    }

    public EnemyCar(float x, float y, float descentSpeed, PlayScreen playScreen) {
        super(x, y, playScreen);
        this.texture = new Texture("car.png");

        float ratio = 0.075f;
        this.width = (int)(texture.getWidth() * ratio);
        this.height = (int)(texture.getHeight() * ratio);
        this.userData = new CarUserData(ContactType.ENEMY, 3);
        this.body = BodyHelper.createBody(x, y, width, height, false, 100, playScreen.getWorld(), userData);
        this.descentSpeed = descentSpeed;
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        velY = -playScreen.getPlayerCar().getInGameVelocity() - descentSpeed;
        body.setLinearVelocity(velX * speedX, velY * speedY);

        if (y < -300 || userData.getCurrentHealth() <= 0) {
            dispose();
        }
    }

    public void dispose() {
        playScreen.getWorld().destroyBody(body);
        playScreen.getEnemyCarList().removeValue(this, true);
        texture.dispose();
    }

    public Texture getTexture() {
        return this.texture;
    }
}
