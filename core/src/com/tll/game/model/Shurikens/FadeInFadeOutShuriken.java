package com.tll.game.model.Shurikens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

/**
 * Created by abdullahtellioglu on 24/08/15.
 */
public class FadeInFadeOutShuriken extends Shuriken {
    public FadeInFadeOutShuriken(Texture texture) {
        super(texture);
        Action fadeInAction = Actions.fadeIn(1);
        Action fadeOutAction = Actions.fadeOut(1);
        Action waitAction = Actions.delay(0.5f);
        addAction(Actions.forever(Actions.sequence(fadeInAction,waitAction,fadeOutAction)));
    }
}
