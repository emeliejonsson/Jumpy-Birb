package com.test.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Tube {
    public static final int TUBE_WIDTH = 52;
    private static final int FLUCTUATION = 130;
    private static final int TUBE_GAP = 100;
    private static final int LOWEST_OPENING = 120;
    private Texture topTube, bottomTube;
    private Vector2 positionTop, positionBottom;
    private Rectangle boundsTop, boundsBottom;
    private Random random;

    public Tube(float x) {
        topTube = new Texture("toptube.png");
        bottomTube = new Texture("bottomtube.png");
        random = new Random();

        positionTop = new Vector2(x, random.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING);
        positionBottom = new Vector2(x, positionTop.y - TUBE_GAP - bottomTube.getHeight());

        boundsTop = new Rectangle(positionTop.x, positionTop.y, topTube.getWidth(), topTube.getHeight());
        boundsBottom = new Rectangle(positionBottom.x, positionBottom.y, bottomTube.getWidth(), bottomTube.getHeight());

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
        positionTop.set(x, random.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING);
        positionBottom.set(x, positionTop.y - TUBE_GAP - bottomTube.getHeight());
        boundsTop.setPosition(positionTop.x, positionTop.y);
        boundsBottom.setPosition(positionBottom.x, positionBottom.y);
    }

    public boolean collides(Rectangle player) {
        return player.overlaps(boundsTop) || (player.overlaps(boundsBottom));
    }
}
