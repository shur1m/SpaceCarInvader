package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.mygdx.game.helper.AudioManager;

/**
 * Represents the SpaceCarInvader game, performing basic setup and provides public methods to
 * switch between screens and get screen dimensions. The class follows a singleton pattern and
 * only has a single instance under the static variable INSTANCE.
 */
public class Boot extends Game {

	/**
	 * The singleton instance of the game.
	 */
	public static Boot INSTANCE;
	private int screenWidth, screenHeight;
	private MainMenuScreen mainMenuScreen;
	private GameOverScreen gameOverScreen;
	FreeTypeFontGenerator fontGenerator;

	/**
	 * Initializes the singleton instance of Boot.
	 */
	public Boot() {
		INSTANCE = this;
	}

	/**
	 * Called on instantiation and prepares initial setup of the game, creating screens and fonts.
	 */
	@Override
	public void create () {
		this.screenWidth = Gdx.graphics.getWidth();
		this.screenHeight = Gdx.graphics.getHeight();
		this.fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("retro_gaming.ttf"));
    
		this.mainMenuScreen = new MainMenuScreen(this);
		this.gameOverScreen = new GameOverScreen(this);
		setToMainMenu();
	}

	private void update() {
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
			Gdx.app.exit();
	}

	/**
	 * Detects if Escape button is pressed, and exits game if it is.
	 */
	@Override
	public void render() {
		update();
		super.render();
	}

	/**
	 * Returns the width of the screen in pixels.
	 * @return width of the screen in pixels
	 */
	public int getScreenWidth() {
		return screenWidth;
	}

	/**
	 * Returns the height of the screen in pixels.
	 * @return height of the screen in pixels
	 */
	public int getScreenHeight() {
		return screenHeight;
	}

	/**
	 * Sets the current screen to the main menu.
	 */
	public void setToMainMenu() {
		AudioManager.playMainMenu();
		setScreen(mainMenuScreen);

	}

	/**
	 * Sets the current screen to the game over screen.
	 */
	public void setToGameOver() {
		setScreen(gameOverScreen);
		gameOverScreen.reset();
	}

	/**
	 * Returns the font generator used to generate BitMapFonts.
	 * @return a font generator used across the game to create text.
	 */
	public FreeTypeFontGenerator getFontGenerator() {
		return fontGenerator;
	}

	/**
	 * Does any final cleanup before the game ends.
	 */
	@Override
	public void dispose() {
		super.dispose();
		fontGenerator.dispose();
		mainMenuScreen.dispose();
		gameOverScreen.dispose();
	}
}
