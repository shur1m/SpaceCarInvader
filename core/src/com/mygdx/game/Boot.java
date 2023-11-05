package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.mygdx.game.helper.AudioManager;

public class Boot extends Game {
	public static Boot INSTANCE;
	private int screenWidth, screenHeight;
	private MainMenuScreen mainMenuScreen;
	private GameOverScreen gameOverScreen;
	FreeTypeFontGenerator fontGenerator;

	public Boot() {
		INSTANCE = this;
	}

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

	@Override
	public void render() {
		update();
		super.render();
	}

	public int getScreenWidth() {
		return screenWidth;
	}

	public int getScreenHeight() {
		return screenHeight;
	}

	public void setToMainMenu() {
		AudioManager.playMainMenu();
		setScreen(mainMenuScreen);

	}

	public void setToGameOver() {
		setScreen(gameOverScreen);
		gameOverScreen.reset();
	}

	public FreeTypeFontGenerator getFontGenerator() {
		return fontGenerator;
	}

	@Override
	public void dispose() {
		super.dispose();
		fontGenerator.dispose();
	}
}
