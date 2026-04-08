package com.test.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector3;

public class Bird {
    private static final int GRAVITY = -900;
    private static final int MOVEMENT = 120;
    private Vector3 position;
    private Vector3 velocity;
    private Circle bounds;

    private Texture bird;

    public Bird(int x, int y) {
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0, 0, 0);
        bird = new Texture("domherre.png");

        float radius = bird.getWidth() / 4f;
        bounds = new Circle(x, y, radius);
    }

    public void update(float delta) {
        if (position.y > 0) {
            velocity.add(0, GRAVITY * delta, 0);
        }

        velocity.scl(delta);
        position.add(MOVEMENT * delta, velocity.y, 0);

        if (position.y < 0) {
            position.y = 0;
        }

        velocity.scl(1 / delta);

        bounds.setPosition(position.x + bird.getWidth() / 2,
            position.y + bird.getHeight() / 2);
    }

    public Texture getTexture() {
        return bird;
    }

    public Vector3 getPosition() {
        return position;
    }

    public void jump() {
        velocity.y = 333;
    }

    public Circle getBounds() {
        return bounds;
    }

    public void dispose() {
        bird.dispose();
    }
}
