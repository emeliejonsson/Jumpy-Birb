package com.test.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import org.w3c.dom.Text;

public class Bird {
    private static final int GRAVITY = -15;
    private static final int MOVEMENT = 100;
    private Vector3 position;
    private Vector3 velocity;
    private Rectangle bounds;

    private Texture bird;

    public Bird(int x, int y) {
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0, 0, 0);
        bird = new Texture("domherre.png");
        bounds = new Rectangle(x, y, bird.getWidth(), bird.getHeight());
    }

    public void update(float delta) {
        if (position.y > 0) {
            velocity.add(0, GRAVITY, 0);
        }

        velocity.scl(delta);
        position.add(MOVEMENT * delta, velocity.y, 0);

        if (position.y < 0) {
            position.y = 0;
        }

        velocity.scl(1 / delta);
        bounds.setPosition(position.x, position.y);
    }

    public Texture getTexture() {
        return bird;
    }

    public Vector3 getPosition() {
        return position;
    }

    public void jump() {
        velocity.y = 300;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void dispose() {
        bird.dispose();
    }
}
