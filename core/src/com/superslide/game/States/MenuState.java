package com.superslide.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.superslide.game.SuperSlide;
import com.superslide.game.Sprites.Pillar;
import com.superslide.game.Sprites.Player;
import com.superslide.game.Sprites.Text;

/**
 * Created by Kevin on 26/03/2017.
 */

public class MenuState extends State{
    private int CURRENTPILLARINDEX;
    private Texture background;
    private Player player;
    //private Text font;
    private Array<Pillar> pillars;
    private Pillar pillar;
    public MenuState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, SuperSlide.WIDTH/2, SuperSlide.HEIGHT/2);
        background = new Texture("background2.png");
        pillars = new Array<Pillar>();
        for(int x = 0; x < 2; x++){
            pillars.add(new Pillar(x));
        }
        CURRENTPILLARINDEX = 0;
        pillar = pillars.get(CURRENTPILLARINDEX);
        player = new Player();
        //font = new Text(50, Color.RED);
    }

    @Override
    protected void handleInput(){
        if(Gdx.input.justTouched()){
            gsm.set(new PlayState(gsm, player));
        }
        if((player.getPosition().x < pillar.getPillarWidth())||
                (player.getPosition().x > (SuperSlide.WIDTH/2 - pillar.getPillarWidth()) - player.getPlayer().getWidth()/player.getPLAYERSCALE())){
            player.slide();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        updatePillars(dt);
        player.update(dt);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, 0 , 0, background.getWidth()/2, background.getHeight()/2);
        for(Pillar pillar: pillars){
            sb.draw(pillar.getLeftpillar(), pillar.getLeftpillarpos().x, pillar.getLeftpillarpos().y);
            sb.draw(pillar.getRightpillar(), pillar.getRightpillarpos().x, pillar.getRightpillarpos().y);
        }
        //font.getFont().draw(sb, "SUPER", SuperSlide.WIDTH/16, SuperSlide.HEIGHT/2 - 75);
        //font.getFont().draw(sb, "SLIDE", SuperSlide.WIDTH/16 + 20, SuperSlide.HEIGHT/2 - 125);
        sb.draw(player.getPlayer(), player.getPosition().x, SuperSlide.HEIGHT/4 - player.getPLAYERHEIGHT(),
                player.getPlayer().getWidth()/player.getPLAYERSCALE(), player.getPlayer().getHeight()/player.getPLAYERSCALE());
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        //font.dispose();
        for(Pillar pillar: pillars){
            pillar.dispose();
        }
        System.out.println("MenuState Disposed");
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
