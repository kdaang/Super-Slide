package com.superslide.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.superslide.game.SuperSlide;

import java.util.Random;

/**
 * Created by Kevin on 08/04/2017.
 */

public class Wall {
    public final int WALLWIDTH = 50;
    private final int WALLLENGTH = 300;
    private final int WALLGAP = 50;//from leftwall to rightwall
    private final int WALLSPACING = 150;//from wall to next set of walls
    private final int WALLTRIANGLEWIDTH = 45;
    private final int ADDWALLHEIGHT = 50;
    private final int CLOSESTPOS = WALLTRIANGLEWIDTH;
    private final int FURTHESTPOS = SuperSlide.WIDTH/2 - WALLGAP - WALLTRIANGLEWIDTH;//145
    private Random rand;
    private Texture leftwall, rightwall;
    private Texture llwall, lrwall,rlwall, rrwall;
    private Vector2 leftwallpos, rightwallpos;
    private Polygon leftpoly, rightpoly;

    public Wall(int currentwall){
        //create walls
        lrwall = new Texture("leftrightwall.png");
        rrwall = new Texture("rightrightwall.png");
        llwall = new Texture("leftleftwall.png");
        rlwall = new Texture("rightleftwall.png");
        //set position of walls
        rand = new Random();
        int tempposx = rand.nextInt(FURTHESTPOS - CLOSESTPOS) + CLOSESTPOS - WALLLENGTH;//generate # from 45 - 145(-255 to -155)
        leftwallpos = new Vector2(tempposx, (WALLWIDTH + WALLSPACING)*currentwall + ADDWALLHEIGHT);
        rightwallpos = new Vector2(leftwallpos.x + WALLLENGTH + WALLGAP, leftwallpos.y);
        //set hitboxes of random set of walls
        if(tempposx % 2 == 0) {
            leftpoly = new Polygon(setwallpos(leftwallpos, 0, 0, 0, WALLTRIANGLEWIDTH));
            rightpoly = new Polygon(setwallpos(rightwallpos, 0, WALLTRIANGLEWIDTH, 0, 0));
            leftwall = lrwall;
            rightwall = rrwall;
        }
        else{
            leftpoly = new Polygon(setwallpos(leftwallpos, 0, 0, WALLTRIANGLEWIDTH, 0));
            rightpoly = new Polygon(setwallpos(rightwallpos, WALLTRIANGLEWIDTH, 0, 0, 0));
            leftwall = llwall;
            rightwall = rlwall;
        }
    }
    public float[] setwallpos(Vector2 wallpos, int RL, int RR, int LL, int LR){
        float [] wallbounds = {
                wallpos.x + RL, wallpos.y,//bottom-left | RIGHT-LEFT WALL
                wallpos.x + RR, wallpos.y + WALLWIDTH,//top-left | RIGHT-RIGHT WALL
                wallpos.x + WALLLENGTH - LL, wallpos.y + WALLWIDTH,//top-right | LEFT-LEFT WALL
                wallpos.x + WALLLENGTH - LR, wallpos.y};//bottom-right | LEFT-RIGHT WALL
        return wallbounds;
    }
    public void reposition(int totalwalls){
        int tempposx = rand.nextInt(FURTHESTPOS - CLOSESTPOS) + CLOSESTPOS - WALLLENGTH;//generate # from 45 - 145(-255 to -155)
        leftwallpos.set(tempposx, leftwallpos.y + (WALLWIDTH + WALLSPACING)*totalwalls);
        rightwallpos.set(leftwallpos.x + WALLLENGTH + WALLGAP, leftwallpos.y);
        if(tempposx % 2 == 0) {
            leftpoly.setVertices(setwallpos(leftwallpos, 0, 0, 0, WALLTRIANGLEWIDTH));//left-right wall
            rightpoly.setVertices(setwallpos(rightwallpos, 0, WALLTRIANGLEWIDTH, 0, 0));//right-right wall
            leftwall = lrwall;
            rightwall = rrwall;
        }
        else{
            leftpoly.setVertices(setwallpos(leftwallpos, 0, 0, WALLTRIANGLEWIDTH, 0));//left-left wall
            rightpoly.setVertices(setwallpos(rightwallpos, WALLTRIANGLEWIDTH, 0, 0, 0));//left-right wall
            leftwall = llwall;
            rightwall = rlwall;
        }
    }
    public Vector2 getLeftwallpos(){
        return leftwallpos;
    }
    public Vector2 getRightwallpos(){
        return rightwallpos;
    }
    public Texture getLeftwall() {
        return leftwall;
    }
    public Texture getRightwall() {
        return rightwall;
    }
    public Polygon getLeftpoly() {
        return leftpoly;
    }
    public Polygon getRightpoly() {
        return rightpoly;
    }
    public void dispose(){
        lrwall.dispose();
        rrwall.dispose();
        llwall.dispose();
        rlwall.dispose();
    }
}
