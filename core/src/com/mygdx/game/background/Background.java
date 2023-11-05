package com.mygdx.game.background;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.PlayScreen;
import com.mygdx.game.objects.Car;

public class Background {
    private Array<Road> roads;
    private PlayScreen playScreen;
    private TextureRegion filledHeart;
    private TextureRegion emptyHeart;
    private BitmapFont controlsFont;
    private String[] controlMessages = { "[W] speed up", "[A] move left", "[D] move right", "[SPACE] shoot"};

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
    }

    public void update(float delta) {
        for (Road road: roads) { road.update(delta); }
    }

    public void render(SpriteBatch batch) {
        for (Road road: roads) { road.render(batch); }
        renderHearts(batch);
        renderControls(batch);
    }

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

    private void renderControls(SpriteBatch batch){
        final float x = 25;
        float y = 15;

        for (int i = controlMessages.length - 1; i >= 0; --i)
            controlsFont.draw(batch, controlMessages[i], x, y+=25);
    }

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

    public int getRoadCount() {
        return this.roads.size - 2;
    }
}
