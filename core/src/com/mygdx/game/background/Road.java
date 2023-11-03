package com.mygdx.game.background;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.PlayScreen;
import com.mygdx.game.helper.Const;

public class Road {
    protected static final Texture[] textures = {
            new Texture("leftWall.png"),
            new Texture("leftRoad.png"),
            new Texture("road.png"),
            new Texture("rightRoad.png"),
            new Texture("rightWall.png"),
    };

    protected final float x;
    protected float yShift;
    protected int position;
    protected final PlayScreen playScreen;

    public Road(float x, PlayScreen playScreen) {
        this(x, playScreen, 0);
    }

    public Road(float x, PlayScreen playScreen, int position) {
        this.playScreen = playScreen;
        this.x = x;
        this.position = position;
    }

    public void update(float delta){
        yShift = (yShift + (200 + playScreen.getPlayerCar().getInGameVelocity() * 200)*delta) % 51;
    }

    public void render(SpriteBatch batch){
        Texture textureUsed = getTexture(position);
        batch.draw(textureUsed, x - (float) textureUsed.getWidth()/2, (float) playScreen.getGame().getScreenHeight()/2 - yShift - (float) textureUsed.getHeight()/2);
    }

    public static Texture getTexture(int position) {
        int index = textures.length / 2 + position;
        return textures[index];
    }
}
