package com.mygdx.game.helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.mygdx.game.PlayScreen;

/**
 * Updates, renders, and saves the score of the ongoing game. Used in instances of PlayScreen.
 */

public class ScoreKeeper {
    private PlayScreen playScreen;
    private double score;
    private BitmapFont scoreFont;
    private GlyphLayout scoreGlyph;

    /**
     * Constructor of the ScoreKeeper. Performs initial setup.
     * @param playScreen The instance of PlayScreen that the ScoreKeeper keeps score for.
     */
    public ScoreKeeper(PlayScreen playScreen) {
        this.playScreen = playScreen;
        this.score = 0;
        FreeTypeFontGenerator.FreeTypeFontParameter scoreFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        scoreFontParameter.size = 30;
        scoreFontParameter.color = Color.WHITE;
        this.scoreFont = playScreen.getGame().getFontGenerator().generateFont(scoreFontParameter);
        this.scoreGlyph = new GlyphLayout(scoreFont, "0");
    }

    /**
     * Update the score based on distance traveled by the player car.
     * @param delta The time in seconds since the last render.
     */
    public void update(float delta){
        updateScore(delta * (playScreen.getPlayerCar().getInGameVelocity() * 50 + 25));
        scoreGlyph.setText(scoreFont, String.valueOf((int)score));
    }

    /**
     * Render the score onto the screen.
     * @param batch the SpriteBatch of the PlayScreen to render on.
     */
    public void render(SpriteBatch batch){
        scoreFont.draw(batch, scoreGlyph, 20, playScreen.getGame().getScreenHeight() - 20);
    }

    /**
     * Perform cleanup tasks before deallocation.
     */
    public void dispose(){
        scoreFont.dispose();
    }

    /**
     * Update the score by a value. The change is added to the score.
     * @param change Value to update the score by.
     */
    public void updateScore(float change){
        score += change;
    }

    /**
     * Saves the current score to list of scores on disk, which is used to display high scores.
     */
    public void saveScore() {
        Preferences scores = Gdx.app.getPreferences("scores");
        String scoreString = scores.getString("scoreString", "");

        if (!scoreString.isEmpty())
            scoreString = scoreString + ",";

        scores.putString("scoreString", scoreString + getScore());
        // scores.putString("scoreString", ""); //clear scores
        scores.flush();
    }

    /**
     * Returns the current score.
     * @return the current score.
     */
    public int getScore() {
        return (int) score;
    }
}
