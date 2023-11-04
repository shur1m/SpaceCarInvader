package com.mygdx.game.helper;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.mygdx.game.PlayScreen;

public class ScoreKeeper {
    PlayScreen playScreen;
    double score;
    BitmapFont scoreFont;
    GlyphLayout scoreGlyph;

    public ScoreKeeper(PlayScreen playScreen) {
        this.playScreen = playScreen;
        this.score = 0;
        FreeTypeFontGenerator.FreeTypeFontParameter scoreFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        scoreFontParameter.size = 30;
        scoreFontParameter.color = Color.WHITE;
        this.scoreFont = playScreen.getGame().getFontGenerator().generateFont(scoreFontParameter);
        this.scoreGlyph = new GlyphLayout(scoreFont, "0");
    }

    public void update(float delta){
        updateScore(delta * (playScreen.getPlayerCar().getInGameVelocity() * 50 + 25));
        scoreGlyph.setText(scoreFont, String.valueOf((int)score));
    }

    public void render(SpriteBatch batch){
        scoreFont.draw(batch, scoreGlyph, 20, playScreen.getGame().getScreenHeight() - 20);
    }

    public void dispose(){
        scoreFont.dispose();
    }

    public void updateScore(float change){
        score += change;
    }
}
