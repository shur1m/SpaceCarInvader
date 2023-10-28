package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.PlayScreen;
import com.mygdx.game.helper.BodyHelper;
import com.mygdx.game.helper.ContactType;

public class EnemyCar extends Car {
    public EnemyCar(float x, float y, PlayScreen playScreen) {
        super(x, y, playScreen);
        this.body = BodyHelper.createBody(x, y, width, height, false, 100, playScreen.getWorld(), ContactType.ENEMY);
        this.texture = new Texture("red.png");
    }
}
