package com.test.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class DeathMenuState extends State {
    private final BitmapFont gameOver;
    private final BitmapFont playAgain;
    private final BitmapFont highScoreText;
    private final PlayState state;


    protected DeathMenuState(GameStateManager gsm, PlayState state) {
        super(gsm);
        this.state = state;
        highScoreText = new BitmapFont(Gdx.files.internal("winter_font.fnt"));
        highScoreText.getData().setScale(0.25f);
        highScoreText.setUseIntegerPositions(false);
        gameOver = new BitmapFont(Gdx.files.internal("winter_font_gameover.fnt"));
        gameOver.getData().setScale(0.6f);
        gameOver.setUseIntegerPositions(false);
        playAgain = new BitmapFont(Gdx.files.internal("winter_font.fnt"));
        playAgain.getData().setScale(0.2f);
        playAgain.setUseIntegerPositions(false);
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
        int currentHighScore = state.getHighScore();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        gameOver.draw(batch, "Game Over", camera.position.x - (camera.viewportWidth / 2) + 80, camera.position.y + (camera.viewportHeight / 3) - 40);
        playAgain.draw(batch, "Press space to play again", camera.position.x - (camera.viewportWidth / 2) + 105, camera.position.y + (camera.viewportHeight / 3) - 80);
        highScoreText.draw(batch, "Highscore: " + currentHighScore, camera.position.x - (camera.viewportWidth / 2) + 142, camera.position.y + (camera.viewportHeight / 3));
        batch.end();
    }

    @Override
    public void dispose() {

    }
}
