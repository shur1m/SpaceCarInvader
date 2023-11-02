package com.mygdx.game.background;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.PlayScreen;
import com.mygdx.game.objects.Car;

public class Background {
    private Array<Road> roads;
    private PlayScreen playScreen;
    private TextureRegion filledHeart;
    private TextureRegion emptyHeart;

    public Background(int roadCount, PlayScreen playScreen){
        this.roads = new Array<>();
        this.playScreen = playScreen;
        createRoads(roadCount);

        Texture heartTexture = new Texture("heart64x64.png");
        TextureRegion[] heartSpriteSheet = TextureRegion.split(heartTexture, heartTexture.getWidth()/2, heartTexture.getHeight())[0];
        this.filledHeart = heartSpriteSheet[0];
        this.emptyHeart = heartSpriteSheet[1];
    }

    public void update(float delta) {
        for (Road road: roads) { road.update(delta); }
    }

    public void render(SpriteBatch batch) {
        for (Road road: roads) { road.render(batch); }
        renderHearts(batch);
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

        leftRoadx -= (Road.getTexture(-1).getWidth() - textureWidth)/2;
        rightRoadx += (Road.getTexture(-1).getWidth() - textureWidth)/2;

        Road leftMostRoad = new Road(leftRoadx, playScreen, -1);
        Road rightMostRoad = new Road(rightRoadx, playScreen, 1);
        roads.add(leftMostRoad);
        roads.add(rightMostRoad);
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
        return this.roads.size;
    }
}