package com.mygdx.game.background;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.PlayScreen;
import com.mygdx.game.helper.BodyHelper;

/**
 * A Wall in the background of the PlayScreen. The texture rendered onto the screen is
 * shifted along the y-axis so that it looks like it is moving. For convenience, a Wall
 * is considered a road.
 */

public class Wall extends Road {
    private Texture texture;

    /**
     * The constructor for a Wall
     * @param x x-coordinate, measured in pixels, to render the road on. Positioned in the center of the road.
     * @param playScreen The screen to render this road on.
     * @param isLeftWall Set to true if this Wall is a left wall, and false if it is a right wall.
     */
    public Wall(float x, PlayScreen playScreen, boolean isLeftWall) {
        super(x, playScreen, isLeftWall ? -2 : 2);
        this.texture = getTexture(position);
        BodyHelper.createBody(x, 0, texture.getWidth(), texture.getHeight(), true, 10000, playScreen.getWorld(), null);
    }
}
