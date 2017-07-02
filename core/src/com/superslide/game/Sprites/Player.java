package com.superslide.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.superslide.game.SuperSlide;

/**
 * Created by Kevin on 08/04/2017.
 */

public class Player {
    private int RISESPEED;
    private final int PLAYERSPEED = 130;
    private final float PLAYERSCALE = 2.7f;
    private final int PLAYERHEIGHT = 130;
    private Vector2 position, velocity;
    private Texture player;
    public Polygon playerpoly;
    private Sound slidesound;
    move dir;
    private enum move{
        left,
        right
    }
    public Player(){
        player = new Texture("pig.png");
        position = new Vector2 ((SuperSlide.WIDTH/4)-(player.getWidth()/(2*PLAYERSCALE)), SuperSlide.HEIGHT/4-PLAYERHEIGHT);
        velocity = new Vector2(0, 0);
        playerpoly = new Polygon(setplayerpos(position));
        slidesound = Gdx.audio.newSound(Gdx.files.internal("slidesound.ogg"));
        dir = move.right;
        RISESPEED = 0;//to keep stationary in menustate
    }
    public float [] setplayerpos(Vector2 position){
        float [] playerbounds = {
                position.x, position.y,//bottom-left
                position.x, position.y + player.getHeight()/PLAYERSCALE,//top-left
                position.x + player.getWidth()/PLAYERSCALE, position.y + player.getHeight()/PLAYERSCALE,//top-right
                position.x + player.getWidth()/PLAYERSCALE, position.y//bottom-right
        };
        return playerbounds;
    }
    public void update(float dt){
        //keep direction of movement after pause
        if(velocity.x == 0){
            if(dir == move.left){
                dir = move.right;
            }
            else{
                dir = move.left;
            }
            slide();
        }
        velocity.scl(dt);
        position.add(velocity.x, RISESPEED*dt);
        if(position.x < 0){
            position.x = 0;
        }
        else if(position.x > (SuperSlide.WIDTH/2) - player.getWidth()/PLAYERSCALE){
            position.x = (SuperSlide.WIDTH/2) - player.getWidth()/PLAYERSCALE;
        }
        playerpoly.setVertices(setplayerpos(position));
        if(dt != 0) {
            velocity.scl(1/dt);
        }
    }
    public void slide(){
        if(dir == move.left) {
            velocity.x = PLAYERSPEED;
            dir = move.right;
        }
        else{
            velocity.x = -PLAYERSPEED;
            dir = move.left;
        }
        slidesound.play(0.8f);
    }
    public void setRISESPEED(){
        RISESPEED = PLAYERSPEED;
    }
    public Texture getPlayer(){
        return player;
    }
    public Vector2 getPosition(){
        return position;
    }
    public float getPLAYERSCALE(){
        return PLAYERSCALE;
    }
    public int getPLAYERHEIGHT(){
        return PLAYERHEIGHT;
    }
    public void dispose(){
        player.dispose();
        slidesound.dispose();
    }
}