package com.test.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.test.game.JumpyBirb;
import com.test.game.sprites.Bird;
import com.test.game.sprites.Tube;

import java.util.ArrayList;

public class PlayState extends State {
    private static final int TUBE_SPACING = 125;
    private static final int TUBE_COUNT = 4;

    private Bird bird;
    private Texture background;

    private boolean debugMode;

    private ArrayList<Tube> tubes;

    protected PlayState(GameStateManager gsm) {
        super(gsm);
        bird = new Bird(50, 300);
        camera.setToOrtho(false, JumpyBirb.WIDTH / 2, JumpyBirb.HEIGHT / 2);
        background = new Texture("bg.png");
        debugMode = false;
        tubes = new ArrayList<>();

        for (int i = 1; i <= TUBE_COUNT; i++) {
            tubes.add(new Tube(i * (TUBE_SPACING + Tube.TUBE_WIDTH)));
        }
    }

    @Override
    protected void handleInput() {
        // Jump
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            bird.jump();
        }

        // Turn on debug mode
        if (Gdx.input.isKeyJustPressed(Input.Keys.H)) {
            debugMode = !debugMode;
        }
    }

    @Override
    public void update(float delta) {
        handleInput();
        bird.update(delta);
        camera.position.x = bird.getPosition().x + 80; //offset bird

        for (Tube tube : tubes) {
            if (camera.position.x - (camera.viewportWidth / 2) > tube.getPositionTop().x + tube.getTopTube().getWidth()) {
                tube.reposition(tube.getPositionTop().x + ((Tube.TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT));
            }

            if (tube.collides(bird.getBounds())) {
                gsm.set(new PlayState(gsm));
            }
        }
        camera.update();
    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer renderer) {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(background, camera.position.x - (camera.viewportWidth / 2), 0);
        batch.draw(bird.getTexture(), bird.getPosition().x, bird.getPosition().y);


        for (Tube tube : tubes) {
            batch.draw(tube.getTopTube(), tube.getPositionTop().x, tube.getPositionTop().y);
            batch.draw(tube.getBottomTube(), tube.getPositionBottom().x, tube.getPositionBottom().y);
        }

        if (debugMode) {
            renderHitbox(renderer);
        }
        batch.end();
    }

    public void renderHitbox(ShapeRenderer renderer) {
        renderer.setProjectionMatrix(camera.combined);
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(0, 1, 1, 1);
        renderer.circle(bird.getBounds().x, bird.getBounds().y, bird.getBounds().radius);
        renderer.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        bird.dispose();
        for (Tube tube : tubes) {
            tube.dispose();
            System.out.println("PlayState disposed");
        }
    }
}
