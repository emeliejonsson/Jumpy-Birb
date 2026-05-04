package com.test.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.test.game.JumpyBirb;

public class MenuState extends State {
    private Texture background;
    private Sound backgroundMusic = Gdx.audio.newSound(Gdx.files.internal("bg_music.wav")); //music source: https://freesound.org/people/rebrie18/

    public MenuState(GameStateManager gsm) {
        super(gsm);
        background = new Texture("bg.png");
        backgroundMusic.play();
    }

    @Override
    public void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.justTouched()) {
            gsm.set(new PlayState(gsm));
        }
    }

    @Override
    public void update(float delta) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer renderer) { //draw starts bottom left-hand corner, continues right and upwards
        batch.begin();
        batch.draw(background, 0, 0, JumpyBirb.WIDTH, JumpyBirb.HEIGHT);
        batch.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        System.out.println("MenuState disposed");
    }
}


