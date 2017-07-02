package com.superslide.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.Array;
import com.superslide.game.SuperSlide;
import com.superslide.game.Sprites.Player;
import com.superslide.game.Sprites.Text;
import com.superslide.game.Sprites.Wall;

/**
 * Created by Kevin on 26/03/2017.
 */

class PlayState extends State {
    private final int TOTALWALLS = 3;
    private int score;
    private boolean CURRENTSCORE;
    private int CURRENTWALLINDEX;
    private Preferences prefs;
    private Text font;
    private Player player;
    private Texture background;
    private Wall wall;
    private Array<Wall> walls;
    private Music scoresound;
    private Sound gameoversound;
    public PlayState(GameStateManager gsm, Player player){
        super(gsm);
        cam.setToOrtho(false, SuperSlide.WIDTH/2, SuperSlide.HEIGHT/2);
        background = new Texture("background.png");
        prefs = Gdx.app.getPreferences("mysettings");
        score = 0;
        CURRENTSCORE = false;
        font = new Text(25, Color.RED);
        scoresound = Gdx.audio.newMusic(Gdx.files.internal("scoresound.ogg"));
        gameoversound = Gdx.audio.newSound(Gdx.files.internal("gameoversound.ogg"));
        //player = new Player();
        this.player = player;
        player.setRISESPEED();
        walls = new Array<Wall>();
        for(int x = 1; x <= TOTALWALLS; x++){
            walls.add(new Wall(x));
        }
        this.CURRENTWALLINDEX = 0;
        wall = walls.get(CURRENTWALLINDEX);
    }
    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            player.slide();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        player.update(dt);
        cam.position.y = player.getPosition().y + player.getPLAYERHEIGHT();
        wallupdate();
        scoreupdate();
        cam.update();
        //Gdx.app.log("POSITION", player.getPosition().x + ", " + player.getPosition().y);
        //Gdx.app.log("VELOCITY", player.getVelocity() + ", " + player.getVelocity());
        //Gdx.app.log("TIME", " " + dt);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, cam.position.x - cam.viewportWidth/2, cam.position.y - cam.viewportHeight/2);
        sb.draw(player.getPlayer(), player.getPosition().x, player.getPosition().y,
                player.getPlayer().getWidth()/player.getPLAYERSCALE(), player.getPlayer().getHeight()/player.getPLAYERSCALE());
        for(Wall wall : walls) {
            sb.draw(wall.getLeftwall(), wall.getLeftwallpos().x, wall.getLeftwallpos().y);
            sb.draw(wall.getRightwall(), wall.getRightwallpos().x, wall.getRightwallpos().y);
        }
        font.getFont().draw(sb, Integer.toString(score), cam.position.x - cam.viewportWidth/2 + 15, cam.position.y + cam.viewportHeight/2 - 30);
        font.getFont().draw(sb, Integer.toString(prefs.getInteger("score", 0)),
                cam.position.x + cam.viewportWidth/2 - 45, cam.position.y + cam.viewportHeight/2 - 30);
        sb.end();
    }

    @Override
    public void dispose() {
        player.dispose();
        for(Wall wall: walls) {
            wall.dispose();
        }
        font.dispose();
        scoresound.dispose();
        gameoversound.dispose();
        System.out.println("PlayState Disposed");
    }
    public void wallupdate(){
        //reposition walls when out of view
        if (cam.position.y - (cam.viewportHeight / 2) > wall.getLeftwallpos().y + wall.WALLWIDTH) {
            wall.reposition(TOTALWALLS);
            if(CURRENTWALLINDEX == TOTALWALLS-1) {
                CURRENTWALLINDEX = 0;
            }
            else{
                CURRENTWALLINDEX++;
            }
            wall = walls.get(CURRENTWALLINDEX);
            CURRENTSCORE = false;
        }
        //check for collision
        if((Intersector.overlapConvexPolygons(player.playerpoly, wall.getLeftpoly()))||
                (Intersector.overlapConvexPolygons(player.playerpoly, wall.getRightpoly()))){
            gameoversound.play(3.0f);
            gsm.set(new GameOverState(gsm, score));
            //Gdx.app.log("COLLIDED");
        }
    }
    public void scoreupdate(){
        if(player.getPosition().y > wall.getLeftwallpos().y + wall.WALLWIDTH){
            if(!CURRENTSCORE) {
                scoresound.play();
                score++;
                CURRENTSCORE = true;
            }
        }
        if(prefs.getInteger("score", 0) < score){
            prefs.putInteger("score", score);
            prefs.flush();
        }
    }
}
