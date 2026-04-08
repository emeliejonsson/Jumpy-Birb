package com.test.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class DeathMenuState extends State {
    private Texture gameOver;
    private final Texture playAgain;


    public DeathMenuState(GameStateManager gsm) {
        super(gsm);
        playAgain = new Texture("Goy.png");
    }

    @Override
    protected void handleInput() {

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            gsm.set(new PlayState(gsm));
        }

    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer renderer) {

        float menuw = 130f;
        float menuh = 120f;

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(playAgain, camera.position.x - (menuw / 2f), camera.position.y - (menuh / 2f),menuh,menuw);
        batch.end();
    }

    @Override
    public void dispose() {
        gameOver.dispose();
        playAgain.dispose();
    }
}
