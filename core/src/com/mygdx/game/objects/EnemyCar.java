package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.PlayScreen;
import com.mygdx.game.helper.BodyHelper;
import com.mygdx.game.helper.ContactType;

public class EnemyCar extends Car {
    public EnemyCar(float x, float y, PlayScreen playScreen) {
        super(x, y, playScreen);
        this.texture = new Texture("red.png");
        this.userData = new ObjectUserData(ContactType.ENEMY);
        this.body = BodyHelper.createBody(x, y, width, height, false, 100, playScreen.getWorld(), userData);
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        velY = -playScreen.getPlayerCar().getInGameVelocity();
        body.setLinearVelocity(velX * speedX, velY * speedY);
    }
}
