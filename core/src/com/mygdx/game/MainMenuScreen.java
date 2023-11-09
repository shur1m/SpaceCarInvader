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
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.helper.AudioManager;

/**
 * Screen shown on boot. Displays title of game and instructions to start game. Sets
 * the screen to the PlayScreen if the Space button is pressed.
 */

public class MainMenuScreen extends ScreenAdapter {
    private final Boot game;
    private final OrthographicCamera camera;
    private final Box2DDebugRenderer box2DDebugRenderer;
    private final SpriteBatch batch;

    // font parameters
    private float blinkTimer, blinkInterval = 1, lineHeight = 25;

    // fonts
    private BitmapFont titleFont, pressToPlayFont;
    private GlyphLayout titleGlyph, pressToPlayGlyph;

    /**
     * The constructor of MainMenuScreen.
     * @param game The instance of the game.
     */
    public MainMenuScreen(Boot game){
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
        FreeTypeFontParameter titleFontParameter = new FreeTypeFontParameter();
        titleFontParameter.size = 30;
        titleFontParameter.color = Color.WHITE;
        this.titleFont = game.getFontGenerator().generateFont(titleFontParameter);
        this.titleGlyph = new GlyphLayout(titleFont, "Space Car Invaders");

        FreeTypeFontParameter pressToPlayFontParameter = new FreeTypeFontParameter();
        pressToPlayFontParameter.size = 25;
        pressToPlayFontParameter.color = new Color(255, 255, 255, 0.5f);
        this.pressToPlayFont = game.getFontGenerator().generateFont(pressToPlayFontParameter);
        this.pressToPlayGlyph = new GlyphLayout(pressToPlayFont, "[Press SPACE to play]");
    }

    /**
     * Detects if the Space button is pressed. If it is, sets the screen to the PlayScreen.
     */
    private void update(){
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            AudioManager.stopMainMenu();
            game.setScreen(new PlayScreen(game));
        }
    }

    /**
     * Renders text onto the screen. Blinks "Press space to play" button.
     * @param delta The time in seconds since the last render.
     */
    public void render(float delta){
        update();
        ScreenUtils.clear(0, 0, 0, 1);
        blinkTimer += delta;

        batch.begin();

        //draw text
        float totalHeight = titleGlyph.height + pressToPlayGlyph.height + lineHeight*1;
        titleFont.draw(batch, titleGlyph, (float) game.getScreenWidth() /2 - titleGlyph.width/2, (float) game.getScreenHeight()/2 + totalHeight/2);

        blinkTimer += delta;
        if (blinkTimer < blinkInterval)
            pressToPlayFont.draw(batch, pressToPlayGlyph, (float) game.getScreenWidth() /2 - pressToPlayGlyph.width/2, (float) game.getScreenHeight()/2 + totalHeight/2 - titleGlyph.height - lineHeight);
        else if (blinkTimer > blinkInterval*2)
            blinkTimer = 0;

        batch.end();
    }
}
