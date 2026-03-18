package com.test.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.test.game.JumpyBirb;
import com.test.game.sprites.Bird;

public class CountdownState extends State {
    private Texture background;

    private Texture one;
    private Texture two;
    private Texture three;
    private Texture four;
    private Texture five;

    private float count = 6f;
    private boolean started = false;

    public CountdownState(GameStateManager gsm) {
        super(gsm);
        one = new Texture("one_resize.png");
        two = new Texture("two_resize.png");
        three = new Texture("three_resize.png");
        four = new Texture("four_resize.png");
        five = new Texture("five_resize.png");

        background = new Texture("bg.png");
        camera.setToOrtho(false, JumpyBirb.WIDTH / 2, JumpyBirb.HEIGHT / 2);
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            startGame();
        }
    }

    @Override
    public void update(float delta) {
        if (!started) {
            count -= delta;

            if (count <= 0) {
                startGame();
            }
        }
        handleInput();
    }

    private void startGame() {
        gsm.set(new PlayState(gsm));
        dispose();
    }

    @Override
    public void render(SpriteBatch batch) { //render a countdown?
        batch.begin();
        batch.draw(background, 0, 0, JumpyBirb.WIDTH, JumpyBirb.HEIGHT);
        int c = (int) count;
        switch (c) {
            case 5:
                batch.draw(five, (JumpyBirb.WIDTH / 2) - (five.getWidth() / 2), JumpyBirb.HEIGHT / 2);
                break;
            case 4:
                batch.draw(four, (JumpyBirb.WIDTH / 2) - (five.getWidth() / 2), JumpyBirb.HEIGHT / 2);
                break;
            case 3:
                batch.draw(three, (JumpyBirb.WIDTH / 2) - (five.getWidth() / 2), JumpyBirb.HEIGHT / 2);
                break;
            case 2:
                batch.draw(two, (JumpyBirb.WIDTH / 2) - (five.getWidth() / 2), JumpyBirb.HEIGHT / 2);
                break;
            case 1:
                batch.draw(one, (JumpyBirb.WIDTH / 2) - (five.getWidth() / 2), JumpyBirb.HEIGHT / 2);
            default:
                break;
        }
        batch.end();
    }

    @Override
    public void dispose() {
        background.dispose();
    }
}
