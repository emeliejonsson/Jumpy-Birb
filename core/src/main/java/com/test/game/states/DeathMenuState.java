package com.test.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
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
        highScoreText = new BitmapFont(Gdx.files.internal("assets/fonts/squeaky_highscore.fnt"));
        highScoreText.getData().setScale(0.25f);
        highScoreText.setUseIntegerPositions(false);

        gameOver = new BitmapFont(Gdx.files.internal("assets/fonts/squeaky_gameover.fnt"));
        gameOver.getData().setScale(0.35f);
        gameOver.setUseIntegerPositions(false);
        gameOver.getRegion().getTexture().setFilter(
            Texture.TextureFilter.Linear,
            Texture.TextureFilter.Linear
        );

        playAgain = new BitmapFont(Gdx.files.internal("assets/fonts/squeaky_green_font.fnt"));
        playAgain.getData().setScale(0.25f);
        playAgain.setUseIntegerPositions(false);
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            gsm.set(new PlayState(gsm));
            dispose();
        }
    }

    @Override
    public void update(float delta) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer renderer) {
        int currentHighScore = state.getHighScore();
        GlyphLayout gameOverLayout = new GlyphLayout(gameOver, "Game Over");
        GlyphLayout playAgainLayout = new GlyphLayout(playAgain, "Press SPACE to play again");
        GlyphLayout highScoreLayout = new GlyphLayout(highScoreText, "HIGHSCORE: " + currentHighScore);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        gameOver.draw(batch, gameOverLayout, camera.position.x - gameOverLayout.width /2, camera.position.y + gameOverLayout.height / 2);
        playAgain.draw(batch, playAgainLayout, camera.position.x - playAgainLayout.width / 2, camera.position.y - 25);
        highScoreText.draw(batch, highScoreLayout, camera.position.x - highScoreLayout.width / 2, camera.position.y + 80);

        batch.end();
    }

    @Override
    public void dispose() {
        highScoreText.dispose();
        playAgain.dispose();
        gameOver.dispose();
    }
}
