package com.test.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.test.game.JumpyBirb;
import com.test.game.sprites.Bird;
import com.test.game.sprites.Tube;

import java.util.ArrayList;

public class PlayState extends State {
    private static final int TUBE_SPACING = 200;
    private static final int TUBE_COUNT = 4;
    private final Bird bird;
    private final Texture background;
    private boolean debugMode;
    private final ArrayList<Tube> tubes;
    private final Sound passOver = Gdx.audio.newSound(Gdx.files.internal("assets/sounds/bird_squeak.mp3")); // sound source: https://freesound.org/people/JarredGibb/
    private final Sound deathSound = Gdx.audio.newSound(Gdx.files.internal("assets/sounds/bird_crow.mp3")); // sound source: https://freesound.org/people/Jofae/
    private final BitmapFont scoreText;
    private int score;
    private int currentHighScore;
    private final DeathMenuState deathMenuState;
    private boolean isDead = false;
    private float waitTimer = 1.0f;


    private boolean startGame = false;

    protected PlayState(GameStateManager gsm) {
        super(gsm);
        deathMenuState = new DeathMenuState(gsm, this);
        bird = new Bird(50, 200);
        camera.setToOrtho(false, (float) JumpyBirb.WIDTH / 2, (float) JumpyBirb.HEIGHT / 2);
        background = new Texture("assets/graphics/bg.png");
        background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        scoreText = new BitmapFont(Gdx.files.internal("assets/fonts/squeaky_green_font.fnt"));
        scoreText.getData().setScale(0.5f);
        scoreText.setUseIntegerPositions(false);
        score = 0;
        debugMode = false;
        tubes = new ArrayList<>();

        for (int i = 1; i <= TUBE_COUNT; i++) {
            tubes.add(new Tube(i * ((float) TUBE_SPACING + Tube.TUBE_WIDTH)));
        }
    }

    @Override
    protected void handleInput() {

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            bird.setMovement(120);
            bird.setGravity(-900);
            startGame = true;
        }

        // Jump
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            bird.jump();
        }

        // Turn on debug mode
        if (Gdx.input.isKeyJustPressed(Input.Keys.H)) {
            debugMode = !debugMode;
        }

        if (isDead && Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            gsm.set(new PlayState(gsm));
        }


        float topBorder = 420f;
        if (bird.getPosition().y >= topBorder) {
            isDead = true;
        }

        float bottomBorder = 0f;
        if (bird.getPosition().y == bottomBorder) {
            isDead = true;
        }
    }

    @Override
    public void update(float delta) {

        // boolean that stops the game if true
        if (isDead) {
            if (waitTimer > 0) {
                waitTimer -= delta;
            } else {
                handleInput();
            }
            return;
        }

        handleInput();

        if (!startGame) {
            bird.setMovement(0);
            bird.setGravity(0);
            bird.setRotation(0);
        }
        bird.update(delta);

        camera.position.x = bird.getPosition().x + 80; //offset bird

        for (Tube tube : tubes) {
            if (camera.position.x - (camera.viewportWidth / 2) > tube.getPositionTop().x + tube.getTopTube().getWidth()) {
                tube.reposition(tube.getPositionTop().x + ((Tube.TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT));
            }

            if (tube.collides(bird.getBounds())) {
                isDead = true;
                deathSound.play();
            }


            if (tube.pass((bird.getBounds()))) {
                passOver.play();
                score++;
                if (currentHighScore < score) {
                    currentHighScore = score;
                }
            }
            camera.update();
        }


    }

    private void renderHitbox(ShapeRenderer renderer) {
        renderer.setProjectionMatrix(camera.combined);
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(0, 1, 1, 1);
        renderer.circle(bird.getBounds().x, bird.getBounds().y, bird.getBounds().radius);
        renderer.end();
    }

    public int getHighScore() {
        return currentHighScore;
    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer renderer) {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        float camLeft = camera.position.x - (camera.viewportWidth / 2);
        float width = camera.viewportWidth;
        float offSet = Math.floorMod((int) camLeft, (int) width);
        batch.setColor(1, 1, 1, 0.8f);
        batch.draw(background, camLeft - offSet, 0, width, camera.viewportHeight);
        batch.draw(background, camLeft - offSet, -width, 0, camera.viewportHeight);
        batch.draw(background, (camLeft - offSet) + width, 0, width, camera.viewportHeight);
        batch.setColor(1, 1, 1, 1);
        batch.draw(
            bird.getTexture(),
            bird.getPosition().x, bird.getPosition().y,
            bird.getWidth() / 2, bird.getHeight() / 2,
            bird.getWidth(), bird.getHeight(),
            1f, 1f,
            bird.getRotation()
        );

        for (Tube tube : tubes) {
            batch.draw(tube.getTopTube(), tube.getPositionTop().x, tube.getPositionTop().y);
            batch.draw(tube.getBottomTube(), tube.getPositionBottom().x, tube.getPositionBottom().y);

        }
        GlyphLayout layout = new GlyphLayout(scoreText, "SCORE: " + score);
        scoreText.draw(batch, layout, camera.position.x - layout.width / 2, camera.position.y + (camera.viewportHeight / 2) - 20);

        if (debugMode) {
            renderHitbox(renderer);
        }


        batch.end();
        if (isDead) {
            deathMenuState.camera.viewportWidth = (camera.viewportWidth / 2);
            deathMenuState.camera.viewportHeight = (camera.viewportHeight / 2);
            deathMenuState.camera.position.set(camera.position);
            deathMenuState.camera.update();

            batch.setProjectionMatrix(deathMenuState.camera.combined);
            deathMenuState.render(batch, renderer);
        }


    }


    @Override
    public void dispose() {
        background.dispose();
        bird.dispose();
        scoreText.dispose();
        for (Tube tube : tubes) {
            tube.dispose();
        }
        passOver.dispose();
        deathMenuState.dispose();
    }
}

