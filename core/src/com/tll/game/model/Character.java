package com.tll.game.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by abdullahtellioglu on 23/08/15.
 */
public class Character extends Image {
    private static final float movementSpeed = 0.5f;
    private static final float movementLong = 30;
    private float stateTime = 0;
    private float centerX;
    private float centerY;
    private Texture[] leftImages, rightImages, bottomImages, topImages;
    private Texture stopImage;
    private Texture baseTexture;
    private TextureRegion[] leftRegion, rightRegion, bottomRegion, topRegion;
    private TextureRegion stopRegion;
    private Animation activeAnimation;
    private Action movementAction;
    public static final int CHARACTER_WIDTH = 30,CHARACTER_HEIGHT = 60;

    public float getCenterX() {
        return this.getX()+CHARACTER_WIDTH/2;
    }

    public float getCenterY() {
        return this.getY()+CHARACTER_HEIGHT/2;
    }

    public Character(Texture texture) {
        super(texture);
        baseTexture = texture;
        initTextures();
    }

    public void moveRight() {
        activeAnimation = new Animation(0.20f, rightRegion);
        movementAction = Actions.moveTo(this.getX()+movementLong,this.getY(),movementSpeed);
        addAction(movementAction);
    }

    public void moveTop() {
        activeAnimation = new Animation(0.20f,topRegion);
        movementAction = Actions.moveTo(this.getX(),this.getY()+movementLong,movementSpeed);
        addAction(movementAction);
    }

    public void moveBottom() {
        activeAnimation = new Animation(0.20f,bottomRegion);
        movementAction = Actions.moveTo(this.getX(),this.getY()-movementLong,movementSpeed);
        addAction(movementAction);
    }
    public void moveLeft() {
        activeAnimation = new Animation(0.20f,leftRegion);
        movementAction = Actions.moveTo(this.getX()-movementLong,this.getY(),movementSpeed);
        addAction(movementAction);

    }
    public void stop(){
        removeAction(movementAction);
        activeAnimation=new Animation(1,stopRegion);


    }



    private void initTextures() {
        stopRegion = new TextureRegion(baseTexture);

        leftImages = new Texture[3];
        for (int i = 0; i < 3; i++) {
            leftImages[i] = new Texture(String.format("character/left/%d.png", i + 1));
        }
        rightImages = new Texture[3];
        for (int i = 0; i < 3; i++) {
            rightImages[i] = new Texture(String.format("character/right/%d.png", i + 1));
        }
        bottomImages = new Texture[3];
        for (int i = 0; i < 3; i++) {
            bottomImages[i] = new Texture(String.format("character/bottom/%d.png", i + 1));
        }
        topImages = new Texture[3];
        for (int i = 0; i < 3; i++) {
            topImages[i] = new Texture(String.format("character/top/%d.png", i + 1));
        }

        leftRegion = new TextureRegion[4];
        leftRegion[0] = new TextureRegion(leftImages[0]);
        leftRegion[1] = new TextureRegion(leftImages[1]);
        leftRegion[2] = new TextureRegion(leftImages[0]);
        leftRegion[3] = new TextureRegion(leftImages[2]);

        rightRegion = new TextureRegion[4];
        rightRegion[0] = new TextureRegion(rightImages[0]);
        rightRegion[1] = new TextureRegion(rightImages[1]);
        rightRegion[2] = new TextureRegion(rightImages[0]);
        rightRegion[3] = new TextureRegion(rightImages[2]);

        bottomRegion = new TextureRegion[4];
        bottomRegion[0] = new TextureRegion(bottomImages[0]);
        bottomRegion[1] = new TextureRegion(bottomImages[1]);
        bottomRegion[2] = new TextureRegion(bottomImages[0]);
        bottomRegion[3] = new TextureRegion(bottomImages[2]);

        topRegion = new TextureRegion[4];
        topRegion[0] = new TextureRegion(topImages[0]);
        topRegion[1] = new TextureRegion(topImages[1]);
        topRegion[2] = new TextureRegion(topImages[0]);
        topRegion[3] = new TextureRegion(topImages[2]);
    }

    public void act(float delta) {
        if(activeAnimation!=null){
            ((TextureRegionDrawable) getDrawable()).setRegion(activeAnimation.getKeyFrame(stateTime += delta, true));
        }
        super.act(delta);
    }

    public void reset() {
        stateTime = 0;
    }
}
