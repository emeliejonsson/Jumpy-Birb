package com.test.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class DeathMenuState extends State {
    private Texture gameOver;
    private final Texture playAgain;
    private BitmapFont highScoreText;
    private PlayState state;


    protected DeathMenuState(GameStateManager gsm, PlayState state) {
        super(gsm);
        this.state = state;
        highScoreText = new BitmapFont();
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

        float menuWidth = 130f;
        float menuHeight = 120f;
        int currentHighScore = state.getHighScore();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(playAgain, camera.position.x - (menuWidth / 2f), camera.position.y - (menuHeight / 2f), menuWidth, menuHeight);
        highScoreText.draw(batch, "top: " + currentHighScore, camera.position.x - (camera.viewportWidth / 2), camera.position.y + (camera.viewportHeight / 3));
        batch.end();
    }

    @Override
    public void dispose() {
        gameOver.dispose();
        playAgain.dispose();
    }
}
