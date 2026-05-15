package com.test.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.test.game.JumpyBirb;

public class MenuState extends State {
    private Texture background;
    private Music backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("assets/sounds/background_music.mp3")); //music source: https://freesound.org/people/rebrie18/
    private BitmapFont font;
    private BitmapFont bigFont;
    private GlyphLayout startLayout;
    private GlyphLayout titleLayout;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        background = new Texture("assets/graphics/bg.png");
        backgroundMusic.play();
        font = new BitmapFont(Gdx.files.internal("assets/fonts/squeaky_green_font.fnt"));
        bigFont = new BitmapFont(Gdx.files.internal("assets/fonts/squeaky_title.fnt"));
        font.getData().setScale(1f);
        titleLayout = new GlyphLayout(bigFont, "Squeaky the Birb");
        startLayout = new GlyphLayout(font, "Press SPACE to start game");
        backgroundMusic.setLooping(true);
    }

    @Override
    public void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.justTouched()) {
            gsm.set(new PlayState(gsm));
        }
    }

    @Override
    public void update(float delta) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer renderer) { //draw starts bottom left-hand corner, continues right and upwards
        float x = (JumpyBirb.WIDTH - titleLayout.width) / 2;
        float y = (float) JumpyBirb.HEIGHT / 2 + titleLayout.height / 2;
        batch.begin();

        batch.setColor(1,1,1,0.8f);
        batch.draw(background, 0, 0, JumpyBirb.WIDTH, JumpyBirb.HEIGHT);

        batch.setColor(1,1,1,1);
        bigFont.draw(batch, titleLayout, x, (y + 70));
        font.draw(batch, startLayout,
            (JumpyBirb.WIDTH - startLayout.width) / 2,
            (JumpyBirb.HEIGHT - startLayout.height) / 2 - 10);

        batch.end();

    }

    @Override
    public void dispose() {
        background.dispose();
        font.dispose();
        System.out.println("MenuState disposed");
    }
}


