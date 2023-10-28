package com.mygdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.PlayScreen;
import com.mygdx.game.helper.BodyHelper;
import com.mygdx.game.helper.ContactType;

public class PlayerCar extends Car {
    public PlayerCar(float x, float y, PlayScreen playScreen) {
        super(x, y, playScreen);
        this.body = BodyHelper.createBody(x, y, width, height, false, 100, playScreen.getWorld(), ContactType.PLAYER);
        this.texture = new Texture("white.png");
    }

    @Override
    public void update() {
        super.update();
        velY = 0;
        velX = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.W))
            velY = 1;
        if (Gdx.input.isKeyPressed(Input.Keys.S))
            velY = -1;
        if (Gdx.input.isKeyPressed(Input.Keys.A))
            velX = -1;
        if (Gdx.input.isKeyPressed(Input.Keys.D))
            velX = 1;

        if (y <= 100 && velY <= 0) // lower bound of car position
            velY = 0;

        body.setLinearVelocity(velX * speedX, velY * speedY);
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y, width, height);
    }
}
