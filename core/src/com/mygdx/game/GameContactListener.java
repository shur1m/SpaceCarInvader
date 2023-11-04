package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.helper.ContactType;
import com.mygdx.game.objects.Bullet;
import com.mygdx.game.objects.Car;
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

            Bullet.BulletUserData bullet = (Bullet.BulletUserData) (userDataA.getType() == ContactType.BULLET ? userDataA : userDataB);
            Car.CarUserData enemy = (Car.CarUserData) (userDataA.getType() == ContactType.ENEMY ? userDataA: userDataB);

            bullet.setToDelete(true);
            int reducedHealth = enemy.getCurrentHealth() - 1;
            if (reducedHealth == 0){
                playScreen.getScoreKeeper().updateScore(300);
            }
            enemy.setCurrentHealth(reducedHealth);
        }

        if (userDataA.getType() == ContactType.PLAYER && userDataB.getType() == ContactType.ENEMY ||
            userDataB.getType() == ContactType.ENEMY && userDataA.getType() == ContactType.PLAYER){

            Car.CarUserData enemy = (Car.CarUserData) (userDataA.getType() == ContactType.ENEMY ? userDataA : userDataB);
            Car.CarUserData player = (Car.CarUserData) (userDataA.getType() == ContactType.PLAYER ? userDataA : userDataB);

            enemy.setCurrentHealth(0);
            player.setCurrentHealth(player.getCurrentHealth()-1);
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
