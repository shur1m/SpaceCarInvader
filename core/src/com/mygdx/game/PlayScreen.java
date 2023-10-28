package com.mygdx.game;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.helper.Const;
import com.mygdx.game.objects.EnemyCar;
import com.mygdx.game.objects.PlayerCar;

import java.util.ArrayList;
import java.util.List;

public class PlayScreen extends ScreenAdapter {
    Boot game;
    OrthographicCamera camera;
    Box2DDebugRenderer box2DDebugRenderer;
    SpriteBatch batch;
    GameContactListener gameContactListener;
    World world;

    PlayerCar playerCar;
    List<EnemyCar> enemyCarList;

    public PlayScreen(Boot game){
        this.game = game;
        this.camera = new OrthographicCamera();
        this.batch = new SpriteBatch();

        this.world = new World(new Vector2(0, 0), false);
        this.gameContactListener = new GameContactListener(this);
        this.world.setContactListener(this.gameContactListener);

        this.playerCar = new PlayerCar(game.getScreenWidth()/2, game.getScreenHeight()/2, this);
        this.enemyCarList = new ArrayList<>();
        this.enemyCarList.add(new EnemyCar(game.getScreenWidth()/2, game.getScreenHeight() - 100, this));

        this.box2DDebugRenderer = new Box2DDebugRenderer();
        camera.setToOrtho(false, game.getScreenWidth(), game.getScreenHeight());
    }

    private void update(){
        world.step(1/60f, 6, 2);

        camera.update();
        playerCar.update();
        for (EnemyCar enemyCar : enemyCarList) { enemyCar.update(); }
        batch.setProjectionMatrix(camera.combined);
    }

    public void render(float delta){
        update();
        ScreenUtils.clear(0.31f, 0.396f, 0.78f,1);

        batch.begin();
        playerCar.render(batch);
        for (EnemyCar enemyCar : enemyCarList) { enemyCar.render(batch); }
        batch.end();

        this.box2DDebugRenderer.render(world, camera.combined.scl(Const.PPM));
    }

    public World getWorld() {
        return world;
    }
}
