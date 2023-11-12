package com.mygdx.game.background;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.PlayScreen;
import com.mygdx.game.objects.Car;

/**
 * The background of the PlayScreen. Renders the roads, walls, explosions, control instructions,
 * and player health. Roads and walls speed up and slow down depending on the
 * in-game speed of the player car.
 */
public class Background {
    private final Array<Road> roads;
    private final Array<Explosion> explosions;
    private final PlayScreen playScreen;
    private final TextureRegion filledHeart;
    private final TextureRegion emptyHeart;
    private final BitmapFont controlsFont;
    private final String[] controlMessages = { "[W] speed up", "[A] move left", "[D] move right", "[SPACE] shoot"};

    /**
     * The constructor of the Background.
     * @param roadCount Number of roads to render. Does not include walls.
     * @param playScreen The PlayScreen to render the background onto.
     */
    public Background(int roadCount, PlayScreen playScreen){
        this.roads = new Array<>();
        this.playScreen = playScreen;
        createRoads(roadCount);

        Texture heartTexture = new Texture("heart64x64.png");
        TextureRegion[] heartSpriteSheet = TextureRegion.split(heartTexture, heartTexture.getWidth()/2, heartTexture.getHeight())[0];
        this.filledHeart = heartSpriteSheet[0];
        this.emptyHeart = heartSpriteSheet[1];

        FreeTypeFontGenerator.FreeTypeFontParameter controlsFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        controlsFontParameter.color = Color.WHITE;
        controlsFontParameter.size = 20;
        this.controlsFont = playScreen.getGame().getFontGenerator().generateFont(controlsFontParameter);

        this.explosions = new Array<>();
    }

    /**
     * Updates the position of the background.
     * @param delta The time in seconds since the last render.
     */
    public void update(float delta) {
        for (Road road: roads) { road.update(delta); }
        for (Explosion explosion: explosions) { explosion.update(delta); }
    }

    /**
     * Renders the background on to the screen.
     * @param batch The SpriteBatch to render the background on.
     */
    public void render(SpriteBatch batch) {
        for (Road road: roads) { road.render(batch); }
        for (Explosion explosion: explosions) { explosion.render(batch); }
        renderHearts(batch);
        renderControls(batch);
    }

    /**
     * Creates the roads and walls in the background.
     * @param n number of roads to create. Does not include walls.
     */
    private void createRoads(int n) {
        Road middleRoad = new Road((float) playScreen.getGame().getScreenWidth()/2, playScreen);
        roads.add(middleRoad);

        int textureWidth = Road.getTexture(0).getWidth();
        int roadCount = 1;

        float leftRoadx = (float) playScreen.getGame().getScreenWidth()/2 - textureWidth;
        float rightRoadx = (float) playScreen.getGame().getScreenWidth()/2 + textureWidth;

        while (roadCount < n - 2) {
            roadCount += 2;

            roads.add(new Road(leftRoadx, playScreen));
            roads.add(new Road(rightRoadx, playScreen));

            leftRoadx -= textureWidth;
            rightRoadx += textureWidth;
        }

        leftRoadx -= (float)(Road.getTexture(-1).getWidth() - textureWidth)/2;
        rightRoadx += (float)(Road.getTexture(1).getWidth() - textureWidth)/2;
        roads.add(new Road(leftRoadx, playScreen, -1));
        roads.add(new Road(rightRoadx, playScreen, 1));

        float leftWallx = leftRoadx - (float)(Road.getTexture(-1).getWidth() + Road.getTexture(-2).getWidth())/2;
        float rightWallx = rightRoadx + (float)(Road.getTexture(-1).getWidth() + Road.getTexture(-2).getWidth())/2;
        roads.add(new Wall(leftWallx, playScreen, true));
        roads.add(new Wall(rightWallx, playScreen, false));
    }

    /**
     * Creates an explosion animation in the background in the coordinates specified.
     * @param x x-position, measured in pixels, of explosion created.
     * @param y x-position, measured in pixels, of explosion created.
     */
    public void createExplosion(float x, float y){
        //todo need to specify location of explosion
        explosions.add(new Explosion(x, y, this));
    }

    /**
     * Renders the controls onto the screen.
     */
    private void renderControls(SpriteBatch batch){
        final float x = 25;
        float y = 15;

        for (int i = controlMessages.length - 1; i >= 0; --i)
            controlsFont.draw(batch, controlMessages[i], x, y+=25);
    }

    /**
     * Renders hearts representing the player health onto the screen.
     */
    private void renderHearts(SpriteBatch batch){
        final int size = 52;

        Car.CarUserData carUserData = playScreen.getPlayerCar().getUserData();
        int currentHealth = carUserData.getCurrentHealth();
        int fullHealth = carUserData.getFullHealth();

        float heartx = playScreen.getGame().getScreenWidth() - size - 10;
        float hearty = playScreen.getGame().getScreenHeight() - size - 10;

        int heartCount = 0;
        while (heartCount < fullHealth - currentHealth){
            batch.draw(emptyHeart, heartx, hearty, size, size);
            heartx -= size;
            heartCount++;
        }

        while (heartCount < fullHealth){
            batch.draw(filledHeart, heartx, hearty, size, size);
            heartx -= size;
            heartCount++;
        }
    }

    /**
     * Returns the number of roads in the background.
     * @return an integer equal to the number of roads in the background.
     */
    public int getRoadCount() {
        return this.roads.size - 2;
    }

    /**
     * Returns Array of active explosion animations in the background.
     * @return LibGDX array of active explosions.
     */
    public Array<Explosion> getExplosions() {
        return explosions;
    }
}
