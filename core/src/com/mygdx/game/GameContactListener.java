package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.helper.ContactType;
import com.mygdx.game.objects.Bullet;
import com.mygdx.game.objects.ObjectUserData;

public class GameContactListener implements ContactListener {

    private PlayScreen playScreen;

    public GameContactListener(PlayScreen playScreen) {
        this.playScreen = playScreen;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        if (a == null || b == null) return;
        if (a.getUserData() == null || b.getUserData() == null) return;

        ObjectUserData userDataA = (ObjectUserData) a.getUserData();
        ObjectUserData userDataB = (ObjectUserData) b.getUserData();

        if (userDataA.getType() == ContactType.BULLET && userDataB.getType() == ContactType.ENEMY ||
                userDataA.getType() == ContactType.ENEMY && userDataB.getType() == ContactType.BULLET ){

            Bullet.BulletUserData bullet = userDataA.getType() == ContactType.BULLET ? (Bullet.BulletUserData) userDataA : (Bullet.BulletUserData) userDataB;
            ObjectUserData enemy = userDataA.getType() == ContactType.ENEMY ? userDataA: userDataB;

            bullet.setToDelete(true);
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
