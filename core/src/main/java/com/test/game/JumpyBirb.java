package com.test.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.test.game.states.GameStateManager;
import com.test.game.states.MenuState;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class JumpyBirb extends ApplicationAdapter {
    public static final int WIDTH = 480;
    public static final int HEIGHT = 800;

    public static final String TITLE = "Jumpy Birb";
    private GameStateManager gsm;
    private SpriteBatch batch;
<<<<<<< HEAD
    private Texture image;
    private ShapeRenderer shapeRenderer;
=======
>>>>>>> 54b4de8e890f886d1db4924e711146451a5269a4

    @Override
    public void create() {
        batch = new SpriteBatch();
        gsm = new GameStateManager();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        gsm.push(new MenuState(gsm));
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gsm.update(Gdx.graphics.getDeltaTime());
        gsm.render(batch, shapeRenderer);
    }

    @Override
    public void dispose() {
        batch.dispose();
<<<<<<< HEAD
        shapeRenderer.dispose();
=======
>>>>>>> 54b4de8e890f886d1db4924e711146451a5269a4
    }
}
