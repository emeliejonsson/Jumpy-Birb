package com.test.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.test.game.JumpyBirb;
import com.test.game.sprites.Bird;
import com.test.game.sprites.Tube;

import java.util.ArrayList;

public class PlayState extends State {
    private static final int TUBE_SPACING = 125;
    private static final int TUBE_COUNT = 4;
    private Bird bird;
    private Texture background;
    private ArrayList<Tube> tubes;
    private int score;
    private BitmapFont textFont;
    private Sound sound = Gdx.audio.newSound(Gdx.files.internal("bading.mp3"));

    protected PlayState(GameStateManager gsm) {
        super(gsm);
        bird = new Bird(50, 300);
        camera.setToOrtho(false, JumpyBirb.WIDTH / 2, JumpyBirb.HEIGHT / 2);
        background = new Texture("bg.png");
        textFont = new BitmapFont();
        textFont.getData().setScale(2);
        score = 0;
        tubes = new ArrayList<Tube>();

        for (int i = 1; i <= TUBE_COUNT; i++) {
            tubes.add(new Tube(i * (TUBE_SPACING + Tube.TUBE_WIDTH)));
        }


    }

    @Override
    protected void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.justTouched()) {
            bird.jump();
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

            if (tube.pass((bird.getBounds()))) {
                score++;
                System.out.println(score);
                long id = sound.play(1.0f);
            }


        }
        camera.update();
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(background, camera.position.x - (camera.viewportWidth / 2), 0);
        batch.draw(bird.getTexture(), bird.getPosition().x, bird.getPosition().y);

        for (Tube tube : tubes) {
            batch.draw(tube.getTopTube(), tube.getPositionTop().x, tube.getPositionTop().y);
            batch.draw(tube.getBottomTube(), tube.getPositionBottom().x, tube.getPositionBottom().y);


        }


        textFont.draw(batch, "score: " + score, camera.position.x - (camera.viewportWidth / 2), camera.position.y + (camera.viewportHeight / 2) - 20);
        batch.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        bird.dispose();
        textFont.dispose();
        for (Tube tube : tubes) {
            tube.dispose();
            System.out.println("PlayState disposed");

        }

        sound.dispose();
    }
}
