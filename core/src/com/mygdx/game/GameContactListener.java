package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.helper.AudioManager;
import com.mygdx.game.helper.ContactType;
import com.mygdx.game.objects.Bullet;
import com.mygdx.game.objects.Car;
import com.mygdx.game.objects.ObjectUserData;

/**
 * Detects if two objects in-game collide. This facilitates mechanics like bullets
 * inflicting damage on enemy cars, and decreasing player car health when it collides with
 * enemy cars. Other than beginContact, all other public methods are empty.
 */
public class GameContactListener implements ContactListener {

    private PlayScreen playScreen;

    /**
     * The constructor for GameContactListener.
     * @param playScreen The Screen that owns this ContactListener.
     */
    public GameContactListener(PlayScreen playScreen) {
        this.playScreen = playScreen;
    }

    /**
     * Called when two objects collide. Used to decrease enemy health when enemy cars collide with bullets,
     * and to decrease player health when the player collides with enemy cars.
     *
     * @param contact the Contact object provided by libGDX that describes how and what two objects collided.
     */
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
                AudioManager.playEnemyDied();
            }
            enemy.setCurrentHealth(reducedHealth);
        }

        if (userDataA.getType() == ContactType.PLAYER && userDataB.getType() == ContactType.ENEMY ||
            userDataB.getType() == ContactType.ENEMY && userDataA.getType() == ContactType.PLAYER){

            Car.CarUserData enemy = (Car.CarUserData) (userDataA.getType() == ContactType.ENEMY ? userDataA : userDataB);
            Car.CarUserData player = (Car.CarUserData) (userDataA.getType() == ContactType.PLAYER ? userDataA : userDataB);

            enemy.setCurrentHealth(0);
            player.setCurrentHealth(player.getCurrentHealth()-1);
            AudioManager.playTakeDamage();
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
