package com.test.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector3;

public class Bird {
    private float rotation = 0f;
    private static final float MAX_ROTATION_UP = 5f;
    private static final float MAX_ROTATION_DOWN = -25f;
    private static final float ROTATION_SPEED = 80f; // degrees per second
    private int gravity = -900;
    private int movement = 120;
    private Vector3 position;
    private Vector3 velocity;
    private Circle bounds;
    private TextureRegion birdTexture;

    public Bird(int x, int y) {
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0, 0, 0);
        birdTexture = new TextureRegion(new Texture("graphics/domherre.png"));

        float radius = birdTexture.getRegionWidth() / 4f;
        bounds = new Circle(x, y, radius);
    }

    public void update(float delta) {
        if (position.y > 0) {
            velocity.add(0, gravity * delta, 0);
        }

        velocity.scl(delta);
        position.add(movement * delta, velocity.y, 0);

        if (position.y < 0) {
            position.y = 0;
        }

        velocity.scl(1 / delta);

        bounds.setPosition(position.x + (float) birdTexture.getRegionWidth() / 2,
            position.y + (float) birdTexture.getRegionHeight() / 2);

        if (velocity.y > 0) {
            rotation = Math.min(rotation + ROTATION_SPEED * delta, MAX_ROTATION_UP);
        } else {
            rotation = Math.max(rotation - ROTATION_SPEED * delta, MAX_ROTATION_DOWN);
        }
    }

    public TextureRegion getTexture() {
        return birdTexture;
    }

    public Vector3 getPosition() {
        return position;
    }

    public void jump() {
        velocity.y = 333;
        rotation = MAX_ROTATION_UP;
    }

    public float getWidth() {
        return birdTexture.getRegionWidth();
    }

    public float getHeight() {
        return birdTexture.getRegionHeight();
    }

    public float getRotation() {
        return rotation;
    }

    public Circle getBounds() {
        return bounds;
    }

    public void dispose() {
        birdTexture.getTexture().dispose();
    }

    public void setMovement(int speed) {
        this.movement = speed;
    }

    public void setGravity(int gravity) {
        this.gravity = gravity;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }
}
