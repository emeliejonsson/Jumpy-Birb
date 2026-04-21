package com.test.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.test.game.JumpyBirb;
import com.test.game.sprites.Bird;
import com.test.game.sprites.Tube;

import java.util.ArrayList;

public class PlayState extends State {
    private static final int TUBE_SPACING = 175;
    private static final int TUBE_COUNT = 4;
    private Bird bird;
    private Texture background;
    private boolean debugMode;
    private ArrayList<Tube> tubes;
    private int score;
    private BitmapFont textFont;
    private Sound passOver = Gdx.audio.newSound(Gdx.files.internal("bading.mp3"));
    private Sound deathSound = Gdx.audio.newSound(Gdx.files.internal("damnbro.mp3"));
    private BitmapFont highScoreText;
    private static int currentHighScore;
    private DeathMenuState deathMenuState;
    private boolean isDead = false;
    private float scroll = 200f;
    private float topBorder = 340f;
    private float bottomBorder = 0f;
    private Texture test;
    //    private Preferences prefs;


    private boolean startGame = false;

    protected PlayState(GameStateManager gsm) {
        super(gsm);
        deathMenuState = new DeathMenuState(gsm);
        bird = new Bird(50, 200);
        camera.setToOrtho(false, JumpyBirb.WIDTH / 2, JumpyBirb.HEIGHT / 2);
        background = new Texture("bg.png");
        background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        textFont = new BitmapFont();
        textFont.getData().setScale(2);
        test = new Texture("bg.png");
        score = 0;
        highScoreText = new BitmapFont();
        debugMode = false;
        tubes = new ArrayList<>();
//        prefs = Gdx.app.getPreferences("JPBirdSave");
//        currentHighScore = prefs.getInteger("currentHighScore", 0);

        for (int i = 1; i <= TUBE_COUNT; i++) {
            tubes.add(new Tube(i * (TUBE_SPACING + Tube.TUBE_WIDTH)));
        }
    }

    @Override
    protected void handleInput() {
        if (!startGame) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                startGame = true;
            }
            return;
        }
        // Jump
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            bird.jump();
        }

        // Turn on debug mode
        if (Gdx.input.isKeyJustPressed(Input.Keys.H)) {
            debugMode = !debugMode;
        }

        if (isDead) {


            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {

                gsm.set(new PlayState(gsm));
            }
        }

        if (bird.getPosition().y >= topBorder) {
            isDead = true;

        }

        if (bird.getPosition().y == bottomBorder) {
            isDead = true;
        }


    }

    @Override
    public void update(float delta) {

        // boolean that stops the game if true
        if (isDead) {
            handleInput();

            return;
        }


        handleInput();

        if (!startGame) {
            return;
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
                System.out.println(score);
                if (currentHighScore < score) {
                    currentHighScore = score;
//                prefs.putInteger("currentHighScore", currentHighScore);
//                prefs.flush();
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

    @Override
    public void render(SpriteBatch batch, ShapeRenderer renderer) {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        float camLeft = camera.position.x - (camera.viewportWidth / 2);
        float width = camera.viewportWidth;
        float offSet = Math.floorMod((int) camLeft, (int) width);
        batch.draw(background, camLeft - offSet, 0, width, camera.viewportHeight);
        batch.draw(background, camLeft - offSet, -width, 0, camera.viewportHeight);
        batch.draw(background, (camLeft - offSet) + width, 0, width, camera.viewportHeight);
        batch.draw(bird.getTexture(), bird.getPosition().x, bird.getPosition().y);

        for (Tube tube : tubes) {
            batch.draw(tube.getTopTube(), tube.getPositionTop().x, tube.getPositionTop().y);
            batch.draw(tube.getBottomTube(), tube.getPositionBottom().x, tube.getPositionBottom().y);
//            batch.draw(tube.getScoreTexture(),
//                tube.getScoreBounds().x,
//                tube.getScoreBounds().y,
//                tube.getScoreBounds().width,
//                tube.getScoreBounds().height);

        }

        textFont.draw(batch, "score: " + score, camera.position.x - (camera.viewportWidth / 2), camera.position.y + (camera.viewportHeight / 2) - 20);
        highScoreText.draw(batch, "top: " + currentHighScore, camera.position.x - (camera.viewportWidth / 2), camera.position.y + (camera.viewportHeight / 3));

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
        textFont.dispose();
        for (Tube tube : tubes) {
            tube.dispose();
            System.out.println("PlayState disposed");
        }
        highScoreText.dispose();
        passOver.dispose();
//        prefs.flush();
    }
}

