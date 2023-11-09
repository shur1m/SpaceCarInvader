package com.mygdx.game;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.background.Background;
import com.mygdx.game.background.Road;
import com.mygdx.game.helper.ScoreKeeper;
import com.mygdx.game.objects.Bullet;
import com.mygdx.game.objects.EnemyCar;
import com.mygdx.game.objects.PlayerCar;

import java.util.Arrays;

/**
 * Screen shown when playing the game. Displays sprites, background, score, and health.
 * If the player loses, the screen is set to the GameOverScreen.
 */

public class PlayScreen extends ScreenAdapter {
    private Boot game;
    private OrthographicCamera camera;
    private Box2DDebugRenderer box2DDebugRenderer;
    private SpriteBatch batch;
    private GameContactListener gameContactListener;
    private World world;
    private PlayerCar playerCar;
    private Array<EnemyCar> enemyCarList;
    private Array<Bullet> bulletList;
    private ScoreKeeper scoreKeeper;
    private Background background;

    /**
     * The constructor of the PlayScreen. Creates Box2D world and performs initial setup.
     * @param game
     */
    public PlayScreen(Boot game){
        this.game = game;
        this.camera = new OrthographicCamera();
        this.batch = new SpriteBatch();

        this.world = new World(new Vector2(0, 0), false);
        this.gameContactListener = new GameContactListener(this);
        this.world.setContactListener(this.gameContactListener);

        // background setup
        this.background = new Background(5, this);

        this.playerCar = new PlayerCar((float) game.getScreenWidth()/2, 100, this);
        this.enemyCarList = new Array<>();
        this.bulletList = new Array<>();
        this.scoreKeeper = new ScoreKeeper(this);

        this.box2DDebugRenderer = new Box2DDebugRenderer();
        camera.setToOrtho(false, game.getScreenWidth(), game.getScreenHeight());
    }

    /**
     * Updates the camera, sprites, score, and background before the screen is rendered.
     * @param delta The time in seconds since the last render.
     */
    private void update(float delta){
        world.step(1/60f, 6, 2);

        camera.update();
        playerCar.update(delta);
        scoreKeeper.update(delta);

        // add enemy cars when all are deallocated
        createEnemies(1, 5, 0.1f, 1f);

        for (EnemyCar enemyCar : enemyCarList) { enemyCar.update(delta); }
        for (Bullet bullet: bulletList) { bullet.update(delta); }
        background.update(delta);
        batch.setProjectionMatrix(camera.combined);
    }

    /**
     * Renders sprites, background, and text onto the screen 60 times per second.
     * @param delta The time in seconds since the last render.
     */
    public void render(float delta){
        update(delta);
        ScreenUtils.clear(0f, 0f, 0f,1);

        batch.begin();
        background.render(batch);
        for (EnemyCar enemyCar : enemyCarList) { enemyCar.render(batch); }
        for (Bullet bullet : bulletList){ bullet.render(batch); }
        playerCar.render(batch);
        scoreKeeper.render(batch);
        batch.end();

        // this.box2DDebugRenderer.render(world, camera.combined.scl(Const.PPM));
}

    /**
     * Performs cleanup before the object is destroyed.
     */
    @Override
    public void dispose() {
        super.dispose();
        box2DDebugRenderer.dispose();
        batch.dispose();
        world.dispose();
        scoreKeeper.dispose();
    }

    /**
     * creates enemies when all existing enemies are deallocated.
     * @param minCars The minimum enemies to create.
     * @param maxCars The maximum enemies to create.
     * @param minDescent The minimum descent speed of enemies.
     * @param maxDescent The maximum descent speed of enemies.
     */
    private void createEnemies(int minCars, int maxCars, float minDescent, float maxDescent){
        int roadWidth = Road.getTexture(0).getWidth();
        float leftMostRoadx = (float) game.getScreenWidth()/2 - (background.getRoadCount()/2) * roadWidth;

        int carCount = minCars + (int)(Math.random() * (maxCars - minCars + 1));
        int[] carPerRoad = new int[5];
        float[] roadMaxSpeed = new float[5];
        Arrays.fill(roadMaxSpeed, maxDescent);

        if (enemyCarList.size == 0){
            for (int i = 0; i < carCount; i++) {

                // place enemy on random road
                int roadIndex = (int) Math.floor(Math.random() * 5);
                float carX = leftMostRoadx + roadIndex * roadWidth;
                float carY = 100 + game.getScreenHeight() + carPerRoad[roadIndex] * 120;

                carPerRoad[roadIndex]++;
                float descentSpeed = Math.min(roadMaxSpeed[roadIndex], minDescent + (float) Math.random() * (maxDescent - minDescent));
                roadMaxSpeed[roadIndex] = Math.min(roadMaxSpeed[roadIndex], descentSpeed);

                enemyCarList.add(new EnemyCar(carX, carY, descentSpeed, this));
            }
        }


    }

    /**
     * Returns the Box2D world.
     * @return Box2D world of this game.
     */
    public World getWorld() {
        return world;
    }

    /**
     * Returns the instance of this game.
     * @return Instance of this game.
     */
    public Boot getGame() {
        return game;
    }

    /**
     * Returns the ScoreKeeper, which keeps track of, renders, and updates the score.
     * @return ScoreKeeper of this game.
     */
    public ScoreKeeper getScoreKeeper() {
        return scoreKeeper;
    }

    /**
     * Returns the current instance of the PlayerCar that the player controls.
     * @return current instance of the PlayerCar.
     */
    public PlayerCar getPlayerCar() {
        return playerCar;
    }

    /**
     * Returns a LibGDX Array of currently active EnemyCars.
     * @return LibGDX Array of currently active EnemyCars.
     */
    public Array<EnemyCar> getEnemyCarList() {
        return enemyCarList;
    }

    /**
     * Returns a LibGDX Array of currently active Bullets.
     * @return LibGDX Array of currently active Bullets.
     */
    public Array<Bullet> getBulletList() {
        return bulletList;
    }
}
