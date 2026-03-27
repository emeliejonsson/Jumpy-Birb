package com.test.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Intersector;

import java.util.Random;

public class Tube {
    public static final int TUBE_WIDTH = 52;
    private static final int FLUCTUATION = 130;
    private static final int TUBE_GAP = 100;
    private static final int LOWEST_OPENING = 120;
    private Texture topTube, bottomTube;
    private Vector2 positionTop, positionBottom;
    private Rectangle hitboxTopTube, hitboxBottomTube;
    private Random random;

    public Tube(float x) {
        topTube = new Texture("toptube.png");
        bottomTube = new Texture("bottomtube.png");
        random = new Random();

        positionTop = new Vector2(x, random.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING);
        positionBottom = new Vector2(x, positionTop.y - TUBE_GAP - bottomTube.getHeight());

        //hitbox på fågelholk behöver ändras, alternativt hinder kan vara stubbar?
        hitboxTopTube = new Rectangle(positionTop.x, positionTop.y, topTube.getWidth(), topTube.getHeight());
        hitboxBottomTube = new Rectangle(positionBottom.x, positionBottom.y, bottomTube.getWidth(), bottomTube.getHeight());

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
        hitboxTopTube.setPosition(positionTop.x, positionTop.y);
        hitboxBottomTube.setPosition(positionBottom.x, positionBottom.y);
    }

    public boolean collides(Circle player) {
        return Intersector.overlaps(player, hitboxTopTube) ||
            Intersector.overlaps(player, hitboxBottomTube);
    }

    public void dispose() {
        topTube.dispose();
        bottomTube.dispose();
    }
}
