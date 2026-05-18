package com.test.game.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

public class GameStateManager {

    private final Deque<State> states;

    public GameStateManager() {
        states = new ArrayDeque<>();
    }

    public void push(State state) {
        states.push(state);
    }

    public void pop() {
        states.pop().dispose();
    }

    public void set(State state) {
        if (!states.isEmpty()) {
            states.pop().dispose();
        }
        states.push(state);
    }

    public void update(float delta) {
        states.peek().update(delta);
    }

    public void render(SpriteBatch batch, ShapeRenderer renderer) {
        states.peek().render(batch, renderer);
    }
}
