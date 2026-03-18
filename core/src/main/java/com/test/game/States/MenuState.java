package com.test.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.test.game.JumpyBirb;

public class MenuState extends State {
    private Texture background;
    private Texture playButton;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        background = new Texture("bg.png");
        playButton = new Texture("playbtn.png");
    }

    @Override
    public void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            //some sort of delay here?
            gsm.set(new PlayState(gsm));
            dispose();
        }
    }

    @Override
    public void update(float delta) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch batch) { //draw starts bottom left-hand corner, continues right and upwards
        batch.begin();
        batch.draw(background, 0, 0, JumpyBirb.WIDTH, JumpyBirb.HEIGHT);
        batch.draw(playButton, (JumpyBirb.WIDTH / 2) - (playButton.getWidth() / 2), JumpyBirb.HEIGHT / 2);
        batch.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        playButton.dispose();
    }
}


