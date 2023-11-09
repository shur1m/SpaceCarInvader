package com.mygdx.game.background;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.PlayScreen;
import com.mygdx.game.helper.Const;

/**
 * A Road in the background of the PlayScreen. The texture rendered onto the screen is
 * shifted along the y-axis so that it looks like it is moving.
 */

public class Road {

    /**
     * List of possible Road textures, used to change the appearance of a Road.
     */
    protected static final Texture[] textures = {
            new Texture("leftWall.png"),
            new Texture("leftRoad.png"),
            new Texture("road.png"),
            new Texture("rightRoad.png"),
            new Texture("rightWall.png"),
    };

    /**
     * The x-coordinate of this Road in pixels, positioned in the centered of the Road.
     */
    protected final float x;

    /**
     * The number of pixels the Road is shifted along the y-axis, which creates the illusion
     * that the road is moving.
     */
    protected float yShift;

    /**
     * The position of the road, which determines which texture is used.
     */
    protected int position;

    /**
     * The playScreen that this road is rendered on.
     */
    protected final PlayScreen playScreen;

    /**
     * The constructor of a center-positioned Road.
     * @param x x-coordinate, measured in pixels, to render the road on. Positioned in the center of the road.
     * @param playScreen The screen to render this road on.
     */
    public Road(float x, PlayScreen playScreen) {
        this(x, playScreen, 0);
    }

    /**
     * The constructor of a Road.
     * @param x x-coordinate, measured in pixels, to render the road on. Positioned in the center of the road.
     * @param playScreen The screen to render this road on.
     * @param position The position of this road, which is a value between -2 and 2.
     */
    public Road(float x, PlayScreen playScreen, int position) {
        this.playScreen = playScreen;
        this.x = x;
        this.position = position;
    }

    /**
     * Update the road to create the illusion that it is moving.
     * @param delta The time in seconds since the last render.
     */
    public void update(float delta){
        yShift = (yShift + (200 + playScreen.getPlayerCar().getInGameVelocity() * 200)*delta) % 51;
    }

    /**
     * Render the road onto the screen.
     * @param batch The SpriteBatch to render with.
     */
    public void render(SpriteBatch batch){
        Texture textureUsed = getTexture(position);
        batch.draw(textureUsed, x - (float) textureUsed.getWidth()/2, (float) playScreen.getGame().getScreenHeight()/2 - yShift - (float) textureUsed.getHeight()/2);
    }

    /**
     * Returns the texture with the specified position.
     * @param position a value between -2 and 2 that represents the position of the road.
     * @return the texture that a road in the position specified would use.
     */
    public static Texture getTexture(int position) {
        int index = textures.length / 2 + position;
        return textures[index];
    }
}
