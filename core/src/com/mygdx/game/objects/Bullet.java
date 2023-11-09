package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.PlayScreen;
import com.mygdx.game.helper.BodyHelper;
import com.mygdx.game.helper.Const;
import com.mygdx.game.helper.ContactType;

/**
 * Represents a Bullet shot by the PlayerCar. Updates the position of the bullet and renders it.
 */

public class Bullet {

    /**
     * The user data of a bullet. Holds the information of a Bullet so that
     * it can be accessible in the GameContactListener.
     */
    public static class BulletUserData extends ObjectUserData {
        private boolean toDelete;

        /**
         * The constructor of BulletUserData.
         */
        BulletUserData(){
            super(ContactType.BULLET);
            this.toDelete = false;
        }

        /**
         * Sets the Bullet should be deleted the next time it is rendered.
         * @param toDelete Set to true if the Bullet should be deleted.
         */
        public void setToDelete(boolean toDelete) {
            this.toDelete = toDelete;
        }

        /**
         * Returns true if the Bullet should be deleted, and false if it should not.
         * @return
         */
        public boolean isToDelete() {
            return toDelete;
        }
    }

    private Body body;
    private float x, y, speedY;
    public static final int width = 6, height = 20;
    private Texture texture = new Texture("yellow.png");
    private PlayScreen playScreen;
    private BulletUserData userData;

    /**
     * The constructor of a Bullet.
     * @param x x-position, in pixels, of the Bullet to be created.
     * @param y y-position, in pixels, of the Bullet to be created.
     * @param playScreen The screen to render the Bullet on.
     */
    public Bullet(float x, float y, PlayScreen playScreen){
        this.x = x;
        this.y = y;
        this.playScreen = playScreen;
        this.speedY = 18;

        this.userData = new BulletUserData();
        this.body = BodyHelper.createBody(x, y, width, height, false, 0, playScreen.getWorld(), userData);
    }

    /**
     * Update the Bullet's position, moving it up along the y-axis at a constant speed.
     * @param delta The time in seconds since the last render.
     */
    public void update(float delta) {
        x = body.getPosition().x * Const.PPM - (width/2);
        y = body.getPosition().y * Const.PPM - (height/2);

        body.setLinearVelocity(0, speedY);

        if (userData.toDelete || y > playScreen.getGame().getScreenHeight()) {
            dispose();
        }
    }

    /**
     * Render the Bullet onto the screen.
     * @param batch The SpriteBatch to render the Bullet with.
     */
    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y, width, height);
    }

    /**
     * Perform cleanup tasks before deallocating the Bullet.
     */
    public void dispose(){
        playScreen.getWorld().destroyBody(this.body);
        playScreen.getBulletList().removeValue(this, true);
        userData.setToDelete(true);
    }
}
