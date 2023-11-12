package com.mygdx.game.background;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * An explosion animation triggered when an EnemyCar is destroyed by bullets or ran over.
 */
public class Explosion {
    private Background background;
    private Animation<TextureRegion> animation;
    private Texture texture;
    private float stateTime;
    private float x, y;
    private int tileWidth, tileHeight;

    /**
     * Constructor of Explosion, creates animation at specified coordinates.
     * @param x x-coordinates, in pixels, of the Explosion.
     * @param y y-coordinates, in pixels, of the Explosion.
     * @param background Background to render this explosion in.
     */
    public Explosion(float x, float y, Background background) {
        this.x = x;
        this.y = y;
        this.background = background;
        this.texture = new Texture("explosion.png");

        tileWidth = texture.getWidth()/4;
        tileHeight = texture.getHeight()/4;
        TextureRegion[][] explosionSheet = TextureRegion.split(texture, tileWidth, tileHeight);
        TextureRegion flattenedExplosionSheet[] = new TextureRegion[explosionSheet.length * explosionSheet[0].length];

        float scale = 2f;
        tileWidth *= scale;
        tileHeight *= scale;

        int index = 0;
        for (int row = 0; row < explosionSheet.length; ++row){
            for (int col = 0; col < explosionSheet[0].length; ++col){
                flattenedExplosionSheet[index++] = explosionSheet[row][col];
            }
        }

        this.animation = new Animation<>(0.025f, flattenedExplosionSheet);
        this.stateTime = 0f;
    }

    /**
     * Update this Explosion's animation.
     * @param delta The time passed since the last render.
     */
    public void update(float delta) {
        this.stateTime += delta;
        if (animation.isAnimationFinished(stateTime)){
            background.getExplosions().removeValue(this, true);
            dispose();
        }
    }

    /**
     * Render this explosion onto the screen.
     * @param batch The SpriteBatch used to render the Explosion.
     */
    public void render(SpriteBatch batch) {
        batch.draw(animation.getKeyFrame(stateTime, false), x - (float) tileWidth/2, y + (float) tileHeight/2, tileWidth, tileHeight);
    }

    /**
     * Perform cleanup tasks before Explosion is deallocated.
     */
    public void dispose(){
        texture.dispose();
    }
}
