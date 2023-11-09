package com.mygdx.game.helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

/**
 * Manages and plays all audio used in the game. All functions are static.
 */

public class AudioManager {
    private static final Sound shoot = Gdx.audio.newSound(Gdx.files.internal("audio/shoot.wav"));
    private static final Sound enemyDied = Gdx.audio.newSound(Gdx.files.internal("audio/enemyDied.wav"));
    private static final Sound gameOver = Gdx.audio.newSound(Gdx.files.internal("audio/gameover.wav"));
    private static final Sound takeDamage = Gdx.audio.newSound(Gdx.files.internal("audio/takeDamage.wav"));
    private static final Sound mainMenu = Gdx.audio.newSound(Gdx.files.internal("audio/mainMenu.wav"));

    /**
     * Plays the sound effect used when the PlayerCar shoots bullets.
     */
    public static void playShoot() {
        shoot.play();
    }

    /**
     * Plays the sound effect used when an enemy loses all health.
     */
    public static void playEnemyDied() {
        enemyDied.play();
    }

    /**
     * Plays the sound effect used when the player loses the game.
     */
    public static void playGameOver() {
        gameOver.play();
    }

    /**
     * Plays the sound effect used when the player takes damage.
     */
    public static void playTakeDamage(){
        takeDamage.play();
    }

    /**
     * Loops the music played during the main menu.
     */
    public static void playMainMenu() {
        mainMenu.setLooping(mainMenu.play(), true);
    }

    /**
     * Stops the looping music played during the main menu.
     */
    public static void stopMainMenu() {
        mainMenu.stop();
    }
}
