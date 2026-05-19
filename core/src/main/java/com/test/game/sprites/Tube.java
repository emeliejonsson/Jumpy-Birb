package com.test.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Tube {
    public static final int TUBE_WIDTH = 52;
    private static final int FLUCTUATION = 160;
    private static final int TUBE_GAP = 125;
    private static final int LOWEST_OPENING = 100;
    private Texture topTube;
    private Texture bottomTube;
    private Vector2 positionTop;
    private Vector2 positionBottom;
    private Rectangle hitboxTopTube;
    private Rectangle hitboxBottomTube;
    private Random random;
    private Rectangle scoreBounds;
    private boolean isScoreFalse = false;


    public Tube(float x) {
        topTube = new Texture("assets/graphics/toptube.png");
        // NOSONAR - tube gap randomness
        random = new Random();
        bottomTube = random.nextBoolean() ? new Texture("assets/graphics/bottomtube.png") : new Texture("assets/graphics/bottomtube2.png");

        positionTop = new Vector2(x, random.nextInt(FLUCTUATION) + (float)TUBE_GAP + LOWEST_OPENING);
        positionBottom = new Vector2(x, positionTop.y - TUBE_GAP - bottomTube.getHeight());

        float widthTop = topTube.getWidth() * 0.5f;
        float heightTop = topTube.getHeight();
        float widthBottom = bottomTube.getWidth() * 0.5f;
        float heightBottom = bottomTube.getHeight() * 0.98f;

        float topX = positionTop.x + (topTube.getWidth() - widthTop) / 2;
        float bottomX = positionBottom.x + (bottomTube.getWidth() - widthBottom) / 2;

        hitboxTopTube = new Rectangle(topX, positionTop.y, widthTop, heightTop);
        hitboxBottomTube = new Rectangle(bottomX, positionBottom.y, widthBottom, heightBottom);

        // score wall
        scoreBounds = new Rectangle(bottomX + TUBE_WIDTH,
            positionBottom.y + bottomTube.getHeight(),
            2, TUBE_GAP);

    }

    public Texture getTopTube() {
        return topTube;
    }

    public Texture getBottomTube() {
        return bottomTube;
    }

    public Vector2 getPositionTop() {
        return positionTop;
    }

    public Vector2 getPositionBottom() {
        return positionBottom;
    }


    public void reposition(float x) {
        positionTop.set(x, random.nextInt(FLUCTUATION) + (float)TUBE_GAP + LOWEST_OPENING);

        bottomTube.dispose();
        bottomTube = random.nextBoolean() ? new Texture("assets/graphics/bottomtube.png") : new Texture("assets/graphics/bottomtube2.png");

        positionBottom.set(x, positionTop.y - TUBE_GAP - bottomTube.getHeight());

        float topOffsetX = (topTube.getWidth() - hitboxTopTube.getWidth()) / 2;
        float bottomOffsetX = (bottomTube.getWidth() - hitboxBottomTube.getWidth()) / 2;

        hitboxTopTube.setPosition(positionTop.x + topOffsetX, positionTop.y);
        hitboxBottomTube.setPosition(positionBottom.x + bottomOffsetX, positionBottom.y);

        scoreBounds.setPosition(x + TUBE_WIDTH, positionBottom.y + bottomTube.getHeight());
        isScoreFalse = false;
    }

    public boolean collides(Circle player) {
        return Intersector.overlaps(player, hitboxTopTube) ||
            Intersector.overlaps(player, hitboxBottomTube);
    }

    public boolean pass(Circle player) {
        if (!isScoreFalse && Intersector.overlaps(player, scoreBounds)) {
            isScoreFalse = true;
            return true;
        }
        return false;
    }

    public void dispose() {
        topTube.dispose();
        bottomTube.dispose();
    }
}
