package com.mygdx.game;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.background.Road;
import com.mygdx.game.helper.Const;
import com.mygdx.game.objects.Bullet;
import com.mygdx.game.objects.EnemyCar;
import com.mygdx.game.objects.PlayerCar;

public class PlayScreen extends ScreenAdapter {
    Boot game;
    OrthographicCamera camera;
    Box2DDebugRenderer box2DDebugRenderer;
    SpriteBatch batch;
    GameContactListener gameContactListener;
    World world;

    PlayerCar playerCar;
    Array<EnemyCar> enemyCarList;
    Array<Bullet> bulletList;
    Array<Road> roads;

    public PlayScreen(Boot game){
        this.game = game;
        this.camera = new OrthographicCamera();
        this.batch = new SpriteBatch();

        this.world = new World(new Vector2(0, 0), false);
        this.gameContactListener = new GameContactListener(this);
        this.world.setContactListener(this.gameContactListener);

        this.playerCar = new PlayerCar((float) game.getScreenWidth()/2, 100, this);
        this.enemyCarList = new Array<>();
        this.enemyCarList.add(new EnemyCar((float) game.getScreenWidth()/2, game.getScreenHeight() - 100, this));

        this.bulletList = new Array<>();

        // setup background
        this.roads = new Array<>();
        createRoads(5);

        this.box2DDebugRenderer = new Box2DDebugRenderer();
        camera.setToOrtho(false, game.getScreenWidth(), game.getScreenHeight());
    }

    private void update(float delta){
        world.step(1/60f, 6, 2);

        camera.update();
        playerCar.update(delta);
        for (EnemyCar enemyCar : enemyCarList) { enemyCar.update(delta); }
        for (Bullet bullet: bulletList) { bullet.update(delta); }
        for (Road road: roads) { road.update(delta); }
        batch.setProjectionMatrix(camera.combined);
    }

    public void render(float delta){
        update(delta);
        ScreenUtils.clear(0.31f, 0.396f, 0.78f,1);

        batch.begin();
        playerCar.render(batch);
        for (Road road: roads) { road.render(batch); }
        for (EnemyCar enemyCar : enemyCarList) { enemyCar.render(batch); }
        for (Bullet bullet : bulletList){ bullet.render(batch); }
        batch.end();

        this.box2DDebugRenderer.render(world, camera.combined.scl(Const.PPM));
    }

    public void createRoads(int n) {
        Road middleRoad = new Road((float) game.getScreenWidth()/2, this);
        roads.add(middleRoad);

        int textureWidth = Road.getTexture(0).getWidth();
        int roadCount = 1;

        float leftRoadx = (float) game.getScreenWidth()/2 - textureWidth;
        float rightRoadx = (float) game.getScreenWidth()/2 + textureWidth;

        while (roadCount < n - 2) {
            roadCount += 2;

            roads.add(new Road(leftRoadx, this));
            roads.add(new Road(rightRoadx, this));

            leftRoadx -= textureWidth;
            rightRoadx += textureWidth;
        }

        leftRoadx -= (Road.getTexture(-1).getWidth() - textureWidth)/2;
        rightRoadx += (Road.getTexture(-1).getWidth() - textureWidth)/2;

        Road leftMostRoad = new Road(leftRoadx, this, -1);
        Road rightMostRoad = new Road(rightRoadx, this, 1);
        roads.add(leftMostRoad);
        roads.add(rightMostRoad);

    }

    public World getWorld() {
        return world;
    }

    public Boot getGame() {
        return game;
    }

    public PlayerCar getPlayerCar() {
        return playerCar;
    }

    public Array<Bullet> getBulletList() {
        return bulletList;
    }
}
