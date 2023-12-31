package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;

import java.util.Arrays;

/**
 * Screen shown when the player car has no more health in the PlayScreen. Shows the score
 * achieved by the player in the last game as well as the top 3 scores achieved in all games.
 * If the user presses R, the screen is set back to the PlayScreen with a new game.
 */

public class GameOverScreen extends ScreenAdapter{

    private Boot game;
    private OrthographicCamera camera;
    private Box2DDebugRenderer box2DDebugRenderer;
    private SpriteBatch batch;

    // font parameters
    private float blinkTimer, blinkInterval = 1, lineHeight = 25;

    // fonts
    private BitmapFont gameOverFont, restartFont, highScoreFont, youScoredFont;
    private GlyphLayout gameOverGlyph, restartGlyph, highScoreGlyph, youScoredGlyph;
    private boolean[] textIsVisible;
    private final int scoresShown = 3;
    private Array<Integer> highScores;
    private int latestScore;

    /**
     * The constructor of the GameOverScreen. Performs initial setup.
     * @param game The instance of the game.
     */
    public GameOverScreen(Boot game){
        this.game = game;
        this.camera = new OrthographicCamera();
        this.batch = new SpriteBatch();
        this.box2DDebugRenderer = new Box2DDebugRenderer();
        camera.setToOrtho(false, game.getScreenWidth(), game.getScreenHeight());
        createText();
    }

    /**
     * Creates the BitMapFonts and GlyphLayouts needed to display text on the screen.
     */
    private void createText(){
        this.highScores = getScores();

        FreeTypeFontParameter gameOverFontParameter = new FreeTypeFontParameter();
        gameOverFontParameter.size = 80;
        gameOverFontParameter.color = Color.RED;
        this.gameOverFont = game.getFontGenerator().generateFont(gameOverFontParameter);
        this.gameOverGlyph = new GlyphLayout(gameOverFont, "GAME OVER");


        FreeTypeFontParameter restartFontParameter = new FreeTypeFontParameter();
        restartFontParameter.size = 25;
        restartFontParameter.color = new Color(255, 255, 255, 0.5f);
        this.restartFont = game.getFontGenerator().generateFont(restartFontParameter);
        this.restartGlyph = new GlyphLayout(restartFont, "[Press R to play again]");


        FreeTypeFontParameter highScoreFontParameter = new FreeTypeFontParameter();
        highScoreFontParameter.size = 40;
        highScoreFontParameter.color = Color.WHITE;
        this.highScoreFont = game.getFontGenerator().generateFont(highScoreFontParameter);
        this.highScoreGlyph = new GlyphLayout(highScoreFont, "Top 3 Scores");

        FreeTypeFontParameter youScoredFontParameter = new FreeTypeFontParameter();
        youScoredFontParameter.size = 26;
        youScoredFontParameter.color = Color.WHITE;
        this.youScoredFont = game.getFontGenerator().generateFont(youScoredFontParameter);
        this.youScoredGlyph =  new GlyphLayout(youScoredFont, "You Scored: " + latestScore);

        this.textIsVisible = new boolean[scoresShown+4];
    }

    /**
     * detects if R button is pressed, and sets screen to PlayScreen.
     */
    private void update(){
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        if (Gdx.input.isKeyJustPressed(Input.Keys.R))
            game.setScreen(new PlayScreen(game));
    }

    /**
     * Renders all text onto the screen. A short delay is set between each line of text.
     * @param delta The time in seconds since the last render.
     */
    public void render(float delta){
        update();

        ScreenUtils.clear(0, 0, 0, 1);

        batch.begin();

        //draw text
        float currentY = game.getScreenHeight() - 100;

        if (textIsVisible[0]){
            gameOverFont.draw(batch, gameOverGlyph, (float) game.getScreenWidth() /2 - gameOverGlyph.width/2, currentY);
            currentY -= gameOverGlyph.height + lineHeight;
        }

        if (textIsVisible[1]){
            blinkTimer += delta;
            if (blinkTimer < blinkInterval)
                restartFont.draw(batch, restartGlyph, (float) game.getScreenWidth() /2 - restartGlyph.width/2, currentY);
            else if (blinkTimer > blinkInterval*2)
                blinkTimer = 0;
            currentY -= restartGlyph.height + lineHeight + 50;
        }

        if (textIsVisible[2]){
            youScoredFont.draw(batch, youScoredGlyph, (float) game.getScreenWidth()/2 - youScoredGlyph.width/2, currentY);
            currentY -= youScoredGlyph.height + lineHeight + 50;
        }

        if (textIsVisible[3]){
            highScoreFont.draw(batch, highScoreGlyph, (float) game.getScreenWidth()/2 - highScoreGlyph.width/2, currentY);
            currentY -= highScoreGlyph.height + lineHeight + 20;
        }

        for (int i = 0; i < scoresShown; ++i){
            if (textIsVisible[i+4]){
                String score = (i+1 > highScores.size) ? "[empty]" : String.valueOf(highScores.get(i));
                GlyphLayout scoreGlyph = new GlyphLayout(restartFont, i+1 + ". " + score);
                restartFont.draw(batch, scoreGlyph, (float) game.getScreenWidth()/2 - scoreGlyph.width/2, currentY);
                currentY -= scoreGlyph.height + lineHeight;
            }
        }
        batch.end();
    }

    /**
     * Returns an array of high scores.
     * @return LibGDX Array of scores sorted from highest to lowest.
     */
    private Array<Integer> getScores() {
        String[] scoreStrings = Gdx.app.getPreferences("scores")
                .getString("scoreString")
                .split(",");

        Array<Integer> scores = new Array<>();
        for (String s : scoreStrings){
            if (!s.isEmpty())
                scores.add(Integer.parseInt(s));
        }

        if (!scores.isEmpty())
            this.latestScore = scores.get(scores.size-1);

        scores.sort();
        scores.reverse();
        return scores;
    }

    /**
     * Resets data so that the transition is repeated and scores are updated.
     */
    public void reset() {
        createText();
        this.blinkTimer = 0;
        Arrays.fill(textIsVisible, false);
        Timer.Task showText = new Timer.Task() {
            private int scoreIndex = 0;
            @Override
            public void run() {
                textIsVisible[scoreIndex++] = true;
            }
        };
        Timer.instance().scheduleTask(showText, 0.3f, 0.3f, textIsVisible.length-1);
    }
}
