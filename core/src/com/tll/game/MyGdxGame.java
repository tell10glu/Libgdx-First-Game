package com.tll.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tll.game.model.Character;
import com.tll.game.model.Shurikens.FadeInFadeOutShuriken;
import com.tll.game.model.Shurikens.Shuriken;

import java.util.Random;

import javax.xml.soap.Text;

public class MyGdxGame extends ApplicationAdapter {

    private static final int VIRTUAL_WIDTH = 800, VIRTUAL_HEIGHT = 480;
    private Texture backgroundTexture;
    private SpriteBatch backgroundSpriteBatch;
    private long lastUpdateShurikenTime = 0;
    private float minimumDurationForShurikens = 1;
    private float maximumDurationForShurikens = 10;
    private final float durationDecreasePerLevel = 0.5f;
    private final int shurikenDecreasePerLevel = 500;

    private int level = 1;
    private int shurikenTossLevel = 1;
    private long shurikenDelayTime = 1000;
    private int SHURIKEN_COUNT_PER_LEVEL = 10;

    private String shurikenImageText = "ninja_shuriken.png";

    private Random generalRandom;
    private Stage stage;
    private Viewport viewport;
    private Camera camera;
    private Character character;

    private Touchpad touchpad;
    private Touchpad.TouchpadStyle touchpadStyle;
    private Skin touchpadSkin;
    private Drawable touchBackground;
    private Drawable touchKnob;

    private void init() {
        camera = new OrthographicCamera(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
        stage = new Stage(viewport);
        stage.getCamera().position.set(VIRTUAL_WIDTH / 2, VIRTUAL_HEIGHT / 2, 0);
        character = new Character(new Texture("character/bottom/1.png"));
        character.setPosition(VIRTUAL_WIDTH / 2 - Character.CHARACTER_WIDTH / 2, VIRTUAL_HEIGHT / 2 - Character.CHARACTER_HEIGHT / 2);
        character.setSize(Character.CHARACTER_WIDTH, Character.CHARACTER_HEIGHT);
        touchpadSkin = new Skin();
        //Set background image
        touchpadSkin.add("touchBackground", new Texture("touchpad/touchBackground.png"));
        //Set knob image
        touchpadSkin.add("touchKnob", new Texture("touchpad/touchKnob.png"));
        //Create TouchPad Style
        touchpadStyle = new Touchpad.TouchpadStyle();
        //Create Drawable's from TouchPad skin
        touchBackground = touchpadSkin.getDrawable("touchBackground");
        touchKnob = touchpadSkin.getDrawable("touchKnob");
        //Apply the Drawables to the TouchPad Style
        touchpadStyle.background = touchBackground;
        touchpadStyle.knob = touchKnob;
        //Create new TouchPad with the created style
        touchpad = new Touchpad(10, touchpadStyle);
        //setBounds(x,y,width,height)
        touchpad.setBounds(15, 15, 200, 200);

        touchpad.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (touchpad.getKnobPercentX() == 0.0 && touchpad.getKnobPercentY() == 0.0) {
                    character.stop();
                    return;
                }
                if (Math.abs(touchpad.getKnobPercentY()) < 0.45) {
                    if (touchpad.getKnobPercentX() > 0) {
                        character.moveRight();
                    } else {
                        character.moveLeft();
                    }
                } else {
                    if (touchpad.getKnobPercentY() > 0) {
                        character.moveTop();
                    } else {
                        character.moveBottom();
                    }
                }
                Gdx.app.log("touchpad x,y", touchpad.getKnobPercentX() + "," + touchpad.getKnobPercentY());
            }
        });
        backgroundSpriteBatch = new SpriteBatch();
        backgroundTexture = new Texture("background.png");

        Gdx.input.setInputProcessor(stage);
        stage.addActor(character);
        stage.addActor(touchpad);
    }

    @Override
    public void create() {
        init();
        generalRandom = new Random();
        camera.update();
    }

    @Override
    public void render() {
        if (shurikenTossLevel * SHURIKEN_COUNT_PER_LEVEL > 20) {
            shurikenTossLevel = 1;

            if (maximumDurationForShurikens > 2) {
                maximumDurationForShurikens -= durationDecreasePerLevel;
            }
            if (shurikenDelayTime > 1000)
                shurikenDelayTime -= shurikenDecreasePerLevel;
        }
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        backgroundSpriteBatch.begin();
        backgroundSpriteBatch.draw(backgroundTexture,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        backgroundSpriteBatch.end();
        if (System.currentTimeMillis() - lastUpdateShurikenTime > shurikenDelayTime) {
            lastUpdateShurikenTime = System.currentTimeMillis();
            createRandomShurikens();
        }
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        camera.update();
    }

    private void createRandomShurikens() {
        int randomShurikenCount = (generalRandom.nextInt() % (shurikenTossLevel * SHURIKEN_COUNT_PER_LEVEL)) + 1;
        for (int i = 0; i < randomShurikenCount; i++) {
            Shuriken shuriken = null;
            switch (generalRandom.nextInt(2)) {
                case 0:
                    shuriken = new FadeInFadeOutShuriken(new Texture(shurikenImageText));
                    break;
                case 1:
                    shuriken = new Shuriken(new Texture(shurikenImageText));
                    break;
            }
            switch (new Random().nextInt(2)) {
                case 0:
                    shuriken.setPosition(0, generalRandom.nextInt(VIRTUAL_HEIGHT));
                    break;
                case 1:
                    shuriken.setPosition(VIRTUAL_WIDTH, generalRandom.nextInt(VIRTUAL_HEIGHT));
                    break;
            }
            shuriken.setSize(Shuriken.sizeX, Shuriken.sizeY);
            stage.addActor(shuriken);
            shuriken.addAction(shuriken.throwShuriken(character, generalRandom.nextFloat() % maximumDurationForShurikens + minimumDurationForShurikens));
        }
        level++;
        shurikenTossLevel++;
    }
}
