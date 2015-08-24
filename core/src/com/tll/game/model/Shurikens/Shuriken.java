package com.tll.game.model.Shurikens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.Random;

/**
 * Created by abdullahtellioglu on 23/08/15.
 */
public class Shuriken extends Image {
    private boolean isThrowShurikenCreated = false;
    public static final float sizeX = 25, sizeY = 25;



    public Shuriken(Texture texture) {
        super(texture);
    }

    public Action throwShuriken(com.tll.game.model.Character character, float throwDuration) {
        this.setOrigin(getWidth() / 2, getHeight() / 2);
        Action move = Actions.moveTo(character.getCenterX(), character.getCenterY(), throwDuration);
        Action fadeOut = Actions.fadeOut(2);
        Action sequence = Actions.sequence(move, fadeOut);
        ParallelAction actions = Actions.parallel(sequence, Actions.repeat(Integer.MAX_VALUE, Actions.rotateBy((new Random().nextInt(3) + 1) * 45f, 0.5f)));
        return actions;
    }

    @Override
    public void addAction(Action action) {
        super.addAction(action);
        if (isThrowShurikenCreated) {
            this.remove();
        } else {
            isThrowShurikenCreated = true;
        }
    }


}
