package com.superslide.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.superslide.game.SuperSlide;
import com.superslide.game.Sprites.Pillar;
import com.superslide.game.Sprites.Player;
import com.superslide.game.Sprites.Text;

/**
 * Created by Kevin on 14/04/2017.
 */

public class GameOverState extends State{
    private int CURRENTPILLARINDEX;
    private Texture background, replay;
    Rectangle replaybounds;
    private Text font;
    private Array<Pillar> pillars;
    private Pillar pillar;
    private Preferences prefs;
    private int lastscore;
    public GameOverState(GameStateManager gsm, int score) {
        super(gsm);
        cam.setToOrtho(false, SuperSlide.WIDTH/2, SuperSlide.HEIGHT/2);
        background = new Texture("background.png");
        replay = new Texture("pig.png");
        replaybounds = new Rectangle(SuperSlide.WIDTH/4 - replay.getWidth()/2, 75,
                replay.getWidth(), replay.getHeight());
        pillars = new Array<Pillar>();
        for(int x = 0; x < 2; x++){
            pillars.add(new Pillar(x));
        }
        CURRENTPILLARINDEX = 0;
        pillar = pillars.get(CURRENTPILLARINDEX);
        lastscore = score;
        prefs = Gdx.app.getPreferences("mysettings");
        font = new Text(25, Color.RED);
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            int x = Gdx.input.getX();
            int y = Gdx.input.getY();
            Vector3 input = new Vector3(x, y, 0);
            cam.unproject(input);
            if(replaybounds.contains(input.x, input.y)) {
                gsm.set(new PlayState(gsm, new Player()));
            }
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        updatePillars(dt);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, 0 , 0);
        sb.draw(replay, SuperSlide.WIDTH/4 - replay.getWidth()/2, 75);
        for(Pillar pillar: pillars){
            sb.draw(pillar.getLeftpillar(), pillar.getLeftpillarpos().x, pillar.getLeftpillarpos().y);
            sb.draw(pillar.getRightpillar(), pillar.getRightpillarpos().x, pillar.getRightpillarpos().y);
        }
        font.getFont().draw(sb, "SCORE: " + lastscore, SuperSlide.WIDTH/8, SuperSlide.HEIGHT/2 - 75);
        font.getFont().draw(sb, "BEST: " + prefs.getInteger("score", 0), SuperSlide.WIDTH/8, SuperSlide.HEIGHT/2 - 125);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        font.dispose();
        for(Pillar pillar: pillars){
            pillar.dispose();
        }
        System.out.println("GamerOverState Disposed");
    }
    public void updatePillars(float dt){
        for(Pillar pillar: pillars) {
            pillar.update(dt);
        }
        if(pillar.getLeftpillarpos().y + pillar.getLeftpillar().getHeight() < 0) {
            pillar.reposition();
            if (CURRENTPILLARINDEX == 0) {
                CURRENTPILLARINDEX++;
            } else {
                CURRENTPILLARINDEX = 0;
            }
            pillar = pillars.get(CURRENTPILLARINDEX);
        }
    }
}
