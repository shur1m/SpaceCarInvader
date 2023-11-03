package com.mygdx.game.background;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.PlayScreen;
import com.mygdx.game.helper.BodyHelper;

public class Wall extends Road {
    private Texture texture;
    public Wall(float x, PlayScreen playScreen, boolean isLeftWall) {
        super(x, playScreen, isLeftWall ? -2 : 2);
        this.texture = getTexture(position);
        BodyHelper.createBody(x, 0, texture.getWidth(), texture.getHeight(), true, 10000, playScreen.getWorld(), null);
    }
}
