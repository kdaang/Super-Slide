package com.superslide.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/**
 * Created by Kevin on 16/04/2017.
 */

public class Text {
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private BitmapFont font;
    public Text(int size, Color color){
        generator = new FreeTypeFontGenerator(Gdx.files.internal("alpha_echo.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        parameter.color = color;
        font = generator.generateFont(parameter);
        font.setUseIntegerPositions(false);
    }
    public BitmapFont getFont(){
        return font;
    }
    public void dispose(){
        generator.dispose();
        font.dispose();
    }
}
