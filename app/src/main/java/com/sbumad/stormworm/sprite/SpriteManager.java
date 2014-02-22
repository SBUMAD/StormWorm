package com.sbumad.stormworm.sprite;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.sbumad.stormworm.game.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by John on 2/22/14.
 */
public class SpriteManager {
    HashMap<String, SpriteType> spriteTypes;
    ArrayList<Sprite> sprites;
    Bitmap background;
    Paint p;

    // leave a dimension 0 to maintain aspect ratio on a galaxy s4
    public SpriteManager(Bitmap background, int widthPercent, int heightPercent){
        this.background = background;
        if (widthPercent == 0){
            widthPercent = (int)(((float)background.getWidth() / (float)background.getHeight()) * (1920.0f / 1080.0f) * heightPercent + .5f);
        }
        if (heightPercent == 0){
            heightPercent = (int)(((float)background.getHeight() / (float)background.getWidth()) * (1080.0f / 1920.0f) * widthPercent + .5f);
        }
        this.background = Bitmap.createScaledBitmap(background, (int)((float)MainActivity.getScreenWidth() * ((float)widthPercent / 100.0f)), (int)((float)MainActivity.getScreenHeight() * ((float)heightPercent / 100.0f)), true);
        p = new Paint();
    }
    public void initSpriteType(String id, Bitmap image, int states, float statesPerSecond){
        spriteTypes.put(id, new SpriteType(id, image, states, statesPerSecond));
    }
    public void update(Canvas canvas, float transX, float transY){
        canvas.drawBitmap(background, transX, transY, p);
    }
}
