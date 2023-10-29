package com.mygdx.game.background;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.PlayScreen;
import com.mygdx.game.helper.Const;

public class Road {
    private static final Texture[] textures = {
            new Texture("leftRoad.png"),
            new Texture("road.png"),
            new Texture("rightRoad.png")
    };

    private final float x;
    private float yShift;
    private int position;
    private final PlayScreen playScreen;

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
        Texture textureUsed = textures[position+1];
        batch.draw(textureUsed, x - (float) getTexture(position).getWidth()/2, (float) playScreen.getGame().getScreenHeight()/2 - yShift - (float) getTexture(position).getHeight()/2);
    }

    public static Texture getTexture(int position) {
        return textures[position + 1];
    }
}
