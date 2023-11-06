package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.PlayScreen;
import com.mygdx.game.helper.BodyHelper;
import com.mygdx.game.helper.Const;
import com.mygdx.game.helper.ContactType;

public class Bullet {

    public class BulletUserData extends ObjectUserData {
        private boolean toDelete;
        BulletUserData(){
            super(ContactType.BULLET);
            this.toDelete = false;
        }

        public void setToDelete(boolean toDelete) {
            this.toDelete = toDelete;
        }

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

    public Bullet(float x, float y, PlayScreen playScreen){
        this.x = x;
        this.y = y;
        this.playScreen = playScreen;
        this.speedY = 18;

        this.userData = new BulletUserData();
        this.body = BodyHelper.createBody(x, y, width, height, false, 0, playScreen.getWorld(), userData);
    }

    public void update(float delta) {
        x = body.getPosition().x * Const.PPM - (width/2);
        y = body.getPosition().y * Const.PPM - (height/2);

        body.setLinearVelocity(0, speedY);

        if (userData.toDelete || y > playScreen.getGame().getScreenHeight()) {
            dispose();
        }
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y, width, height);
    }

    public void dispose(){
        playScreen.getWorld().destroyBody(this.body);
        playScreen.getBulletList().removeValue(this, true);
        userData.setToDelete(true);
    }
}
