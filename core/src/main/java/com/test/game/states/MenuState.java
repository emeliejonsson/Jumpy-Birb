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

public class MenuState extends State {
    private Texture background;
    private Sound backgroundMusic = Gdx.audio.newSound(Gdx.files.internal("background_music.mp3")); //music source: https://freesound.org/people/rebrie18/
    private BitmapFont font;
    private GlyphLayout startLayout;
    private GlyphLayout titleLayout;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        background = new Texture("bg.png");
        backgroundMusic.play();
        font = new BitmapFont(Gdx.files.internal("blue_font.fnt"));
        font.getData().setScale(1f);
        titleLayout = new GlyphLayout(font, "Jumpy Bird");
        startLayout = new GlyphLayout(font, "Press SPACE to start game");
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
        batch.begin();

        batch.setColor(1,1,1,0.9f);
        batch.draw(background, 0, 0, JumpyBirb.WIDTH, JumpyBirb.HEIGHT);

        batch.setColor(1,1,1,1);
        font.draw(batch, titleLayout, (JumpyBirb.WIDTH - titleLayout.width) / 2, JumpyBirb.HEIGHT * 0.95f);
        font.draw(batch, startLayout,
            (JumpyBirb.WIDTH - startLayout.width) / 2,
            JumpyBirb.HEIGHT * 0.5f);

        batch.end();

    }

    @Override
    public void dispose() {
        background.dispose();
        font.dispose();
        System.out.println("MenuState disposed");
    }
}


