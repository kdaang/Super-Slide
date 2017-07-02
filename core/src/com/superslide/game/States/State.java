package com.superslide.game.States;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import javax.annotation.Resources;

/**
 * Created by Kevin on 26/03/2017.
 */

public abstract class State {
    protected OrthographicCamera cam;
    protected GameStateManager gsm;

    public State(GameStateManager gsm){
        this.gsm = gsm;
        cam = new OrthographicCamera();
    }
    protected abstract void handleInput();
    public abstract void update(float dt);
    public abstract void render(SpriteBatch sb);
    public abstract void dispose();
}
