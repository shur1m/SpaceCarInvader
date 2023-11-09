package com.mygdx.game.helper;

/**
 * Defines the types of objects in the game. Used in the GameContactListener to determine
 * what objects have collided.
 */
public enum ContactType {
    /**
     * Contact type of the in-game player car.
     */
    PLAYER,

    /**
     * Contact type of in-game enemy car.
     */
    ENEMY,

    /**
     * Contact type of in-game bullet.
     */
    BULLET;
}
