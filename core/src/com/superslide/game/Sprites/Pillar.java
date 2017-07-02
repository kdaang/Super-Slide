package com.superslide.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.superslide.game.SuperSlide;

/**
 * Created by Kevin on 16/04/2017.
 */

public class Pillar {
    private final int PILLARSPEED = -100;
    private Texture leftpillar, rightpillar;
    private Vector2 leftpillarpos, rightpillarpos, velocity;
    public Pillar(int currentpillar){
        leftpillar = new Texture("pillar.png");
        rightpillar = new Texture("pillar.png");
        leftpillarpos = new Vector2(0, leftpillar.getHeight()*currentpillar);
        rightpillarpos = new Vector2(SuperSlide.WIDTH/2 - leftpillar.getWidth(), leftpillarpos.y);
        velocity = new Vector2(0, PILLARSPEED);
    }
    public void update(float dt){
        if(velocity.y == 0){
            velocity.y = PILLARSPEED;
        }
        velocity.scl(dt);
        leftpillarpos.add(0, velocity.y);
        rightpillarpos.add(0, velocity.y);
        if(dt != 0) {
            velocity.scl(1/dt);
        }
    }
    public void reposition(){
        leftpillarpos.set(0, leftpillarpos.y + (leftpillar.getHeight()*2));
        rightpillarpos.set(SuperSlide.WIDTH/2 - leftpillar.getWidth(), leftpillarpos.y);
    }
    public Texture getLeftpillar(){
        return leftpillar;
    }
    public Texture getRightpillar(){
        return rightpillar;
    }
    public Vector2 getLeftpillarpos(){
        return leftpillarpos;
    }
    public Vector2 getRightpillarpos(){
        return rightpillarpos;
    }
    public int getPillarWidth(){
        return leftpillar.getWidth();
    }
    public void dispose(){
        leftpillar.dispose();
        rightpillar.dispose();
    }
}
