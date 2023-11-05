package com.mygdx.game.helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class AudioManager {
    public static AudioManager INSTANCE;
    private static Sound shoot = Gdx.audio.newSound(Gdx.files.internal("audio/shoot.wav"));
    private static Sound enemyDied = Gdx.audio.newSound(Gdx.files.internal("audio/enemyDied.wav"));
    private static Sound gameOver = Gdx.audio.newSound(Gdx.files.internal("audio/gameover.wav"));
    private static Sound takeDamage = Gdx.audio.newSound(Gdx.files.internal("audio/takeDamage.wav"));
    private static Sound mainMenu = Gdx.audio.newSound(Gdx.files.internal("audio/mainMenu.wav"));

    public static void playShoot() {
        shoot.play();
    }
    public static void playEnemyDied() {
        enemyDied.play();
    }
    public static void playGameOver() {
        gameOver.play();
    }

    public static void playTakeDamage(){
        takeDamage.play();
    }

    public static void playMainMenu() {
        mainMenu.setLooping(mainMenu.play(), true);
    }

    public static void stopMainMenu() {
        mainMenu.stop();
    }
}
